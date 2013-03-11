package de.helmut.test.jpacache;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ManagerArquillianTest {

    @EJB
    Manager manager;

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "JpaCacheTest.war")
                .addPackage(Manager.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        return war;
    }

    @Before
    public void cleanUpJobs() {
        manager.removeParentNodes();
    }

    @Test
    @Ignore
    public void testSimpleEntity() throws Exception {
        System.out.println("Test 1 simple Entity.");
        assertNotNull(manager);

        ParentNode pdetached = new ParentNode();

        manager.saveParentNode(pdetached);

        ParentNode pread = manager.getParentNode();
        assertNotNull(pread);
    }

    @Test
    @Ignore
    public void testSimpleOneToMany() throws Exception {
        System.out.println("Test a simple OneToMany with 1 parent and 2 childs.");
        assertNotNull(manager);

        ParentNode pdetached = new ParentNode();

        ChildNode c1 = new ChildNode();
        c1.setParentNode(pdetached);
        pdetached.getChilds().add(c1);

        ChildNode c2 = new ChildNode();
        c2.setParentNode(pdetached);
        pdetached.getChilds().add(c2);

        manager.saveParentNode(pdetached);

        ParentNode pread = manager.getParentNode();
        assertEquals(2, pread.getChilds().size());
    }

    @Test
    //@Ignore
    public void testOneToManyWithDoubleChildObjects() throws Exception {
        System.out.println("Test a simple OneToMany with 1 parent and 3 childs, where 2 childs are the same object.");
        assertNotNull(manager);

        ParentNode pdetached = new ParentNode();

        ChildNode c1 = new ChildNode();
        c1.setParentNode(pdetached);
        pdetached.getChilds().add(c1);

        ChildNode c2 = new ChildNode();
        c2.setParentNode(pdetached);
        pdetached.getChilds().add(c2);
        //add the same object twice (not sure what happen)
        pdetached.getChilds().add(c2);

        //What is to be expected? 3 or 2 Rows in database table childnode?
        manager.saveParentNode(pdetached);
        //Let's see what is in the database with simple SQL
        int count = countChildNodes();
        //We get 2 rows in childnode!
        assertEquals(2, count);

        //Ok let's read with jpa and cache active
        ParentNode pread = manager.getParentNode();
        // I think we should get 2 childnotes now, right?
        assertEquals(2, pread.getChilds().size());
        //When we disable the cache in persistence.xml this test will pass.
    }

    private int countChildNodes() throws SQLException {
        int count = 0;
        DataSource ds = manager.geDataSource();
        Connection con = null;
        try {
            con = ds.getConnection();
            ResultSet result = con.createStatement().executeQuery("Select count(*) from ChildNode");
            if (result != null && result.next()) {
                count = result.getInt(1);
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return count;
    }
}
