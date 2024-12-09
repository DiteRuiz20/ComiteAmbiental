package utez.edu.mx.environmentcommittee.modules.user.DTO;

import utez.edu.mx.environmentcommittee.modules.group.Group;
import utez.edu.mx.environmentcommittee.modules.role.Role;

public class UserDTO {
    private long id;
    private String name, phone, surname, lastname, email, username;
    private Role role;
    private Group group;

    public UserDTO() {
    }


    public UserDTO(long id, String name, String phone, String surname, String lastname, String email, String username, Role role, Group group) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.role = role;
        this.group = group;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
