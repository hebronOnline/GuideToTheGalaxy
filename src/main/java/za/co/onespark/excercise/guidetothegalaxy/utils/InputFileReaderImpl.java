package za.co.onespark.excercise.guidetothegalaxy.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class InputFileReaderImpl implements InputFileReader {
    private static final String TEST_DATA_PATH = "testdata/";

    @Override
    public String[] readFile(String fileName) throws IOException {
        File file = new ClassPathResource(TEST_DATA_PATH + fileName).getFile();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        return bufferedReader.lines().collect(Collectors.joining()).split("\n");
    }
}
