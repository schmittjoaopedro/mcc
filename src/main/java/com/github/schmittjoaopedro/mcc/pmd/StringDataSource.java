package com.github.schmittjoaopedro.mcc.pmd;

import net.sourceforge.pmd.util.datasource.DataSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Hold the source code to run PMD validations, the base behavior of
 * PMD use the IO File, but this class allows run PMD Validation in memory
 *
 * @author schmittjoaopedro
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class StringDataSource implements DataSource {

    /**
     * Source code to validate
     */
    private String code;

    /**
     * Constructor with source code as argument
     *
     * @param code
     */
    public StringDataSource(String code) {
        super();
        this.code = code;
    }

    /**
     * Transform the string in a ByteArrayInputStream
     *
     * @return inputStream
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Return empty
     */
    @Override
    public String getNiceFileName(boolean shortNames, String inputFileName) {
        return "";
    }

}
