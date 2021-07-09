package two.microservice.model;

import javax.persistence.*;

@Entity
@Table(name = "diplomates")
public class Diplomate {

    long id;
    String title;
    String image;
    String description;

    public Diplomate() {
    }

    public Diplomate(String title, String image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "image", nullable = true)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "description", nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Diplomate ["+"id= "+id+", title= "+title+", image="+image+", description="+description+"]";
    }
}
