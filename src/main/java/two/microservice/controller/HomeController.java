package two.microservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
class HomeController {

    @GetMapping("/")
    public @ResponseBody
    String greeting(){
        return "Microservicio 2 - Grupo 2 Taller de Ingeniería de Software ";
    }

}


