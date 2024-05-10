package com.capstone.producer.shared;

import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

/**
 * FileHelper class for reading files
 */
public class FileHelper {
    private FileHelper() {
    }

    public static String readFile(String path) throws IOException {

        var report = ResourceUtils.getFile("classpath:" + path);

        return new String(Files.readAllBytes(report.toPath()));
    }
}
