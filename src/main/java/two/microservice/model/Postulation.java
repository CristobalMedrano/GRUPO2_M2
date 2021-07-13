package two.microservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "postulations")
public class Postulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "registrationForm", nullable = true)
    String registrationForm;

    @Column(name = "curriculumVitae", nullable = true)
    String curriculumVitae;

    @Column(name = "birthCertificate", nullable = true)
    String birthCertificate;

    @Column(name = "graduateCertificate", nullable = true)
    String graduateCertificate;

    @Column(name = "copyIdentityCard", nullable = true)
    String copyIdentityCard;

    @Column(name ="received", nullable = false)
    Boolean received;

    @Column(name ="valid", nullable = false)
    Boolean valid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "diplomate_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Diplomate diplomate;


    public Postulation() {
    }

    public Postulation(String registrationForm, String curriculumVitae, String birthCertificate, String graduateCertificate, String copyIdentityCard) {
        this.registrationForm = registrationForm;
        this.curriculumVitae = curriculumVitae;
        this.birthCertificate = birthCertificate;
        this.graduateCertificate = graduateCertificate;
        this.copyIdentityCard = copyIdentityCard;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationForm() {
        return registrationForm;
    }

    public void setRegistrationForm(String registrationForm) {
        this.registrationForm = registrationForm;
    }

    public String getCurriculumVitae() {
        return curriculumVitae;
    }

    public void setCurriculumVitae(String curriculumVitae) {
        this.curriculumVitae = curriculumVitae;
    }

    public String getBirthCertificate() {
        return birthCertificate;
    }

    public void setBirthCertificate(String birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public String getGraduateCertificate() {
        return graduateCertificate;
    }

    public void setGraduateCertificate(String graduateCertificate) {
        this.graduateCertificate = graduateCertificate;
    }

    public String getCopyIdentityCard() {
        return copyIdentityCard;
    }

    public void setCopyIdentityCard(String copyIdentityCard) {
        this.copyIdentityCard = copyIdentityCard;
    }

    public Boolean getReceived() {
        return received;
    }

    public void setReceived(Boolean received) {
        this.received = received;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Diplomate getDiplomate() {
        return diplomate;
    }

    public void setDiplomate(Diplomate diplomate) {
        this.diplomate = diplomate;
    }

    @Override
    public String toString() {
        return "Postulation{" +
                "id=" + id +
                ", registrationForm='" + registrationForm + '\'' +
                ", curriculumVitae='" + curriculumVitae + '\'' +
                ", birthCertificate='" + birthCertificate + '\'' +
                ", graduateCertificate='" + graduateCertificate + '\'' +
                ", copyIdentityCard='" + copyIdentityCard + '\'' +
                '}';
    }
}
