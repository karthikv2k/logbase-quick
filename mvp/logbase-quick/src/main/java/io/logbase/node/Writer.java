package io.logbase.node;

import io.logbase.datamodel.Event;

public interface Writer<E extends Event> {

  public void write(E event);

}
