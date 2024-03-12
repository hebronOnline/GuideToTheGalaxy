package za.co.onespark.excercise.guidetothegalaxy.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class InputFileReaderImpl implements InputFileReader {
    private static final String TEST_DATA_PATH = "testdata/";

    @Override
    public String[] readFile(String fileName) throws IOException {
        File file = new ClassPathResource(TEST_DATA_PATH + fileName).getFile();

        List<String> list = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(line);
                line = bufferedReader.readLine();
            }
        }

        return list.toArray(new String[0]);
    }
}
