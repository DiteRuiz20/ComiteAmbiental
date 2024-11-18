package utez.edu.mx.environmentcommittee.modules.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    // BRING ALL TYPES
    List<Type> findAll();

    // BRING TYPE BY ID
    Type findById(long id);

    // SAVE/UPDATE TYPE
    Type save(Type type);

    // DELETE TYPE
    @Modifying
    @Query(value = "DELETE FROM type WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") long id);

    // FIND TYPE BY NAME
    @Query(value = "SELECT * FROM type WHERE name = :name", nativeQuery = true)
    Type findByName(@Param("name") String name);
}
