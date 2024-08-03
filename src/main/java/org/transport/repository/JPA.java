package org.transport.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.transport.common.CommonUtils;

import java.util.List;
import java.util.Map;

@Repository
public class JPA<ENTITY, ID> {
    @PersistenceContext()
    private EntityManager entityManager;

    @Transactional
    public void save(ENTITY entity) throws Exception {
        CommonUtils.setNull(entity);
        entityManager.persist(entity);
    }

    @Transactional
    public void update(ENTITY entity) throws Exception {
        CommonUtils.setNull(entity);
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

    public List listByQuery(Query query, Map<String, Object> param) {
        if (!CommonUtils.isNull(param) && param.size() > 0)
            for (String key : param.keySet()) {
                query.setParameter(key, param.get(key));
            }
        return query.getResultList();
    }

    public void persist(Object o) {
        entityManager.persist(o);
    }

    public Page<ENTITY> findAllWithPaging(Class<ENTITY> aClass) {
        Entity entity = aClass.getAnnotation(Entity.class);
        Query query = entityManager.createQuery("select entity from " + entity.name() + " entity");
        List<ENTITY> fooList = query.getResultList();
        PageRequest pageRequest = PageRequest.of(0, fooList.isEmpty() ? 1 : fooList.size());
        return new PageImpl<ENTITY>(fooList, pageRequest, fooList.isEmpty() ? 1 : fooList.size());
    }

    public Page<ENTITY> findAllWithPaging(Class<ENTITY> aClass, PageRequest pageRequest) {
        Entity entity = aClass.getAnnotation(Entity.class);
        Query query = entityManager.createQuery("select entity from " + entity.name() + " entity");
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List<ENTITY> fooList = query.getResultList();
        Query queryTotal = entityManager.createQuery("select count(entity.id) from " + entity.name() + " entity");
        Long countResult = (Long) queryTotal.getSingleResult();
        return new PageImpl<ENTITY>(fooList, pageRequest, countResult);
    }

    public Page listByQueryWithPaging(Query query, Map<String, Object> param, PageRequest pageRequest) {
        if (!CommonUtils.isNull(param) && !param.isEmpty())
            for (String key : param.keySet()) {
                query.setParameter(key, param.get(key));
            }
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List fooList = query.getResultList();
        long countResult = (long) fooList.size();
        return new PageImpl(fooList, pageRequest, countResult);
    }

    public int executeUpdate(Query query, Map<String, Object> param) {
        if (!CommonUtils.isNull(param) && !param.isEmpty())
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        return query.executeUpdate();
    }
}
