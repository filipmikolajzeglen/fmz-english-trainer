package com.filipmikolajzeglen.fmzenglishtrainer.translationphrasalverb;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "translation_phrasal_verb", schema= "translation")
@SequenceGenerator(name = "translation_phrasal_verb_seq", allocationSize = 1, schema = "translation")
class TranslationPhrasalVerb {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_phrasal_verb_seq")
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "phrasal_verb", nullable = false)
    private String phrasalVerb;

    @Column(name = "translation", nullable = false)
    private String translation;

    @Column(name = "is_taught", nullable = false)
    private boolean taught = false;

    @Column(name = "number_of_mistakes", nullable = false)
    private int numberOfMistakes = 0;

    @Column(name = "number_of_correct_answers", nullable = false, columnDefinition = "int default 0")
    private int numberOfCorrectAnswers;

    @Column(name = "number_of_repetitions", nullable = false)
    private int numberOfRepetitions = 0;
}
