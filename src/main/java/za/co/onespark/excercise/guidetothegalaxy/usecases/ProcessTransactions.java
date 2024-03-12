package za.co.onespark.excercise.guidetothegalaxy.usecases;

import org.springframework.stereotype.Component;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.Metal;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.RomanNumeral;
import za.co.onespark.excercise.guidetothegalaxy.common.models.InterGalacticConversionData;
import za.co.onespark.excercise.guidetothegalaxy.exceptions.FileProcessingException;
import za.co.onespark.excercise.guidetothegalaxy.utils.InputDataParser;
import za.co.onespark.excercise.guidetothegalaxy.utils.InputFileReader;
import za.co.onespark.excercise.guidetothegalaxy.utils.RomanNumeralConverter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ProcessTransactions {
    private static final String SPACE = " ";
    private static final String QUOTATIONS = "";
    private static final String QUESTION_MARK = "?";
    private static final String IS  = "is";
    private static final String HOW_MUCH = "how much is ";
    private static final String CREDITS = "Credits";
    private static final String HOW_MANY_CREDITS = "how many Credits is ";

    private final InputFileReader inputFileReader;
    private final InputDataParser inputDataParser;
    private final RomanNumeralConverter romanNumeralConverter;

    public ProcessTransactions(InputFileReader inputFileReader,
                               InputDataParser inputDataParser,
                               RomanNumeralConverter romanNumeralConverter) {
        this.inputFileReader = inputFileReader;
        this.inputDataParser = inputDataParser;
        this.romanNumeralConverter = romanNumeralConverter;
    }

    public List<String> execute(String fileName) throws IOException, FileProcessingException {
        String[] fileContent = inputFileReader.readFile(fileName);
        if (fileContent.length == 0) {
            throw new FileProcessingException(
                "The file provided has no data. Please load correct input data and try again"
            );
        }

        InterGalacticConversionData inputData = inputDataParser.parseData(fileContent);

        List<String> answers = inputData.getAnswers();
        List<String> questions = inputData.getQuestions();

        questions.forEach(question -> {
            String answer = processQuestion(question, inputData);
            answers.add(answer);
        });

        return answers;
    }

    private String processQuestion(String question, InterGalacticConversionData data) {
        String answer = "I have no idea what you are talking about";
        if (question.startsWith(HOW_MUCH)) {
            return processHowMuch(question, data);
        }

        if (question.startsWith(HOW_MANY_CREDITS)) {
            return processHowMany(question, data, answer);
        }
        return answer;
    }


    private String processHowMuch(String question, InterGalacticConversionData data) {
        Map<String, RomanNumeral> galacticToRomanNumeralMap = data.getGalacticToRomanNumeralsMap();
        String[] galacticUnits = question.replace(HOW_MUCH, QUOTATIONS).split(SPACE);

        StringBuilder romanNumerals = new StringBuilder();
        StringBuilder processedAnswer = new StringBuilder();
        for (String unit : galacticUnits) {
            if (galacticToRomanNumeralMap.containsKey(unit)) {
                romanNumerals.append(galacticToRomanNumeralMap.get(unit));
                processedAnswer.append(unit).append(" ");
                continue;
            }

            if (unit.contains(QUESTION_MARK)) {
                break;
            }
        }

        int number = romanNumeralConverter.convertToNumber(romanNumerals.toString());
        processedAnswer.append(IS).append(SPACE).append(number);

        return processedAnswer.toString();
    }

    private String processHowMany(String question, InterGalacticConversionData data, String answer) {
        Map<String, RomanNumeral> galacticToRomanNumeralMap = data.getGalacticToRomanNumeralsMap();
        Map<Metal, Double> metalCreditConversionmMap = data.getUnitPerMetalCreditConversionMapping();

        String[] galacticUnitsMetal = question.replace(HOW_MANY_CREDITS, QUOTATIONS).split(SPACE);

        StringBuilder romanNumerals = new StringBuilder();
        StringBuilder processedAnswer = new StringBuilder();
        String metalName = "";

        for (String unitMetal : galacticUnitsMetal) {
            if (galacticToRomanNumeralMap.containsKey(unitMetal)) {
                romanNumerals.append(galacticToRomanNumeralMap.get(unitMetal));
                processedAnswer.append(unitMetal).append(SPACE);
                continue;
            }

            try {
                if (unitMetal.contains(QUESTION_MARK)) {
                    break;
                }
                Metal metal = Metal.fromName(unitMetal);
                if (metalCreditConversionmMap.containsKey(metal)) {
                    metalName = unitMetal;
                    processedAnswer.append(metal.getName()).append(SPACE);
                }
            } catch (IllegalArgumentException e) {
                break;
            }
        }

        if (metalName.isEmpty()) {
            return answer;
        }

        int number = romanNumeralConverter.convertToNumber(romanNumerals.toString());
        Double metalCreditPerUnit = metalCreditConversionmMap.get(Metal.fromName(metalName));

        Integer creditCost = (int) (metalCreditPerUnit * number);

        processedAnswer.append(IS).append(SPACE)
                .append(creditCost)
                .append(SPACE)
                .append(CREDITS);

        return processedAnswer.toString();
    }
}
