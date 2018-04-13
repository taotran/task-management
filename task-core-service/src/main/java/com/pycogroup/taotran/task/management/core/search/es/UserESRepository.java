package com.pycogroup.taotran.task.management.core.search.es;

import com.pycogroup.taotran.task.management.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//@Repository
//public interface UserESRepository extends ESDocumentRepository<User> {
public interface UserESRepository extends ElasticsearchRepository<User, String> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"user.username\":\"?0\"}}]}}")
    Page<User> findByUsername(String username, Pageable pageable);


}
