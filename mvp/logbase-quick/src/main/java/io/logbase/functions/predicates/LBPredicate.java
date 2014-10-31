package io.logbase.functions.predicates;

/**
 * Created by Kousik on 27/10/14.
 */
public interface LBPredicate{

  /**
   * Evaluates this predicate on the given double value.
   *
   * @param value
   * @return
   */
  public default boolean apply(double value) {
    assert(false);
    return false;
  };

  /**
   * Evaluates this predicate on the given long value.
   *
   * @param value
   * @return
   */
  public default boolean apply(long value) {
    assert(false);
    return false;
  };

  /**
   * Evaluates this predicate on the given float value.
   *
   * @param value
   * @return
   */
  public default boolean apply(float value) {
    assert(false);
    return false;
  };

  /**
   * Evaluates this predicate on the given int value.
   *
   * @param value
   * @return
   */
  public default boolean apply(int value) {
    assert(false);
    return false;
  };

  /**
   * Evaluates this predicate on the given String value.
   * @return
   */
  public default boolean apply(String value) {
    assert(false);
    return false;
  }

  public default boolean isNull() {
    return false;
  }
}
