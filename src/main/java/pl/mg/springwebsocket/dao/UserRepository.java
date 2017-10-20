package pl.mg.springwebsocket.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public List<UserEntity> getAll() {
        return em.createQuery("from UserEntity").getResultList();
    }
}
