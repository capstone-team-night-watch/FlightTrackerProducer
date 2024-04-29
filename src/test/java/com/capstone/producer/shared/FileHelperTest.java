package com.capstone.shared;

import com.capstone.producer.shared.FileHelper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class FileHelperTest {

    private String file = "test.txt";

    @Test
    void should_read_file() throws IOException {
        String content = FileHelper.readFile(file);

        String expected = "Hello world";

        assertEquals(expected, content);
    }

}