package com.filipmikolajzeglen.fmzenglishtrainer.backend.translation.irregularverb;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class IrregularVerbGradeDTOAssembler
{
   private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

   static IrregularVerbGradeDTO assemble(IrregularVerbGrade translationIrregularVerbGrade)
   {
      String dateString = formatDate(translationIrregularVerbGrade.getDate()) + "r";
      return IrregularVerbGradeDTO.builder()
            .withGrade(translationIrregularVerbGrade.getGrade())
            .withDate(dateString)
            .withWrongAnswers(translationIrregularVerbGrade.getWrongAnswers())
            .withCorrectAnswers(translationIrregularVerbGrade.getCorrectAnswers())
            .build();
   }

   private static String formatDate(Instant date)
   {
      return date.atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
   }
}
