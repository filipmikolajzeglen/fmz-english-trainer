package com.filipmikolajzeglen.fmzenglishtrainer.backend.translation.irregularverb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

@Route("irregular-verb-exam")
@CssImport("../../../frontend/styles/exam-style.css")
public class IrregularVerbExamView extends VerticalLayout
{
   private static final int MAX_VERBS_PER_EXAM = 12;

   private final List<IrregularVerbDTO> verbs;
   private final Map<IrregularVerbDTO, TextField> fields = new HashMap<>();

   public IrregularVerbExamView(IrregularVerbExamService service)
   {
      this.verbs = service.prepareIrregularVerbsForExam(MAX_VERBS_PER_EXAM);
      this.addClassName("exam-view");
      var verbIndex = new AtomicInteger(1);

      for (IrregularVerbDTO verb : verbs)
      {
         add(createLayoutForVerb(verb, verbIndex.getAndIncrement()));
      }

      var buttonsLayout = new ButtonsLayout(
            click -> {
               String grade = service.checkAnswers(this.fields, this.verbs);
               displayResults(grade);
            },
            click -> UI.getCurrent().navigate("irregular-verb-summary")
      );

      add(buttonsLayout);
   }

   private HorizontalLayout createLayoutForVerb(IrregularVerbDTO verb, int index)
   {
      HorizontalLayout layout = new HorizontalLayout();
      layout.addClassName("center-align-items");
      layout.addClassName("table-row");

      var indexSpan = new Span(index + ". ");
      var ifromSpan = new Span(verb.getIFrom());
      var iiformSpan = new Span(verb.getIIForm());
      var iiiformSpan = new Span(verb.getIIIForm());

      indexSpan.addClassName("fixed-index-width-span");
      ifromSpan.addClassName("fixed-width-span");
      iiformSpan.addClassName("fixed-width-span");
      iiiformSpan.addClassName("fixed-width-span");

      layout.add(indexSpan, ifromSpan, iiformSpan, iiiformSpan);

      var translationInput = new TextField();
      fields.put(verb, translationInput);

      layout.add(translationInput);

      return layout;
   }

   private void displayResults(String grade)
   {
      int correctAnswers = (int) this.fields.values()
            .stream()
            .filter(text -> !text.isInvalid())
            .count();

      int allAnswers = this.fields.size();

      var resultDialog = new Dialog();
      VerticalLayout resultsLayout = new VerticalLayout();
      resultsLayout.add(new Span("Correct answers:  " + correctAnswers));
      resultsLayout.add(new Span("Total questions:  " + allAnswers));
      resultsLayout.add(new Span("Grade:  " + grade));

      resultDialog.add(resultsLayout);
      resultDialog.open();
   }

   @Getter
   static class ButtonsLayout extends HorizontalLayout
   {
      private final Button submitButton;
      private final Button summaryButton;

      public ButtonsLayout(ComponentEventListener<ClickEvent<Button>> submitButtonClickListener,
            ComponentEventListener<ClickEvent<Button>> summaryButtonClickListener)
      {

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