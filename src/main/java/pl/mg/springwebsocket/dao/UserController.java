package pl.mg.springwebsocket.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getUsers")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userRepository.getAll());
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userRepository.getByIdWithDetach(id));
    }

    
    @GetMapping("/updateUserCity/{id}/{city}")
    public ResponseEntity updateUserCity(@PathVariable("id") String id, @PathVariable("city") String city) {
        UserEntity userEntity = userRepository.getByIdWithDetach(id);
        if(userEntity!=null) {
            userEntity.setCity(city);
            userEntity.setId("123");
        }
        
        
        UserEntity updateUser = userRepository.updateUser(userEntity);
        return ResponseEntity.ok(updateUser);
        
    }

}
