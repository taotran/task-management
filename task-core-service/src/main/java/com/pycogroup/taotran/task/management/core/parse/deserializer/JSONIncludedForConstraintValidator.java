package com.pycogroup.taotran.task.management.core.parse.deserializer;

import com.pycogroup.taotran.task.management.core.parse.serializer.JSONIncludedFor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JSONIncludedForConstraintValidator implements ConstraintValidator<JSONIncludedFor, Object> {

    private List<String> users;

    private List<String> roles;

    private List<String> rights;

    @Override
    public void initialize(JSONIncludedFor jsonIncludedFor) {
        roles = new ArrayList<>(Arrays.asList(jsonIncludedFor.roles()));
        rights = new ArrayList<>(Arrays.asList(jsonIncludedFor.rights()));
        users = new ArrayList<>(Arrays.asList(jsonIncludedFor.users()));
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (users.contains(userDetails.getUsername())) {
            return true;
        }

        return false;
    }
}
