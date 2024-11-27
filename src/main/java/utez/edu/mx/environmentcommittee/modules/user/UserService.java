package utez.edu.mx.environmentcommittee.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.environmentcommittee.modules.user.DTO.UserDTO;
import utez.edu.mx.environmentcommittee.utils.CustomResponseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomResponseEntity customResponseEntity;

    // Método para transformar una entidad User a un DTO
    private UserDTO transformUserToDTO(User u) {
        return new UserDTO(
                u.getId(),
                u.getName(),
                u.getPhone(),
                u.getEmail(),
                u.getUsername(),
                u.getRole()
        );
    }

    // ENCONTRAR TODOS LOS USUARIOS
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return customResponseEntity.getOkResponse("No se encontraron usuarios", "OK", 200, null);
        }

        // Convertir la lista de User a UserDto
        List<UserDTO> userDtos = users.stream()
                .map(this::transformUserToDTO)
                .collect(Collectors.toList());

        return customResponseEntity.getOkResponse("Usuarios encontrados", "OK", 200, userDtos);
    }

    // BUSCAR USUARIO POR ID
    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            return customResponseEntity.get404Response();
        }

        // Transformar el usuario a DTO
        UserDTO userDto = transformUserToDTO(user);
        return customResponseEntity.getOkResponse("Usuario encontrado", "OK", 200, userDto);
    }

    // BUSCAR USUARIOS POR ID DE ROL
    @Transactional(readOnly = true)
    public ResponseEntity<?> findByRoleId(long roleId) {
        List<User> users = userRepository.findByRoleId(roleId);
        if (users.isEmpty()) {
            return customResponseEntity.getOkResponse("No se encontraron usuarios para este rol", "OK", 200, null);
        }

        // Transformar usuarios a DTOs
        List<UserDTO> userDtos = users.stream()
                .map(this::transformUserToDTO)
                .collect(Collectors.toList());

        return customResponseEntity.getOkResponse("Usuarios encontrados", "OK", 200, userDtos);
    }

    // GUARDAR USUARIO
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<?> save(User user) {
        try {
            userRepository.save(user);
            return customResponseEntity.getOkResponse("Usuario registrado exitosamente", "CREADO", 201, null);
        } catch (Exception e) {
            e.printStackTrace();
            return customResponseEntity.get400Response();
        }
    }

    // ACTUALIZAR USUARIO
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<?> update(User user) {
        User existingUser = userRepository.findById(user.getId());
        if (existingUser == null) {
            return customResponseEntity.get404Response();
        }

        try {
            // Mantener la contraseña original
            user.setPassword(existingUser.getPassword());
            userRepository.save(user);
            return customResponseEntity.getOkResponse("Usuario actualizado correctamente", "OK", 200, null);
        } catch (Exception e) {
            e.printStackTrace();
            return customResponseEntity.get400Response();
        }
    }

    // ELIMINAR USUARIO
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<?> deleteById(long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            return customResponseEntity.get404Response();
        }

        try {
            userRepository.deleteById(id);
            return customResponseEntity.getOkResponse("Usuario eliminado correctamente", "OK", 200, null);
        } catch (Exception e) {
            e.printStackTrace();
            return customResponseEntity.get400Response();
        }
    }
}
