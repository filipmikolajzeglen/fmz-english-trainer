package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TranslationIrregularVerbRepository extends JpaRepository<TranslationIrregularVerb, Long> {
    TranslationIrregularVerb findByTranslation(String translation);

    List<TranslationIrregularVerb> searchAllByTaughtTrue();
    List<TranslationIrregularVerb> searchAllByTaughtFalse();
}
