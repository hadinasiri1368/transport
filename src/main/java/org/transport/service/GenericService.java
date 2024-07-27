package org.transport.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.model.BaseEntity;
import org.transport.repository.JPA;

import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GenericService<Entity> {
    @Autowired
    private JPA<Entity, Long> genericJPA;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${PageRequest.page}")
    private Integer page;
    @Value("${PageRequest.size}")
    private Integer size;

    @Transactional
    public void insert(Entity entity, Long userId) throws Exception {
        Method m = entity.getClass().getMethod("setId", Long.class);
        m.invoke(entity, (Long) null);
        CommonUtils.setNull(entity);
        ((BaseEntity) entity).setInsertedUserId(userId);
        ((BaseEntity) entity).setInsertedDateTime(new Date());
        genericJPA.save(entity);
    }

    @Transactional
    public void update(Entity entity, Long userId, Class<Entity> aClass) throws Exception {
        Method m = entity.getClass().getMethod("getId");
        Long id = (Long) m.invoke(entity);
        if (CommonUtils.isNull(id))
            throw new RuntimeException("2004");
        if (CommonUtils.isNull(findOne(aClass, id)))
            throw new RuntimeException("2004");
        ((BaseEntity) entity).setUpdatedUserId(userId);
        ((BaseEntity) entity).setUpdatedDateTime(new Date());
        genericJPA.update(entity);
    }

    @Transactional
    public void delete(Entity entity) {
        genericJPA.remove(entity);
    }

    @Transactional
    public int delete(Long id, Class<Entity> aClass) {
        jakarta.persistence.Entity entity = aClass.getAnnotation(jakarta.persistence.Entity.class);
        int returnValue = entityManager.createQuery("delete  " + entity.name() + " o where o.id=:id").setParameter("id", id).executeUpdate();
        if (returnValue == 0) {
            throw new RuntimeException("2004");
        }
        return returnValue;
    }

    public Entity findOne(Class<Entity> aClass, Long id) {
        return genericJPA.findOne(aClass, id);
    }

    public List<Entity> findAll(Class<Entity> aClass) {
        return genericJPA.findAll(aClass);
    }

    public Page<Entity> findAll(Class<Entity> aClass, Integer page, Integer size) {
        if (CommonUtils.isNull(page) && CommonUtils.isNull(size)) {
            return genericJPA.findAllWithPaging(aClass);
        }
        PageRequest pageRequest = PageRequest.of(CommonUtils.isNull(page, this.page), CommonUtils.isNull(size, this.size));
        return genericJPA.findAllWithPaging(aClass, pageRequest);

    }

}