package pl.mg.springwebsocket.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private EntityManager em;

    @Override
    public List<UserEntity> getAll() {
        return em.createQuery("from UserEntity").getResultList();
    }

    @Override
    public UserEntity getByIdWithDetach(String id) {
        UserEntity user = em.find(UserEntity.class, id);
        if (user != null) {
            em.detach(user);
        }
        return user;
    }

    @Override
    public UserEntity getById(String id) {
        UserEntity user = em.find(UserEntity.class, id);
        return user;
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        // TODO check how to check if user already exists
        UserEntity merge = em.merge(userEntity);
        return merge;
    }

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        em.merge(userEntity);
        return userEntity;
    }

    @Override
    public boolean removeAll() {
        em.joinTransaction();
        Query query = em.createNativeQuery("DELETE FROM User");
        query.executeUpdate();
        return true;
    }
}
