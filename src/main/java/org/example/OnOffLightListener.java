package org.example;

import java.util.AbstractList;
import java.util.EventListener;

public interface OnOffLightListener extends EventListener{
  void  onOffLightInvoke(OnOffLightEvent event);
}
