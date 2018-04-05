package com.pycogroup.taotran.task.management.core.service.acl;


import com.pycogroup.taotran.task.management.core.entity.AbstractDocument;

public interface AclUtilService<T extends AbstractDocument> {

    void setOwnerRightForPersistenceObject(T persistedObject);
}
