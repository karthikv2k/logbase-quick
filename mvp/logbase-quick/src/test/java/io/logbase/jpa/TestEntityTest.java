package io.logbase.jpa;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A test to checkout if JPA is working
 * 
 * @author Abishek Baskaran
 *
 */
public class TestEntityTest {

  static final Logger logger = LoggerFactory.getLogger(TestEntityTest.class);

  @Test
  public void test() {
    EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("$objectdb/db/points.odb");
    EntityManager em = emf.createEntityManager();
    TestEntity te = new TestEntity("Just a test");
    em.getTransaction().begin();
    em.persist(te);
    em.getTransaction().commit();
    Query q1 = em.createQuery("SELECT COUNT(t) FROM TestEntity t");
    Long count = (Long) q1.getSingleResult();
    logger.info("No. of messages in TestEntity: " + count);
    assertTrue("Table should not be empty", count > 0);
  }

}
