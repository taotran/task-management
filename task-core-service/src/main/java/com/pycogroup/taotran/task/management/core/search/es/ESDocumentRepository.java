package com.pycogroup.taotran.task.management.core.search.es;

import com.pycogroup.taotran.task.management.core.entity.AbstractDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESDocumentRepository<T extends AbstractDocument> {//extends ElasticsearchRepository<T, String> {
}
