package io.logbase.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A test JPA entity just for example.
 * 
 * @author Abishek Baskaran
 *
 */
@Entity
public class TestEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private long id;
  private String message;

  public TestEntity() {

  }

  public TestEntity(String message) {
    this.message = message;
  }

  public long getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }
}
