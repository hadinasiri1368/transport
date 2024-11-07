package org.transport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transport.common.CommonUtils;
import org.transport.common.MapperUtil;
import org.transport.common.ObjectMapperUtils;
import org.transport.dto.PlaqueDto;
import org.transport.dto.Response.PlaqueTagPersianPartDto;
import org.transport.model.Plaque;
import org.transport.repository.JPA;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlaqueService {
    @Value("${PageRequest.page}")
    private Integer page;
    @Value("${PageRequest.size}")
    private Integer size;

    @PersistenceContext
    private EntityManager entityManager;

    private final JPA<Plaque, Long> jpaPlaque;

    private final BasicDataServiceProxy basicDataServiceProxy;

    public PlaqueService(JPA<Plaque, Long> jpaPlaque, BasicDataServiceProxy basicDataServiceProxy) {

        this.jpaPlaque = jpaPlaque;
        this.basicDataServiceProxy = basicDataServiceProxy;
    }

    @Transactional
    public void insert(Plaque plaque, Long userId) throws Exception {
        plaque.setId(null);
        plaque.setInsertedUserId(userId);
        plaque.setInsertedDateTime(new Date());
        jpaPlaque.save(plaque);
    }

    @Transactional
    public void update(Plaque plaque, Long userId) throws Exception {
        if (CommonUtils.isNull(plaque.getId()))
            throw new RuntimeException("3005");
        if (CommonUtils.isNull(findOne(Plaque.class, plaque.getId())))
            throw new RuntimeException("3005");
        plaque.setUpdatedUserId(userId);
        plaque.setUpdatedDateTime(new Date());
        jpaPlaque.update(plaque);
    }


    @Transactional
    public void delete(Plaque plaque) {
        jpaPlaque.remove(plaque);
    }

    @Transactional
    public int delete(Long id) {
        Query query = entityManager.createQuery("delete from plaque where id = :id");
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return jpaPlaque.executeUpdate(query, param);
    }

    public Plaque findOne(Class<Plaque> aClass, Long id) {
        return jpaPlaque.findOne(aClass, id);
    }

    public PlaqueDto findOne(Long id, String token, String uuid) {
        Plaque plaque = jpaPlaque.findOne(Plaque.class, id);
        List<PlaqueTagPersianPartDto> plaqueTagPersianPartDtos = basicDataServiceProxy.listPlaqueTagPersianPart(token, uuid).getContent();
        return MapperUtil.mapToPlaqueDto(plaque, plaqueTagPersianPartDtos);
    }

    public List<Plaque> findAll(Class<Plaque> aClass) {
        return jpaPlaque.findAll(aClass);
    }

    public Page<PlaqueDto> findAll(Integer page, Integer size, String token, String uuid) {
        List<Plaque> plaques = findAll(Plaque.class);
        List<PlaqueTagPersianPartDto> plaqueTagPersianPartDtos = basicDataServiceProxy.listPlaqueTagPersianPart(token, uuid).getContent();
        List<PlaqueDto> plaqueDtos = plaques.stream()
                .map(plaque -> MapperUtil.mapToPlaqueDto(plaque, plaqueTagPersianPartDtos))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(CommonUtils.isNull(page, this.page), CommonUtils.isNull(size, this.size));
        return CommonUtils.listPaging(plaqueDtos, pageRequest);
    }
}
