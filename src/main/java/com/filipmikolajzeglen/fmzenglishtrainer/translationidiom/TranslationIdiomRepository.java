package com.filipmikolajzeglen.fmzenglishtrainer.translationidiom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TranslationIdiomRepository extends JpaRepository<TranslationIdiom, Long> {
}
