package com.github.schmittjoaopedro.mcc.message;

/**
 *
 * Different message status used by MCC to manage errors.
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public enum MessageStatus {

    /**
     * Everything is all right
     */
    COMPILED,

    /**
     * Some error throw by the compiler, normally a configuration
     * problem associated with the consumer
     */
    FAILED,

    /**
     * Invalid PMD verification
     */
    PMD_ERROR,

    /**
     * Unavailable compiler in the pool at moment
     */
    NO_COMPILER_AVAILABLE,

    /**
     * Compiler error, this error indicates a internal problem
     * with the compiler
     */
    COMPILER_ERROR

}
