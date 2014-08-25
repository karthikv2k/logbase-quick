package io.logbase.collections;

/**
 * Created by Kousik on 25/08/14.
 */
public interface LBBitSet {
  /**
   * Sets the bit at the given index
   * @param index
   */
  public void set(int index);

  /**
   * Gets the boolean value at the given index
   * @param index
   * @return
   */
  public boolean get(int index);

  public int size();

}
