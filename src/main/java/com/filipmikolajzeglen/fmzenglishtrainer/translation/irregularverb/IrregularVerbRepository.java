package com.filipmikolajzeglen.fmzenglishtrainer.translation.irregularverb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IrregularVerbRepository extends JpaRepository<IrregularVerb, Long>
{
   IrregularVerb findByTranslation(String translation);
   List<IrregularVerb> searchAllByTaughtTrue();
   List<IrregularVerb> searchAllByTaughtFalse();
}
