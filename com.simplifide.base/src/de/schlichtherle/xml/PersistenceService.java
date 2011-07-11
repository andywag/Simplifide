/*
 * PersistenceService.java
 *
 * Created on 21. September 2004, 10:58
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

import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Provides a collection of static methods to support comfortable loading and
 * storing of objects as XML data.
 * <p>
 * This class uses the classes {@link XMLEncoder} and
 * {@link XMLDecoder} to encode and decode an object
 * to and from an XML file for long term persistence.
 * It allows you to provide custom {@link PersistenceDelegate}
 * instances for the serialisation of any classes which do not implement the
 * JavaBean design pattern and are not supported by <code>XMLEncoder</code>
 * as a default.
 * <p>
 * For the latter case, <code>PersistenceService</code> offers the
 * {@link #setPersistenceDelegate(Class, PersistenceDelegate)} method which
 * could be called from a static initialiser block in the class which would
 * like to use <code>PersistenceService</code>'s <code>store</code> and <code>load</code>
 * methods.
 * <p>
 * Note that the Java API already provides some default persistence delegates
 * for some classes of its API which are not JavaBeans. If in doubt, simply
 * test the class before writing a custom <code>PersistenceDelegate</code>.
 * If you see exceptions happening, you most probably need to provide a 
 * <code>PersistenceDelegate</code> and use the <code>setPersistenceDelegate</code>
 * method to install it.
 * <p>
 * Note that the store and load methods in this class have been designed
 * to deal with any kind of {@link Throwable}s throughout the course of
 * (de)serialisation, even {@link OutOfMemoryError}s.
 * <p>
 * This class is designed to be thread safe, i.e. when an object is
 * persistet, it is put into a <code>synchronized (object) { ... }</code> block.
 *
 * @author Christian Schlichtherle
 *
 * @see XMLEncoder
 * @see XMLDecoder
 * @see PersistenceDelegate
 * @see java.beans.DefaultPersistenceDelegate
 * @see <a href="http://java.sun.com/products/jfc/tsc/articles/persistence4/">Sun Developer Network Site: Using XMLEncoder</a>
 */
public class PersistenceService implements XMLConstants {
    
    /**
     * This map maps from <code>Class</code> instances to
     * <code>PersistenceDelegate</code> instances. Its elements are installed
     * in the <code>XMLEncoder</code> prior to encoding an object.
     */
    private static final HashMap allPDs = new HashMap();
    
    /**
     * The buffer size for I/O used in the store and load methods.
     * You may customise this to your needs - the default is
     * <code>DEFAULT_BUFSIZE</code>.
     */
    public static int BUFSIZE = DEFAULT_BUFSIZE;

    /**
     * Returns an <code>ExceptionListener</code>.
     * This custom exception listener enforces zero tolerance when encoding
     * or decoding objects to or from XML files in order not to compromise
     * the integrity of an object.
     */
    private static final ExceptionListener createExceptionListener() {
        return new ExceptionListener() {
            public void exceptionThrown(Exception exc) {
                throw exc instanceof UndeclaredThrowableException
                    ? (UndeclaredThrowableException) exc // don't wrap again
                    : new UndeclaredThrowableException(exc);
            }
        };
    }
    
    /**
     * Associates a <code>PersistenceDelegate</code> to the given
     * class <code>type</code>.
     * <p>
     * This must be called prior to the <code>store</code> methods for each class
     * which's instances are to be persisted.
     * Thus, the best place to put this call in is a <em>static initializer
     * block</em> for the corresponding class.
     * <p>
     * Here is an example:
     * <pre>
     *      class PersistentObject {
     *          static {
     *              PersistenceService.setPersistenceDelegate(
     *                  PersistentObject.class,
     *                  new DefaultPersistenceDelegate(
     *                      new String[] { "property" }));
     *          }
     *
     *          public int property;
     *
     *          public PersistentObject(int property) {
     *              this.property = property;
     *          }
     *      }
     * </pre>
     * <p>
     * Note that you should not use this method for any class which's source
     * code you can control.
     * The preferred way to associate a persistence delegate with a class is
     * to write a <code>BeanInfo</code> class which's
     * <code>BeanDescriptor</code> has an attribute set with
     * <code>"persistenceDelegate"</code> as its name and the respective
     * persistence delegate as its value
     * (see {@link Encoder#getPersistenceDelegate}).
     * However, this method is still useful in case you can't control the
     * source code, as then at least you can still associate a persistence
     * delegate to this class.
     *
     * @see java.beans.XMLEncoder
     * @see java.beans.PersistenceDelegate
     * @see java.beans.DefaultPersistenceDelegate
     */
    public static synchronized final void setPersistenceDelegate(
            Class clazz,
            PersistenceDelegate persistenceDelegate) {
        allPDs.put(clazz, persistenceDelegate);
    }

