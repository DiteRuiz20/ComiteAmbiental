package utez.edu.mx.environmentcommittee.modules.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.environmentcommittee.utils.CustomResponseEntity;

import java.sql.SQLException;
import java.util.List;

@Service
public class EventService {
        @Autowired
        private EventRepository eventRepository;

        @Autowired
        private CustomResponseEntity customResponseEntity;

        // BRING ALL EVENTS
        @Transactional(readOnly = true)
        public ResponseEntity<?> findAll() {
            List<Event> events = eventRepository.findAll();
            if (events.isEmpty()) {
                return customResponseEntity.getOkResponse("No events found", "OK", 200, null);
            }
            return customResponseEntity.getOkResponse("Events found", "OK", 200, events);
        }

        // BRING EVENT BY ID
        @Transactional(readOnly = true)
        public ResponseEntity<?> findById(long id) {
            Event event = eventRepository.findById(id);
            if (event == null) {
                return customResponseEntity.get404Response();
            }
            return customResponseEntity.getOkResponse("Event found", "OK", 200, event);
        }

        // FIND EVENTS BY STATUS
        @Transactional(readOnly = true)
        public ResponseEntity<?> findByStatus(String status) {
            List<Event> events = eventRepository.findByStatus(status);
            if (events.isEmpty()) {
                return customResponseEntity.getOkResponse("No events found with the given status", "OK", 200, null);
            }
            return customResponseEntity.getOkResponse("Events found", "OK", 200, events);
        }

        // SAVE EVENT
        @Transactional(rollbackFor = {SQLException.class, Exception.class})
        public ResponseEntity<?> save(Event event) {
            try {
                eventRepository.save(event);
                return customResponseEntity.getOkResponse("Event successfully registered", "CREATED", 201, null);
            } catch (Exception e) {
                e.printStackTrace();
                return customResponseEntity.get400Response();
            }
        }

        // UPDATE EVENT
        @Transactional(rollbackFor = {SQLException.class, Exception.class})
        public ResponseEntity<?> update(Event event) {
            Event existingEvent = eventRepository.findById(event.getId());
            if (existingEvent == null) {
                return customResponseEntity.get404Response();
            }
            try {
                eventRepository.save(event);
                return customResponseEntity.getOkResponse("Event successfully updated", "OK", 200, null);
            } catch (Exception e) {
                e.printStackTrace();
                return customResponseEntity.get400Response();
            }
        }

        // DELETE EVENT
        @Transactional(rollbackFor = {SQLException.class, Exception.class})
        public ResponseEntity<?> deleteById(long id) {
            Event event = eventRepository.findById(id);
            if (event == null) {
                return customResponseEntity.get404Response();
            }
            try {
                eventRepository.deleteById(id);
                return customResponseEntity.getOkResponse("Event successfully deleted", "OK", 200, null);
            } catch (Exception e) {
                e.printStackTrace();
                return customResponseEntity.get400Response();
            }
        }
    }
