package org.transport.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.transport.model.BaseEntity;
import org.transport.repository.JPA;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GenericService<Entity> {
    @Autowired
    private JPA<Entity, Long> genericJPA;

    public void insert(Entity entity, Long userId) {
        try {
            Method m = entity.getClass().getMethod("setId", Long.class);
            m.invoke(entity, (Long) null);
        } catch (Exception e) {
            log.error("setId has error: " + e.getMessage());
        }
        ((BaseEntity) entity).setInsertedUserId(userId);
        ((BaseEntity) entity).setInsertedDateTime(new Date());
        genericJPA.save(entity);
    }

    public void update(Entity entity, Long userId) {
        ((BaseEntity) entity).setUpdatedUserId(userId);
        ((BaseEntity) entity).setUpdatedDateTime(new Date());
        genericJPA.update(entity);
    }

    public void delete(Entity entity) {
        genericJPA.remove(entity);
    }

    public Entity findOne(Class<Entity> aClass, Long id) {
        return genericJPA.findOne(aClass, id);
    }

    public List<Entity> findAll(Class<Entity> aClass) {
        return genericJPA.findAll(aClass);
    }


}