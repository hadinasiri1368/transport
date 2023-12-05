package org.transport.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JPA<ENTITY, ID> {
    @PersistenceContext()
    private EntityManager entityManager;

    @Transactional
    public void save(ENTITY entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(ENTITY entity) {
        entityManager.merge(entity);
    }

    @Transactional
    public void remove(ENTITY entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    public ENTITY findOne(Class<ENTITY> aClass, ID id) {
        return entityManager.find(aClass, id);
    }

    public List<ENTITY> findAll(Class<ENTITY> aClass) {
        Entity entity = aClass.getAnnotation(Entity.class);
        Query query = entityManager.createQuery("select entity from " + entity.name() + " entity");
        return query.getResultList();
    }
}
