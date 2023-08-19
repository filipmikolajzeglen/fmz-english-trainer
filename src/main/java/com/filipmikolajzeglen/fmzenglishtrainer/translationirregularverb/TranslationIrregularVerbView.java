package com.filipmikolajzeglen.fmzenglishtrainer.translationirregularverb;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("translation-irregular-verb")
public class TranslationIrregularVerbView extends VerticalLayout {

    private final TranslationIrregularVerbService service;

    TranslationIrregularVerbView(TranslationIrregularVerbService service) {
        this.service = service;

        Grid<TranslationIrregularVerbDTO> grid = new Grid<>(TranslationIrregularVerbDTO.class);
        grid.setColumns("IFrom", "IIForm", "IIIForm", "translation");
        add(grid);

        List<TranslationIrregularVerbDTO> verbs = service.getAllIrregularVerbs();
        grid.setItems(verbs);
    }

}