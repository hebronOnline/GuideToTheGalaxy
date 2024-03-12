package za.co.onespark.excercise.guidetothegalaxy.common.models;

import za.co.onespark.excercise.guidetothegalaxy.common.enums.Metal;
import za.co.onespark.excercise.guidetothegalaxy.common.enums.RomanNumeral;

import java.util.List;
import java.util.Map;

public class InterGalacticConversionData {
    private Map<String, RomanNumeral> galacticToRomanNumeralsMap;
    private Map<Metal, Double> unitPerMetalCreditConversionMapping;
    private List<String> answers;
    private List<String> questions;

    public Map<Metal, Double> getUnitPerMetalCreditConversionMapping() {
        return unitPerMetalCreditConversionMapping;
    }

    public void setUnitPerMetalCreditConversionMapping(Map<Metal, Double> unitPerMetalCreditConversionMapping) {
        this.unitPerMetalCreditConversionMapping = unitPerMetalCreditConversionMapping;
    }

    public Map<String, RomanNumeral> getGalacticToRomanNumeralsMap() {
        return galacticToRomanNumeralsMap;
    }

    public void setGalacticToRomanNumeralsMap(Map<String, RomanNumeral> galacticToRomanNumeralsMap) {
        this.galacticToRomanNumeralsMap = galacticToRomanNumeralsMap;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
}
