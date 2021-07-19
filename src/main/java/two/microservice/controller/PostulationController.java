package two.microservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import two.microservice.exception.ResourceNotFoundException;
import two.microservice.model.Postulation;
import two.microservice.repository.DiplomateRepository;
import two.microservice.repository.PostulationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/diplomates/{diplomateId}")
public class PostulationController {
    private final PostulationRepository postulationRepository;
    private final DiplomateRepository diplomateRepository;

    public PostulationController(PostulationRepository postulationRepository, DiplomateRepository diplomateRepository) {
        this.postulationRepository = postulationRepository;
        this.diplomateRepository = diplomateRepository;
    }

    @GetMapping("/postulations")
    public List<Postulation> getAllPostulationsByDiplomateId(@PathVariable (value = "diplomateId") Long diplomateId){
        return postulationRepository.findByDiplomateId(diplomateId);
    }

    @GetMapping("/postulations/{postulationId}")
    public ResponseEntity<Postulation> getPostulationById(@PathVariable(value = "postulationId") Long postulationId)
            throws ResourceNotFoundException {
        Postulation postulation = postulationRepository.findById(postulationId)
                .orElseThrow(() -> new ResourceNotFoundException("Postulation not found for this id :: " + postulationId));
        return ResponseEntity.ok().body(postulation);
    }

    @PostMapping("/postulations")
    public Postulation createPostulation(@PathVariable (value = "diplomateId") Long diplomateId,
                                         @Validated @RequestBody Postulation postulation) throws ResourceNotFoundException {
        return diplomateRepository.findById(diplomateId).map(diplomate -> {
            postulation.setDiplomate(diplomate);
            return postulationRepository.save(postulation);
        }).orElseThrow(() -> new ResourceNotFoundException("diplomateId " + diplomateId + " not found"));
    }

    @PutMapping("/postulations/{postulationId}")
    public ResponseEntity<Postulation> updatePostulation(@PathVariable(value = "postulationId") Long postulationId,
                                                         @PathVariable(value = "diplomateId") Long diplomateId,
                                                         @Validated @RequestBody Postulation postulationDetails) throws ResourceNotFoundException {
        if (!diplomateRepository.existsById(diplomateId)) {
            throw new ResourceNotFoundException("DiplomateId "+ diplomateId + " not found");
        }
        Postulation postulation = postulationRepository.findById(postulationId)
                .orElseThrow(() -> new ResourceNotFoundException("PostulationId "+ postulationId + " not found"));

        postulation.setRegistrationForm(postulationDetails.getRegistrationForm());
        postulation.setGraduateCertificate(postulationDetails.getGraduateCertificate());
        postulation.setCurriculumVitae(postulationDetails.getCurriculumVitae());
        postulation.setBirthCertificate(postulationDetails.getBirthCertificate());
        postulation.setCopyIdentityCard(postulationDetails.getCopyIdentityCard());
        postulation.setReceived(postulationDetails.getReceived());
        postulation.setValid(postulationDetails.getValid());
        final Postulation updatePostulation = postulationRepository.save(postulation);
        return ResponseEntity.ok(updatePostulation);
    }

    @DeleteMapping("/postulations/{postulationId}")
    public Map<String, Boolean> deletePostulation(@PathVariable(value = "postulationId") Long postulationId,
                                                  @PathVariable(value = "diplomateId") Long diplomateId)
            throws ResourceNotFoundException {
        Postulation postulation = (Postulation) postulationRepository.findByIdAndDiplomateId(postulationId, diplomateId)
                .orElseThrow(() -> new ResourceNotFoundException("Postulation not found with id " + postulationId + " and diplomateId " + diplomateId));

            postulationRepository.delete(postulation);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
        }

}
