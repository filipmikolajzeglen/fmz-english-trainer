package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class TranslationIrregularVerbDTO {
    private String IFrom;
    private String IIForm;
    private String IIIForm;
    private String translation;
    private int numberOfMistakes;
    private int numberOfCorrectAnswers;
    private int numberOfRepetitions;
    private boolean taught;

    public void incrementNumberOfMistakes() {
        this.numberOfMistakes += 1;
    }

    public void incrementNumberOfCorrectAnswers() {
        this.numberOfCorrectAnswers += 1;
    }

    public void incrementNumberOfRepetitions() {
        this.numberOfRepetitions += 1;
    }
}
