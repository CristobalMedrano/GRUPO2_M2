package two.microservice;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.gson.Gson;
import org.springframework.web.client.HttpClientErrorException;
import two.microservice.model.Diplomate;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiplomateControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    private String getRootUrl(){
        return "http://localhost:" + port + "/api/v1";
    }
    private String getSection(){
        return "/diplomates";
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllDiplomates() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + getSection(),
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

//    Diplomate data = new Gson().fromJson(response.getBody(), Diplomate.class);
    @Test
    public void testGetDiplomateById() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + getSection(),
                HttpMethod.GET, entity, String.class);
        Diplomate[] diplomates = new Gson().fromJson(response.getBody(), Diplomate[].class);

        Diplomate diplomate = restTemplate.getForObject(getRootUrl() + getSection() +"/" +diplomates[0].getId(), Diplomate.class);
        System.out.println(diplomate.getTitle());
        assertNotNull(diplomate);
    }

    @Test
    public void testCreateDiplomate() {
        Diplomate newDiplomate = new Diplomate("Diplomado posteado por un test para crear",
                "https://www.inlingua.com/wp-content/uploads/2018/05/language-courses-companies-test.svg",
                "Nueva descripción hecha *nuevamente*");


        ResponseEntity<Diplomate> postResponse = restTemplate.postForEntity(getRootUrl() + getSection() , newDiplomate, Diplomate.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        restTemplate.delete(getRootUrl() +  getSection() + "/" + postResponse.getBody().getId());
    }

    @Test
    public void testUpdateDiplomate() {
        Diplomate newDiplomate = new Diplomate("Diplomado posteado por un test para editar",
                "https://www.inlingua.com/wp-content/uploads/2018/05/language-courses-companies-test.svg",
                "Nueva descripción hecha *nuevamente*");


        ResponseEntity<Diplomate> postResponse = restTemplate.postForEntity(getRootUrl() + getSection() , newDiplomate, Diplomate.class);

        Diplomate employee = restTemplate.getForObject(getRootUrl() + getSection() + "/" + postResponse.getBody().getId(), Diplomate.class);
        employee.setTitle(employee.getTitle());
        employee.setDescription("Descripcion editada");
        employee.setImage(employee.getImage());
        restTemplate.put(getRootUrl() + getSection() + "/" + postResponse.getBody().getId(), employee);

        Diplomate updatedDiplomate = restTemplate.getForObject(getRootUrl() + getSection() + "/" + postResponse.getBody().getId(), Diplomate.class);
        assertNotNull(updatedDiplomate);

        restTemplate.delete(getRootUrl() +  getSection() + "/" + postResponse.getBody().getId());
    }

    @Test
    public void testDeleteDiplomate() {
        Diplomate newDiplomate = new Diplomate("Diplomado posteado por un test para borrado",
                "https://www.inlingua.com/wp-content/uploads/2018/05/language-courses-companies-test.svg",
                "Nueva descripción hecha *nuevamente*");


        ResponseEntity<Diplomate> postResponse = restTemplate.postForEntity(getRootUrl() + getSection() , newDiplomate, Diplomate.class);

        Diplomate diplomate = restTemplate.getForObject(getRootUrl() +  getSection() + "/" + postResponse.getBody().getId(), Diplomate.class);
        assertNotNull(diplomate);
        restTemplate.delete(getRootUrl() +  getSection() + "/" + postResponse.getBody().getId());
        try {
            restTemplate.getForObject(getRootUrl() +  getSection() + "/" + postResponse.getBody().getId(), Diplomate.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
