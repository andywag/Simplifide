/*
 * LicenseContent.java
 *
 * Created on 27. Januar 2005, 02:37
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

import java.beans.DefaultPersistenceDelegate;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.XMLEncoder;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import de.schlichtherle.xml.PersistenceService;

/**
 * This non-visual JavaBean represents the content of an application license.
 * This encompasses information about which license subjects may be licensed by
 * which kind of license consumers and their quantity (per kind and a maximum
 * total). In addition, an issue time stamp and optional information about
 * the license holder is contained.
 * <p>
 * This class is <em>not</em> trusted by the license notary.
 * As a result, the license manager validates and verifies the state of this
 * object itself whenever necessary.
 * <p>
 * In general, all properties may be <tt>null</tt> which indicates that this
 * information is not (yet) available and should be ignored.
 * Note however that validation may fail if certain properties are not
 * available.
 * <p>
 * Subclasses are encouraged to use the <tt>firePropertyChange</tt> methods
 * to notify all listeners of all property events.
 * Subclasses <em>must</em> also implement the JavaBean pattern in order to
 * be usable.
 * <p>
 * Note that the property change listeners are <em>not</em> persistet when
 * using {@link ObjectOutputStream} or {@link XMLEncoder}.
 *
 * @see LicenseNotary
 * @author Christian Schlichtherle
 */
public class LicenseContent implements Serializable, Cloneable {

    static {
        // With a little help of a persistence delegate we can even make
        // X.500 Principals persistent.
        PersistenceService.setPersistenceDelegate(
                X500Principal.class,
                new DefaultPersistenceDelegate(new String[] { "name" })); // NOI18N
    }

    private X500Principal holder;
    private X500Principal issuer;
    private String subject;
    private Date issued;
    private Date notBefore;
    private Date notAfter;
    private String consumerType;
    private int consumerAmount = 1;
    private String info;
    private Object extra;

    /**
     * Utility field used by bound properties.
     */
    private transient PropertyChangeSupport propertySupport;

    protected Object clone() {
        try {
            LicenseContent clone = (LicenseContent) super.clone();
            clone.issued    = (Date) issued   .clone();
            clone.notBefore = (Date) notBefore.clone();
            clone.notAfter  = (Date) notAfter .clone();
            return clone;
        }
        catch (CloneNotSupportedException exc) {
            throw new AssertionError(exc);
        }
    }

    /**
     * Returns the legal entity (i.e. the user) to which the license is granted
     * by the issuer.
     * The default is <tt>null</tt>.
     *
     * @see #getIssuer()
     * @return Value of property <tt>holder</tt>.
     */
    public X500Principal getHolder() {
        return this.holder;
    }

    /**
     * Sets the legal entity (i.e. the user) to which the license is granted
     * by the issuer.
     *
     * @see #setIssuer(X500Principal)
     * @param holder New value of bound property <tt>holder</tt>.
     */
    public synchronized void setHolder(X500Principal holder) {
        X500Principal oldHolder = this.holder;
        this.holder = holder;
        firePropertyChange("holder", oldHolder, holder); // NOI18N
    }

    /**
     * Returns the legal entity which grants the license to the holder.
     * The default is <tt>null</tt>.
     *
     * @see #getHolder()
     * @return Value of property <tt>issuer</tt>.
     */
    public X500Principal getIssuer() {
        return this.issuer;
    }

    /**
     * Sets the legal entity which grants the license to the holder.
     *
     * @see #setHolder(X500Principal)
     * @param issuer New value of bound property <tt>issuer</tt>.
     */
    public synchronized void setIssuer(X500Principal issuer) {
        X500Principal oldIssuer = this.issuer;
        this.issuer = issuer;
        firePropertyChange("issuer", oldIssuer, issuer); // NOI18N
    }

    /**
     * Returns the abstract description of the entity which needs to be
     * licensed in order for it to be used - it could be the name of a
     * software application like e.g. "TrueMirror".
     * The default is <tt>null</tt>.
     *
     * @return Value of property <tt>subject</tt>.
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * Sets the abstract description of the entity which needs to be
     * licensed in order for it to be used - it could be the name of a
     * software application like e.g. "TrueMirror".
     *
     * @param subject New value of bound property <tt>subject</tt>.
     */
    public synchronized void setSubject(String subject) {
        String oldSubject = this.subject;
        this.subject = subject;
        firePropertyChange("subject", oldSubject, subject); // NOI18N
    }

