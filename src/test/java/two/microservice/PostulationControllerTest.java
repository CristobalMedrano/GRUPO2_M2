package two.microservice;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
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

import java.util.Objects;

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
    private String getMainSection(){
        return "/diplomates";
    }
    private String getSubSecction() { return "/postulations"; }


    // Obteniendo todas las postulaciones hechas para el primer diplomado
    @Test
    public void testGetAllPostulations_BIS() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + getMainSection(),
                HttpMethod.GET, entity, String.class);
        Diplomate[] diplomates = new Gson().fromJson(response.getBody(), Diplomate[].class);
        long diplomateId = diplomates[0].getId();


        ResponseEntity<String> postulationResponse = restTemplate.exchange(getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction(),
                HttpMethod.GET, entity, String.class);

        Assertions.assertNotNull(postulationResponse.getBody());
    }

    // Se obtiene la primera postulación y se muestra su id y su forma de ingreso.
    @Test
    public void testGetPostulationById_IDS() {

        // Se obtiene la id de algun diplomado.
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + getMainSection(),
                HttpMethod.GET, entity, String.class);
        Diplomate[] diplomates = new Gson().fromJson(response.getBody(), Diplomate[].class);
        long diplomateId = diplomates[0].getId();

        HttpHeaders postulationHeaders = new HttpHeaders();
        HttpEntity<String> postulationEntity = new HttpEntity<>(null, postulationHeaders);
        ResponseEntity<String> postulationResponse = restTemplate.exchange(
                getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction(),
                HttpMethod.GET,
                postulationEntity, String.class);
        Postulation[] postulations = new Gson().fromJson(postulationResponse.getBody(), Postulation[].class);
        long postulationId = postulations[0].getId();

        Postulation postulation = restTemplate.getForObject(
                getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction() + postulationId,
                Postulation.class);
        System.out.println("id: " + postulation.getId() +"/"+ postulation.getRegistrationForm());
        Assertions.assertNotNull(postulation);
    }


    @Test
    public void testPostingPostulationIDS() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + getMainSection(),
                HttpMethod.GET, entity, String.class);
        Diplomate[] diplomates = new Gson().fromJson(response.getBody(), Diplomate[].class);
        long diplomateId = diplomates[0].getId();
        //System.out.println("id: diplomado: "+ diplomateId);
        // Creation of a new Postulation
        Postulation AnotherPostulation = new Postulation("url", "IDHO", "IDHO", "IDHO", "IDHO");
        AnotherPostulation.setReceived(false);
        AnotherPostulation.setValid(false);


        ResponseEntity<Postulation> postPostulation = restTemplate.postForEntity(
                getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction(),
                AnotherPostulation,
                Postulation.class);

        // Se rescata la Id seteada por el posteo.
        AnotherPostulation.setId(Objects.requireNonNull(postPostulation.getBody()).getId());
        //System.out.println("id de la postulacion: "+ postPostulation.getBody().getId());

        // Test for verify if the post success
        Postulation gotPostulation = restTemplate.getForObject(getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction() +"/"+postPostulation.getBody().getId(), Postulation.class);

        Assertions.assertEquals(gotPostulation.toString(), AnotherPostulation.toString());

        restTemplate.delete(getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction() +"/"+postPostulation.getBody().getId());

    }

    @Test
    public void testUpdatingPostulationIDS() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + getMainSection(),
                HttpMethod.GET, entity, String.class);
        Diplomate[] diplomates = new Gson().fromJson(response.getBody(), Diplomate[].class);
        long diplomateId = diplomates[0].getId();

        // Creation of a new Postulation
        Postulation AnotherPostulation = new Postulation("url", "IDHO", "IDHO", "IDHO", "IDHO");
        AnotherPostulation.setReceived(false);
        AnotherPostulation.setValid(false);


        ResponseEntity<Postulation> postPostulation = restTemplate.postForEntity(
                getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction() ,
                AnotherPostulation,
                Postulation.class);

        // Se rescata la Id seteada por el posteo.
        AnotherPostulation.setId(Objects.requireNonNull(postPostulation.getBody()).getId());
        //System.out.println("Id de la postulacion para editar: "+ postPostulation.getBody().getId());

        // Test for verify if the post success
        Postulation gotPostulation = restTemplate.getForObject(
                getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction()  +"/"+postPostulation.getBody().getId(),
                Postulation.class);

        // Section of setters

        gotPostulation.setReceived(true);
        gotPostulation.setValid(true);
        gotPostulation.setRegistrationForm("Vía URL");

        // post the updated
        restTemplate.put(
                getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction()  + "/" + postPostulation.getBody().getId(),
                gotPostulation);

        Postulation updatedPostulation = restTemplate.getForObject(
                getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction()  + "/" + postPostulation.getBody().getId(),
                Postulation.class);

        //System.out.println("ID de la postulacion editada: "+ updatedPostulation.getId());
        Assertions.assertEquals(updatedPostulation.toString(), gotPostulation.toString());

        restTemplate.delete(getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction()  + "/"+ AnotherPostulation.getId());

    }

    @Test
    public void testDeletePostulationFromDS() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + getMainSection(),
                HttpMethod.GET, entity, String.class);
        Diplomate[] diplomates = new Gson().fromJson(response.getBody(), Diplomate[].class);
        long diplomateId = diplomates[0].getId();

        // Creation of a new Postulation
        Postulation AnotherPostulation = new Postulation("url", "IDHO", "IDHO", "IDHO", "IDHO");
        AnotherPostulation.setReceived(false);
        AnotherPostulation.setValid(false);

        // Post for Another Postulation
        ResponseEntity<Postulation> postResponse = restTemplate.postForEntity(
                getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction() , AnotherPostulation,
                Postulation.class);


        Postulation postulation = restTemplate.getForObject(
                getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction()  + "/" + Objects.requireNonNull(postResponse.getBody()).getId(),
                Postulation.class);
        Assertions.assertNotNull(postulation);
        restTemplate.delete(getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction()+ "/" + postResponse.getBody().getId());
        try {
            restTemplate.getForObject(getRootUrl() + getMainSection() + "/" + diplomateId  + getSubSecction() + "/" + postResponse.getBody().getId(), Postulation.class);
        } catch (final HttpClientErrorException e) {
            Assertions.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
