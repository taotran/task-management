package com.pycogroup.taotran.task.management.core.util;


import com.pycogroup.taotran.task.management.core.constant.MappingPath;
import com.pycogroup.taotran.task.management.core.entity.AbstractDocument;
import com.pycogroup.taotran.task.management.core.entity.Task;
import com.pycogroup.taotran.task.management.core.entity.User;

import java.net.URI;
import java.net.URISyntaxException;

public class ResponseURIUtils {

    public static <T extends AbstractDocument> URI getURI(T result) throws URISyntaxException {
        if (result instanceof User) {
            return new URI(MappingPath.USER + result.getId());
        } else if (result instanceof Task) {
            return new URI(MappingPath.TASK + result.getId());
        }

        return null;

    }


    private ResponseURIUtils() {

    }
}
