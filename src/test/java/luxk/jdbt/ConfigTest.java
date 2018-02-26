package luxk.jdbt;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import luxk.jdbt.config.Config;
import luxk.jdbt.config.ConfigException;
import luxk.jdbt.config.ConfigXMLParser;
import luxk.jdbt.config.Database;

public class ConfigTest {
	
	private Config c;

	@Before
	public void setUp() throws Exception {
		URL url = ConfigTest.class.getResource("config_v1_test.xml");
		ConfigXMLParser p = new ConfigXMLParser();
		this.c = p.parseConfig(url.getPath());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConfigMain() throws Exception {
		
		Database d1 = c.getDatabase("default");
		assertNotNull("check 'default' database is not null", d1);
		assertEquals("check 'default' database driver", "luxk.dbt.mockjdbc.MockDriver", d1.getDriver());
		assertEquals("check 'default' database url", "jdbc:xxx:thin:@1.1.1.10:10000:zzzz", d1.getUrl());
		assertEquals("check 'default' database user", "sys", d1.getUser());
		assertEquals("check 'default' database password", "sys_pwd", d1.getPassword());
		assertEquals("check 'default' database version", "6.0.5.1.12345", d1.getVersion());
		assertTrue("check 'default' database reconnect error code -90409", d1.isReconnectableError(-90409));
		assertTrue("check 'default' database reconnect error code -90401", d1.isReconnectableError(-90401));
		assertTrue("check 'default' database reconnect error code -90405", d1.isReconnectableError(-90405));
		assertTrue("check 'default' database reconnect error code -90403", d1.isReconnectableError(-90403));
		assertTrue("check 'default' database reconnect error code -90407", d1.isReconnectableError(-90407));
		assertTrue("check 'default' database reconnect error code -90406", d1.isReconnectableError(-90406));
		assertFalse("check 'default' database reconnect error code -90410(false)", d1.isReconnectableError(-90410));
	
		Database d2 = c.getDatabase("db1");
		assertNotNull("check 'db1' database is not null", d2);
		assertNull("check 'db1' database driver is null", d2.getDriver());
		assertEquals("check 'db1' database url", "jdbc:xxx:thin:@10.1.1.20:20100:zzz", d2.getUrl());

		d2 = c.getDatabase("db2");
		assertNotNull("check 'db2' database is not null", d2);
		assertNull("check 'db2' database driver is null", d2.getDriver());
		assertEquals("check 'db2' database url", "jdbc:xxx:thin:@10.1.1.21:20200:zzz", d2.getUrl());
		
		d2 = c.getDatabase("another_db");
		assertNotNull("check 'another_db' database is not null", d2);
		assertEquals("check 'another_db' database version", "12.1.0.1.0", d2.getVersion());
		assertEquals("check 'another_db' database prop name1", "value1", d2.getProp("name1"));
		assertEquals("check 'another_db' database prop name2", "value2", d2.getProp("name2"));
		assertNull("check 'another_db' database prop name3(null)", d2.getProp("name3"));

		assertEquals("check user param test.duration", "60s", c.getParam("test.duration"));
	}

	@Test(expected=ConfigException.class)
	public void testParamException() throws Exception {
		c.setParam(null, "detail");
	}
}
