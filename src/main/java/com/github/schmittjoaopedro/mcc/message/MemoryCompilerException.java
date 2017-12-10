package com.github.schmittjoaopedro.mcc.message;

/**
 * This class extends RuntimeException to encapsulate
 * possibles errors thrown by the compiler.
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class MemoryCompilerException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * This exception have a dependence to MessageCompiler
     * used to thrown the errors more detailed
     */
    private MessageCompiler messageCompiler;


    public MemoryCompilerException(MessageCompiler messageCompiler) {
        this.messageCompiler = messageCompiler;
        Throwable throwable = new Throwable(this.getFormatedMessage());
        this.addSuppressed(throwable);
    }

    public MemoryCompilerException(String message, MessageStatus status) {
        super(message);
        this.messageCompiler = new MessageCompiler(message, status);
        Throwable throwable = new Throwable(this.getFormatedMessage());
        this.addSuppressed(throwable);
    }

    private String getFormatedMessage() {
        String message = this.messageCompiler.getStatus().name() + ": " + this.messageCompiler.getMessage();
        if(this.messageCompiler.getDiagnostics() != null) {
            message += "\nDiagnostic: " + this.messageCompiler.getDiagnostics().toString();
        }
        return message;
    }

    public MessageCompiler getMessageCompiler() {
        return this.messageCompiler;
    }

    @Override
    public String getMessage() {
        return this.getFormatedMessage();
    }

    @Override
    public String toString() {
        return this.getFormatedMessage();
    }

}
