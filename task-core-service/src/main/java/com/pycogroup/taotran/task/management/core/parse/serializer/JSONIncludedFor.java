package com.pycogroup.taotran.task.management.core.parse.serializer;


import com.pycogroup.taotran.task.management.core.parse.deserializer.JSONIncludedForConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JSONIncludedForConstraintValidator.class)
public @interface JSONIncludedFor {

    String[] users() default "ALL";

    String[] roles() default "ALL";

    String[] rights() default "ALL";

    String message() default "{exclude.request.validator}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
