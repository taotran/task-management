package com.pycogroup.taotran.task.management.core.service;


import com.pycogroup.taotran.task.management.core.entity.AbstractDocument;

import java.util.List;

public interface DocumentService<T extends AbstractDocument> {

    List<T> findAll();

    T findOne(String id);

    T save(T t);

    void delete(T t);

    void delete(String id);

}
