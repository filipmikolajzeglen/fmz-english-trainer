package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class TranslationIrregularVerbSummaryService {

    private TranslationIrregularVerbRepository repository;
    private TranslationIrregularVerbGradeRepository gradeRepository;

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

    List<TranslationIrregularVerbGradeDTO> getAllLatestGrades() {
        return gradeRepository.findAll().stream()
                .sorted(Comparator.comparing(TranslationIrregularVerbGrade::getDate).reversed())
                .map(TranslationIrregularVerbGradeMapper::mapToDTO)
                .toList();
    }

    String getAverageGrade() {
        List<String> grades = gradeRepository.findAll().stream()
                .sorted(Comparator.comparing(TranslationIrregularVerbGrade::getDate).reversed())
                .map(TranslationIrregularVerbGrade::getGrade)
                .limit(10)
                .toList();

        return String.format("Average grade: %s", calculateAverageGrade(grades));
    }

    String calculateAverageGrade(List<String> grades) {
        if (grades == null || grades.isEmpty()) {
            return "There are no grades yet";
        }

        double sum = grades.stream()
                .mapToDouble(this::gradeToNumeric)
                .sum();

        double average = sum / grades.size();

        return numericToGrade(average);
    }

    double gradeToNumeric(String grade) {
        return switch (grade) {
            case "A+" -> 97;
            case "A" -> 93;
            case "B+" -> 87;
            case "B" -> 80;
            case "C+" -> 77;
            case "C" -> 73;
            case "D+" -> 67;
            case "D" -> 63;
            case "F+" -> 30;
            default -> 25;
        };
    }

    String numericToGrade(double score) {
        if (score >= 95) return "A+";
        else if (score >= 90) return "A";
        else if (score >= 85) return "B+";
        else if (score >= 80) return "B";
        else if (score >= 75) return "C+";
        else if (score >= 70) return "C";
        else if (score >= 65) return "D+";
        else if (score >= 60) return "D";
        else if (score >= 30) return "F+";
        else return "F";
    }

}
