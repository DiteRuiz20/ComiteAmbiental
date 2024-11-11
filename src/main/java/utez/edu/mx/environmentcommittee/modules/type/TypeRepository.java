package utez.edu.mx.environmentcommittee.modules.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    //GETTERS -> CONSULTAS
    // Get all types
    List<Type> findAll();

    // Get by id
    Type findById(long id);

    // Get by name
    @Modifying
    @Query(value = "SELECT * FROM type WHERE name = :nameType", nativeQuery = true)
    Type findByNameType(@Param("nameType") String nameType);

    // ACTIONS ->
    // save | update
    Type save(Type type);

    // change status
    @Modifying
    @Query(value = "UPDATE type SET status = :statusType WHERE id = :idType", nativeQuery = true)
    void updateStatusType(@Param("statusType") String statusType, @Param("idType") long idType);
}
