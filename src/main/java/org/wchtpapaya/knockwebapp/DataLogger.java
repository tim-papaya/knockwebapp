package org.wchtpapaya.knockwebapp;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Slf4j
@Component
public class DataLogger {

    private final File file;

    DataLogger() {
        file = new File("datalog.txt");
        createFileIfNotExists();
    }

    DataLogger(String fileName) {
        file = new File(fileName);
        createFileIfNotExists();
    }

    public void log(String message) {
        try {
            Files.writeString(file.toPath(), message + System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Can`t open DataLog file to write incoming data");
        }
    }

    private void createFileIfNotExists() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            log.error("Can`t create DataLog file");
        }
    }
}
