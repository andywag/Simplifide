/*
 * LicenseNotary.java
 *
 * Created on 27. Januar 2005, 02:53
 */
/*
 * Copyright 2005 Schlichtherle IT Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.schlichtherle.license;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import de.schlichtherle.util.ObfuscatedString;
import de.schlichtherle.xml.GenericCertificate;

/**
 * This notary knows how to sign and verify a {@link GenericCertificate}.
 * <p>
 * This class is <em>not</em> thread safe.
 *
 * @author Christian Schlichtherle
 */
public class LicenseNotary {
    
    /** The buffer size for I/O. */
    private static final int BUFSIZE = 5 * 1024;

    static final String PARAM = new ObfuscatedString(new long[] {
        0x9462FFDE0183752L, 0xE2A34A222A24DB14L
    }).toString(); /* => "param" */

    private static final String ALIAS = new ObfuscatedString(new long[] {
        0xF7122BB1103EE24L, 0x5D073BF77D50CE8AL
    }).toString(); /* => "alias" */

    private static final String EXC_NO_KEY_PWD = new ObfuscatedString(new long[] {
        0x9BEEC1A930D89BC1L, 0x314F8BFE96B1D7BL, 0x7D41D459E6191D0AL
    }).toString(); /* => "exc.noKeyPwd" */

    private static final String EXC_NO_KEY_ENTRY = new ObfuscatedString(new long[] {
        0xECC3EE809CC45994L, 0x395EC0314F8227A1L, 0x90B1DBA3D701F0FBL
    }).toString(); /* => "exc.noKeyEntry" */

    private static final String EXC_PRIVATE_KEY_OR_PWD_IS_NOT_ALLOWED = new ObfuscatedString(new long[] {
        0xD6E9FE0BD39F8075L, 0x351D278C14FABB1AL, 0xD64A9C9BD412AB10L,
        0x1AEB0F657DB66448L, 0x41EE587D2CD73A1AL
    }).toString(); /* => "exc.privateKeyOrPwdIsNotAllowed" */

    private static final String EXC_NO_CERTIFICATE_ENTRY = new ObfuscatedString(new long[] {
        0xCA437064C1D0C41EL, 0xDDBBA0FF1F17FC35L, 0x5D2CD0D970444C3DL,
        0xF94EAAC3F634D04CL
    }).toString(); /* => "exc.noCertificateEntry" */

    private static final String SHA1_WITH_DSA = new ObfuscatedString(new long[] {
        0xEB0CFFD676FD2839L, 0x176DF514D5A0ED59L, 0xBFE1DE24AEF8E9B0L
    }).toString(); /* => "SHA1withDSA" */

    private static final String JKS = new ObfuscatedString(new long[] {
        0xA97AF8FB6356CB08L, 0x20E47C2995D2FE7AL
    }).toString(); /* => "JKS" */

    private KeyStoreParam param; // init by setKeyStoreParam() - should be accessed via getKeyStoreParam() only!

    //
    // Data computed and cached from the keyStore configuration parameters.
    //

    private KeyStore keyStore; // init by getKeyStore()
    private PrivateKey privateKey; // lazy initialised by getPrivateKey()
    private PublicKey  publicKey;  // lazy initialised by getPublicKey()

    /**
     * Creates a new License Notary.
     * <p>
     * <b>Warning:</b> The notary created by this constructor is <em>not</em>
     * valid and cannot be used unless {@link #setKeyStoreParam(KeyStoreParam)}
     * is called!
     */
    protected LicenseNotary() {
    }

    /**
     * 
     * Creates a new License Notary.
     * 
     * @param param The keyStore configuration parameters
     *        - may <em>not</em> be <tt>null</tt>.
     * 
     * @throws NullPointerException If the given parameter object does not
     *         obey the contract of its interface due to a <tt>null</tt>
     *         pointer.
     * @throws IllegalPasswordException If any password in the parameter object
     *         does not comply to the current policy.
     */
    public LicenseNotary(final KeyStoreParam param)
    throws  NullPointerException,
            IllegalPasswordException {
        setKeyStoreParam(param);
    }

