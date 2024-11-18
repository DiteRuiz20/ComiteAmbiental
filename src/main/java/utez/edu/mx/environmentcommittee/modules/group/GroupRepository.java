package utez.edu.mx.environmentcommittee.modules.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    // BRING ALL GROUPS
    List<Group> findAll();

    // BRING GROUP BY ID
    Group findById(long id);

    // SAVE/UPDATE GROUP
    Group save(Group group);

    // DELETE GROUP
    @Modifying
    @Query(value = "DELETE FROM group WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") long id);

    // FIND GROUP BY MUNICIPALITY
    @Query(value = "SELECT * FROM group WHERE municipality = :municipality", nativeQuery = true)
    List<Group> findByMunicipality(@Param("municipality") String municipality);
}
