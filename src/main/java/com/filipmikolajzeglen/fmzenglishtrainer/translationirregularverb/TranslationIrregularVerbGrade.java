package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "translation_irregular_verb_grade", schema = "translation")
@SequenceGenerator(name = "translation_irregular_verb_grade_seq", allocationSize = 1, schema = "translation")
public class TranslationIrregularVerbGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation_irregular_verb_grade_seq")
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "wrong_answers", nullable = false)
    private int wrongAnswers;

    @Column(name = "correct_answers", nullable = false)
    private int correctAnswers;
}
