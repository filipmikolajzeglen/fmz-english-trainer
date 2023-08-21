package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TranslationIrregularVerbGradeMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    static TranslationIrregularVerbGradeDTO mapToDTO(TranslationIrregularVerbGrade translationIrregularVerbGrade) {
        String dateString = formatDate(translationIrregularVerbGrade.getDate()) + "r";
        return TranslationIrregularVerbGradeDTO.builder()
                .withGrade(translationIrregularVerbGrade.getGrade())
                .withDate(dateString)
                .withWrongAnswers(translationIrregularVerbGrade.getWrongAnswers())
                .withCorrectAnswers(translationIrregularVerbGrade.getCorrectAnswers())
                .build();
    }

    private static String formatDate(Instant date) {
        return date.atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
    }
}
