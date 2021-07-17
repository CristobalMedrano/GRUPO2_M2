package two.microservice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpHeaders;
import two.microservice.model.Diplomate;
import two.microservice.model.Postulation;

import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class MicroserviceApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;

	private String getRootUrl(){
		return "http://localhost:" + port + "/api/v1";
	}


	@Test
	public void testGetDiplomate_BIO(){ //By ID ONE

		Diplomate expectedDiplomate = new Diplomate("Diplomado 1 - Editado", "https://google.cl", "Esta es la descripcion del diplomado 1 editado" );
		expectedDiplomate.setId(1);
		Diplomate gotDiplomate = restTemplate.getForObject(getRootUrl() + "/diplomates/1", Diplomate.class);

		// For results in the screen
		//System.out.println("gotDiplomate -> " + gotDiplomate.toString());
		//System.out.println("expectedDiplomate -> " + expectedDiplomate.toString());

		assertEquals(expectedDiplomate.toString(), gotDiplomate.toString());
	}

	@Test
	public void testCreatePostulationIDIO(){ // In Diplomate Id One
		Postulation Another_postulation = new Postulation("url", "si tengo", "si naci", "si me graduè",
				"AHi tan :3");
		Another_postulation.setId(6);
		Another_postulation.setValid(Boolean.TRUE);
		Another_postulation.setReceived(Boolean.FALSE);
		/*Another_postulation.setRegistrationForm("url");
		Another_postulation.setCurriculumVitae("si tengo");
		Another_postulation.setBirthCertificate("si nacì");
		Another_postulation.setGraduateCertificate("si me graduè");
		Another_postulation.setCopyIdentityCard("Ahi tan :3");
		Another_postulation.setReceived(Boolean.TRUE);
		Another_postulation.setValid(Boolean.FALSE);
		*/

		ResponseEntity<Postulation> postPostulation = restTemplate.postForEntity(getRootUrl() + "/diplomates/1/postulations",
				Another_postulation, Postulation.class);

		Postulation gotPostulation = restTemplate.getForObject(getRootUrl()+ "/diplomates/1/postulations/6", Postulation.class);

		assertEquals(gotPostulation.toString(), Another_postulation.toString());

	}


	@Test
	public void testCreateDiplomate(){

	}

}
