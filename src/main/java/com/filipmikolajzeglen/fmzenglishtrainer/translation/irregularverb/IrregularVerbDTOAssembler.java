package com.filipmikolajzeglen.fmzenglishtrainer.translation.irregularverb;

class IrregularVerbDTOAssembler
{
   static IrregularVerbDTO assemble(IrregularVerb translationIrregularVerb)
   {
      return IrregularVerbDTO.builder()
            .withIFrom(translationIrregularVerb.getIFrom())
            .withIIForm(translationIrregularVerb.getIIForm())
            .withIIIForm(translationIrregularVerb.getIIIForm())
            .withTranslation(translationIrregularVerb.getTranslation())
            .withNumberOfMistakes(translationIrregularVerb.getNumberOfMistakes())
            .withNumberOfCorrectAnswers(translationIrregularVerb.getNumberOfCorrectAnswers())
            .withNumberOfRepetitions(translationIrregularVerb.getNumberOfRepetitions())
            .build();
   }
}
