test-jpacache
=============
This test show a problem with JPA Cache enabled
<shared-cache-mode>ALL</shared-cache-mode> and eclipselink.

The test "testOneToManyWithDoubleChildObjects" shows that it's possible
to get more Entitys as stored in the underlying database.
 Execute "mvn test" to see.
Run this test in the Hibernate  profile or with cache disabled it work.


