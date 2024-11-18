package utez.edu.mx.environmentcommittee.modules.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.environmentcommittee.utils.CustomResponseEntity;

import java.sql.SQLException;
import java.util.List;

@Service
public class TypeService {
        @Autowired
        private TypeRepository typeRepository;

        @Autowired
        private CustomResponseEntity customResponseEntity;

        // BRING ALL TYPES
        @Transactional(readOnly = true)
        public ResponseEntity<?> findAll() {
            List<Type> types = typeRepository.findAll();
            if (types.isEmpty()) {
                return customResponseEntity.getOkResponse("No types found", "OK", 200, null);
            }
            return customResponseEntity.getOkResponse("Types found", "OK", 200, types);
        }

        // BRING TYPE BY ID
        @Transactional(readOnly = true)
        public ResponseEntity<?> findById(long id) {
            Type type = typeRepository.findById(id);
            if (type == null) {
                return customResponseEntity.get404Response();
            }
            return customResponseEntity.getOkResponse("Type found", "OK", 200, type);
        }

        // FIND TYPE BY NAME
        @Transactional(readOnly = true)
        public ResponseEntity<?> findByName(String name) {
            Type type = typeRepository.findByName(name);
            if (type == null) {
                return customResponseEntity.getOkResponse("No type found with the given name", "OK", 200, null);
            }
            return customResponseEntity.getOkResponse("Type found", "OK", 200, type);
        }

        // SAVE TYPE
        @Transactional(rollbackFor = {SQLException.class, Exception.class})
        public ResponseEntity<?> save(Type type) {
            try {
                typeRepository.save(type);
                return customResponseEntity.getOkResponse("Type successfully registered", "CREATED", 201, null);
            } catch (Exception e) {
                e.printStackTrace();
                return customResponseEntity.get400Response();
            }
        }

        // UPDATE TYPE
        @Transactional(rollbackFor = {SQLException.class, Exception.class})
        public ResponseEntity<?> update(Type type) {
            Type existingType = typeRepository.findById(type.getId());
            if (existingType == null) {
                return customResponseEntity.get404Response();
            }
            try {
                typeRepository.save(type);
                return customResponseEntity.getOkResponse("Type successfully updated", "OK", 200, null);
            } catch (Exception e) {
                e.printStackTrace();
                return customResponseEntity.get400Response();
            }
        }

        // DELETE TYPE
        @Transactional(rollbackFor = {SQLException.class, Exception.class})
        public ResponseEntity<?> deleteById(long id) {
            Type type = typeRepository.findById(id);
            if (type == null) {
                return customResponseEntity.get404Response();
            }
            try {
                typeRepository.deleteById(id);
                return customResponseEntity.getOkResponse("Type successfully deleted", "OK", 200, null);
            } catch (Exception e) {
                e.printStackTrace();
                return customResponseEntity.get400Response();
            }
        }
    }
