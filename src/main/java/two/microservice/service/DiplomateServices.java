package two.microservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import two.microservice.model.Diplomate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


public class DiplomateServices {

    EntityManager entityManager;
    public DiplomateServices(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional
    public long saveDiplomate(Diplomate diplomate){
        entityManager.persist(diplomate);
        return diplomate.getId();
    }
}
