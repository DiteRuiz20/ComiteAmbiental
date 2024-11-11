package utez.edu.mx.environmentcommittee.modules.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    // GETTERS -> CONSULTAS
    // Get all
    List<Role> findAll();

    // Get by id
    Role findById(long id);

    // Get by role name
    @Query(value = "SELECT * FROM role WHERE name = :nameRole", nativeQuery = true)
    Role findByNameRole(@Param("nameRole") String nameRole);

    // ACTIONS ->
    // save | update
    Role save (Role role);

    //delete
    @Modifying
    @Query(value = "DELETE FROM role WHERE id = :idRole", nativeQuery = true)
    void deleteById(@Param("idRole") long idRole);
}
