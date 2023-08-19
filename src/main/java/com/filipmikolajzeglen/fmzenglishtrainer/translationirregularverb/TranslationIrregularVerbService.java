package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TranslationIrregularVerbService {

    private TranslationIrregularVerbRepository repository;

    List<TranslationIrregularVerbDTO> prepareIrregularVerbsForExam(int maxVerbsPerExam) {
        List<TranslationIrregularVerbDTO> verbs = repository.findAll().stream()
                .filter(verb -> !verb.isTaught())
                .limit(maxVerbsPerExam)
                .map(TranslationIrregularVerbMapper::mapToDTO)
                .collect(Collectors.toList());

        Collections.shuffle(verbs);
        return verbs;
    }

    List<TranslationIrregularVerbDTO> getAllIrregularVerbs() {
        return repository.findAll().stream()
                .map(TranslationIrregularVerbMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    String calculateGrade(int correctAnswers, int allAnswers) {
        if (allAnswers == 0)
            throw new IllegalArgumentException("Number of all answers cannot be 0");

        float percentage = ((float) correctAnswers / allAnswers) * 100;

        if (percentage >= 95) return "A+";
        else if (percentage >= 90) return "A";
        else if (percentage >= 80) return "B+";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C+";
        else if (percentage >= 50) return "C";
        else if (percentage >= 40) return "D+";
        else if (percentage >= 30) return "D";
        else if (percentage >= 25) return "F+";
        else return "F";
    }

}