    /**
     * Installs all persistence delegates registered via
     * <code>{@link #setPersistenceDelegate(Class, PersistenceDelegate)}</code>
     * in <code>encoder</code>.
     *
     * @param encoder The encoder - may <em>not</em> be <code>null</code>.
     *
     * @throws RuntimeException if <code>encoder</code> is <code>null</code>.
     */
    protected static synchronized void installPersistenceDelegates(final Encoder encoder) {
        Iterator i = allPDs.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();
            encoder.setPersistenceDelegate(
                    (Class) entry.getKey(),
                    (PersistenceDelegate) entry.getValue());
        }
    }
    
    /**
     * Stores the object <code>root</code>, which may form the root of an entire
     * object graph, as XML content to the output stream <code>xmlOut</code> for
     * long term persistence.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     serialisation exceptions!</li>
     * <li>The underlying stream is <em>always</em> closed (even if an
     *     exception is thrown).</li>
     * </ul>
     *
     * @param root The object to store - may be <code>null</code>.
     * @param xmlOut The unbuffered stream to output the XML content
     *        - may <em>not</em> be <code>null</code>.
     *
     * @throws NullPointerException If <code>xmlOut</code> is </code>null</code>.
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    public static void store(
            final Object root,
            final OutputStream xmlOut)
    throws  NullPointerException,
            PersistenceServiceException {
        if (xmlOut == null)
            throw new NullPointerException();
        
        // Guard against OutOfMemoryError here!
        // This is not unlikely as XMLEncoder clones the root, which could be
        // a large object graph.
        // Remember: We guarantee to close the underlying stream, that's why
        // we make a fuss out of this.
        try {
            OutputStream bufOut = null;
            XMLEncoder encoder = null;
            try {
                bufOut = new BufferedOutputStream(xmlOut, BUFSIZE);
                encoder = new XMLEncoder(bufOut);
                installPersistenceDelegates(encoder);
                encoder.setExceptionListener(createExceptionListener());
                if (root != null) {
                    synchronized (root) { // inhibit concurrent modifications - this requires cooperation
                        encoder.writeObject(root);
                    }
                } else { // root == null
                    encoder.writeObject(root);
                }
            } finally {
                if (encoder != null) {
                    try {
                        // This method actually writes the XML content
                        // and may throw an OutOfMemoryError (again)!
                        encoder.close();
                    } catch (Throwable paranoid) {
                        // In case of an exception during the closing of
                        // the encoder it does not properly close its
                        // underlying stream. Do this now.
                        bufOut.close();
                        throw paranoid;
                    }
                } else if (bufOut != null) {
                    // Allocating the encoder failed.
                    // This should not normally happen unless the JVM is very
                    // very scarce on heap memory!
                    bufOut.close();
                } else {
                    // Allocating the buffered output stream failed.
                    // This should not normally happen unless the JVM is very
                    // very scarce on heap memory!
                    xmlOut.close();
                }
            }
        } catch (UndeclaredThrowableException exc) {
            // Allocating a new exception should always succeed as the encoder
            // and its associated clone of the root object graph is now
            // eligible for garbage collection!
            throw new PersistenceServiceException(exc.getCause()); // unwrap cause
        } catch (Throwable thr) {
            // Allocating a new exception should always succeed as the encoder
            // and its associated clone of the root object graph is now
            // eligible for garbage collection!
            throw new PersistenceServiceException(thr);
        }
    }
    
    /**
     * Stores the object <code>root</code>, which may form the root of an entire
     * object graph, as XML content to the file <code>file</code> for
     * long term persistence.
     * This method supports writing to a file located in a ZIP or JAR file.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     serialisation exceptions!</li>
     * <li>This is a transaction, i.e. the method either completely succeeds
     *     with saving the file or the file is restored to its original state.
     * </ul>
     *
     * @param root The object to store - may be <code>null</code>.
     * @param file The file to output the XML content to
     *        - may <em>not</em> be <code>null</code>.
     *
     * @throws NullPointerException If <code>file</code> is </code>null</code>.
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    public static void store(
            final Object root,
            final File file)
    throws  NullPointerException,
            PersistenceServiceException {
        if (file == null)
            throw new NullPointerException();
        
        File backup = null;
        boolean renamed = false;
        try {
            backup = getRenamedFile(file);
            renamed = file.renameTo(backup);
            store(root, new FileOutputStream(file));
            if (renamed)
                backup.delete();
        } catch (Throwable thr) {
            if (renamed) {
                // (Note that the stream has been closed by store(...) already!)
                // The transaction failed at a point in time where the
                // target file has at least been partially written, so we
                // need to restore the target file from the backup again.
                try {
                    file.delete();
                } catch (Throwable paranoid) {
                    thr = paranoid;
                }
                try {
                    backup.renameTo(file);
                } catch (Throwable paranoid) {
                    thr = paranoid;
                }
            }
            throw thr instanceof PersistenceServiceException
                ? (PersistenceServiceException) thr
                : new PersistenceServiceException(thr);
        }
    }

    private static File getRenamedFile(File plainFile) {
        String path = plainFile.getPath();
        File renamedFile;
        do {
            path += '~'; // This should be OK on any current Java platform...
            renamedFile = new File(path);
        } while (renamedFile.exists());
        return renamedFile;
    }

    /**
     * Stores the object <code>root</code>, which may form the root of an entire
     * object graph, as XML content to the single entry file <code>zipEntry</code>
     * in ZIP file format to the output stream <code>zipOut</code> for long
     * term persistence.
     * <p>
     * Please note the following:
     * <ul>
     * <li>The stream is connected to a new <code>BufferedOutputStream</code>
     *     with <code>BUFSIZE</code> as its buffer size and is <em>always</em>
     *     closed (even if an exception is thrown).</li>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     serialisation exceptions!</li>
     * </ul>
     *
     * @param root The object to store - may be <code>null</code>.
     * @param zipEntry The relative entry name of the XML file in the ZIP file
     *        - may <em>not</em> be <code>null</code>.
     * @param zipOut The unbuffered stream to output the raw ZIP file content
     *        - may <em>not</em> be <code>null</code>.
     *
     * @throws NullPointerException If the preconditions for the parameters
     *         do not hold.
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    /*public static void store(
            final Object root,
            final OutputStream zipOut,
            final String zipEntry)
    throws  NullPointerException,
            PersistenceServiceException {
        ZipOutputStream zip = null;
        try {
            if (zipEntry == null || zipOut == null)
                throw new NullPointerException(); // throwing inside try protects against OutOfMemoryError!

            zip = new ZipOutputStream(new BufferedOutputStream(zipOut, BUFSIZE));
            zip.putNextEntry(new ZipEntry(zipEntry));
        }
        catch (NullPointerException illegalArguments) {
            throw illegalArguments;
        }
        catch (Throwable cause) { // Guard against OutOfMemoryError!
            throw new PersistenceServiceException(cause);
        }
        store(root, zip);
    }*/
    
    /**
     * Stores the object <code>root</code>, which may form the root of an entire
     * object graph, as XML content into a UTF-8 encoded byte array for
     * long term persistence.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     serialisation exceptions!</li>
     * </ul>
     *
     * @param root The object to store - may be <code>null</code>.
     *
     * @return The XML with UTF-8 charset encoded byte array
     *         representation of <code>root</code>
     *         - <code>null</code> is never returned.
     *
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    public static byte[] store2ByteArray(Object root)
    throws  PersistenceServiceException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            store(root, out);
            return out.toByteArray();
        } catch (PersistenceServiceException exc) {
            throw exc;
        } catch (Throwable thr) {
            throw new PersistenceServiceException(thr);
        }
    }

    /**
     * Stores the object <code>root</code>, which may form the root of an entire
     * object graph, as XML content into a string for long term persistence.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     serialisation exceptions!</li>
     * </ul>
     *
     * @param root The object to store - may be <code>null</code>.
     *
     * @return The XML string encoded representation of <code>root</code>
     *         - <code>null</code> is never returned.
     *
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    public static String store2String(Object root)
    throws  PersistenceServiceException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            store(root, out);
            return out.toString(XML_CHARSET);
        } catch (UnsupportedEncodingException cannotHappen) {
            throw new AssertionError(cannotHappen);
        } catch (PersistenceServiceException exc) {
            throw exc;
        } catch (Throwable thr) {
            throw new PersistenceServiceException(thr);
        }
    }
    
    /**
     * Loads a single object, which may form the root of an entire
     * object graph, from XML content in the given input stream <code>xmlIn</code>.
     * <p>
     * Please note the following:
     * <ul>
     * <li>The stream is connected to a new <code>BufferedInputStream</code>
     *     with <code>BUFSIZE</code> as its buffer size and is <em>always</em>
     *     closed (even if an exception is thrown).</li>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     deserialisation exceptions!</li>
     * </ul>
     *
     * @param xmlIn The unbuffered stream to input the XML content
     *        - may <em>not</em> be <code>null</code>.
     *
     * @return The root of the loaded object graph - may be <code>null</code>.
     *
     * @throws NullPointerException If <code>xmlIn</code> is <code>null</code>.
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    public static Object load(InputStream xmlIn)
    throws  NullPointerException,
            PersistenceServiceException {
        if (xmlIn == null)
            throw new NullPointerException();

        XMLDecoder decoder = null;
        try {
            // Note that the constructor already loads the complete object
            // graph into memory. If anything goes wrong, an unchecked
            // exception is thrown already HERE!
            decoder = new XMLDecoder(
                    new BufferedInputStream(xmlIn, BUFSIZE),
                    null,
                    createExceptionListener());
            return decoder.readObject();
        } catch (UndeclaredThrowableException exc) {
            throw new PersistenceServiceException(exc.getCause()); // unwrap cause
        } catch (Throwable thr) {
            throw new PersistenceServiceException(thr);
        } finally {
            if (decoder != null) {
                try {
                    decoder.close(); // Could throw e.g. OutOfMemoryError (again)!
                }
                catch (Throwable paranoid) {
                    throw new PersistenceServiceException(paranoid);
                }
            }
        }
    }
    
    /**
     * Loads a single object, which may form the root of an entire
     * object graph, from XML content in the given file <code>file</code>.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     deserialisation exceptions!</li>
     * </ul>
     *
     * @param file The file to load the XML content from
     *        - may <em>not</em> be <code>null</code>.
     *
     * @return The root of the loaded object graph - may be <code>null</code>.
     *
     * @throws NullPointerException If <code>file</code> is <code>null</code>.
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    public static Object load(File file)
    throws  NullPointerException,
            PersistenceServiceException {
        if (file == null)
            throw new NullPointerException();
        
        try {
            return load(new FileInputStream(file));
        } catch (PersistenceServiceException exc) {
            throw exc;
        } catch (Throwable thr) {
            throw new PersistenceServiceException(thr);
        }
    }
    
    /**
     * Loads a single object, which may form the root of an entire
     * object graph, from XML content in the single entry file <code>zipEntry</code>
     * from the ZIP file <code>zipFile</code>.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     deserialisation exceptions!</li>
     * </ul>
     *
     * @param zipEntry The relative entry name of the XML file in the ZIP file
     *        - may <em>not</em> be <code>null</code>.
     * @param zipFile The ZIP file to read the XML entry file from
     *        - may <em>not</em> be <code>null</code>.
     *
     * @return The root of the loaded object graph - may be <code>null</code>.
     *
     * @throws NullPointerException If the preconditions for the parameters
     *         do not hold.
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    /*public static Object load(
            final java.io.File zipFile,
            final String zipEntry)
    throws  NullPointerException,
            PersistenceServiceException {
        ZipFile zip = null;
        try {
            if (zipEntry == null || zipFile == null)
                throw new NullPointerException(); // throwing inside try protects against OutOfMemoryError!
        
            zip = new ZipFile(zipFile);
            return load(zip.getInputStream(new ZipEntry(zipEntry)));
        }
        catch (NullPointerException illegalArguments) {
            throw illegalArguments;
        }
        catch (PersistenceServiceException exc) {
            throw exc;
        }
        catch (Throwable thr) { // Guard against OutOfMemoryError!
            throw new PersistenceServiceException(thr);
        }
        finally {
            if (zip != null) {
                try {
                    zip.close(); // Could throw IOException or even e.g. OutOfMemoryError (again)!
                }
                catch (Throwable cause) {
                    throw new PersistenceServiceException(cause);
                }
            }
        }
    }*/
    
    /**
     * Loads a single object, which may form the root of an entire
     * object graph, from XML content in the single entry file <code>xmlEntry</code>
     * from the ZIP file <code>zipFile</code>.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     deserialisation exceptions!</li>
     * </ul>
     *
     * @param xmlEntry The relative entry name of the XML file in the ZIP file
     *        - may <em>not</em> be <code>null</code>.
     * @param zipFile The ZIP file to read the XML entry file from
     *        - may <em>not</em> be <code>null</code>.
     *
     * @return The root of the loaded object graph - may be <code>null</code>.
     *
     * @throws NullPointerException If the preconditions for the parameters
     *         do not hold.
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    /*public static final Object load(
            final String zipFile,
            final String xmlEntry)
    throws  NullPointerException,
            PersistenceServiceException {
        if (zipFile == null)
            throw new NullPointerException();

        try {
            return load(new java.io.File(zipFile), xmlEntry);
        }
        catch (PersistenceServiceException exc) {
            throw exc;
        }
        catch (Throwable thr) {
            throw new PersistenceServiceException(thr);
        }
    }*/
    
    /**
     * Loads a single object, which may form the root of an entire
     * object graph, from XML content in the UTF-8 encoded byte array
     * <code>encoded</code>.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     deserialisation exceptions!</li>
     * </ul>
     *
     * @param encoded The XML with UTF-8 charset encoded byte array
     *        representation of the root of an object graph
     *        - may <em>not</em> be <code>null</code>.
     *
     * @return The root of the loaded object graph - may be <code>null</code>.
     *
     * @throws NullPointerException If <code>encoded</code> is <code>null</code>.
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    public static Object load(final byte[] encoded)
    throws  NullPointerException,
            PersistenceServiceException {
        if (encoded == null)
            throw new NullPointerException();
        
        try {
            return load(new ByteArrayInputStream(encoded));
        } catch (PersistenceServiceException exc) {
            throw exc;
        } catch (Throwable thr) {
            throw new PersistenceServiceException(thr);
        }
    }
    
    /**
     * Loads a single object, which may form the root of an entire
     * object graph, from XML content in the string <code>encoded</code>.
     * <p>
     * Please note the following:
     * <ul>
     * <li>This method will <em>not</em> tolerate any I/O or other
     *     deserialisation exceptions!</li>
     * </ul>
     *
     * @param encoded The XML string encoded representation of the root
     *        of an object graph
     *        - may <em>not</em> be <code>null</code>.
     *
     * @return The root of the loaded object graph - may be <code>null</code>.
     *
     * @throws NullPointerException If <code>encoded</code> is <code>null</code>.
     * @throws PersistenceServiceException If <em>any</em> throwable was thrown
     *         during serialisation. Its cause is always set.
     */
    public static Object load(final String encoded)
    throws  NullPointerException,
            PersistenceServiceException {
        if (encoded == null)
            throw new NullPointerException();
        
        try {
            return load(encoded.getBytes(XML_CHARSET));
        } catch (UnsupportedEncodingException cannotHappen) {
            throw new AssertionError(cannotHappen);
        } catch (PersistenceServiceException exc) {
            throw exc;
        } catch (Throwable thr) {
            throw new PersistenceServiceException(thr);
        }
    }

    /** You cannot instantiate this class. */
    protected PersistenceService() { }

}
