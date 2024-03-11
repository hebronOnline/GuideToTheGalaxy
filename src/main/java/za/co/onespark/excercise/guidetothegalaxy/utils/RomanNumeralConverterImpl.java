package za.co.onespark.excercise.guidetothegalaxy.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.RomanNumeral;
import za.co.onespark.excercise.guidetothegalaxy.exceptions.InvalidRomanNumeralException;

@Component
public class RomanNumeralConverterImpl implements RomanNumeralConverter {

    @Override
    public int convertToNumber(String romanNumber) {
        RomanNumeral previousNumeral = null;
        int characterRepeatCount = 1;
        int convertedNumber = 0;

        for (int i = 0; i < romanNumber.length(); i++) {
            RomanNumeral currentDigit = RomanNumeral.valueOf(
                    StringUtils.capitalize(String.valueOf(romanNumber.charAt(i)))
            );
            int currentRomanCharNumericValue = currentDigit.getValue();

            if (currentDigit.equals(previousNumeral)) {
                characterRepeatCount++;

                if (characterRepeatCount > 3) {
                    throw new InvalidRomanNumeralException("Repeatable roman numeral is repeated more than 3 times");
                }

                if (!currentDigit.isRepeatable()) {
                    throw new InvalidRomanNumeralException("Unrepeatable roman numeral has been repeated");
                }

                convertedNumber += currentRomanCharNumericValue;
            } else if (previousNumeral != null && previousNumeral.compareTo(currentDigit) < 0) {
                if (characterRepeatCount > 1) {
                    throw new InvalidRomanNumeralException("Repeatable Digit is repeated before larger digit");
                }

                if (!previousNumeral.isSubtractableFrom(currentDigit)) {
                    throw new InvalidRomanNumeralException("Digit may not be subtracted from other digit");
                }

                if (previousNumeral.isSubtractableFrom(currentDigit)) {
                    characterRepeatCount = 1;
                    convertedNumber += currentRomanCharNumericValue - (2 * previousNumeral.getValue());
                }
            } else {
                characterRepeatCount = 1;
                convertedNumber += currentRomanCharNumericValue;
            }

            previousNumeral = currentDigit;
        }

        return convertedNumber;
    }
}
