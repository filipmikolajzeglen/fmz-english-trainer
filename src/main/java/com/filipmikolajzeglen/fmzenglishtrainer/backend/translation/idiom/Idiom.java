package com.filipmikolajzeglen.fmzenglishtrainer.backend.translation.idiom;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "idiom", schema = "translation")
class Idiom
{
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "translation.idiom_seq_gen")
   @SequenceGenerator(name = "translation.idiom_seq_gen",
                      sequenceName = "translation.idiom_seq",
                      allocationSize = 1)
   @Column(name = "id", unique = true, nullable = false)
   private Long id;

   @Column(name = "idiom", nullable = false)
   private String idiom;

   @Column(name = "translation", nullable = false)
   private String translation;
}
