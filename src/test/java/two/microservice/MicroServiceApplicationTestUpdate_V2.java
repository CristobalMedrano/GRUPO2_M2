package two.microservice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import two.microservice.model.Diplomate;
import two.microservice.model.Postulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MicroServiceApplicationTestUpdate_V2 {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    private String getRootUrl(){
        return "http://localhost:" + port + "/api/v1";
    }


    @Test
    public void testUpdatingPostulationIDBIS(){ // In Diplomate By Id Six
        int postID_postu = 14;
        Postulation expectedPostulation = restTemplate.getForObject(getRootUrl() + "/diplomates/6/postulations/" + postID_postu, Postulation.class);
        // section of setters
        expectedPostulation.setGraduateCertificate("I have one");
        expectedPostulation.setCopyIdentityCard("I have one");
        expectedPostulation.setReceived(true);
        expectedPostulation.setValid(true);
        // ------------------------------------------------------

        restTemplate.put(getRootUrl() + "/diplomates/6/postulations/"+expectedPostulation.getId(), expectedPostulation);

        Postulation updatedPostulation = restTemplate.getForObject(getRootUrl() + "/diplomates/6/postulations/" + postID_postu, Postulation.class);

        assertEquals(updatedPostulation.toString(), expectedPostulation.toString());
    }

    @Test
    public void testUpdatingDiplomates(){
        int postID_diplo = 36;
        Diplomate expectedDiplomate = restTemplate.getForObject(getRootUrl() + "/diplomates/"+ postID_diplo, Diplomate.class);
        // secction of setters
        expectedDiplomate.setImage("https://www.fdd.cl/wp-content/uploads/2017/09/Test.jpg");
        expectedDiplomate.setDescription("Esta es la nueva descripcion puesta por el testUpdate para id -> " + postID_diplo);

        restTemplate.put(getRootUrl() + "/diplomates/" + expectedDiplomate.getId(), expectedDiplomate);

        Diplomate updatedDiplomate = restTemplate.getForObject(getRootUrl() + "/diplomates/" + postID_diplo, Diplomate.class);

        assertEquals(updatedDiplomate.toString(), expectedDiplomate.toString());
    }
}
