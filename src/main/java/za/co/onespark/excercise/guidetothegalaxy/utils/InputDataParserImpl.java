package za.co.onespark.excercise.guidetothegalaxy.utils;

import org.springframework.stereotype.Component;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.Metal;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.RomanNumeral;
import za.co.onespark.excercise.guidetothegalaxy.common.models.InterGalacticConversionData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InputDataParserImpl implements InputDataParser {
    private static final String IS = "is";
    private static final String SPACE = " ";
    private static final String QUESTION_MARK = "?";
    private static final String HOW_MUCH = "how much";
    private static final String HOW_MANY = "how many";
    private static final String CREDITS = "Credits";

    private final RomanNumeralConverter romanNumeralConverter;

    public InputDataParserImpl(RomanNumeralConverter romanNumeralConverter) {
        this.romanNumeralConverter = romanNumeralConverter;
    }

    @Override
    public InterGalacticConversionData parseData(String[] fileContent) {
        Map<String, RomanNumeral> galacticToRomanNumeralsMap = new HashMap<>();
        List<String> answers = new ArrayList<>();
        List<String> questions = new ArrayList<>();
        Map<Metal, Double> unitPerMetalCreditConversionMapping = new HashMap<>();

        for (String currentLine : fileContent) {
            if (currentLine.split(SPACE).length == 3 && currentLine.contains(IS)) {
                String[] galacticToRomanArray = currentLine.split(SPACE);
                try {
                    galacticToRomanNumeralsMap.put(
                            galacticToRomanArray[0],
                            RomanNumeral.valueOf(galacticToRomanArray[2])
                    );
                } catch (IllegalArgumentException e) {
                    answers.add("Invalid Roman numeral provided: " + galacticToRomanArray[2]);
                }
            }

            if (currentLine.endsWith(CREDITS)) {
                String[] galacticUnitsToCreditsLineArray = currentLine.split(SPACE);
                StringBuilder romanConversion = new StringBuilder();
                int metalIndex = 0;

                for (String currentArrayValue : galacticUnitsToCreditsLineArray) {
                    if (galacticToRomanNumeralsMap.containsKey(currentArrayValue)) {
                        galacticToRomanNumeralsMap.get(currentArrayValue);
                        romanConversion.append(galacticToRomanNumeralsMap.get(currentArrayValue));
                        metalIndex++;
                    } else {
                        break;
                    }
                }

                double galacticCredits = getGalacticCredits(galacticUnitsToCreditsLineArray);

                double units = romanNumeralConverter.convertToNumber(romanConversion.toString());
                Metal metal = Metal.fromName(galacticUnitsToCreditsLineArray[metalIndex]);
                Double creditsValuePerGalacticUnit = galacticCredits / units;

                unitPerMetalCreditConversionMapping.put(metal, creditsValuePerGalacticUnit);
            }

            if (isCurrentLineAQuestion(currentLine)) {
                questions.add(currentLine);
            }
        }

        InterGalacticConversionData data = new InterGalacticConversionData();
        data.setAnswers(answers);
        data.setQuestions(questions);
        data.setUnitPerMetalCreditConversionMapping(unitPerMetalCreditConversionMapping);
        data.setGalacticToRomanNumeralsMap(galacticToRomanNumeralsMap);
        return data;
    }

    private int getGalacticCredits(String[] galacticUnitsToCreditsLineArray) {
        int galacticCredits = 0;
        for (String currentArrayValue : galacticUnitsToCreditsLineArray) {
            try {
                int credits = Integer.parseInt(currentArrayValue);
                if (credits > 0) {
                    galacticCredits = credits;
                    break;
                }
            } catch (NumberFormatException e) {
                //Continue
            }
        }
        return galacticCredits;
    }

    private boolean isCurrentLineAQuestion(String currentLine) {
        return (currentLine.startsWith(HOW_MUCH) || currentLine.startsWith(HOW_MANY))
                && currentLine.endsWith(QUESTION_MARK);
    }
}
