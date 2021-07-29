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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MicroServiceApplicationTestGet {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    private String getRootUrl(){
        return "http://localhost:" + port + "/api/v1";
    }

    @Test
    public void testGetDiplomate(){
        /*
        *
        * "id": 6,
        "title": "Diplomado en Ciencia de Datos Aplicada",
        "image": "https://universoabiertoblog.files.wordpress.com/2019/07/dreamhost-data-science-revolution-750x498.jpg",
        "description": "El Diplomado en ciencia de datos aplicada entrega los conocimientos necesarios en el uso de técnicas y herramientas de análisis de datos, con el fin de mejorar procesos de tomas de decisiones en diversas áreas de desempeño profesional."*/

        Diplomate gotDiplomate = new Diplomate("Diplomado en Ciencia de Datos Aplicada",
                "https://universoabiertoblog.files.wordpress.com/2019/07/dreamhost-data-science-revolution-750x498.jpg",
                "El Diplomado en ciencia de datos aplicada entrega los conocimientos necesarios en el uso de técnicas y herramientas de análisis de datos, con el fin de mejorar procesos de tomas de decisiones en diversas áreas de desempeño profesional.");
        gotDiplomate.setId(6);


        Diplomate db_diplomate = restTemplate.getForObject(getRootUrl() + "/diplomates/6", Diplomate.class);

        System.out.println(gotDiplomate.toString());
        System.out.println(db_diplomate.toString());

        assertEquals(db_diplomate.toString(), gotDiplomate.toString());

    }

    @Test
    public void testGetPostulation_BDIS(){ // By Diplomate Id Six
        /*
        * {
        "id": 10,
        "registrationForm": "url",
        "curriculumVitae": "IHO",
        "birthCertificate": "IHO",
        "graduateCertificate": "IHO",
        "copyIdentityCard": "IHO",
        "received": true,
        "valid": true
    },
        * */
        Postulation expectedPostulation = new Postulation("url", "IHO", "IHO", "IHO", "IHO");
        expectedPostulation.setReceived(false);
        expectedPostulation.setValid(true);
        expectedPostulation.setId(10);

        Postulation gotPostulation = restTemplate.getForObject(getRootUrl() + "/diplomates/6/postulations/10", Postulation.class);

        assertEquals(gotPostulation.toString(), expectedPostulation.toString());

    }
}
