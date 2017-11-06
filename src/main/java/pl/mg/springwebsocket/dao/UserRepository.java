package pl.mg.springwebsocket.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.mg.springwebsocket.Profiles;

import java.util.List;

public interface UserRepository {
    List<UserEntity> getAll();

    UserEntity getByIdWithDetach(String id);

    UserEntity getById(String id);

    UserEntity updateUser(UserEntity userEntity);

    UserEntity addUser(UserEntity userEntity);

    @Profile(Profiles.TEST)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    boolean removeAll();
}
