package com.pycogroup.taotran.task.management.core.service;

import com.pycogroup.taotran.task.management.core.entity.AbstractDocument;
import com.pycogroup.taotran.task.management.core.repository.DocumentRepository;
import com.pycogroup.taotran.task.management.core.service.acl.AclUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentServiceBean<T extends AbstractDocument> implements DocumentService<T> {

    @Autowired
    private DocumentRepository<T> documentRepository;

    @Autowired
    private AclUtilService<T> aclUtilService;

    @Override
    public List<T> findAll() {
        return documentRepository.findAll();
    }

    @Override
    public T findOne(String id) {
        return documentRepository.findOne(id);
    }

    @Override
    @Transactional
    public T save(T t) {

        final T persistedObject = documentRepository.save(t);

        aclUtilService.setOwnerRightForPersistenceObject(persistedObject);

        return t;
    }

    @Override
    public void delete(T t) {
        documentRepository.delete(t);
    }

    @Override
    public void delete(String id) {
        documentRepository.delete(id);
    }
}
