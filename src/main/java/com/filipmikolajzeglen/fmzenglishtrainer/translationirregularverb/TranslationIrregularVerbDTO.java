package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
public class TranslationIrregularVerbDTO {
    private String IFrom;
    private String IIForm;
    private String IIIForm;
    private String translation;
}
