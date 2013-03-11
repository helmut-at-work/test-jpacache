/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.helmut.test.jpacache;

import java.util.List;
import java.util.Properties;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class ManagerTest {

    public static final String JNDI_MANAGER = "java:global/test-jpacache/Manager!de.helmut.test.jpacache.Manager";
    EJBContainer container;
    Manager instance;
    Properties jndiprops;

    public ManagerTest() throws NamingException {
        jndiprops = new Properties();
        //jndiprops.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.core.LocalInitialContextFactory");

        jndiprops.put("JPACACHETEST", "new://Resource?type=DataSource");
        jndiprops.put("JPACACHETEST.JdbcDriver", "org.apache.derby.jdbc.ClientDriver");
        jndiprops.put("JPACACHETEST.JdbcUrl", "jdbc:derby://localhost/memory:jpatest;create=true");
        jndiprops.put("JPACACHETEST.UserName", "app");
        jndiprops.put("JPACACHETEST.Password", "app");
        jndiprops.put("JPACACHETEST.JtaManaged", "true");


    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws NamingException {
        container = EJBContainer.createEJBContainer(jndiprops);
        //container = javax.ejb.embeddable.EJBContainer.createEJBContainer(jndiprops);
        final Context context = container.getContext();
        instance = (Manager) context.lookup(JNDI_MANAGER);
    }

    //@After
    public void tearDown() {
        container.close();
    }

    @Test
    @Ignore
    public void testSimple() {
        Simple s = new Simple();

        instance.saveSimple(s);


    }

    /**
     * Test of saveParentNode method, of class Manager.
     */
    @Test
    @Ignore
    public void testSaveParentNode() throws Exception {
        System.out.println("removeParentNodes");
        instance.removeParentNodes();

        System.out.println("saveParentNode");
        ParentNode p = new ParentNode();
        instance.saveParentNode(p);

        System.out.println("getParentNode");
        ParentNode expResult = null;
        ParentNode result = instance.getParentNode();
        assertEquals(expResult, result);

    }
}
