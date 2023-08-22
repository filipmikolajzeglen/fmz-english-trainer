package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import com.vaadin.flow.component.*;
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

    private final TranslationIrregularVerbSummaryService service;
    private final Button goToExaminButton = new Button("Start exam",
            e -> UI.getCurrent().navigate("translation-irregular-verb-exam"));

    TranslationIrregularVerbSummaryView(TranslationIrregularVerbSummaryService service) {
        this.service = service;
        this.goToExaminButton.addClassName("start-exam-button");
        this.addClassName("summary-view");

        HorizontalLayout learnedLayout = createLayout("Learned irregular verbs", service.getLearnedToUnlearnedIrregularVerbsStatistics());
        Grid<TranslationIrregularVerbDTO> learnedGrid = createGrid(service.getAllLearnedIrregularVerbs());

        HorizontalLayout notLearnedLayout = createLayout("Irregular verbs to learn", goToExaminButton);
        Grid<TranslationIrregularVerbDTO> notLearnedGrid = createGrid(service.getAllUnlearnedIrregularVerbs());

        Grid<TranslationIrregularVerbGradeDTO> gradeGrid = createGradeGrid(service.getAllLatestGrades());

        String averageGrade = service.getAverageGrade();
        HorizontalLayout averageGradeLayout = createLayout("Grade Statistics", averageGrade);

        VerticalLayout verbsLayout = createLayout("65%", learnedLayout, learnedGrid, notLearnedLayout, notLearnedGrid);
        VerticalLayout gradeLayout = createLayout("34%", averageGradeLayout, gradeGrid);

        HorizontalLayout mainLayout = createLayout(verbsLayout, gradeLayout);

        add(mainLayout);
    }

    private HorizontalLayout createLayout(String title, Component rightComponent) {
        return createLayout(new Span(title), rightComponent);
    }

    private HorizontalLayout createLayout(String title, String content) {
        return createLayout(new Span(title), new Span(content));
    }

    private HorizontalLayout createLayout(Component... components) {
        HorizontalLayout layout = new HorizontalLayout(components);
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        return layout;
    }

    private VerticalLayout createLayout(String width, Component... components) {
        VerticalLayout layout = new VerticalLayout(components);
        layout.setWidth(width);
        layout.setHeightFull();
        return layout;
    }

    private Grid<TranslationIrregularVerbDTO> createGrid(List<TranslationIrregularVerbDTO> items) {
        Grid<TranslationIrregularVerbDTO> grid = new Grid<>(TranslationIrregularVerbDTO.class);
        grid.setColumns("IFrom", "IIForm", "IIIForm", "translation");
        grid.setItems(items);
        grid.setHeight("345px");
        return grid;
    }

    private Grid<TranslationIrregularVerbGradeDTO> createGradeGrid(List<TranslationIrregularVerbGradeDTO> grades) {
        Grid<TranslationIrregularVerbGradeDTO> grid = new Grid<>(TranslationIrregularVerbGradeDTO.class);
        grid.setColumns("grade", "date", "wrongAnswers", "correctAnswers");
        grid.setItems(grades);
        return grid;
    }
}