package pl.mg.springwebsocket.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.mg.springwebsocket.Profiles;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public List<UserEntity> getAll() {
        return em.createQuery("from UserEntity").getResultList();
    }

    /**
     * with detach
     * 
     * @param name
     * @return
     */
    public UserEntity getByIdWithDetach(String id) {
        UserEntity user = em.find(UserEntity.class, id);
        if (user != null) {
            em.detach(user);
        }
        return user;
    }

    public UserEntity getById(String id) {
        UserEntity user = em.find(UserEntity.class, id);
        return user;
    }

    @Transactional
    public UserEntity updateUser(UserEntity userEntity) {
        // TODO check how to check if user already exists
        UserEntity merge = em.merge(userEntity);
        return merge;

    }

    @Transactional
    public UserEntity addUser(UserEntity userEntity) {
        em.persist(userEntity);
        return userEntity;

    }

    @Transactional
    @Profile(Profiles.TEST)
    public boolean removeAll() {
        Query query = em.createNativeQuery("DELETE FROM User");
        query.executeUpdate();
        return true;

    }
}
