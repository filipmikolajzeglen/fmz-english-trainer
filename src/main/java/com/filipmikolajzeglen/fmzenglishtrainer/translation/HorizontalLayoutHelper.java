package com.filipmikolajzeglen.fmzenglishtrainer.translation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class HorizontalLayoutHelper
{
   public static HorizontalLayout create(String title, Component rightComponent)
   {
      return create(new Span(title), rightComponent);
   }

   public static HorizontalLayout create(String title, String content)
   {
      return create(new Span(title), new Span(content));
   }

   public static HorizontalLayout create(Component... components)
   {
      HorizontalLayout layout = new HorizontalLayout(components);
      layout.setWidthFull();
      layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
      layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
      return layout;
   }
}