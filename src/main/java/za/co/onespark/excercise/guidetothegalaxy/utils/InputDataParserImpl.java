package za.co.onespark.excercise.guidetothegalaxy.utils;

import za.co.onespark.excercise.guidetothegalaxy.common.enums.RomanNumeral;
import za.co.onespark.excercise.guidetothegalaxy.exceptions.InvalidRomanNumeralException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputDataParserImpl implements InputDataParser {
    private static final String IS = "is";
    private static final String SPACE = " ";
    private static final String QUESTION_MARK = "?";
    private static final String HOW_MUCH = "how much";
    private static final String HOW_MANY = "how many";
    private static final String CREDITS = "Credits";


    @Override
    public Object parseData(String[] fileContent) {
        Map<String, RomanNumeral> galacticToRomanNumeralsMap = new HashMap<>();
        List<String> answers = new ArrayList<>();

        for (int i = 0; i < fileContent.length; i++) {
            String currentLine =  fileContent[i];

            if (currentLine.split(SPACE).length == 3 && currentLine.contains(IS)) {
                String[] galacticToRomanArray = currentLine.split(SPACE);
                try {
                    galacticToRomanNumeralsMap.put(
                            galacticToRomanArray[0],
                            RomanNumeral.valueOf(galacticToRomanArray[2])
                    );
                } catch (InvalidRomanNumeralException e) {
                    answers.add("Invalid Roman numeral provided, " + e.getMessage());
                }
            }

            if (currentLine.endsWith(CREDITS)) {
                List<String> galacticNumerals = new ArrayList<>();

            }

            if (isCurrentLineAQuestion(currentLine)) {
                //TODO::do something
            }
        }
        return null;
    }

    private boolean isCurrentLineAQuestion(String currentLine) {
        return (currentLine.startsWith(HOW_MUCH) || currentLine.startsWith(HOW_MANY))
                && currentLine.endsWith(QUESTION_MARK);
    }
}
