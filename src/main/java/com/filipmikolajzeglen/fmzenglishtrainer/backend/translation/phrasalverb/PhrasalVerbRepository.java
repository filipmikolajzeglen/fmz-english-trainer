package com.filipmikolajzeglen.fmzenglishtrainer.backend.translation.phrasalverb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PhrasalVerbRepository extends JpaRepository<PhrasalVerb, Long>
{
}