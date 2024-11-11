package utez.edu.mx.environmentcommittee.modules.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // GETTERS -> CONSULTAS
    // Get all events
    List<Event> findAll();

    // Get all events by tittle
    @Query(value = "SELECT * FROM event WHERE title = :tittleEvent", nativeQuery = true)
    List<Event> findByName(@Param("tittleEvent") String tittleEvent);

    // Get event by ID
    Event  findById(long id);

    // ACTIONS ->
    // save | update
    Event save(Event event);

    // change the event's status (próximamente,en ejecucion, finalizado)
    @Modifying
    @Query(value = "UPDATE event SET status = :statusEvent WHERE id = :idEvent",nativeQuery = true)
    void updateStatusEvent(@Param("statusEvent") String statusEvent, @Param("idEvent") long idEvent);


}
