package br.com.digitalparking;


import br.com.digitalparking.shared.annotation.DatabaseTest;
import br.com.digitalparking.shared.annotation.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@IntegrationTest
@DatabaseTest
class DigitalParkingApplicationTest {

	private final ApplicationContext applicationContext;

	@Autowired
	DigitalParkingApplicationTest(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Test
	void contextLoads() {
		var digitalParkingApplication = applicationContext.getBean(DigitalParkingApplication.class);
		Assertions.assertThat(digitalParkingApplication).isNotNull();
	}

}
