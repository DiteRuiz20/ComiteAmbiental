package utez.edu.mx.environmentcommittee.modules.user.DTO;

import utez.edu.mx.environmentcommittee.modules.role.Role;

public class UserDTO {
    private long id;
    private String name, phone, surname, lastname, email, username;
    private Role role;

    public UserDTO() {
    }


    public UserDTO(long id, String name, String phone, String email, String username, Role role) {

        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSurname() {
        return surname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Role getRol() {
        return role;
    }



    public void setId(long id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRol(Role role) {
        this.role = role;
    }
}
