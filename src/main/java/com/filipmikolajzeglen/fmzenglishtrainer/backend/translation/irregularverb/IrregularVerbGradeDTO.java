package com.filipmikolajzeglen.fmzenglishtrainer.backend.translation.irregularverb;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
public class IrregularVerbGradeDTO
{
   private String grade;
   private String date;
   private int wrongAnswers;
   private int correctAnswers;
}
