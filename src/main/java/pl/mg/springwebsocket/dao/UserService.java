package pl.mg.springwebsocket.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public UserEntity updateUserCity(String userId, String city) {
        System.out.println("update user: " + userId + " city " + city);
        UserEntity userEntity = userRepository.getById(userId);
        userEntity.setCity(city);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserEntity updateUser = userRepository.updateUser(userEntity);
        System.out.println("updated user: " + updateUser.toString());
        return updateUser;
    }
}
