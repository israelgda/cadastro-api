package com.israelgda.cadastroapi.utils;

import com.israelgda.cadastroapi.services.exceptions.BirthDateInvalidFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class VerificadorDataUtil {

    public static String verificaData(String data){
        String dateFormat = "dd/MM/uuuu";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate date = LocalDate.parse(data, dateTimeFormatter);
            return data;
        } catch (DateTimeParseException e) {
            throw new BirthDateInvalidFormatException("");
        }
    }

}
