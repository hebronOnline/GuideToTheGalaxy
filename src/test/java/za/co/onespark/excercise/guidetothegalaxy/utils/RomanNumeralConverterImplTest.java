package za.co.onespark.excercise.guidetothegalaxy.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.co.onespark.excercise.guidetothegalaxy.exceptions.InvalidRomanNumeralException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RomanNumeralConverterImplTest {
    private final RomanNumeralConverterImpl cut = new RomanNumeralConverterImpl();

    @Test
    @DisplayName("Given a roman number, " +
            "when the convertToNumber method is executed, " +
            "then we expect the roman numeral to be converted correctly")
    void testBasicNumerals() {
        //Given when
        int number = cut.convertToNumber("MCMXLIV");

        //Then
        assertEquals(1944, number);
    }

    @Test
    @DisplayName("Given a roman number, " +
            "when the convertToNumber method is executed, " +
            "and some roman numerals are repeated an acceptable number of times" +
            "then we expect the roman numeral to be converted correctly")
    void testRepeatingNumerals() {
        //Given when
        int number = cut.convertToNumber("MMMDCCXXIV");

        //Then
        assertEquals(3724, number);
    }

    @Test
    @DisplayName("Given a roman number, " +
            "when the convertToNumber method is executed, " +
            "and some roman numerals are repeated an invalid number of times" +
            "then we expect an invalid roman numeral exception to be thrown")
    void testRepeatingNumeralsError() {
        //Given when then
        assertThrows(InvalidRomanNumeralException.class, () -> cut.convertToNumber("MMMMCCXXXXXIV"));
    }

    @Test
    @DisplayName("Given a roman number, " +
            "when the convertToNumber method is executed, " +
            "and some roman numerals that are not repeatable are repeated" +
            "then we expect an invalid roman numeral exception to be thrown")
    void testNonRepeatingNumeralsError() {
        //Given when then
        assertThrows(InvalidRomanNumeralException.class, () -> cut.convertToNumber("LLDDVV"));
    }
}