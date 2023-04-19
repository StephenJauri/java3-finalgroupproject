package com.arman_jaurigue.data_objects.data_annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckAfter {
    String errorField() default "";
    String errorMessage() default "Something went wrong";
}
