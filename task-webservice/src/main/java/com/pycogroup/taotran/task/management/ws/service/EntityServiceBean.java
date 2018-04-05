package com.pycogroup.taotran.task.management.ws.service;

import com.pycogroup.taotran.task.management.ws.entity.AbstractEntity;
import com.pycogroup.taotran.task.management.ws.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class EntityServiceBean<T extends AbstractEntity> implements EntityService<T> {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntityRepository<T> entityRepository;

    @Override
    public List<T> findAll() {
        return entityRepository.findAll();
    }

    @Override
    public T findOne(String id) {
        return entityRepository.findOne(id);
    }

    @Override
    public T save(T t) {
        em.persist(t);
        em.flush();

        return t;
    }

    @Override
    public void delete(T t) {
        entityRepository.delete(t);
    }

    @Override
    public void delete(String id) {
        entityRepository.delete(id);
    }
}
