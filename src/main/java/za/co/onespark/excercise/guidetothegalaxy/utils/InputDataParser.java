package za.co.onespark.excercise.guidetothegalaxy.utils;


import za.co.onespark.excercise.guidetothegalaxy.common.models.InterGalacticConversionData;

public interface InputDataParser {
    InterGalacticConversionData parseData(String [] fileContent);
}
