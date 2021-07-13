package two.microservice.model;

import javax.persistence.*;

@Entity
@Table(name = "diplomates")
public class Diplomate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true, name = "title", nullable = false)
    String title;

    @Column(name = "image", nullable = true)
    String image;

    @Column(name = "description", nullable = true)
    String description;

    public Diplomate() {
    }

    public Diplomate(String title, String image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Diplomate{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
