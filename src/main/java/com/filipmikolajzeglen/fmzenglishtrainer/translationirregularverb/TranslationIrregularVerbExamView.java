package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb.TranslationIrregularVerbExamConfiguration.MAX_VERBS_PER_EXAM;

@Route("translation-irregular-verb-exam")
@CssImport("../../../frontend/styles/exam-style.css")
public class TranslationIrregularVerbExamView extends VerticalLayout {
    private final TranslationIrregularVerbService service;
    private final List<TranslationIrregularVerbDTO> verbs;
    private final Map<TranslationIrregularVerbDTO, TextField> fields = new HashMap<>();
    private final Button submitButton = new Button("Zatwierdź egzamin");

    public TranslationIrregularVerbExamView(TranslationIrregularVerbService service) {
        this.service = service;
        this.verbs = service.prepareIrregularVerbsForExam(MAX_VERBS_PER_EXAM);
        this.addClassName("exam-view");
        this.submitButton.addClassName("submit-button");
        AtomicInteger verbIndex = new AtomicInteger(1);

        for (TranslationIrregularVerbDTO verb : verbs) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.addClassName("center-align-items");
            layout.addClassName("table-row");
            Span indexSpan = new Span(verbIndex.getAndIncrement() + ". ");
            Span ifromSpan = new Span(verb.getIFrom());
            Span iiformSpan = new Span(verb.getIIForm());
            Span iiiformSpan = new Span(verb.getIIIForm());

            indexSpan.addClassName("fixed-index-width-span");
            ifromSpan.addClassName("fixed-width-span");
            iiformSpan.addClassName("fixed-width-span");
            iiiformSpan.addClassName("fixed-width-span");

            layout.add(indexSpan, ifromSpan, iiformSpan, iiiformSpan);

            TextField translationInput = new TextField();
            fields.put(verb, translationInput);
            layout.add(translationInput);

            add(layout);
        }

        submitButton.addClickListener(click -> checkAnswers());
        add(submitButton);
    }

    private void checkAnswers() {
        int correctAnswers = 0;
        int allAnswers = verbs.size();

        for (TranslationIrregularVerbDTO verb : verbs) {
            TextField textField = fields.get(verb);
            String userTranslation = textField.getValue().toUpperCase();

            String userTranslationAfterClean = userTranslation.replace("SIĘ", "").trim();
            String[] correctTranslations = verb.getTranslation().split("[/,]");
            boolean isCorrect = Arrays.stream(correctTranslations)
                    .map(correctTranslation -> correctTranslation.replace("SIĘ", ""))
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .toList()
                    .contains(userTranslationAfterClean);

            if (isCorrect) {
                correctAnswers++;
                textField.setInvalid(false);
            } else {
                textField.setValue(verb.getTranslation());
                textField.setInvalid(true);
            }
        }

        String grade = service.calculateGrade(correctAnswers, allAnswers);
        Dialog resultDialog = new Dialog();
        VerticalLayout resultsLayout = new VerticalLayout();
        resultsLayout.add(new Span("Correct answers:  " + correctAnswers));
        resultsLayout.add(new Span("Total questions:  " + allAnswers));
        resultsLayout.add(new Span("Grade:  " + grade));

        resultDialog.add(resultsLayout);
        resultDialog.open();
    }

}