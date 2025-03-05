package com.filipmikolajzeglen.fmzenglishtrainer.translation.irregularverb;

import java.util.List;

import com.filipmikolajzeglen.fmzenglishtrainer.translation.HorizontalLayoutHelper;
import com.filipmikolajzeglen.fmzenglishtrainer.translation.VerticalLayoutHelper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("irregular-verb-summary")
@CssImport("../../../frontend/styles/exam-style.css")
public class IrregularVerbSummaryView extends VerticalLayout
{
   IrregularVerbSummaryView(IrregularVerbSummaryService service)
   {
      var examinButton = new Button("Start exam", e -> UI.getCurrent().navigate("irregular-verb-exam"));
      examinButton.addClassName("start-exam-button");
      this.addClassName("summary-view");

      var learnedLayout = HorizontalLayoutHelper.create("Learned irregular verbs", service.getLearnedToUnlearnedIrregularVerbsStatistics());
      var learnedGrid = createGrid(service.getAllLearnedIrregularVerbs());

      var notLearnedLayout = HorizontalLayoutHelper.create("Irregular verbs to learn", examinButton);
      var notLearnedGrid = createGrid(service.getAllUnlearnedIrregularVerbs());

      var gradeGrid = createGradeGrid(service.getAllLatestGrades());

      var averageGrade = service.getAverageGrade();
      var averageGradeLayout = HorizontalLayoutHelper.create("Grade Statistics", averageGrade);

      var verbsLayout = VerticalLayoutHelper.create("65%", learnedLayout, learnedGrid, notLearnedLayout, notLearnedGrid);
      var gradeLayout = VerticalLayoutHelper.create("34%", averageGradeLayout, gradeGrid);

      var mainLayout = HorizontalLayoutHelper.create(verbsLayout, gradeLayout);

      add(mainLayout);
   }

   private Grid<IrregularVerbDTO> createGrid(List<IrregularVerbDTO> items)
   {
      Grid<IrregularVerbDTO> grid = new Grid<>(IrregularVerbDTO.class);
      grid.setColumns("IFrom", "IIForm", "IIIForm", "translation");
      grid.setItems(items);
      grid.setHeight("345px");
      return grid;
   }

   private Grid<IrregularVerbGradeDTO> createGradeGrid(List<IrregularVerbGradeDTO> grades)
   {
      Grid<IrregularVerbGradeDTO> grid = new Grid<>(IrregularVerbGradeDTO.class);
      grid.setColumns("grade", "date", "wrongAnswers", "correctAnswers");
      grid.setItems(grades);
      return grid;
   }
}