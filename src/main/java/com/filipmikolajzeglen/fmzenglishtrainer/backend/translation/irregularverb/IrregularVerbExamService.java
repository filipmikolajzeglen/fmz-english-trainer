package com.filipmikolajzeglen.fmzenglishtrainer.backend.translation.irregularverb;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.filipmikolajzeglen.fmzenglishtrainer.backend.translation.GradeCalculatorHelper;
import com.vaadin.flow.component.textfield.TextField;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IrregularVerbExamService
{
   private IrregularVerbRepository repository;
   private IrregularVerbGradeRepository gradeRepository;

   List<IrregularVerbDTO> prepareIrregularVerbsForExam(int maxVerbsPerExam)
   {
      List<IrregularVerb> verbs = getVerbsForExam(maxVerbsPerExam);
      List<IrregularVerbDTO> list = convertToDTOList(verbs);
      Collections.shuffle(list);
      return list;
   }

   private List<IrregularVerb> getVerbsForExam(int maxVerbsPerExam)
   {
      List<IrregularVerb> verbs = new ArrayList<>();
      int repetitionsCount = 0;

      while (verbs.size() < maxVerbsPerExam)
      {
         List<IrregularVerb> fetchedVerbs = fetchVerbs(repetitionsCount, maxVerbsPerExam - verbs.size());
         verbs.addAll(fetchedVerbs);
         repetitionsCount++;
      }
      return verbs;
   }

   private List<IrregularVerb> fetchVerbs(int repetitionsCount, int limit)
   {
      return repository.searchAllByTaughtFalse().stream()
            .filter(verb -> verb.getNumberOfRepetitions() == repetitionsCount)
            .limit(limit)
            .toList();
   }

   private List<IrregularVerbDTO> convertToDTOList(List<IrregularVerb> verbs)
   {
      return verbs.stream()
            .map(IrregularVerbDTOAssembler::assemble)
            .collect(Collectors.toCollection(ArrayList::new));
   }

   public String checkAnswers(Map<IrregularVerbDTO, TextField> fields,
         List<IrregularVerbDTO> verbs)
   {
      int correctAnswers = 0;
      int allAnswers = verbs.size();

      for (final IrregularVerbDTO verb : verbs)
      {
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

         if (isCorrect)
         {
            correctAnswers++;
            textField.setInvalid(false);
            verb.incrementNumberOfCorrectAnswers();
         }
         else
         {
            textField.setValue(verb.getTranslation());
            textField.setInvalid(true);
            verb.incrementNumberOfMistakes();
         }
         verb.incrementNumberOfRepetitions();
         updateVerbStatus(verb);
      }

      final String grade = GradeCalculatorHelper.calculate(correctAnswers, allAnswers);
      saveSummaryOfExam(grade, correctAnswers, allAnswers - correctAnswers);

      return grade;
   }

   void updateVerbStatus(IrregularVerbDTO verbDTO)
   {
      IrregularVerb verb = repository.findByTranslation(verbDTO.getTranslation());
      verb.setNumberOfMistakes(verbDTO.getNumberOfMistakes());
      verb.setNumberOfCorrectAnswers(verbDTO.getNumberOfCorrectAnswers());
      verb.setNumberOfRepetitions(verbDTO.getNumberOfRepetitions());

      if (verb.getNumberOfCorrectAnswers() >= 3 &&
            verb.getNumberOfMistakes() < (verb.getNumberOfCorrectAnswers() + 3))
      {
         verb.setTaught(true);
      }

      repository.save(verb);
   }

   void saveSummaryOfExam(String grade, int correctAnswers, int allAnswers)
   {
      gradeRepository.save(IrregularVerbGrade.builder()
            .withGrade(grade)
            .withCorrectAnswers(correctAnswers)
            .withWrongAnswers(allAnswers)
            .withDate(Instant.now())
            .build()
      );
   }

}
