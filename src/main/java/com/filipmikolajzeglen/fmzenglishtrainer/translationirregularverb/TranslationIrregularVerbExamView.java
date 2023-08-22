package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb.TranslationIrregularVerbExamConfiguration.MAX_VERBS_PER_EXAM;

@Route("translation-irregular-verb-exam")
@CssImport("../../../frontend/styles/exam-style.css")
public class TranslationIrregularVerbExamView extends VerticalLayout {
    private final TranslationIrregularVerbExamService service;
    private final List<TranslationIrregularVerbDTO> verbs;
    private final Map<TranslationIrregularVerbDTO, TextField> fields = new HashMap<>();
    private ButtonsLayout buttonsLayout;

    public TranslationIrregularVerbExamView(TranslationIrregularVerbExamService service) {
        this.service = service;
        this.verbs = service.prepareIrregularVerbsForExam(MAX_VERBS_PER_EXAM);
        this.addClassName("exam-view");
        AtomicInteger verbIndex = new AtomicInteger(1);

        for (TranslationIrregularVerbDTO verb : verbs) {
            add(createLayoutForVerb(verb, verbIndex.getAndIncrement()));
        }

        buttonsLayout = new ButtonsLayout(
                click -> {
                    String grade = service.checkAnswers(this.fields, this.verbs);
                    displayResults(grade);
                },
                click -> UI.getCurrent().navigate("translation-irregular-verb-summary")
        );

        add(buttonsLayout);
    }

    private HorizontalLayout createLayoutForVerb(TranslationIrregularVerbDTO verb, int index) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("center-align-items");
        layout.addClassName("table-row");

        Span indexSpan = new Span(index + ". ");
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

        return layout;
    }

    private void displayResults(String grade) {
        int correctAnswers = (int) this.fields.values()
                .stream()
                .filter(text -> !text.isInvalid())
                .count();

        int allAnswers = this.fields.size();

        Dialog resultDialog = new Dialog();
        VerticalLayout resultsLayout = new VerticalLayout();
        resultsLayout.add(new Span("Correct answers:  " + correctAnswers));
        resultsLayout.add(new Span("Total questions:  " + allAnswers));
        resultsLayout.add(new Span("Grade:  " + grade));

        resultDialog.add(resultsLayout);
        resultDialog.open();
    }

    @Getter
    static class ButtonsLayout extends HorizontalLayout {
        private final Button submitButton;
        private final Button summaryButton;

        public ButtonsLayout(ComponentEventListener<ClickEvent<Button>> submitButtonClickListener,
                             ComponentEventListener<ClickEvent<Button>> summaryButtonClickListener) {

            this.submitButton = new Button("Submit exam", submitButtonClickListener);
            this.summaryButton = new Button("Summary board", summaryButtonClickListener);

            this.submitButton.addClassName("submit-button");
            this.summaryButton.addClassName("summary-button");

            this.summaryButton.getStyle().set("margin-right", "-200px");

            this.setWidthFull();
            this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            this.setSpacing(false);

            add(this.summaryButton, this.submitButton);
        }

    }
}