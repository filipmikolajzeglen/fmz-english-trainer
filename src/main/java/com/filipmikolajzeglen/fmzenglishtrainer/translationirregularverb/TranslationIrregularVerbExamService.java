package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import com.vaadin.flow.component.textfield.TextField;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TranslationIrregularVerbExamService {

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

    public String checkAnswers(Map<TranslationIrregularVerbDTO, TextField> fields, List<TranslationIrregularVerbDTO> verbs) {
        int correctAnswers = 0;
        int allAnswers = verbs.size();

        for (final TranslationIrregularVerbDTO verb : verbs) {
            final TextField textField = fields.get(verb);
            final String userTranslation = textField.getValue().toUpperCase();

            final String userTranslationAfterClean = userTranslation.replace("SIĘ", "").trim();
            final String[] correctTranslations = verb.getTranslation().split("[/,]");
            final boolean isCorrect = Arrays.stream(correctTranslations)
                    .map(correctTranslation -> correctTranslation.replace("SIĘ", ""))
                    .map(correctTranslation -> correctTranslation.replaceAll("\\(.*?\\)", ""))
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .toList()
                    .contains(userTranslationAfterClean);

            if (isCorrect) {
                correctAnswers++;
                textField.setInvalid(false);
                verb.incrementNumberOfCorrectAnswers();
            } else {
                textField.setValue(verb.getTranslation());
                textField.setInvalid(true);
                verb.incrementNumberOfMistakes();
            }
            verb.incrementNumberOfRepetitions();
            updateVerbStatus(verb);
        }

        final String grade = calculateGrade(correctAnswers, allAnswers);
        saveSummaryOfExam(grade, correctAnswers, allAnswers - correctAnswers);

        return grade;
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

    String calculateGrade(int correctAnswers, int allAnswers) {
        if (allAnswers == 0) {
            throw new IllegalArgumentException("Number of all answers cannot be 0");
        }

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

    void saveSummaryOfExam(String grade, int correctAnswers, int allAnswers) {
        gradeRepository.save(TranslationIrregularVerbGrade.builder()
                .withGrade(grade)
                .withCorrectAnswers(correctAnswers)
                .withWrongAnswers(allAnswers)
                .withDate(Instant.now())
                .build()
        );
    }

}
