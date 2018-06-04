package pro.antonvmax.xspringbootmultipledatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.antonvmax.xspringbootmultipledatabase.entity.db1.Db1User;
import pro.antonvmax.xspringbootmultipledatabase.entity.db2.Db2User;
import pro.antonvmax.xspringbootmultipledatabase.repository.db1.Db1Repository;
import pro.antonvmax.xspringbootmultipledatabase.repository.db2.Db2Repository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XSpringBootMultipleDatabaseApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(XSpringBootMultipleDatabaseApplicationTests.class);

    @Autowired
    private Db1Repository db1Repository;

    @Autowired
    private Db2Repository db2Repository;

	@Test
	public void contextLoads() {
	}

    @Before
    public void init() {
        db1Repository.save(new Db1User("antonvmax", "descr", "sss"));
        db2Repository.save(new Db2User("antonvmax", 0L));
    }

    @Test
    public void db1Test_ShouldFindUser() {
        Db1User db1User = db1Repository.findFirstByUserid("antonvmax");
        Assert.assertNotNull(db1User);
        log.info("db1User :: " + db1User.toString());
    }

    @Test
    public void db2Test_ShouldFindUser() {
        Db2User db2User = db2Repository.findFirstByUser("antonvmax");
        Assert.assertNotNull(db2User);
        log.info("db2User :: " + db2User.toString());
    }
}
