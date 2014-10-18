package io.logbase.utils.TimeStamp;

import io.logbase.event.Event;

/**
 * Created by Kousik on 18/10/14.
 */
public interface TimeStampExtractor<E extends Event> {

  public long timestamp(E event);

}
