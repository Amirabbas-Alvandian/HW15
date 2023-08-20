package example.Connection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerClass {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    javax.persistence.EntityManager em = emf.createEntityManager();
    public javax.persistence.EntityManager returnEm() {
        return em;
    }
}
