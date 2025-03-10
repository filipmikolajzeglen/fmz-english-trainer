package com.filipmikolajzeglen.fmzenglishtrainer.backend.translation.irregularverb;

import com.filipmikolajzeglen.cqrs.Query;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Service
@RequiredArgsConstructor
class IrregularVerbQuery extends Query<IrregularVerb>
{
   private final IrregularVerbRepository repository;
   private Long id;

   @Override
   public IrregularVerb perform()
   {
      return repository.findById(id).orElseThrow();
   }
}