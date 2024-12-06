package utez.edu.mx.environmentcommittee.modules.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.environmentcommittee.utils.CustomResponseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CustomResponseEntity customResponseEntity;

    // BRING ALL GROUPS
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll() {
        List<Group> groups = groupRepository.findAll();
        if (groups.isEmpty()) {
            return customResponseEntity.getOkResponse("No groups found", "OK", 200, null);
        }
        return customResponseEntity.getOkResponse("Groups found", "OK", 200, groups);
    }

    // BRING GROUP BY ID
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(long id) {
        Optional<Group> group = Optional.ofNullable(groupRepository.findById(id));
        if (!group.isPresent()) {
            return customResponseEntity.get404Response();
        }
        return customResponseEntity.getOkResponse("Group found", "OK", 200, group.get());
    }

    // FIND GROUPS BY MUNICIPALITY
    @Transactional(readOnly = true)
    public ResponseEntity<?> findByMunicipality(String municipality) {
        List<Group> groups = groupRepository.findByMunicipality(municipality);
        if (groups.isEmpty()) {
            return customResponseEntity.getOkResponse("No groups found in the given municipality", "OK", 200, null);
        }
        return customResponseEntity.getOkResponse("Groups found", "OK", 200, groups);
    }

    // SAVE GROUP
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<?> save(Group group) {
        try {
            groupRepository.save(group);
            return customResponseEntity.getOkResponse("Group successfully registered", "CREATED", 201, null);
        } catch (Exception e) {
            e.printStackTrace();
            return customResponseEntity.get400Response("Error saving group: " + e.getMessage());
        }
    }

    // UPDATE GROUP
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<?> update(Group group) {
        Optional<Group> existingGroup = Optional.ofNullable(groupRepository.findById(group.getId()));
        if (!existingGroup.isPresent()) {
            return customResponseEntity.get404Response();
        }
        try {
            groupRepository.save(group);
            return customResponseEntity.getOkResponse("Group successfully updated", "OK", 200, null);
        } catch (Exception e) {
            e.printStackTrace();
            return customResponseEntity.get400Response("Error updating group: " + e.getMessage());
        }
    }

    // DELETE GROUP
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<?> deleteById(long id) {
        Optional<Group> group = Optional.ofNullable(groupRepository.findById(id));
        if (!group.isPresent()) {
            return customResponseEntity.get404Response();
        }
        try {
            groupRepository.deleteById(id);
            return customResponseEntity.getOkResponse("Group successfully deleted", "OK", 200, null);
        } catch (Exception e) {
            e.printStackTrace();
            return customResponseEntity.get400Response("Error deleting group: " + e.getMessage());
        }
    }
}
