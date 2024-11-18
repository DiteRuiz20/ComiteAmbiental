package utez.edu.mx.environmentcommittee.modules.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
@CrossOrigin(origins = {"*"})
public class GroupController {
        @Autowired
        private GroupService groupService;

        // BRING ALL GROUPS
        @GetMapping("")
        public ResponseEntity<?> findAll() {
            return groupService.findAll();
        }

        // BRING GROUP BY ID
        @GetMapping("/{id}")
        public ResponseEntity<?> findById(@PathVariable("id") long id) {
            return groupService.findById(id);
        }

        // FIND GROUPS BY MUNICIPALITY
        @GetMapping("/municipality/{municipality}")
        public ResponseEntity<?> findByMunicipality(@PathVariable("municipality") String municipality) {
            return groupService.findByMunicipality(municipality);
        }

        // SAVE GROUP
        @PostMapping("")
        public ResponseEntity<?> save(@RequestBody Group group) {
            return groupService.save(group);
        }

        // UPDATE GROUP
        @PutMapping("")
        public ResponseEntity<?> update(@RequestBody Group group) {
            return groupService.update(group);
        }

        // DELETE GROUP
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
            return groupService.deleteById(id);
        }
    }
