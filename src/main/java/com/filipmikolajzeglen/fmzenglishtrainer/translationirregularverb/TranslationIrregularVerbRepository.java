package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TranslationIrregularVerbRepository extends JpaRepository<TranslationIrregularVerb, Long> {
}
