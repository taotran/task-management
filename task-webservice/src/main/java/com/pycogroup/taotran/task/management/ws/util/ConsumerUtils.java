package com.pycogroup.taotran.task.management.ws.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConsumerUtils {

    public static HttpEntity<String> buildHttpRequest(String authorizationString) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authorizationString);
        return new HttpEntity<>(headers);
    }

    public static HttpEntity<Object> buildHttpRequest(String authorizationString, Object body) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authorizationString);
        return new HttpEntity<>(body, headers);
    }

    public static List<Object> toList(Object[] objects) {
        final List<Object> objectList = new ArrayList<>();
        Collections.addAll(objectList, objects);

        return objectList;
    }

    private ConsumerUtils(){}

}
