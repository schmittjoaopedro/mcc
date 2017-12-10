package com.github.schmittjoaopedro.mcc.message;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.List;

/**
 *
 * MessageCompiler is used to encapsulate exceptions
 * with more details about the errors
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class MessageCompiler {

    /**
     * Detailed message about the error
     */
    private String message;

    /**
     * The message information category
     */
    private MessageStatus status;

    /**
     * Diagnostic problems in compilation process (optional)
     */
    private List<Diagnostic<? extends JavaFileObject>> diagnostics;

    /**
     * This class is immutable, this means that the attributes
     * can not be modified
     *
     * @param message
     * @param status
     */
    public MessageCompiler(String message, MessageStatus status) {
        super();
        this.message = message;
        this.status = status;
    }

    /**
     * This class is immutable, this means that the attributes
     * can not be modified, with diagnostic
     *
     * @param message
     * @param status
     */
    public MessageCompiler(String message, MessageStatus status, List<Diagnostic<? extends JavaFileObject>> diagnostics) {
        super();
        this.message = message;
        this.status = status;
        this.diagnostics = diagnostics;
    }

    /**
     * Only getters, a immutable object.
     *
     * GETTERS
     */

    public String getMessage() {
        return message;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
        return diagnostics;
    }

}
