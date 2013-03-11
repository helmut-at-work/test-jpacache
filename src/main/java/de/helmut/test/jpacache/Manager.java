package de.helmut.test.jpacache;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Stateless
public class Manager {

    @Resource
    private DataSource JPACACHETEST;
    @PersistenceContext
    EntityManager em;

    public void saveParentNode(ParentNode p) {
        em.persist(p);
    }

    public ParentNode getParentNode() {
        return (ParentNode) em.createQuery("Select p from ParentNode p").getSingleResult();
    }

    public void removeParentNodes() {
        //em.createQuery("DELETE FROM ParentNode").executeUpdate();
        //long id = 0;
        //ParentNode p = em.find(ParentNode.class, id);

        List<ParentNode> plist = em.createQuery("SELECT p FROM ParentNode p").getResultList();
        for (ParentNode p : plist) {
            em.remove(p);
        }
    }

    public void saveSimple(Simple s) {
        em.persist(s);
    }

    public DataSource geDataSource() {
        return this.JPACACHETEST;
    }
}
