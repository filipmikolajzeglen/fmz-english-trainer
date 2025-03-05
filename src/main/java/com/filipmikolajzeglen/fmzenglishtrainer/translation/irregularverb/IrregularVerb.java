package com.filipmikolajzeglen.fmzenglishtrainer.translation.irregularverb;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "irregular_verb", schema = "translation")
class IrregularVerb
{
   @Id
   @Column(name = "id", unique = true, nullable = false)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation.irregular_verb_seq_gen")
   @SequenceGenerator(name = "translation.irregular_verb_seq_gen",
                      sequenceName = "translation.irregular_verb_seq",
                      allocationSize = 1)
   private Long id;

   @Column(name = "I_form", nullable = false)
   private String IFrom;

   @Column(name = "II_form", nullable = false)
   private String IIForm;

   @Column(name = "III_form", nullable = false)
   private String IIIForm;

   @Column(name = "translation", nullable = false)
   private String translation;

   @Column(name = "is_taught", nullable = false)
   private boolean taught;

   @Column(name = "number_of_mistakes", nullable = false)
   private int numberOfMistakes;

   @Column(name = "number_of_correct_answers", nullable = false)
   private int numberOfCorrectAnswers;

   @Column(name = "number_of_repetitions", nullable = false)
   private int numberOfRepetitions;
}