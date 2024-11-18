package utez.edu.mx.environmentcommittee.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.environmentcommittee.utils.CustomResponseEntity;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private CustomResponseEntity customResponseEntity;

        // FIND ALL USERS
        @Transactional(readOnly = true)
        public ResponseEntity<?> findAll() {
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                return customResponseEntity.getOkResponse("No users found", "OK", 200, null);
            }
            return customResponseEntity.getOkResponse("Users found", "OK", 200, users);
        }

        // FIND USER BY ID
        @Transactional(readOnly = true)
        public ResponseEntity<?> findById(long id) {
            User user = userRepository.findById(id);
            if (user == null) {
                return customResponseEntity.get404Response();
            }
            return customResponseEntity.getOkResponse("User found", "OK", 200, user);
        }

        // FIND USERS BY ROLE ID
        @Transactional(readOnly = true)
        public ResponseEntity<?> findByRoleId(long roleId) {
            List<User> users = userRepository.findByRoleId(roleId);
            if (users.isEmpty()) {
                return customResponseEntity.getOkResponse("No users found for this role", "OK", 200, null);
            }
            return customResponseEntity.getOkResponse("Users found", "OK", 200, users);
        }

        // SAVE USER
        @Transactional(rollbackFor = {SQLException.class, Exception.class})
        public ResponseEntity<?> save(User user) {
            try {
                userRepository.save(user);
                return customResponseEntity.getOkResponse("User successfully registered", "CREATED", 201, null);
            } catch (Exception e) {
                e.printStackTrace();
                return customResponseEntity.get400Response();
            }
        }

        // UPDATE USER
        @Transactional(rollbackFor = {SQLException.class, Exception.class})
        public ResponseEntity<?> update(User user) {
            User existingUser = userRepository.findById(user.getId());
            if (existingUser == null) {
                return customResponseEntity.get404Response();
            }
            try {
                user.setPassword(existingUser.getPassword()); // Keep the original password
                userRepository.save(user);
                return customResponseEntity.getOkResponse("User successfully updated", "OK", 200, null);
            } catch (Exception e) {
                e.printStackTrace();
                return customResponseEntity.get400Response();
            }
        }

        // DELETE USER
        @Transactional(rollbackFor = {SQLException.class, Exception.class})
        public ResponseEntity<?> deleteById(long id) {
            User user = userRepository.findById(id);
            if (user == null) {
                return customResponseEntity.get404Response();
            }
            try {
                userRepository.deleteById(id);
                return customResponseEntity.getOkResponse("User successfully deleted", "OK", 200, null);
            } catch (Exception e) {
                e.printStackTrace();
                return customResponseEntity.get400Response();
            }
        }
    }