    /**
     * Returns the keyStore configuration parameters.
     */
    public KeyStoreParam getKeyStoreParam() {
        return param;
    }

    /**
     * Sets the keyStore configuration parameters.
     * Calling this method resets the notary as if it had just been created.
     * 
     * @param param The keyStore configuration parameters
     *        - may <em>not</em> be <tt>null</tt>.
     * 
     * @throws NullPointerException If the given parameter object does not
     *         obey the contract of its interface due to a <tt>null</tt>
     *         pointer.
     * @throws IllegalPasswordException If any password in the parameter object
     *         does not comply to the current policy.
     */
    public /*synchronized*/ void setKeyStoreParam(final KeyStoreParam param)
    throws  NullPointerException,
            IllegalPasswordException {
        // Check parameters to implement fail-fast behaviour and enforce
        // a reasonably good security level.
        /*if (param == null)
            throw new NullPointerException(PARAM);
        if (param.getAlias() == null)
            throw new NullPointerException(ALIAS);
            */
        final Policy policy = Policy.getCurrent();
        final String storePwd = param.getStorePwd();
        policy.checkPwd(storePwd);
        final String keyPwd = param.getKeyPwd();
        if (keyPwd != null)
            policy.checkPwd(keyPwd);

        this.param = param;
        keyStore = null;
        privateKey = null;
        publicKey = null;
    }

    /**
     * Encodes and signs the given <tt>content</tt> and returns a locked
     * generic certificate holding the encoded content and its digital
     * signature.
     * <p>
     * Please note the following:
     * <ul>
     * <li>Because this method locks the certificate, a subsequent call to
     *     {@link #sign(GenericCertificate, Object)} or
     *     {@link #verify(GenericCertificate)} is redundant
     *     and will throw a <tt>PropertyVetoException</tt>.
     *     Use {@link GenericCertificate#isLocked()} to detect whether a
     *     generic certificate has been successfuly signed or verified before
     *     or call {@link GenericCertificate#getContent()} and expect an 
     *     Exception to be thrown if it hasn't.</li>
     * <li>There is no way to unlock the returned certificate.
     *     Call the copy constructor of {@link GenericCertificate} if you
     *     need an unlocked copy of the certificate.</li>
     * </ul>
     *
     * @param content The object to sign. This must either be a JavaBean or an
     *        instance of any other class which is supported by
     *        <tt>{@link de.schlichtherle.xml.PersistenceService}</tt>
     *        - maybe <tt>null</tt>.
     *
     * @return A locked generic certificate holding the encoded content and
     *         its digital signature.
     *
     * @throws Exception A subclass of this class may be thrown for various
     *         reasons.
     */
    public /*synchronized*/ GenericCertificate sign(Object content)
    throws Exception {
        GenericCertificate cert = new GenericCertificate();
        sign(cert, content);
        return cert;
    }

    /**
     * Encodes and signs the given <tt>content</tt> in the given
     * <tt>certificate</tt> and locks it.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will throw a <tt>PropertyVetoException</tt> if the
     *     certificate is already locked, i.e. if it has been signed or
     *     verified before.</li>
     * <li>Because this method locks the certificate, a subsequent call to
     *     {@link #sign(GenericCertificate, Object)} or
     *     {@link #verify(GenericCertificate)} is redundant
     *     and will throw a <tt>PropertyVetoException</tt>.
     *     Use {@link GenericCertificate#isLocked()} to detect whether a
     *     generic certificate has been successfuly signed or verified before
     *     or call {@link GenericCertificate#getContent()} and expect an 
     *     Exception to be thrown if it hasn't.</li>
     * <li>There is no way to unlock the certificate.
     *     Call the copy constructor of {@link GenericCertificate} if you
     *     need an unlocked copy of the certificate.</li>
     * </ul>
     *
     * @param certificate The generic certificate used to hold the encoded
     *        content and its digital signature.
     * @param content The object to sign. This must either be a JavaBean or an
     *        instance of any other class which is supported by
     *        <tt>{@link de.schlichtherle.xml.PersistenceService}</tt>
     *        - maybe <tt>null</tt>.
     *
     * @throws Exception A subclass of this class may be thrown for various
     *         reasons.
     *
     * @deprecated This method exists for testing purposes only and
     *             you should not normally need it.
     *             Use {@link #sign(Object)} instead.
     */
    public /*synchronized*/ void sign(GenericCertificate certificate, Object content)
    throws Exception {
        certificate.sign(content, getPrivateKey(), getSignatureEngine());
    }

