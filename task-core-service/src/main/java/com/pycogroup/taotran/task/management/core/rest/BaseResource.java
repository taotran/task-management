package com.pycogroup.taotran.task.management.core.rest;

import com.pycogroup.taotran.task.management.core.entity.AbstractDocument;
import com.pycogroup.taotran.task.management.core.service.DocumentService;
import com.pycogroup.taotran.task.management.core.util.HeaderUtil;
import com.pycogroup.taotran.task.management.core.util.ResponseURIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

public class BaseResource<T extends AbstractDocument> {

    @Autowired
    protected DocumentService<T> documentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostFilter("hasRole('ADMIN') or hasPermission(filterObject, 'READ') or hasPermission(filterObject, admin)")
    public @Valid
    List<T> findAll() {
        return documentService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostFilter("hasRole('ADMIN') or hasPermission(filterObject, 'READ') or hasPermission(filterObject, admin)")
    public T findOne(@PathVariable String id) {
        return documentService.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<T> save(@Valid @RequestBody T t) throws URISyntaxException {
        final T result = documentService.save(t);

        return ResponseEntity.created(ResponseURIUtils.getURI(result))
                .headers(HeaderUtil.createEntityCreationAlert(result.getClass().getSimpleName(), result.getId()))
                .body(result);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam String id) {
        documentService.delete(id);
    }

}
