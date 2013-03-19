test-jpacache
=============
See this thread:
http://eclipse.1072660.n5.nabble.com/If-JPA-Cache-is-enabled-it-s-possible-to-get-more-Entitys-as-stored-in-the-underlying-database-td158489.html

This test show a problem with JPA Cache enabled
<shared-cache-mode>ALL</shared-cache-mode> and eclipselink.

The test "testOneToManyWithDoubleChildObjects" shows that it's possible
to get more Entitys as stored in the underlying database.
 Execute "mvn test" to see.
Run this test in the Hibernate  profile or with cache disabled it work.

Solution
===
Add the refresh to get parentNode:

public ParentNode getParentNode() {
        ParentNode p = (ParentNode) em.createQuery("Select p from ParentNode p").getSingleResult();
        em.refresh(p);
        return p;
}

