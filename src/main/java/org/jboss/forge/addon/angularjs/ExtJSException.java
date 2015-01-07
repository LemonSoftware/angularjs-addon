

package org.jboss.forge.addon.angularjs;

/**
 * Generic Error, to be thrown in case of any error, suspected behaviour or unsupported behaviour.
 *
 * Created by Mohamed Mekkawy on 13/12/2014.
 */
public class ExtJSException extends RuntimeException {
    public ExtJSException() {
        super();
    }

    public ExtJSException(String message) {
        super(message);
    }

    public ExtJSException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtJSException(Throwable cause) {
        super(cause);
    }

    protected ExtJSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
