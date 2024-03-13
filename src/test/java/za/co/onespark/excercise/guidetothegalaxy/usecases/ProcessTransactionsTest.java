package za.co.onespark.excercise.guidetothegalaxy.usecases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.Metal;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.RomanNumeral;
import za.co.onespark.excercise.guidetothegalaxy.common.models.InterGalacticConversionData;
import za.co.onespark.excercise.guidetothegalaxy.exceptions.FileProcessingException;
import za.co.onespark.excercise.guidetothegalaxy.utils.InputDataParser;
import za.co.onespark.excercise.guidetothegalaxy.utils.InputFileReader;
import za.co.onespark.excercise.guidetothegalaxy.utils.RomanNumeralConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessTransactionsTest {
    private static final  String FILE_NAME = "testfile.txt";
    private static final String[] TEST_CONTENT = {"Some content"};
    @InjectMocks
    private ProcessTransactions cut;

    @Mock
    private InputFileReader inputFileReader;
    @Mock
    private InputDataParser inputDataParser;
    @Mock
    private RomanNumeralConverter romanNumeralConverter;

    @Test
    @DisplayName("Given a filename, " +
            "when the execute method is invoked, " +
            "then we expect the transactions to be processed correctly")
    void testExecute() throws IOException, FileProcessingException {
        //Given
        when(inputFileReader.readFile(anyString())).thenReturn(TEST_CONTENT);
        when(inputDataParser.parseData(TEST_CONTENT)).thenReturn(dataStub());
        when(romanNumeralConverter.convertToNumber(anyString())).thenReturn(4);

        //When
        List<String> answers = cut.execute(FILE_NAME);

        //Then
        verify(inputFileReader, times(1)).readFile(FILE_NAME);
        verify(romanNumeralConverter, times(2)).convertToNumber("IV");

        assertEquals(2, answers.size());
        assertEquals("glob prok is 4", answers.get(0));
        assertEquals("glob prok Gold is 480 Credits", answers.get(1));
    }

    @Test
    @DisplayName("Given a filename, " +
            "when the execute method is invoked, " +
            "and a question does not have an answer, " +
            "then we expect the answer to be returned for that question")
    void testExecuteIHaveNoIdea() throws IOException, FileProcessingException {
        //Given
        InterGalacticConversionData dataStub = dataStub();
        dataStub.setQuestions(List.of(
                "how much discount can you give me ?"
        ));
        when(inputFileReader.readFile(anyString())).thenReturn(TEST_CONTENT);
        when(inputDataParser.parseData(TEST_CONTENT)).thenReturn(dataStub);

        //When
        List<String> answers = cut.execute(FILE_NAME);

        //Then
        assertEquals(1, answers.size());
        assertEquals("I have no idea what you are talking about", answers.get(0));
    }

    @Test
    @DisplayName("Given an empty file, " +
            "when the execute method is invoked, " +
            "then we expect a file processing exception to be thrown")
    void testFileProcessingException() throws IOException {
        //Given
        when(inputFileReader.readFile(anyString())).thenReturn(new String[0]);

        //When then
        assertThrows(
            FileProcessingException.class,
            () -> cut.execute(FILE_NAME)
        );
    }

    private InterGalacticConversionData dataStub() {
        Map<String, RomanNumeral> galacticToRomanNumeralsMap = new HashMap<>();
        galacticToRomanNumeralsMap.put("glob", RomanNumeral.I);
        galacticToRomanNumeralsMap.put("prok", RomanNumeral.V);

        Map<Metal, Double> unitPerMetalCreditConversionMapping = new HashMap<>();
        unitPerMetalCreditConversionMapping.put(Metal.GOLD, 120.0);

        InterGalacticConversionData data = new InterGalacticConversionData();
        data.setQuestions(List.of(
                "how much is glob prok ?",
                "how many Credits is glob prok Gold ?"
        ));
        data.setGalacticToRomanNumeralsMap(galacticToRomanNumeralsMap);
        data.setUnitPerMetalCreditConversionMapping(unitPerMetalCreditConversionMapping);
        data.setAnswers(new ArrayList<>());

        return data;
    }
}