    /**
     * Returns the time when the license represented by this license
     * content has been issued.
     * The default is <tt>null</tt>.
     *
     * @see System#currentTimeMillis()
     * @return Value of property <tt>issued</tt>.
     */
    public Date getIssued() {
        return this.issued;
    }

    /**
     * Sets the time when the license represented by this license
     * content has been issued.
     *
     * @param issued New value of bound property <tt>issued</tt>.
     */
    public void setIssued(Date issued) {
        Date oldIssued = this.issued;
        this.issued = issued;
        firePropertyChange("issued", oldIssued, issued); // NOI18N
    }

    /**
     * Returns the time when the license begins to be valid.
     * The default is <tt>null</tt>.
     *
     * @return Value of property <tt>notBefore</tt>.
     */
    public Date getNotBefore() {
        return this.notBefore;
    }

    /**
     * Sets the time when the license begins to be valid.
     *
     * @param notBefore New value of bound property <tt>notBefore</tt>.
     */
    public void setNotBefore(Date notBefore) {
        Date oldNotBefore = this.notBefore;
        this.notBefore = notBefore;
        firePropertyChange("notBefore", oldNotBefore, notBefore); // NOI18N
    }

    /**
     * Returns the time when the license ends to be valid.
     * The default is <tt>null</tt>.
     *
     * @return Value of property <tt>notAfter</tt>.
     */
    public Date getNotAfter() {
        return this.notAfter;
    }

    /**
     * Sets the time when the license ends to be valid.
     *
     * @param notAfter New value of bound property <tt>notAfter</tt>.
     */
    public void setNotAfter(Date notAfter) {
        Date oldNotAfter = this.notAfter;
        this.notAfter = notAfter;
        firePropertyChange("notAfter", oldNotAfter, notAfter); // NOI18N
    }

    /**
     * Returns the type of entity which needs to license the license subject
     * in order to use it. This could be a computer or a user or something
     * else.
     * The default is <tt>null</tt>.
     *
     * @return Value of property <tt>consumerType</tt>.
     */
    public String getConsumerType() {
        return this.consumerType;
    }

    /**
     * Sets the type of entity which needs to license the license subject
     * in order to use it. This could be a computer or a user or something
     * else.
     *
     * @param consumerType New value of bound property <tt>consumerType</tt>.
     */
    public void setConsumerType(String consumerType) {
        String oldConsumerType = this.consumerType;
        this.consumerType = consumerType;
        firePropertyChange("consumerType", oldConsumerType, consumerType); // NOI18N
    }

    /**
     * Returns the amount of consumers which are allowed to license the
     * subject with this license.
     * The default is <tt>1</tt>.
     *
     * @return Value of property <tt>consumerAmount</tt>.
     */
    public int getConsumerAmount() {
        return this.consumerAmount;
    }

    /**
     * Sets the amount of consumers which are allowed to license the
     * subject with this license.
     *
     * @param consumerAmount New value of bound property <tt>consumerAmount</tt>.
     */
    public void setConsumerAmount(int consumerAmount) {
        int oldConsumerAmount = this.consumerAmount;
        this.consumerAmount = consumerAmount;
        firePropertyChange("consumerAmount", Integer.valueOf(oldConsumerAmount), Integer.valueOf(consumerAmount)); // NOI18N
    }

    /**
     * Returns the value of the property <tt>info</tt>.
     * This property may be used by applications to store public license
     * text for informational purposes.
     * Its value is displayed in the
     * {@link de.schlichtherle.license.wizard.LicensePanel}.
     * The default is <tt>null</tt>.
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * Sets the value of the property <tt>info</tt>.
     * This property may be used by applications to store public license
     * text for informational purposes.
     * Its value is displayed in the
     * {@link de.schlichtherle.license.wizard.LicensePanel}.
     *
     * @param info New value of bound property <tt>info</tt>.
     */
    public void setInfo(String info) {
        String oldInfo = this.info;
        this.info = info;
        firePropertyChange("info", oldInfo, info); // NOI18N
    }
    
