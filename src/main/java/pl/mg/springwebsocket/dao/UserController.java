package pl.mg.springwebsocket.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity getAllUsers() {

        List<UserEntity> users = userRepository.getAll();

        UserEntity byId = userRepository.getById("1");

        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userRepository.getByIdWithDetach(id));
    }


    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestParam(name = "id") String id,@RequestParam(name = "city") String city, @RequestParam(name = "name") String name ) {
        UserEntity userEntity = this.userRepository.addUser(new UserEntity(id, name, city, null));
        return ResponseEntity.ok(userEntity);

    }

    @GetMapping("/updateUserCity/{id}/{city}")
    public ResponseEntity updateUserCity(@PathVariable("id") String id, @PathVariable("city") String city) {
        UserEntity userEntity = userRepository.getByIdWithDetach(id);
        if (userEntity != null) {
            userEntity.setCity(city);
            userEntity.setId("123");
        }


        UserEntity updateUser = userRepository.updateUser(userEntity);
        return ResponseEntity.ok(updateUser);
    }

    /*
     * test zachowania w środowisku klastrowym. scenariusz: 1. node-1: pobranie danych z detach. freeze na 10s 2. node-2: pobranie danych z detach.
     * update danych i zapis 3. node-1: update danych i zapis 4. node-1: odczyt danych
     */
    @GetMapping("/updateUserCitySlow/{id}/{city}")
    public ResponseEntity updateUserCitySlow(@PathVariable("id") String id, @PathVariable("city") String city) {
        UserEntity userEntity = userRepository.getByIdWithDetach(id);
        if (userEntity != null) {
            userEntity.setCity(city);
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserEntity updateUser = userRepository.updateUser(userEntity);
        System.out.println("slowUpdate=" + updateUser);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/updateUserCityFast/{id}/{city}")
    public ResponseEntity updateUserCityFast(@PathVariable("id") String id, @PathVariable("city") String city) {
        UserEntity userEntity = userRepository.getByIdWithDetach(id);
        if (userEntity != null) {
            userEntity.setCity(city);
        }

        UserEntity updateUser = userRepository.updateUser(userEntity);
        System.out.println("fastUpdate=" + updateUser);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/updateUserCityTransaction/{id}/{city}")
    public ResponseEntity updateUserCityTransaction(@PathVariable("id") String id, @PathVariable("city") String city) {
        UserEntity updateUser = userService.updateUserCity(id, city);
        System.out.println("transaction update=" + updateUser);
        return ResponseEntity.ok(updateUser);
    }

}
