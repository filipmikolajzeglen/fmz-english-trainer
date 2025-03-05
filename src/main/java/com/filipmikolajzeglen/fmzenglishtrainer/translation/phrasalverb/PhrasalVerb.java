package com.filipmikolajzeglen.fmzenglishtrainer.translation.phrasalverb;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "phrasal_verb", schema = "translation")
class PhrasalVerb
{
   @Id
   @Column(name = "id", unique = true, nullable = false)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation.phrasal_verb_seq_gen")
   @SequenceGenerator(name = "translation.phrasal_verb_seq_gen",
                      sequenceName = "translation.phrasal_verb_seq",
                      allocationSize = 1)
   private Long id;

   @Column(name = "phrasal_verb", nullable = false)
   private String phrasalVerb;

   @Column(name = "translation", nullable = false)
   private String translation;
}
