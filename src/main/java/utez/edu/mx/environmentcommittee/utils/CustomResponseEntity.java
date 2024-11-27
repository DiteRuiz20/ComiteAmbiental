package utez.edu.mx.environmentcommittee.utils;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;
import java.util.Map;

@Service
public class CustomResponseEntity {

        private Map<String, Object> body;

        public ResponseEntity<?> getOkResponse(String message, String status, int code, Object data) {
            body = new HashMap<>();
            body.put("message", message);
            body.put("status", status);
            body.put("code", code);
            if(data != null){
                body.put("data", data);
            }
            return new ResponseEntity<>(body, HttpStatus.OK);
        }

        public ResponseEntity<?> get400Response() {
            body = new HashMap<>();
            body.put("message", "There has been an error on the operation");
            body.put("status", "BAD_REQUEST");
            body.put("code", 400);

            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        public ResponseEntity<?> get404Response() {
            body = new HashMap<>();
            body.put("message", "Resource has not been found");
            body.put("status", "NOT_FOUND");
            body.put("code", 404);

            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
}
