package two.microservice;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import two.microservice.model.Diplomate;
import two.microservice.model.Postulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostulationControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    private String getRootUrl(){
        return "http://localhost:" + port + "/api/v1";
    }
    private String getSection(){
        return "/diplomates/6/postulations";
    }


    // Obteniendo todas las postulaciones hechas para el diplomado.id -> 6
    @Test
    public void testGetAllPostulations_BIS() { // By Id Six
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + getSection(),
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    // Se obtiene la primera postulación y se muestra su id y su forma de ingreso.
    @Test
    public void testGetPostulationById_IDS() { // In Diplomate Six
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + getSection(),
                HttpMethod.GET, entity, String.class);
        Postulation[] postulations = new Gson().fromJson(response.getBody(), Postulation[].class);



        Postulation postulation = restTemplate.getForObject(getRootUrl() + getSection() +"/" +postulations[0].getId(), Postulation.class);
        System.out.println("id: " + postulations[0].getId() +"/"+ postulation.getRegistrationForm());
        assertNotNull(postulation);
    }


    @Test
    public void testPostingPostulationIDS() {
        // Creation of a new Postulation
        Postulation AnotherPostulation = new Postulation("url", "IDHO", "IDHO", "IDHO", "IDHO");
        AnotherPostulation.setReceived(false);
        AnotherPostulation.setValid(false);


        ResponseEntity<Postulation> postPostulation = restTemplate.postForEntity(getRootUrl() + getSection(), AnotherPostulation, Postulation.class);

        // Se rescata la Id seteada por el posteo.
        AnotherPostulation.setId(postPostulation.getBody().getId());

        // Test for verify if the post success
        Postulation gotPostulation = restTemplate.getForObject(getRootUrl() + getSection() +"/"+postPostulation.getBody().getId(), Postulation.class);

        assertEquals(gotPostulation.toString(), AnotherPostulation.toString());

        restTemplate.delete(getRootUrl() + getSection() + "/"+ gotPostulation.getId());

    }

    @Test
    public void testUpdatingPostulationIDS() { // In Diplomate Six
        // Creation of a new Postulation
        Postulation AnotherPostulation = new Postulation("url", "IDHO", "IDHO", "IDHO", "IDHO");
        AnotherPostulation.setReceived(false);
        AnotherPostulation.setValid(false);


        ResponseEntity<Postulation> postPostulation = restTemplate.postForEntity(getRootUrl() + getSection(), AnotherPostulation, Postulation.class);

        // Se rescata la Id seteada por el posteo.
        AnotherPostulation.setId(postPostulation.getBody().getId());

        // Test for verify if the post success
        Postulation gotPostulation = restTemplate.getForObject(getRootUrl() + getSection() +"/"+postPostulation.getBody().getId(), Postulation.class);

        // Section of setters

        gotPostulation.setReceived(true);
        gotPostulation.setValid(true);
        gotPostulation.setRegistrationForm("Vía URL");

        // post the updated
        restTemplate.put(getRootUrl() + getSection() + "/" + postPostulation.getBody().getId(), gotPostulation);

        Postulation updatedPostulation = restTemplate.getForObject(getRootUrl() + getSection() + "/" + postPostulation.getBody().getId(), Postulation.class);

        assertEquals(updatedPostulation.toString(), gotPostulation.toString());

        restTemplate.delete(getRootUrl() + getSection() + "/"+ AnotherPostulation.getId());

    }

    @Test
    public void testDeletePostulationFromDS() { // Diplomate Six
        // Creation of a new Postulation
        Postulation AnotherPostulation = new Postulation("url", "IDHO", "IDHO", "IDHO", "IDHO");
        AnotherPostulation.setReceived(false);
        AnotherPostulation.setValid(false);

        // Post for Another Postulation
        ResponseEntity<Postulation> postResponse = restTemplate.postForEntity(getRootUrl() + getSection(), AnotherPostulation, Postulation.class);


        Postulation postulation = restTemplate.getForObject(getRootUrl() +  getSection() + "/" + postResponse.getBody().getId(), Postulation.class);
        assertNotNull(postulation);
        restTemplate.delete(getRootUrl() +  getSection() + "/" + postResponse.getBody().getId());
        try {
            restTemplate.getForObject(getRootUrl() +  getSection() + "/" + postResponse.getBody().getId(), Postulation.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
