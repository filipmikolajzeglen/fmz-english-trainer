package com.filipmikolajzeglen.fmzenglishtrainer.translation;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class GradeCalculatorHelper
{
   private static final NavigableMap<Integer, String> GRADE_MAP = new TreeMap<>(Map.of(
         95, "A+", 90, "A", 80, "B+", 70, "B",
         60, "C+", 50, "C", 40, "D+", 30, "D",
         25, "F+"
   ));

   public static String calculate(int correctAnswers, int allAnswers)
   {
      if (allAnswers == 0)
      {
         throw new IllegalArgumentException("Number of all answers cannot be 0");
      }

      var percentage = (correctAnswers * 100) / allAnswers;
      return GRADE_MAP.floorEntry(percentage) != null ? GRADE_MAP.floorEntry(percentage).getValue() : "F";
   }
}
