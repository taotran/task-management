package com.pycogroup.taotran.task.management.ws.repository;

import com.pycogroup.taotran.task.management.ws.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository<T extends AbstractEntity> extends JpaRepository<T, String> {
}
