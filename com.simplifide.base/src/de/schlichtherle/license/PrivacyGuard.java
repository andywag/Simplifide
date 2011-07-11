/*
 * PrivacyGuard.java
 *
 * Created on 17. August 2005, 23:01
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import de.schlichtherle.util.ObfuscatedString;
import de.schlichtherle.xml.GenericCertificate;
import de.schlichtherle.xml.PersistenceService;
import de.schlichtherle.xml.PersistenceServiceException;

/**
 * This class provides encoding and encryption services to provide long time
 * persistence for {@link GenericCertificate}s and protect the privacy of its
 * data.
 * <p>
 * This class is <em>not</em> thread safe.
 *
 * @author Christian Schlichtherle
 */
public class PrivacyGuard {

    private static final String PBE_WITH_MD5_AND_DES = new ObfuscatedString(new long[] {
        0x27B2E8783E47F1ABL, 0x45CF8AD4390DC9D8L, 0xAB320350966BC9BFL
    }).toString(); /* => "PBEWithMD5AndDES" */

    private CipherParam param; // initialized by setCipherParam() - should be accessed via getCipherParam() only!

    //
    // Data computed and cached from the cipher configuration parameters.
    //

    private Cipher cipher;
    private SecretKey key;
    private AlgorithmParameterSpec algoParamSpec;

    /**
     * Creates a new Privacy Guard.
     * <p>
     * <b>Warning:</b> The guard created by this constructor is <em>not</em>
     * valid and cannot be used unless {@link #setCipherParam(CipherParam)}
     * is called!
     */
    protected PrivacyGuard() {
    }

    /**
     * Creates a new Privacy Guard.
     *
     * @param param The cipher configuration parameters
     *        - may <em>not</em> be <tt>null</tt>.
     */
    public PrivacyGuard(CipherParam param) {
        setCipherParam(param);
    }

    /**
     * Returns the cipher configuration parameters.
     */
    public CipherParam getCipherParam() {
        return param;
    }

    /**
     * Sets the cipher configuration parameters.
     * Calling this method resets the guard as if it had been
     * newly created.
     * Some plausibility checks are applied to the given parameter object
     * to ensure that it adheres to the contract of the parameter interfaces.
     *
     * @param param The cipher configuration parameters
     *        - may <em>not</em> be <tt>null</tt>.
     *
     * @throws NullPointerException If the given parameter object does not
     *         obey the contract of its interface due to a <tt>null</tt>
     *         pointer.
     * @throws IllegalPasswordException If any password in the parameter object
     *         does not comply to the current policy.
     */
    public /*synchronized*/ void setCipherParam(CipherParam param)
    throws  NullPointerException,
            IllegalPasswordException {
        // Check parameters to implement fail-fast behaviour.
        /*if (param == null)
            throw new NullPointerException(LicenseNotary.PARAM);*/
        Policy.getCurrent().checkPwd(param.getKeyPwd());
        
        this.param = param;
        cipher = null;
        key = null;
        algoParamSpec = null;
    }

