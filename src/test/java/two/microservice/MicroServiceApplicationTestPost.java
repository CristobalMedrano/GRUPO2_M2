package two.microservice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import two.microservice.model.Diplomate;
import two.microservice.model.Postulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)



public class MicroServiceApplicationTestPost {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    private String getRootUrl(){
        return "http://localhost:" + port + "/api/v1";
    }

    @Test
    public void testPostingPostulationIDS(){
        // Creation of a new Postulation
        Postulation AnotherPostulation = new Postulation("url", "IDHO", "IDHO","IDHO","IDHO");
        AnotherPostulation.setReceived(false);
        AnotherPostulation.setValid(false);
        AnotherPostulation.setId(18); // Last number used 17

        ResponseEntity<Postulation> postPostulation = restTemplate.postForEntity(getRootUrl() + "/diplomates/6/postulations", AnotherPostulation, Postulation.class);

        // Test for verify if the post success
        Postulation gotPostulation = restTemplate.getForObject(getRootUrl() + "/diplomates/6/postulations/"+AnotherPostulation.getId(), Postulation.class);

        assertEquals(gotPostulation.toString(), AnotherPostulation.toString());

    }

    @Test
    public void testPostingDiplomates(){
        // Creation of a new Diplomates with test-propouses.
        int postID = 105; // last used 104
        Diplomate AnotherDiplomate = new Diplomate("Diplomado posteado por un test " + postID,
                "https://www.inlingua.com/wp-content/uploads/2018/05/language-courses-companies-test.svg",
                "Nueva descripción hecha *nuevamente*");
        AnotherDiplomate.setId(postID);

        ResponseEntity<Diplomate> postDiplomate = restTemplate.postForEntity(getRootUrl() + "/diplomates", AnotherDiplomate, Diplomate.class);
         /*
         assertNotNull(postDiplomate.getBody());
       */
        // Test for verify if the post success
        Diplomate gotDiplomate = restTemplate.getForObject(getRootUrl() + "/diplomates/"+AnotherDiplomate.getId(), Diplomate.class);

        assertEquals(gotDiplomate.toString(), AnotherDiplomate.toString());


    }
}
