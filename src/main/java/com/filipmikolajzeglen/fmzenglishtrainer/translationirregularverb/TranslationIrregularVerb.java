package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "translation_irregular_verb", schema = "translation")
@SequenceGenerator(name = "translation_irregular_verb_seq", allocationSize = 1, schema = "translation")
class TranslationIrregularVerb {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_irregular_verb_seq")
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "I_form", nullable = false)
    private String IFrom;

    @Column(name = "II_form", nullable = false)
    private String IIForm;

    @Column(name = "III_form", nullable = false)
    private String IIIForm;

    @Column(name = "translation", nullable = false)
    private String translation;

    @Column(name = "is_taught", nullable = false, columnDefinition = "boolean default false")
    private boolean taught;

    @Column(name = "number_of_mistakes", nullable = false, columnDefinition = "int default 0")
    private int numberOfMistakes;

    @Column(name = "number_of_repetitions", nullable = false, columnDefinition = "int default 0")
    private int numberOfRepetitions;
}