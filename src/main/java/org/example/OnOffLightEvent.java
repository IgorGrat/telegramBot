package org.example;

import lombok.Getter;

import java.util.EventObject;

@Getter
public class OnOffLightEvent extends EventObject{
  /**
   * Constructs a prototypical Event.
   *
   * @param source The object on which the Event initially occurred.
   * @throws IllegalArgumentException if source is null.
   */
  private final boolean isOn;
  private final long lastedMinute;

  public OnOffLightEvent(Object source, boolean isOn, long lastedMinute) {
    super(source);
    this.isOn = isOn;
    this.lastedMinute = lastedMinute;
  }
}
