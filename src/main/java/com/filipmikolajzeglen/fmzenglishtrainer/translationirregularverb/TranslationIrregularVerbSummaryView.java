package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("translation-irregular-verb-summary")
@CssImport("../../../frontend/styles/exam-style.css")
public class TranslationIrregularVerbSummaryView extends VerticalLayout {

    private final TranslationIrregularVerbService service;

    private final Button goToExaminButton = new Button("Start exam", e -> UI.getCurrent().navigate("translation-irregular-verb-exam"));

    TranslationIrregularVerbSummaryView(TranslationIrregularVerbService service) {
        this.service = service;
        this.goToExaminButton.addClassName("submit-button");
        this.addClassName("summary-view");

        String statistics = service.getLearnedToUnlearnedIrregularVerbsStatistics();
        Span learnedTitleSpan = new Span("Learned irregular verbs");
        Span statisticsSpan = new Span(statistics);
        HorizontalLayout learnedLayout = new HorizontalLayout(learnedTitleSpan, statisticsSpan);
        learnedLayout.setWidthFull();
        learnedLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Grid<TranslationIrregularVerbDTO> learnedGrid = new Grid<>(TranslationIrregularVerbDTO.class);
        learnedGrid.setColumns("IFrom", "IIForm", "IIIForm", "translation");
        List<TranslationIrregularVerbDTO> learnedVerbs = service.getAllLearnedIrregularVerbs();
        learnedGrid.setItems(learnedVerbs);
        learnedGrid.setHeight("345px");

        Span notLearnedTitleSpan = new Span("Irregular verbs to learn");
        goToExaminButton.getStyle().set("margin", "0");
        HorizontalLayout notLearnedLayout = new HorizontalLayout(notLearnedTitleSpan, goToExaminButton);
        notLearnedLayout.setWidthFull();
        notLearnedLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        notLearnedLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        Grid<TranslationIrregularVerbDTO> notLearnedGrid = new Grid<>(TranslationIrregularVerbDTO.class);
        notLearnedGrid.setColumns("IFrom", "IIForm", "IIIForm", "translation");
        List<TranslationIrregularVerbDTO> notLearnedVerbs = service.getAllUnlearnedIrregularVerbs();
        notLearnedGrid.setItems(notLearnedVerbs);
        notLearnedGrid.setHeight("345px");

        Grid<TranslationIrregularVerbGradeDTO> gradeGrid = new Grid<>(TranslationIrregularVerbGradeDTO.class);
        gradeGrid.setColumns("grade", "date", "wrongAnswers", "correctAnswers");

        List<TranslationIrregularVerbGradeDTO> grades = service.getAllLatestGrades();
        gradeGrid.setItems(grades);

        VerticalLayout verbsLayout = new VerticalLayout(learnedLayout, learnedGrid, notLearnedLayout, notLearnedGrid);
        verbsLayout.setWidth("65%");

        String averageGrade = service.getAverageGrade();
        Span gradeTitleSpan = new Span("Grade Statistics");
        Span averageGradesSpan = new Span(averageGrade);
        HorizontalLayout averageGradeLayout = new HorizontalLayout(gradeTitleSpan, averageGradesSpan);
        averageGradeLayout.setWidthFull();
        averageGradeLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        VerticalLayout gradeLayout = new VerticalLayout(averageGradeLayout, gradeGrid);
        gradeLayout.setWidth("34%");

        HorizontalLayout mainLayout = new HorizontalLayout(verbsLayout, gradeLayout);
        mainLayout.setWidth("100%");

        add(mainLayout);
    }

}