package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder(setterPrefix = "with")
public class TranslationIrregularVerbGradeDTO {
    private String grade;
    private String date;
    private int wrongAnswers;
    private int correctAnswers;
}
