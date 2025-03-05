package com.filipmikolajzeglen.fmzenglishtrainer.translation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class VerticalLayoutHelper
{
   public static VerticalLayout create(String width, Component... components)
   {
      VerticalLayout layout = new VerticalLayout(components);
      layout.setWidth(width);
      layout.setHeightFull();
      return layout;
   }
}
