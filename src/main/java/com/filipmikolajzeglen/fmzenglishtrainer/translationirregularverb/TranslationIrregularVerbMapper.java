package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

class TranslationIrregularVerbMapper {

    static TranslationIrregularVerbDTO mapToDTO(TranslationIrregularVerb translationIrregularVerb) {
        return TranslationIrregularVerbDTO.builder()
                .withIFrom(translationIrregularVerb.getIFrom())
                .withIIForm(translationIrregularVerb.getIIForm())
                .withIIIForm(translationIrregularVerb.getIIIForm())
                .withTranslation(translationIrregularVerb.getTranslation())
                .build();
    }
}
