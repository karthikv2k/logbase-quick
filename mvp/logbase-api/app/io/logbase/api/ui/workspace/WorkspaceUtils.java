package io.logbase.api.ui.workspace;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class WorkspaceUtils {

  public static void saveWorkspace(Workspace ws) {
    EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("$objectdb/db/points.odb");
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    em.persist(ws);
    em.getTransaction().commit();
    em.close();
    emf.close();

  }

  public static Workspace getWorkspace(String wsName) {
    EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("$objectdb/db/points.odb");
    EntityManager em = emf.createEntityManager();
    Workspace ws = em.find(Workspace.class, wsName);
    em.close();
    emf.close();
    return ws;
  }

  public static boolean isSavedWorkspace(String wsName) {
    // TODO check if DB not created!
    EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("$objectdb/db/points.odb");
    EntityManager em = emf.createEntityManager();
    Query q1 = em
        .createQuery("SELECT COUNT(w) FROM Workspace w WHERE w.name = "
            + wsName);
    Long count = (Long) q1.getSingleResult();
    em.close();
    emf.close();
    if (count > 0)
      return true;
    else
      return false;
  }

  public static void deleteWorkspace(String wsName) {
    EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("$objectdb/db/points.odb");
    EntityManager em = emf.createEntityManager();
    Workspace ws = em.find(Workspace.class, wsName);
    em.getTransaction().begin();
    em.remove(ws);
    em.getTransaction().commit();
    em.close();
    emf.close();
  }

  public static void deleteAllWorkspaces() {
    EntityManagerFactory emf = Persistence
        .createEntityManagerFactory("$objectdb/db/points.odb");
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    em.createQuery("DELETE FROM Workspace").executeUpdate();
    em.getTransaction().commit();
    em.close();
    emf.close();
  }

}
