package io.logbase.buffer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: karthik
 */
public class GenericOffHeapBufferTest {
  @Test
  public void testGetBytes() throws Exception {

  }

  @Test
  public void testGetLong() throws Exception {
    GenericOffHeapBuffer buffer = new GenericOffHeapBuffer(2*8);
    byte[] buf =buffer.buf.array();
    buffer.setLong(0,Long.MAX_VALUE);
    buffer.setLong(1,Long.MIN_VALUE);
    assertEquals(Long.MAX_VALUE, buffer.getLong(0));
    assertEquals(Long.MIN_VALUE, buffer.getLong(1));
  }

}
