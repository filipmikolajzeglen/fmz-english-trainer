package com.filipmikolajzeglen.fmzenglishtrainer.translationidiom;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "translation_idiom", schema = "translation")
@SequenceGenerator(name = "translation_idiom_seq", allocationSize = 1, schema = "translation")
class TranslationIdiom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_idiom_seq")
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "idiom", nullable = false)
    private String idiom;

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
