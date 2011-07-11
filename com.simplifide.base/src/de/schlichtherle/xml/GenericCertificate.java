/*
 * GenericCertificate.java
 *
 * Created on 28. Januar 2005, 14:19
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

package de.schlichtherle.xml;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeSupport;
import java.beans.XMLEncoder;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;

/**
 * This non-visual JavaBean implements authentic runtime objects whose
 * integrity cannot be compromised without being detected.
 * The idea and the design of this class is inspired by both
 * {@link java.security.SignedObject} and
 * {@link java.security.cert.Certificate}.
 * <p>
 * More specifically, a <tt>GenericCertificate</tt> contains an XML string
 * encoded representation of an arbitrary object in the "encoded"
 * property and a Base64 immutable string representation of the object's
 * corresponding digital signature in the "signature" property.
 * The selection of this representation form and the design of this class
 * as a plain JavaBean allows its instances to be serialized using either
 * this package's {@link PersistenceService}, JDK's
 * {@link java.beans.XMLEncoder}, or the vanilla {@link ObjectOutputStream}.
 * <p>
 * For an object to be successfully digitally signed, it must support
 * serialisation via JDK's XMLEncoder, for which this package
 * provides the class PersistenceService.
 * This easy-to-use class allows you to provide custom
 * {@link java.beans.PersistenceDelegate} instances for the serialisation of any
 * classes which do not implement the JavaBean design pattern and are not
 * supported by XMLEncoder as a default.
 * <p>
 * Whenever an instance of this GenericCertificate class is created,
 * you can arbitrarily set and get its "encoded" and "signature" properties,
 * allowing you to provide even custom deserialisation methods other than this
 * class already provides via the aforementioned classes. However, once this
 * instance is used to either sign or verify another object it gets locked,
 * allowing subsequent read access to its properties only.
 * <p>
 * The underlying signing algorithm is designated by the Signature
 * object passed to the <tt>sign</tt> and the <tt>verify</tt> methods.
 * <p>
 * A typical usage for signing is the following:
 * <code><pre>
 * GenericCertificate cert = new GenericCertificate();
 * Signature signingEngine = Signature.getInstance(algorithm,
 *                                                 provider);
 * try {
 *     cert.sign(myObject, signingKey, signingEngine);
 * }
 * catch (PropertyVetoException signingVetoed) {
 *     // ...
 * }
 * catch (PersistenceServiceException serialisationFailed) {
 *     // ...
 * }
 * catch (InvalidKeyException invalidKey) {
 *     // ...
 * }
 * catch (SignatureException signingEngineBroken) {
 *     // ...
 * }
 * </pre></code>
 * A typical usage for verification is the following (having
 * received GenericCertificate <code>cert</code>):
 * <code><pre>
 * Signature verificationEngine =
 *     Signature.getInstance(algorithm, provider);
 * try {
 *     cert.verify(publicKey, verificationEngine));
 * }
 * catch (PropertyVetoException verificationVetoed) {
 *     // ...
 * }
 * catch (InvalidKeyException invalidKey) {
 *     // ...
 * }
 * catch (SignatureException verificationEngineBroken) {
 *     // ...
 * }
 * catch (GenericCertificateException integrityCompromised) {
 *     // ...
 * }
 * Object myObject = cert.getContent();
 * </pre></code>
 * Several points are worth noting:
 * <ul><li>
 * There is no need to initialize the signing or verification engine,
 * as it will be re-initialized inside the {@link #sign} and {@link #verify}
 * methods. Secondly, for verification to succeed, the specified
 * public key must be the public key corresponding to the private key
 * used to sign the GenericCertificate.</li>
 * <li>
 * In contrast to SignedObject, this class adds more security
 * as it is impossible to retrieve the signed object without verifying
 * the signature before. A SignedObject however could
 * be deserialised from a compromised file and the application developer
 * may erraticaly forget to call the {@link java.security.SignedObject#verify}
 * method before retrieving the signed object by calling
 * {@link java.security.SignedObject#getObject()}.</li>
 * <li>
 * More importantly, for flexibility reasons, the
 * sign() and verify() methods allow for
 * customized signature engines, which can implement signature
 * algorithms that are not installed formally as part of a crypto
 * provider. However, it is crucial that the programmer writing the
 * verifier code be aware what {@link java.security.Signature} engine is being
 * used, as its own implementation of the {@link Signature#verify} method
 * is invoked to verify a signature. In other words, a malicious
 * Signature engine may choose to always return true on
 * verification in an attempt to bypass a security check.</li>
 * <li>
 * The signature algorithm can be, among others, the NIST standard
 * DSA, using DSA and SHA-1.  The algorithm is specified using the
 * same convention as that for signatures. The DSA algorithm using the
 * SHA-1 message digest algorithm can be specified, for example, as
 * "SHA/DSA" or "SHA-1/DSA" (they are equivalent).  In the case of
 * RSA, there are multiple choices for the message digest algorithm,
 * so the signing algorithm could be specified as, for example,
 * "MD2/RSA", "MD5/RSA" or "SHA-1/RSA". The algorithm name must be
 * specified, as there is no default.</li>
 * <li>
 * The name of the Cryptography Package Provider is designated
 * by the Signature parameter to the sign() and 
 * verify() methods. If the provider is not specified,
 * the default provider is used. Each installation can be configured
 * to use a particular provider as default.</li>
 * <li>The property change listeners are <em>not</em> persistet when
 * using {@link ObjectOutputStream} or {@link XMLEncoder}.</li>
 * <li>{@link Object#equals(Object)} and {@link Object#hashCode()} are
 * <em>not</em> overridden by this class because different JVMs will produce
 * different literal encodings of the same object and we cannot rely on a proper
 * <tt>equals(...)</tt> implementation in the class of a signed object.</li>
 * </ul>
 * Potential applications of GenericCertificate include: 
 * <ul><li>
 * It can be used internally to any Java runtime as an unforgeable
 * authorization token -- one that can be passed around without the
 * fear that the token can be maliciously modified without being
 * detected.</li>
 * <li>
 * It can be used to sign and serialize data/object for storage outside
 * the Java runtime (e.g., storing critical access control data on
 * disk).</li>
 * <li>
 * Nested GenericCertificates can be used to construct a logical
 * sequence of signatures, resembling a chain of authorization and
 * delegation.</li>
 * </ul>
 * <p>
 * This class is designed to be thread-safe.
 * 
 * @see java.security.Signature
 * @see java.security.SignedObject
 * @see java.security.cert.Certificate
 * 
 * @author Christian Schlichtherle
 */
