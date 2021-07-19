package two.microservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import two.microservice.exception.ResourceNotFoundException;
import two.microservice.model.Diplomate;
import two.microservice.repository.DiplomateRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class DiplomateController {

    private final DiplomateRepository diplomateRepository;

    public DiplomateController(DiplomateRepository diplomateRepository) {
        this.diplomateRepository = diplomateRepository;
    }

    @GetMapping("/diplomates")
    public List<Diplomate> getAllDiplomates(){
        return diplomateRepository.findAll();
    }

    @GetMapping("/diplomates/{id}")
    public ResponseEntity<Diplomate> getDiplomateById(@PathVariable(value = "id") Long diplomateId)
            throws ResourceNotFoundException {
        Diplomate diplomate = diplomateRepository.findById(diplomateId)
                .orElseThrow(() -> new ResourceNotFoundException("Diplomate not found for this id :: " + diplomateId));
        return ResponseEntity.ok().body(diplomate);
    }

    @PostMapping("/diplomates")
    public Diplomate createDiplomate(@Validated @RequestBody Diplomate diplomate) {
        return diplomateRepository.save(diplomate);
    }

    @PutMapping("/diplomates/{diplomateId}")
    public ResponseEntity<Diplomate> updateDiplomate(@PathVariable(value = "diplomateId") Long diplomateId,
                                                     @Validated @RequestBody Diplomate diplomateDetails) throws ResourceNotFoundException {
        Diplomate diplomate = diplomateRepository.findById(diplomateId)
                .orElseThrow(() -> new ResourceNotFoundException("Diplomate not found for this id :: " + diplomateId));

        diplomate.setTitle(diplomateDetails.getTitle());
        diplomate.setImage(diplomateDetails.getImage());
        diplomate.setDescription(diplomateDetails.getDescription());
        final Diplomate updatedDiplomate = diplomateRepository.save(diplomate);
        return ResponseEntity.ok(updatedDiplomate);
    }

    @DeleteMapping("/diplomates/{id}")
    public Map<String, Boolean> deleteDiplomate(@PathVariable(value = "id") Long diplomateId)
            throws ResourceNotFoundException {
        Diplomate diplomate = diplomateRepository.findById(diplomateId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + diplomateId));

        diplomateRepository.delete(diplomate);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
