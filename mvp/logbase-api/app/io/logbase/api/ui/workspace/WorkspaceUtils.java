package io.logbase.api.ui.workspace;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class WorkspaceUtils {

  private static EntityManagerFactory emf = Persistence
      .createEntityManagerFactory("$objectdb/db/points.odb");

  public static void saveWorkspace(Workspace ws) {
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    em.persist(ws);
    em.getTransaction().commit();
    em.close();
  }

  public static Workspace getWorkspace(String wsName) {
    EntityManager em = emf.createEntityManager();
    Workspace ws = em.find(Workspace.class, wsName);
    em.close();
    return ws;
  }

  public static boolean isSavedWorkspace(String wsName) {
    // TODO check if DB not created!
    EntityManager em = emf.createEntityManager();
    String sql = "SELECT COUNT(w) FROM Workspace w WHERE w.name = '" + wsName
        + "'";
    Query q1 = em.createQuery(sql);
    Long count = (Long) q1.getSingleResult();
    em.close();
    if (count > 0)
      return true;
    else
      return false;
  }

  public static void deleteWorkspace(String wsName) {
    EntityManager em = emf.createEntityManager();
    Workspace ws = em.find(Workspace.class, wsName);
    em.getTransaction().begin();
    em.remove(ws);
    em.getTransaction().commit();
    em.close();
  }

  public static void deleteAllWorkspaces() {
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    em.createQuery("DELETE FROM Workspace").executeUpdate();
    em.getTransaction().commit();
    em.close();
  }

}