public final class GenericCertificate implements Serializable, XMLConstants {

    private static final String BASE64_CHARSET = "US-ASCII"; // NOI18N
    private static final String SIGNATURE_ENCODING = "US-ASCII/Base64"; // NOI18N
    
    /**
     * Holds value of property locked - cannot be serialised!!!
     */
    private transient boolean locked;

    /**
     * Holds value of property encoded.
     */
    private String encoded;

    /**
     * Holds value of property signature.
     */
    private String signature;

    /**
     * Holds value of property signatureAlgorithm.
     */
    private String signatureAlgorithm;

    /**
     * Holds value of property signatureEncoding.
     */
    private String signatureEncoding;
    
    /**
     * Utility field used by bound properties.
     */
    private transient PropertyChangeSupport propertyChangeSupport;

    /**
     * Utility field used by constrained properties.
     */
    private transient VetoableChangeSupport vetoableChangeSupport;
    
    /** Creates a new generic certificate. */
    public GenericCertificate() {
    }
    
    /**
     * Copy constructor for the given generic certificate.
     * Note that the new certificate is unlocked and does not have any
     * event listeners.
     */
    public GenericCertificate(final GenericCertificate cert) {
        try {
            setEncoded(cert.getEncoded());
            setSignature(cert.getSignature());
            setSignatureAlgorithm(cert.getSignatureAlgorithm());
            setSignatureEncoding(cert.getSignatureEncoding());
        }
        catch (PropertyVetoException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
    }
    
    /**
     * Encodes and signs the given <tt>content</tt> in this certificate and
     * locks it.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will throw a <tt>PropertyVetoException</tt> if this
     *     certificate is already locked, i.e. if it has been signed or
     *     verified before.</li>
     * <li>Because this method locks this certificate, a subsequent call to
     *     {@link #sign(Object, PrivateKey, Signature)} or
     *     {@link #verify(PublicKey, Signature)} is redundant
     *     and will throw a <tt>PropertyVetoException</tt>.
     *     Use {@link #isLocked()} to detect whether a
     *     generic certificate has been successfuly signed or verified before
     *     or call {@link #getContent()} and expect an 
     *     Exception to be thrown if it hasn't.</li>
     * <li>There is no way to unlock this certificate.
     *     Call the copy constructor of {@link GenericCertificate} if you
     *     need an unlocked copy of the certificate.</li>
     * </ul>
     * 
     * @param content The object to sign. This must either be a JavaBean or an
     *        instance of any other class which is supported by
     *        <tt>{@link PersistenceService}</tt>
     *        - maybe <tt>null</tt>.
     * @param signingKey The private key for signing
     *        - may <em>not</em> be <tt>null</tt>.
     * @param signingEngine The signature signing engine
     *        - may <em>not</em> be <tt>null</tt>.
     * 
     * @throws NullPointerException If the preconditions for the parameters
     *         do not hold.
     * @throws GenericCertificateIsLockedException If this certificate is
     *         already locked by signing or verifying it before.
     *         Note that this is actually a subclass of
     *         {@link PropertyVetoException}.
     * @throws PropertyVetoException If locking the certifificate (and thus
     *         signing the object) is vetoed by any listener.
     * @throws PersistenceServiceException If the object cannot be serialised.
     * @throws InvalidKeyException If the verification key is invalid.
     */
    public synchronized final void sign(
            final Object content,
            final PrivateKey signingKey,
            final Signature signingEngine)
    throws  NullPointerException,
            GenericCertificateIsLockedException,
            PropertyVetoException,
            PersistenceServiceException,
            InvalidKeyException {
        // Check parameters.
        if (signingKey == null)
            throw new NullPointerException("signingKey");
        if (signingEngine == null)
            throw new NullPointerException("signingEngine");

        // Check lock status.
        final PropertyChangeEvent evt = new PropertyChangeEvent(
                this, "locked", Boolean.valueOf(isLocked()), Boolean.TRUE); // NOI18N
        if (isLocked())
            throw new GenericCertificateIsLockedException(evt);

        // Notify vetoable listeners and give them a chance to veto.
        fireVetoableChange(evt);

        try {
            // Encode the object.
            final byte[] beo = PersistenceService.store2ByteArray(content);

            // Sign the byte encoded object.
            signingEngine.initSign(signingKey);
            signingEngine.update(beo);
            final byte[] b64es = Base64.encodeBase64(
                    signingEngine.sign()); // the base64 encoded signature
            final String signature = new String(
                    b64es, 0, b64es.length, BASE64_CHARSET);

            // Store results.
            setEncoded(new String(beo, XML_CHARSET));
            setSignature(signature);
            setSignatureAlgorithm(signingEngine.getAlgorithm());
            setSignatureEncoding(SIGNATURE_ENCODING); // NOI18N
        }
        catch (UnsupportedEncodingException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        catch (SignatureException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }
        
        // Lock this certificate and notify property change listeners.
        this.locked = true;
        firePropertyChange(evt);
    }
    
    /**
     * 
     * Verifies the digital signature of the encoded content in this
     * certificate and locks it.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will throw a <tt>PropertyVetoException</tt> if this
     *     certificate is already locked, i.e. if it has been signed or
     *     verified before.</li>
     * <li>Because this method locks this certificate, a subsequent call to
     *     {@link #sign(Object, PrivateKey, Signature)} or
     *     {@link #verify(PublicKey, Signature)} is redundant
     *     and will throw a <tt>PropertyVetoException</tt>.
     *     Use {@link #isLocked()} to detect whether a
     *     generic certificate has been successfuly signed or verified before
     *     or call {@link #getContent()} and expect an 
     *     Exception to be thrown if it hasn't.</li>
     * <li>There is no way to unlock this certificate.
     *     Call the copy constructor of {@link GenericCertificate} if you
     *     need an unlocked copy of the certificate.</li>
     * </ul>
     * 
     * @param verificationKey The public key for verification
     *        - may <em>not</em> be <tt>null</tt>.
     * @param verificationEngine The signature verification engine
     *        - may <em>not</em> be <tt>null</tt>.
     * 
     * @throws NullPointerException If the preconditions for the parameters
     *         do not hold.
     * @throws GenericCertificateIsLockedException If this certificate is
     *         already locked by signing or verifying it before.
     *         Note that this is actually a subclass of
     *         {@link PropertyVetoException}.
     * @throws PropertyVetoException If locking the certifificate (and thus
     *         verifying the object) is vetoed by any listener.
     * @throws InvalidKeyException If the verification key is invalid.
     * @throws SignatureException If signature verification failed.
     * @throws GenericCertificateIntegrityException If the integrity of this
     *         certificate has been compromised.
     */
    public synchronized final void verify(
            final PublicKey verificationKey,
            final Signature verificationEngine)
    throws  NullPointerException,
            GenericCertificateIsLockedException,
            PropertyVetoException,
            InvalidKeyException,
            SignatureException,
            GenericCertificateIntegrityException {
        // Check parameters.
        if (verificationKey == null)
            throw new NullPointerException("verificationKey");
        if (verificationEngine == null)
            throw new NullPointerException("verificationEngine");

        // Check lock status.
        final PropertyChangeEvent evt = new PropertyChangeEvent(
                this, "locked", Boolean.valueOf(isLocked()), Boolean.TRUE); // NOI18N
        if (isLocked())
            throw new GenericCertificateIsLockedException(evt);

        // Notify vetoable listeners and give them a chance to veto.
        fireVetoableChange(evt);

        try {
            // Init the byte encoded object.
            final byte[] beo = getEncoded().getBytes(XML_CHARSET);

            // Verify the byte encoded object.
            verificationEngine.initVerify(verificationKey);
            verificationEngine.update(beo);
            final byte[] b64ds = Base64.decodeBase64(
                    getSignature().getBytes(BASE64_CHARSET));
            if (!verificationEngine.verify(b64ds))
                throw new GenericCertificateIntegrityException();

            // Reset signature parameters.
            setSignatureAlgorithm(verificationEngine.getAlgorithm());
            setSignatureEncoding(SIGNATURE_ENCODING);
        }
        catch (UnsupportedEncodingException cannotHappen) {
            throw new AssertionError(cannotHappen);
        }

        // Lock this certificate and notify property change listeners.
        this.locked = true;
        firePropertyChange(evt);
    }
    
    /**
     * Returns the "locked" property of this generic certificate.
     *         If <tt>true</tt>, an object was successfully signed or verified
     *         before and a clone can be safely retrieved using
     *         <tt>getContent()</tt>.
     */
    public final boolean isLocked() {
        return this.locked;
    }

    /**
     * Returns a clone of the certificate's content as it was signed or
     * verified before.
     * You should save the returned object for later use as each call
     * to this method is pretty expensive in terms of runtime and
     * memory. This method may return <tt>null</tt> if this has been
     * signed before.
     *
     * @throws GenericCertificateNotLockedException If no content has been
     *         signed or verified before.
     *         Note that this is ultimately a {@link RuntimeException}.
     * @throws PersistenceServiceException If the signed object cannot get
     *         reinstantiated from its XML representation for some reason.
     *         This may happen for example if the signed object was created
     *         by a more recent version of its class which contains additional
     *         properties which are not supported by earlier versions.
     */
    public Object getContent()
    throws  GenericCertificateNotLockedException,
            PersistenceServiceException {
        if (!isLocked())
            throw new GenericCertificateNotLockedException();

        return PersistenceService.load(getEncoded());
    }

    /**
     * Getter for the property <tt>encoded</tt>.
     * The default is <tt>null</tt>.
     *
     * @return Value of property encoded.
     */
    public final String getEncoded()   {
        return this.encoded;
    }

    /**
     * Setter for the bound property <tt>encoded</tt>.
     *
     * @param encoded The new encoded representation of the signed object
     *        - may be <tt>null</tt>.
     *
     * @throws GenericCertificateIsLockedException If this certificate is
     *         already locked by signing or verifying it before.
     *         Note that this is actually a subclass of
     *         {@link PropertyVetoException}.
     */
    public synchronized void setEncoded(String encoded)
    throws GenericCertificateIsLockedException {
        // Check parameters.
        if (encoded == this.encoded)
            return;
        
        // Check lock status.
        final PropertyChangeEvent evt = new PropertyChangeEvent(
                this, "encoded", getEncoded(), encoded); // NOI18N
        if (isLocked())
            throw new GenericCertificateIsLockedException(evt);
        
        //vetoableChangeSupport.fireVetoableChange(evt); // Incompatible to sign!
        this.encoded = encoded;
        firePropertyChange(evt);
    }

    /**
     * Getter for the property <tt>signature</tt>.
     * The default is <tt>null</tt>.
     *
     * @return Value of property signature.
     */
    public final String getSignature() {
        return this.signature;
    }

    /**
     * Setter for the bound property <tt>signature</tt>.
     *
     * @param signature The signature encoded as a string
     *        - may be <tt>null</tt>.
     *
     * @throws GenericCertificateIsLockedException If this certificate is
     *         already locked by signing or verifying it before.
     *         Note that this is actually a subclass of
     *         {@link PropertyVetoException}.
     */
    public synchronized void setSignature(String signature)
    throws GenericCertificateIsLockedException {
        // Check parameters.
        if (signature == this.signature)
            return;
        
        // Check lock status.
        final PropertyChangeEvent evt = new PropertyChangeEvent(
                this, "signature", getSignature(), signature); // NOI18N
        if (isLocked())
            throw new GenericCertificateIsLockedException(evt);
        
        //vetoableChangeSupport.fireVetoableChange(evt); // Incompatible to sign!
        this.signature = signature;
        firePropertyChange(evt);
    }

    /**
     * Getter for the property <tt>signatureAlgorithm</tt>.
     * The default is <tt>null</tt>.
     *
     * @return The signature algorithm.
     */
    public final String getSignatureAlgorithm()  {
        return this.signatureAlgorithm;
    }

    /**
     * Setter for the bound property <tt>signatureAlgorithm</tt>.
     *
     * @param signatureAlgorithm The string identifying the signature algorithm
     *        - may be <tt>null</tt>.
     *
     * @throws GenericCertificateIsLockedException If this certificate is
     *         already locked by signing or verifying it before.
     *         Note that this is actually a subclass of
     *         {@link PropertyVetoException}.
     */
    public synchronized void setSignatureAlgorithm(String signatureAlgorithm)
    throws GenericCertificateIsLockedException {
        // Check parameters.
        if (signatureAlgorithm == this.signatureAlgorithm)
            return;
        
        // Check lock status.
        final PropertyChangeEvent evt = new PropertyChangeEvent(
                this, "signatureAlgorithm", getSignatureAlgorithm(), signatureAlgorithm); // NOI18N
        if (isLocked())
            throw new GenericCertificateIsLockedException(evt);
        
        //vetoableChangeSupport.fireVetoableChange(evt); // Incompatible to sign!
        this.signatureAlgorithm = signatureAlgorithm;
        firePropertyChange (evt);
    }

    /**
     * Getter for the property <tt>signatureEncoding</tt>.
     * The default is <tt>null</tt>.
     *
     * @return The character encoding of the signature string.
     */
    public final String getSignatureEncoding() {
        return signatureEncoding;
    }
    
    /**
     * Setter for the bound property <tt>signatureEncoding</tt>.
     *
     * @param signatureEncoding The string identifying the signature encoding
     *        - may be <tt>null</tt>.
     *
     * @throws GenericCertificateIsLockedException If this certificate is
     *         already locked by signing or verifying it before.
     *         Note that this is actually a subclass of
     *         {@link PropertyVetoException}.
     *
     * @deprecated Currently ignored by {@link #verify}.
     *             Only provided to cause {@link XMLEncoder} to encode this
     *             property for upwards compatibility.
     */
    public synchronized void setSignatureEncoding(String signatureEncoding)
    throws GenericCertificateIsLockedException {
        // Check parameters.
        if (signatureEncoding == this.signatureEncoding)
            return;

        // Check lock status.
        final PropertyChangeEvent evt = new PropertyChangeEvent(
                this, "signatureEncoding", getSignatureEncoding(), signatureEncoding); // NOI18N
        if (isLocked())
            throw new GenericCertificateIsLockedException(evt);
        
        //vetoableChangeSupport.fireVetoableChange(evt); // Incompatible to sign!
        this.signatureEncoding = signatureEncoding;
        firePropertyChange(evt);
    }

    //
    // Property handling methods.
    //

    /**
     * Adds a VetoableChangeListener to the listener list.
     *
     * @param l The listener to add.
     */
    public final synchronized void addVetoableChangeListener(java.beans.VetoableChangeListener l) {
        if (vetoableChangeSupport == null)
            vetoableChangeSupport = new VetoableChangeSupport(this);
        vetoableChangeSupport.addVetoableChangeListener(l);
    }

    /**
     * Removes a VetoableChangeListener from the listener list.
     *
     * @param l The listener to remove.
     */
    public final void removeVetoableChangeListener(java.beans.VetoableChangeListener l) {
        if (vetoableChangeSupport == null)
            return;
        vetoableChangeSupport.removeVetoableChangeListener(l);
    }
    
    protected final void fireVetoableChange(PropertyChangeEvent evt)
    throws PropertyVetoException {
        if (vetoableChangeSupport == null)
            return;
        vetoableChangeSupport.fireVetoableChange(evt);
    }
    
    /**
     * Adds a PropertyChangeListener to the listener list.
     *
     * @param l The listener to add.
     */
    public final synchronized void addPropertyChangeListener(PropertyChangeListener l) {
        if (propertyChangeSupport == null)
            propertyChangeSupport = new PropertyChangeSupport(this);
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    /**
     * Removes a PropertyChangeListener from the listener list.
     *
     * @param l The listener to remove.
     */
    public final void removePropertyChangeListener(PropertyChangeListener l) {
        if (propertyChangeSupport == null)
            return;
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    protected final void firePropertyChange(PropertyChangeEvent evt) {
        if (propertyChangeSupport == null)
            return;
        propertyChangeSupport.firePropertyChange(evt);
    }
}
