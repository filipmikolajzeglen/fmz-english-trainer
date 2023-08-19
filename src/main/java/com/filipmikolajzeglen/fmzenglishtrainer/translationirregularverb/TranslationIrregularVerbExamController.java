package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/exam", produces = APPLICATION_JSON_VALUE)
public class TranslationIrregularVerbExamController {

    private TranslationIrregularVerbService service;
    private TranslationIrregularVerbRepository repository;

    @GetMapping(value = "/question/all")
    ResponseEntity<List<TranslationIrregularVerbDTO>> getAllIrregularVerbs() {
        return ok(service.getAllIrregularVerbs());
    }

    @GetMapping(value = "/question")
    ResponseEntity<List<TranslationIrregularVerbDTO>> getExamIrregularVerbs() {
        return ok(service.prepareIrregularVerbsForExam(TranslationIrregularVerbExamConfiguration.MAX_VERBS_PER_EXAM));
    }
}
