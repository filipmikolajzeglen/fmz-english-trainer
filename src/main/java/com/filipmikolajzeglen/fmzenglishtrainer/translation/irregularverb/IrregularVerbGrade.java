package com.filipmikolajzeglen.fmzenglishtrainer.translation.irregularverb;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "irregular_verb_grade", schema = "translation")
public class IrregularVerbGrade
{
   @Id
   @Column(name = "id", unique = true, nullable = false)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation.irregular_verb_grade_seq_gen")
   @SequenceGenerator(name = "translation.irregular_verb_grade_seq_gen",
                      sequenceName = "translation.irregular_verb_grade_seq",
                      allocationSize = 1)
   private Long id;

   @Column(name = "grade", nullable = false)
   private String grade;

   @Column(name = "date", nullable = false)
   private Instant date;

   @Column(name = "wrong_answers", nullable = false)
   private int wrongAnswers;

   @Column(name = "correct_answers", nullable = false)
   private int correctAnswers;
}
