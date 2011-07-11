/*
 * AnimatedImageIcon.java
 *
 * Created on 17. July 2006, 9:00
 */
/*
 * Copyright 2006 Schlichtherle IT Services
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

package de.schlichtherle.swing;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * @author Christian Schlichtherle
 */
public class AnimatedImageIcon extends ImageIcon {

    private static final String CLASS_NAME
            = "de/schlichtherle/swing/AnimatedImageIcon".replace('/', '.'); // beware of code obfuscation!
    private static final Logger logger = Logger.getLogger(CLASS_NAME);

    private static final Level CALLING
            = new Level("CALLING", Level.FINE.intValue()) { };
    private static final Level FAILED
            = new Level("FAILED", Level.WARNING.intValue()) { };
    private static final Level RETURNED
            = new Level("RETURNED", Level.FINER.intValue()) { };

    private int delay;
    private Image[] frames;

    private final Timer timer = new Timer(0, new Animator());

    /**
     * Constructs a new <code>AnimatedImageIcon</code>.
     */
    public AnimatedImageIcon() {
    }

    /**
     * Constructs a new <code>AnimatedImageIcon</code>.
     *
     * @param rate The animation rate in frames per second.
     * @param frames The array of image frames for this animation.
     */
    public AnimatedImageIcon(int rate, Image[] frames) {
        super(frames[0]);
        setRateImpl(rate);
        setFramesImpl(frames);
    }

    /**
     * Returns the effective animation rate in frames per second.
     * This may slightly differ from the value provided to the constructor
     * or {@link #setRate} due to rounding flaws.
     */
    public int getRate() {
        return 1000 / delay;
    }

    /**
     * Set the rate for this animation.
     *
     * @param rate The animation rate in frames per second.
     */
    public void setRate(int rate) {
        setRateImpl(rate);
    }

    private final void setRateImpl(int rate) {
        delay = 1000 / rate;
        timer.setDelay(delay);
    }

    /**
     * Returns a clone of the array which holds the image frames for this animation.
     */
    public Image[] getFrames() {
        return (Image[]) frames.clone();
    }

    /**
     * Sets the image frames for this animation.
     *
     * @param frames The array of image frames for this animation.
     */
    public void setFrames(Image[] frames) {
        setFramesImpl(frames);
    }

    private final void setFramesImpl(Image[] frames) {
        this.frames = frames;
        /*for (int i = 0, l = frames.length; i < l; i++)
            loadImage(frames[i]);*/
        setImage(frames[0]);
    }

    public void paintIcon(
            final Component c, final Graphics g,
            final int x, final int y) {
        logger.log(CALLING, System.identityHashCode(this) + "(c=" + System.identityHashCode(c) + ", g=" + System.identityHashCode(g) + ", x=" + x + ", y=" + y + ")");
        try {
            // Get frame image to paint.
            // Check that it's not null, which may happen if this object
            // was initialized with the nullary constructor.
            final Image image = getImage();
            if (image == null)
                return; // may happen when initialized from a nullary constructor

            // Pass null as the image observer in order to suppress any
            // image updates from the frame image.
            // This is a safe optimization because setImage(Image) has already
            // ensured that the frame image is loaded and may help in case
            // the frame image is actually an animated GIF
            // (which you shouldn't use with this class anyway).
            g.drawImage(image, x, y, null);

            // Rememember component for animation updates and start timer,
            // if it's not already started.
            setImageObserver(c);
            timer.start();

            logger.log(RETURNED, System.identityHashCode(this) + "(...)");
        } catch (Throwable throwable) {
            logger.log(FAILED, System.identityHashCode(this) + "(...)");
            rethrow(throwable);
        }
    }
    
    private static void rethrow(Throwable throwable) {
        if (throwable instanceof RuntimeException)
            throw (RuntimeException) throwable;
        else if (throwable instanceof Error)
            throw (Error) throwable;
        else
            throw new UndeclaredThrowableException(throwable);
    }

    private class Animator implements ActionListener {
        private int current;

        public void actionPerformed(final ActionEvent evt) {
            final ImageObserver observer = getImageObserver();
            current = (current + 1) % frames.length;
            boolean ret = imageUpdate(observer, frames[current]);
            if (!ret) {
                current = 0;
                ret = imageUpdate(observer, frames[current]);
                timer.stop();
                setImageObserver(null);
                assert !ret : "The image observer should not be interested in updates anymore!";
            }
        }
        
        private boolean imageUpdate(final ImageObserver observer, final Image image) {
            logger.log(CALLING, System.identityHashCode(this) + "(observer=" + System.identityHashCode(observer) + ", image=" + image + ")");
            try {
                setImage(image);
                final int width = image.getWidth(observer);
                assert width == getIconWidth();
                final int height = image.getHeight(observer);
                assert height == getIconHeight();
                final boolean ret = observer.imageUpdate(
                        image, ImageObserver.FRAMEBITS, 0, 0, width, height);

                logger.log(RETURNED, System.identityHashCode(this) + "(...)=" + ret);
                return ret;
            } catch (Throwable throwable) {
                logger.log(FAILED, System.identityHashCode(this) + "(...)");
                rethrow(throwable);
                return false; // unreachable
            }
        }
    }
}
