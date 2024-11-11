package utez.edu.mx.environmentcommittee.modules.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    // GETTERS -> CONSULTAS

    // Get all groups
    List<Group> findAll();

    // Get groups by name
    @Query(value = "SELECT * FROM group WHERE name = :nameGroup", nativeQuery = true)
    List<Group> findByName(@Param("nameGroup")String nameGroup);

    // Get group by id
    Group findById(long id);

    // ACTIONS ->

    // save|update
    Group save (Group group);

    // delete
    @Modifying
    @Query(value = "DELETE FROM Group WHERE id = :idGroup", nativeQuery = true)
    void deleteById(@Param("idGroup") long idGroup);
}