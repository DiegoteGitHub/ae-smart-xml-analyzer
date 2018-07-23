package com.dlalo.ae.util;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class FileParser {

    private final String charsetName;

    public FileParser(String charsetName) {
        this.charsetName = charsetName;
    }

    /**
     * Parses given file to {@link Document}
     * @param source, the file
     * @return parsed {@link Document}
     * @throws RuntimeException - when unable parse file
     */
    public Document parse(File source) {
        try {
            return Jsoup.parse(source, charsetName, source.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Unable parse file", e);
        }
    }
}