    /**
     * Encodes, compresses and encrypts the given license certificate
     * and returns the result as a license key.
     * Please note that this method does not sign the certificate.
     *
     * @param certificate The license certificate
     *        - may <em>not</em> be <tt>null</tt>.
     *
     * @return The license key
     *         - <tt>null</tt> is never returned.
     *
     * @throws Exception An instance of a subclass of this class for various
     *         reasons.
     *         Note that you should always use
     *         {@link Throwable#getLocalizedMessage()} to get a (possibly
     *         localized) meaningful detail message.
     */
    public /*synchronized*/ byte[] cert2key(final GenericCertificate certificate)
    throws Exception {
        // Encode the certificate and store it to a file.
        final ByteArrayOutputStream keyOut = new ByteArrayOutputStream();
        final OutputStream out = new GZIPOutputStream(
                new CipherOutputStream(
                    keyOut,
                    getCipher4Encryption()));
        try {
            PersistenceService.store(certificate, out);
        }
        catch (PersistenceServiceException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        return keyOut.toByteArray();
    }

    /**
     * Decrypts, decompresses and decodes the given license key
     * and returns the result as a license certificate.
     * Please note that this method does not verify the certificate.
     *
     * @param key The license key to process
     *        - may <em>not</em> be <tt>null</tt>.
     *
     * @return The license certificate
     *         - <tt>null</tt> is never returned.
     *
     * @throws Exception An instance of a subclass of this class for various
     *         reasons.
     *         Note that you should always use
     *         {@link Throwable#getLocalizedMessage()} to get a (possibly
     *         localized) meaningful detail message.
     */
    public /*synchronized*/ GenericCertificate key2cert(final byte[] key)
    throws Exception {
        final InputStream in = new GZIPInputStream(
                new ByteArrayInputStream(
                    getCipher4Decryption().doFinal(key)));
        final GenericCertificate certificate;
        try {
            certificate = (GenericCertificate) PersistenceService.load(in);
        }
        finally {
            try { in.close(); }
            catch (IOException weDontCare) { }
        }

        return certificate;
    }

    /**
     * Returns a cipher object which is initialised for encryption
     * - <tt>null</tt> is never returned.
     *
     * @deprecated <b>Experimental:</b> Methods marked with this note have
     *             been tested to be functional but may change or disappear
     *             at will in one of the next releases because they are still
     *             a topic for research on extended functionality.
     *             Most likely the methods will prevail however and this note
     *             will just vanish, so you may use them with a certain risk.
     */
    protected /*synchronized*/ Cipher getCipher4Encryption() {
        Cipher cipher = getCipher();
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, algoParamSpec);
        }
        catch (InvalidKeyException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        catch (InvalidAlgorithmParameterException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        
        return cipher;
    }

    /**
     * Returns a cipher object which is initialised for decryption
     * - <tt>null</tt> is never returned.
     *
     * @deprecated <b>Experimental:</b> Methods marked with this note have
     *             been tested to be functional but may change or disappear
     *             at will in one of the next releases because they are still
     *             a topic for research on extended functionality.
     *             Most likely the methods will prevail however and this note
     *             will just vanish, so you may use them with a certain risk.
     */
    protected /*synchronized*/ Cipher getCipher4Decryption() {
        Cipher cipher = getCipher();
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, algoParamSpec);
        }
        catch (InvalidKeyException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        catch (InvalidAlgorithmParameterException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        
        return cipher;
    }

    /**
     * Returns a cipher object which needs to be configured for encryption or
     * decryption
     * - <tt>null</tt> is never returned.
     *
     * @deprecated <b>Experimental:</b> Methods marked with this note have
     *             been tested to be functional but may change or disappear
     *             at will in one of the next releases because they are still
     *             a topic for research on extended functionality.
     *             Most likely the methods will prevail however and this note
     *             will just vanish, so you may use them with a certain risk.
     */
    protected /*synchronized*/ Cipher getCipher() {
        if (cipher != null)
            return cipher;
        
        algoParamSpec = new PBEParameterSpec(
            new byte[] {
                (byte)0xce, (byte)0xfb, (byte)0xde, (byte)0xac,
                (byte)0x05, (byte)0x02, (byte)0x19, (byte)0x71
            },
            2005);

        try {
            KeySpec keySpec = new PBEKeySpec(getCipherParam().getKeyPwd().toCharArray());
            SecretKeyFactory keyFac = SecretKeyFactory.getInstance(PBE_WITH_MD5_AND_DES);
            key = keyFac.generateSecret(keySpec);

            cipher = Cipher.getInstance(PBE_WITH_MD5_AND_DES);
        }
        catch (NoSuchAlgorithmException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        catch (InvalidKeySpecException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        catch (NoSuchPaddingException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }

        return cipher;
    }
}