    /** 
     * Verifies the digital signature of the encoded content in the given
     * <tt>certificate</tt> and locks it.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will throw a <tt>PropertyVetoException</tt> if the
     *     certificate is already locked, i.e. if it has been signed or
     *     verified before.</li>
     * <li>Because this method locks the certificate, a subsequent call to
     *     {@link #sign(GenericCertificate, Object)} or
     *     {@link #verify(GenericCertificate)} is redundant
     *     and will throw a <tt>PropertyVetoException</tt>.
     *     Use {@link GenericCertificate#isLocked()} to detect whether a
     *     generic certificate has been successfuly signed or verified before
     *     or call {@link GenericCertificate#getContent()} and expect an 
     *     Exception to be thrown if it hasn't.</li>
     * <li>There is no way to unlock the certificate.
     *     Call the copy constructor of {@link GenericCertificate} if you
     *     need an unlocked copy of the certificate.</li>
     * </ul>
     *
     * @param certificate The generic certificate to verify
     *        - may <em>not</em> be <tt>null</tt>.
     *
     * @throws Exception A subclass of this class may be thrown for various
     *         reasons.
     */
    public /*synchronized*/ void verify(GenericCertificate certificate)
    throws Exception {
        certificate.verify(getPublicKey(), getSignatureEngine());
    }

    /**
     * Returns the private key from the keyStore.
     * 
     * @throws LicenseNotaryException If the parameters used to access the
     *         corresponding key store are insufficient or incorrect.
     *         Note that you should always use
     *         {@link Throwable#getLocalizedMessage()} to get a (possibly
     *         localized) meaningful detail message.
     * @throws IOException If there is an I/O or format problem with the
     *         keyStore data.
     * @throws CertificateException If any of the certificates in the
     *         keyStore could not be loaded.
     * @throws NoSuchAlgorithmException If the algorithm used to check
     *         the integrity of the keyStore cannot be found.
     * @throws UnrecoverableKeyException If the key cannot get recovered
     *         (e.g. the given password is wrong).
     *
     * @deprecated <b>Experimental:</b> Methods marked with this note have
     *             been tested to be functional but may change or disappear
     *             at will in one of the next releases because they are still
     *             a topic for research on extended functionality.
     *             Most likely the methods will prevail however and this note
     *             will just vanish, so you may use them with a certain risk.
     */
    protected /*synchronized*/ PrivateKey getPrivateKey()
    throws  LicenseNotaryException,
            IOException,
            CertificateException,
            NoSuchAlgorithmException,
            UnrecoverableKeyException {
        if (privateKey == null) {
            final KeyStoreParam param = getKeyStoreParam();
            final String keyPwd = param.getKeyPwd();
            final String alias = param.getAlias();
            if (keyPwd == null)
                throw new LicenseNotaryException(EXC_NO_KEY_PWD, alias);
            final KeyStore keystore = getKeyStore();
            try {
                privateKey = (PrivateKey) keystore.getKey(
                        alias, keyPwd.toCharArray());
            }
            catch (KeyStoreException keystoreIsAlreadyLoaded) {
                throw new AssertionError(keystoreIsAlreadyLoaded);
            }
            if (privateKey == null)
                throw new LicenseNotaryException(EXC_NO_KEY_ENTRY, alias);
        }

        return privateKey;
    }

