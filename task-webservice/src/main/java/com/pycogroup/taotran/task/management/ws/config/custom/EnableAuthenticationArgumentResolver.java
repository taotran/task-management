package com.pycogroup.taotran.task.management.ws.config.custom;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class EnableAuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String ADMIN_CREDENTIALS = "admin:admin123";
    private static final String USER_CREDENTIALS = "user123:user1234";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return String.class.equals(parameter.getParameterType()) && parameter.getParameterAnnotation(EnableAuthentication.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final EnableAuthentication enableAuthentication = parameter.getParameterAnnotation(EnableAuthentication.class);

        final String plainCreds = enableAuthentication.adminAuth() ? ADMIN_CREDENTIALS : USER_CREDENTIALS;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        return new String(base64CredsBytes);
    }
}
