package blog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author dani
 */
public class PersistenceUtil {
    private static EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("blog");
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void generateSchema() {
        Persistence.generateSchema("blog", null);
    }

    public static void shutdownEntityManagerFactory() {
        entityManagerFactory.close();
    }
}
