package utez.edu.mx.environmentcommittee.modules.group;
import jakarta.persistence.*;
import utez.edu.mx.environmentcommittee.modules.user.User;

import java.util.List;

@Entity
@Table(name = "group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "municipality", nullable = false)
    private String municipality;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @OneToMany(mappedBy = "group")
    private List<User> users;

    public Group() {
    }

    public Group(String name, String municipality, String neighborhood) {
        this.name = name;
        this.municipality = municipality;
        this.neighborhood = neighborhood;
    }

    public Group(long id, String name, String municipality, String neighborhood) {
        this.id = id;
        this.name = name;
        this.municipality = municipality;
        this.neighborhood = neighborhood;
    }

    public Group(String name, String municipality, String neighborhood, List<User> users) {
        this.name = name;
        this.municipality = municipality;
        this.neighborhood = neighborhood;
        this.users = users;
    }

    public Group(long id, String name, String municipality, String neighborhood, List<User> users) {
        this.id = id;
        this.name = name;
        this.municipality = municipality;
        this.neighborhood = neighborhood;
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}