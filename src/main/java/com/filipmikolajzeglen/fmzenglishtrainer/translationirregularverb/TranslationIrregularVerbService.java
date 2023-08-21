package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TranslationIrregularVerbService {

    private TranslationIrregularVerbRepository repository;
    private TranslationIrregularVerbGradeRepository gradeRepository;

    List<TranslationIrregularVerbDTO> prepareIrregularVerbsForExam(int maxVerbsPerExam) {
        List<TranslationIrregularVerb> verbs = getVerbsForExam(maxVerbsPerExam);
        List<TranslationIrregularVerbDTO> list = convertToDTOList(verbs);
        Collections.shuffle(list);
        return list;
    }

    private List<TranslationIrregularVerb> getVerbsForExam(int maxVerbsPerExam) {
        List<TranslationIrregularVerb> verbs = new ArrayList<>();
        int repetitionsCount = 0;

        while (verbs.size() < maxVerbsPerExam) {
            List<TranslationIrregularVerb> fetchedVerbs = fetchVerbs(repetitionsCount, maxVerbsPerExam - verbs.size());
            verbs.addAll(fetchedVerbs);
            repetitionsCount++;
        }
        return verbs;
    }

    private List<TranslationIrregularVerb> fetchVerbs(int repetitionsCount, int limit) {
        return repository.searchAllByTaughtFalse().stream()
                .filter(verb -> verb.getNumberOfRepetitions() == repetitionsCount)
                .limit(limit)
                .toList();
    }

    private List<TranslationIrregularVerbDTO> convertToDTOList(List<TranslationIrregularVerb> verbs) {
        return verbs.stream()
                .map(TranslationIrregularVerbMapper::mapToDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    List<TranslationIrregularVerbDTO> getAllUnlearnedIrregularVerbs() {
        return repository.searchAllByTaughtFalse().stream()
                .map(TranslationIrregularVerbMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    List<TranslationIrregularVerbDTO> getAllLearnedIrregularVerbs() {
        return repository.searchAllByTaughtTrue().stream()
                .map(TranslationIrregularVerbMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    String getLearnedToUnlearnedIrregularVerbsStatistics() {
        int learned = repository.searchAllByTaughtTrue().size();
        int unlearned = repository.searchAllByTaughtFalse().size();
        return String.format("%s learned / %s to learn", learned, unlearned);
    }

    void updateVerbStatus(TranslationIrregularVerbDTO verbDTO) {
        TranslationIrregularVerb verb = repository.findByTranslation(verbDTO.getTranslation());
        verb.setNumberOfMistakes(verbDTO.getNumberOfMistakes());
        verb.setNumberOfCorrectAnswers(verbDTO.getNumberOfCorrectAnswers());
        verb.setNumberOfRepetitions(verbDTO.getNumberOfRepetitions());

        if (verb.getNumberOfCorrectAnswers() >= 3 &&
                verb.getNumberOfMistakes() < (verb.getNumberOfCorrectAnswers() + 3)) {
            verb.setTaught(true);
        }

        repository.save(verb);
    }

    void saveSummaryOfExam(String grade, int correctAnswers, int allAnswers) {
        gradeRepository.save(TranslationIrregularVerbGrade.builder()
                .withGrade(grade)
                .withCorrectAnswers(correctAnswers)
                .withWrongAnswers(allAnswers)
                .withDate(Instant.now())
                .build()
        );
    }

    List<TranslationIrregularVerbGradeDTO> getAllLatestGrades() {
        return gradeRepository.findAll().stream()
                .sorted(Comparator.comparing(TranslationIrregularVerbGrade::getDate).reversed())
                .map(TranslationIrregularVerbGradeMapper::mapToDTO)
                .toList();
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
