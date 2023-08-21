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

@Route("translation-irregular-verb")
@CssImport("../../../frontend/styles/exam-style.css")
public class TranslationIrregularVerbView extends VerticalLayout {

    private final TranslationIrregularVerbService service;

    private final Button goToExaminButton = new Button("Przejdź do egzaminu", e -> UI.getCurrent().navigate("translation-irregular-verb-exam"));

    TranslationIrregularVerbView(TranslationIrregularVerbService service) {
        this.service = service;
        this.goToExaminButton.addClassName("submit-button");
        this.addClassName("exam-view");

        String statistics = service.getLearnedToUnlearnedIrregularVerbsStatistics();
        Span learnedSpan = new Span("Wyuczone czasowniki nieregularne");
        Span statisticsSpan = new Span(statistics);
        HorizontalLayout learnedHeader = new HorizontalLayout(learnedSpan, statisticsSpan);
        learnedHeader.setWidthFull();
        learnedHeader.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Grid<TranslationIrregularVerbDTO> learnedGrid = new Grid<>(TranslationIrregularVerbDTO.class);
        learnedGrid.setColumns("IFrom", "IIForm", "IIIForm", "translation");
        List<TranslationIrregularVerbDTO> learnedVerbs = service.getAllLearnedIrregularVerbs();
        learnedGrid.setItems(learnedVerbs);
        learnedGrid.setHeight("345px");

        Span notLearnedSpan = new Span("Czasowniki nieregularne do nauki");
        goToExaminButton.getStyle().set("margin", "0");
        HorizontalLayout notLearnedHeader = new HorizontalLayout(notLearnedSpan, goToExaminButton);
        notLearnedHeader.setWidthFull();
        notLearnedHeader.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        notLearnedHeader.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        Grid<TranslationIrregularVerbDTO> notLearnedGrid = new Grid<>(TranslationIrregularVerbDTO.class);
        notLearnedGrid.setColumns("IFrom", "IIForm", "IIIForm", "translation");
        List<TranslationIrregularVerbDTO> notLearnedVerbs = service.getAllUnlearnedIrregularVerbs();
        notLearnedGrid.setItems(notLearnedVerbs);
        notLearnedGrid.setHeight("345px");

        VerticalLayout layout = new VerticalLayout(learnedHeader, learnedGrid, notLearnedHeader, notLearnedGrid);
        add(layout);
    }

}