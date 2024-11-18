package utez.edu.mx.environmentcommittee.modules.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = {"*"})
public class EventController {
        @Autowired
        private EventService eventService;

        // BRING ALL EVENTS
        @GetMapping("")
        public ResponseEntity<?> findAll() {
            return eventService.findAll();
        }

        // BRING EVENT BY ID
        @GetMapping("/{id}")
        public ResponseEntity<?> findById(@PathVariable("id") long id) {
            return eventService.findById(id);
        }

        // FIND EVENTS BY STATUS
        @GetMapping("/status/{status}")
        public ResponseEntity<?> findByStatus(@PathVariable("status") String status) {
            return eventService.findByStatus(status);
        }

        // SAVE EVENT
        @PostMapping("")
        public ResponseEntity<?> save(@RequestBody Event event) {
            return eventService.save(event);
        }

        // UPDATE EVENT
        @PutMapping("")
        public ResponseEntity<?> update(@RequestBody Event event) {
            return eventService.update(event);
        }

        // DELETE EVENT
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
            return eventService.deleteById(id);
        }
    }
