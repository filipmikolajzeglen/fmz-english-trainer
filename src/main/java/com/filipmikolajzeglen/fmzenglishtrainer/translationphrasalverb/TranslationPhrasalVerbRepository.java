package com.filipmikolajzeglen.fmzenglishtrainer.translationphrasalverb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TranslationPhrasalVerbRepository extends JpaRepository<TranslationPhrasalVerb, Long> {
}
