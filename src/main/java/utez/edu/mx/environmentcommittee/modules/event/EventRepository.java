package utez.edu.mx.environmentcommittee.modules.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // BRING ALL EVENTS
    List<Event> findAll();

    // BRING EVENT BY ID
    Event findById(long id);

    // SAVE/UPDATE EVENT
    Event save(Event event);

    // DELETE EVENT
    @Modifying
    @Query(value = "DELETE FROM event WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") long id);

    // FIND EVENTS BY STATUS
    @Query(value = "SELECT * FROM event WHERE status = :status", nativeQuery = true)
    List<Event> findByStatus(@Param("status") String status);
}

