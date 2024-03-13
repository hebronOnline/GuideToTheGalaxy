package za.co.onespark.excercise.guidetothegalaxy.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.Metal;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.RomanNumeral;
import za.co.onespark.excercise.guidetothegalaxy.common.models.InterGalacticConversionData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InputDataParserImplTest {
    @InjectMocks
    private InputDataParserImpl cut;

    @Mock
    private RomanNumeralConverter romanNumeralConverter;

    @Test
    @DisplayName("Given file content, " +
            "when the parseData method is invoked, " +
            "and intergalactic units to roman numerals are detected, " +
            "then we expect the galactice units roman numeral mapping to be set")
    void testParseGalacticUnitsRomanNumeralMapping() {
        //Given
        String[] content = {"ragnarok is X"};

        //When
        InterGalacticConversionData result = cut.parseData(content);

        //Then
        assertEquals(RomanNumeral.X, result.getGalacticToRomanNumeralsMap().get("ragnarok"));
    }

    @Test
    @DisplayName("Given file content, " +
            "when the parseData method is invoked, " +
            "and intergalactic unit provided as input has an invalid roman numeral, " +
            "then we expect the message to be added to answers")
    void testInvalidRomanNumeralWhenParsingGalacticUnits() {
        //Given
        String[] content = {"kang is P"};

        //When
        InterGalacticConversionData result = cut.parseData(content);

        //Then
        assertEquals("Invalid Roman numeral provided: P", result.getAnswers().get(0));
    }

    @Test
    @DisplayName("Given file content, " +
            "when the parseData method is executed, " +
            "and the content end with Credits, " +
            "then we expect the credit conversion rate to be set for the provided metal")
    void testParseCreditConversion() {
        //Given
        String[] content = {"ragnarok is X", "ragnarok ragnarok Iron is 3910 Credits"};
        when(romanNumeralConverter.convertToNumber(anyString())).thenReturn(20);

        //When
        InterGalacticConversionData result = cut.parseData(content);

        //Then
        verify(romanNumeralConverter, times(1)).convertToNumber("XX");
        assertEquals(195.5, result.getUnitPerMetalCreditConversionMapping().get(Metal.IRON));
    }

    @Test
    @DisplayName("Given file content, " +
            "when the parseData method is executed, " +
            "and the content contains a question, " +
            "then we expect the question to be added to the list of question on the returned object")
    void testParseQuestion() {
        //Given
        String[] content = {
                "ragnarok is X",
                "ragnarok ragnarok Iron is 3910 Credits",
                "how many Credits is pish pish Iron ?"
        };
        when(romanNumeralConverter.convertToNumber(anyString())).thenReturn(20);

        //When
        InterGalacticConversionData result = cut.parseData(content);

        //Then
        assertEquals("how many Credits is pish pish Iron ?", result.getQuestions().get(0));
    }
}