    /**
     * Returns the public key from the keyStore
     * 
     * @throws LicenseNotaryException If the parameters used to access the
     *         corresponding key store are insufficient or incorrect.
     *         Note that you should always use
     *         {@link Throwable#getLocalizedMessage()} to get a (possibly
     *         localized) meaningful detail message.
     * @throws IOException If there is an I/O or format problem with the
     *         keyStore data.
     * @throws CertificateException If any of the certificates in the
     *         keyStore could not be loaded.
     * @throws NoSuchAlgorithmException If the algorithm used to check
     *         the integrity of the keyStore cannot be found.
     *
     * @deprecated <b>Experimental:</b> Methods marked with this note have
     *             been tested to be functional but may change or disappear
     *             at will in one of the next releases because they are still
     *             a topic for research on extended functionality.
     *             Most likely the methods will prevail however and this note
     *             will just vanish, so you may use them with a certain risk.
     */
    protected /*synchronized*/ PublicKey getPublicKey()
    throws  LicenseNotaryException,
            IOException,
            CertificateException,
            NoSuchAlgorithmException {
        if (publicKey == null) {
            final String alias = getKeyStoreParam().getAlias();
            final KeyStore keystore = getKeyStore();
            try {
                if ((getKeyStoreParam().getKeyPwd() != null)
                        != keystore.isKeyEntry(alias))
                    throw new LicenseNotaryException(
                            EXC_PRIVATE_KEY_OR_PWD_IS_NOT_ALLOWED, alias);
                final Certificate cert = keystore.getCertificate(alias);
                if (cert == null)
                    throw new LicenseNotaryException(
                            EXC_NO_CERTIFICATE_ENTRY, alias);
                publicKey = cert.getPublicKey();
            }
            catch (KeyStoreException keystoreIsAlreadyLoaded) {
                throw new AssertionError(keystoreIsAlreadyLoaded);
            }
        }

        return publicKey;
    }

    /**
     * Returns a valid signature engine to be used for signing and verifying
     * a {@link GenericCertificate} - <tt>null</tt> is never returned.
     *
     * @deprecated <b>Experimental:</b> Methods marked with this note have
     *             been tested to be functional but may change or disappear
     *             at will in one of the next releases because they are still
     *             a topic for research on extended functionality.
     *             Most likely the methods will prevail however and this note
     *             will just vanish, so you may use them with a certain risk.
     */
    protected /*synchronized*/ Signature getSignatureEngine() {
        try {
            return Signature.getInstance(SHA1_WITH_DSA);
        }
        catch (NoSuchAlgorithmException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
    }

    /**
     * Returns a loaded/initialized keyStore.
     * 
     * @throws IOException If there is an I/O or format problem with the
     *         keyStore data.
     * @throws CertificateException If any of the certificates in the
     *         keyStore could not be loaded.
     * @throws NoSuchAlgorithmException If the algorithm used to check
     *         the integrity of the keyStore cannot be found.
     *
     * @deprecated <b>Experimental:</b> Methods marked with this note have
     *             been tested to be functional but may change or disappear
     *             at will in one of the next releases because they are still
     *             a topic for research on extended functionality.
     *             Most likely the methods will prevail however and this note
     *             will just vanish, so you may use them with a certain risk.
     */
    protected /*synchronized*/ KeyStore getKeyStore()
    throws  IOException,
            CertificateException,
            NoSuchAlgorithmException {
        if (keyStore != null)
            return keyStore;

        InputStream in = null;
        try {
            keyStore = KeyStore.getInstance(JKS);
            in = new BufferedInputStream(param.getStream(), BUFSIZE);
            keyStore.load(in, getKeyStoreParam().getStorePwd().toCharArray());
        }
        catch (KeyStoreException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        finally {
            try { in.close(); } // May throw NullPointerException!
            catch (Exception weDontCare) { }
        }
        
        return keyStore;
    }
}
