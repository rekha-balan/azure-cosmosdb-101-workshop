package org.cjoakim.azure.cosmos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppConfigTests {

	@Test
	public void getDocDbAcct() {

		String value = AppConfig.getDocDbAcct();
		System.out.println(value);
		assertNotNull(value);
	}

	@Test
	public void getDocDbKey() {

		String value = AppConfig.getDocDbKey();
		System.out.println(value);
		assertNotNull(value);
	}

	@Test
	public void getDocDbDefaultDbName() {

		String value = AppConfig.getDocDbDefaultDbName();
		System.out.println(value);
		assertNotNull(value);
	}

	@Test
	public void getDocDbDefaultCollName() {

		String value = AppConfig.getDocDbDefaultCollName();
		System.out.println(value);
		assertNotNull(value);
	}

	@Test
	public void getOpenWeatherMapKey() {

		String value = AppConfig.getOpenWeatherMapKey();
		System.out.println(value);
		assertNotNull(value);
	}
}
