package com.pycogroup.taotran.task.management.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;


public class HeaderUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderUtil.class);

    private static final String APPLICATION_NAME = "taskCoreApp";

    private HeaderUtil() {

    }

    static HttpHeaders createAlert(String message, String param) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-taskCoreApp-alert", message);
        headers.add("X-taskCoreApp-param", param);

        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeleteAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        LOGGER.error("Entity processing failed, {}", defaultMessage);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-taskCoreApp-alert", "error." + errorKey);
        headers.add("X-taskCoreApp-params", entityName);

        return headers;
    }


}
