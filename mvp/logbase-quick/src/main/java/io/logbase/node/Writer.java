package io.logbase.node;

import io.logbase.event.Event;

public interface Writer<E extends Event> {

  public void write(E event);

}