    /**
     * Returns the value of the property extra.
     * This property may be used by applications to store arbitrary private
     * data which is not displayed to the user or checked by this library.
     * The default is <tt>null</tt>.
     *
     * @since The TrueLicense Library Collection 1.19.
     */
    public Object getExtra() {
        return this.extra;
    }
    
    /**
     * Sets the value of the property extra.
     * This property may be used by applications to store arbitrary private
     * data which is not displayed to the user or checked by this library.
     * <p>
     * <b>Warning:</b> If you use this property, versions of the TrueLicense
     * Library Collection prior to 1.19 will fail to install or verify the
     * generated license key with an {@link AssertionError}, which usually
     * causes the application to terminate abnormally!
     * <p>
     * (Since version 1.19, if an unknown property is found, a
     * {@link de.schlichtherle.xml.PersistenceServiceException} is thrown
     * by these methods instead.)
     *
     * @param extra New value of bound property <tt>extra</tt>.
     *        This object must either implement the JavaBeans specification
     *        or have direct support by the class
     *        {@link java.beans.XMLEncoder}, such as strings and primitive
     *        type wrapper objects.
     *
     * @since The TrueLicense Library Collection 1.19.
     */
    public void setExtra(Object extra) {
        Object oldExtra = this.extra;
        this.extra = extra;
        firePropertyChange("extra", oldExtra, extra); // NOI18N
    }
    
    public int hashCode() {
        return getConsumerAmount()
                + hashCode(getConsumerType())
                + hashCode(getHolder())
                + hashCode(getInfo())
                + hashCode(getIssued())
                + hashCode(getIssuer())
                + hashCode(getNotAfter())
                + hashCode(getNotBefore())
                + hashCode(getSubject());
    }

    /** Returns a hashcode that is consistent with {@link equals(Object)}. */
    private static final int hashCode(Object object) {
        return object != null ? object.hashCode() : 0;
    }

    /**
     * Returns <tt>true</tt> if and only if <tt>object</tt> is an instance of
     * <tt>LicenseContent</tt> and their properties are considered equal.
     */
    public boolean equals(Object object) {
        if (!(object instanceof LicenseContent))
            return false;
        final LicenseContent other = (LicenseContent) object;
        return other.getConsumerAmount() == getConsumerAmount()
                && isNullOrEquals(other.getConsumerType(), getConsumerType())
                && isNullOrEquals(other.getHolder(), getHolder())
                && isNullOrEquals(other.getInfo(), getInfo())
                && isNullOrEquals(other.getIssued(), getIssued())
                && isNullOrEquals(other.getIssuer(), getIssuer())
                && isNullOrEquals(other.getNotAfter(), getNotAfter())
                && isNullOrEquals(other.getNotBefore(), getNotBefore())
                && isNullOrEquals(other.getSubject(), getSubject());
    }
    
    private static final boolean isNullOrEquals(Object a, Object b) {
        return a != null ? a.equals(b) : (b == null);
    }
    
    /**
     * Adds a PropertyChangeListener to the listener list.
     *
     * @param l The listener to add.
     */
    public final synchronized void addPropertyChangeListener(PropertyChangeListener l) {
        if (propertySupport == null)
            propertySupport = new PropertyChangeSupport(this);
        propertySupport.addPropertyChangeListener(l);
    }

    /**
     * Removes a PropertyChangeListener from the listener list.
     *
     * @param l The listener to remove.
     */
    public final void removePropertyChangeListener(PropertyChangeListener l) {
        if (propertySupport == null)
            return;
        propertySupport.removePropertyChangeListener(l);
    }
    
    protected final void firePropertyChange(PropertyChangeEvent evt) {
        if (propertySupport == null)
            return;
        propertySupport.firePropertyChange(evt);
    }
    
    protected final void firePropertyChange(
            String propertyName,
            Object oldValue, Object newValue) {
        if (propertySupport == null)
            return;
        propertySupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
