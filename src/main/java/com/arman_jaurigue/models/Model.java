package com.arman_jaurigue.models;

import com.arman_jaurigue.data_objects.data_annotations.*;
import com.arman_jaurigue.data_objects.data_annotations.Parameter;
import org.apache.commons.lang3.EnumUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.*;

public final class Model {

    public static boolean BuildModel(Object model, HttpServletRequest request)
    {

        boolean modelStateValid = true;

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Ignore.class) || field.isAnnotationPresent(ErrorFor.class)) {
                continue;
            }

            String parameterName;
            if (field.isAnnotationPresent(Parameter.class)) {
                parameterName = field.getDeclaredAnnotation(Parameter.class).value();
            } else {
                parameterName = field.getName();
            }

            boolean required = field.isAnnotationPresent(Required.class);
            Class<?> cls = field.getType();
            String value = request.getParameter(parameterName);
            String errorMessage = "";
            System.out.println(field.getName());
            if (cls.isAssignableFrom(String.class)) {
                boolean valid = true;
                boolean isValueSet = false;
                if (value != null && !value.equals("")) {
                    try {
                        boolean accessible = field.canAccess(model);
                        field.setAccessible(true);
                        field.set(model, value);
                        field.setAccessible(accessible);
                        isValueSet = true;
                        valid = validateStringField(field, model, value);
                    } catch (IllegalAccessException e) {
                        valid = false;
                    }
                }
                if (required && !isValueSet) {
                    valid = false;
                    errorMessage = field.getDeclaredAnnotation(Required.class).errorMessage();
                    setValidationMessage(field.getName(), model, errorMessage);
                }
                modelStateValid = valid && modelStateValid;
                System.out.println(modelStateValid);
                continue;
            }
            if (cls.isAssignableFrom(int.class)) {
                boolean valid = true;
                boolean isValueSet = false;
                if (value != null && !value.equals("")) {
                    try {
                        int num = Integer.parseInt(value);
                        boolean accessible = field.canAccess(model);
                        field.setAccessible(true);
                        field.set(model, num);
                        field.setAccessible(accessible);
                        isValueSet = true;
                        valid = validateIntField(field, model, num);
                    } catch (IllegalAccessException e) {
                        valid = false;
                    } catch (NumberFormatException ex) {
                        valid = false;
                    }
                }
                if (required && !isValueSet) {
                    valid = false;
                    errorMessage = field.getDeclaredAnnotation(Required.class).errorMessage();
                    setValidationMessage(field.getName(), model, errorMessage);
                }
                modelStateValid = valid && modelStateValid;
                continue;
            }
            if (cls.isAssignableFrom(double.class)) {
                boolean isValueSet = false;
                boolean valid = true;
                if (value != null && !value.equals("")) {
                    try {
                        double num = Double.parseDouble(value);
                        boolean accessible = field.canAccess(model);
                        field.setAccessible(true);
                        field.set(model, num);
                        field.setAccessible(accessible);
                        isValueSet = true;
                        valid = validateDoubleField(field, model, num);
                    } catch (NumberFormatException ex) {
                        valid = false;
                    } catch (IllegalAccessException e) {
                        valid = false;
                    }
                }
                if (required && !isValueSet) {
                    valid = false;
                    errorMessage = field.getDeclaredAnnotation(Required.class).errorMessage();
                    setValidationMessage(field.getName(), model, errorMessage);
                }
                modelStateValid = valid && modelStateValid;
                continue;
            }
            if (cls.isEnum()) {
                boolean valid = true;
                boolean valueSet = false;
                Class<Enum> enumClass = (Class<Enum>) cls;
                if (value != null && !value.equals("")) {
                    if (EnumUtils.isValidEnum(enumClass, value)) {
                        try {
                            boolean accessible = field.canAccess(model);
                            field.setAccessible(true);
                            field.set(model, Enum.valueOf(enumClass, value));
                            field.setAccessible(accessible);
                            valueSet = true;
                        } catch (IllegalAccessException e) {
                            valid = false;
                        }
                    } else {
                        valid = false;
                    }
                } else if (required && !valueSet) {
                    valid = false;
                    errorMessage = field.getDeclaredAnnotation(Required.class).errorMessage();
                    setValidationMessage(field.getName(), model, errorMessage);
                }
                modelStateValid = valid && modelStateValid;
                continue;
            }
            if (cls.isAssignableFrom(char[].class)) {
                boolean isValueSet = false;
                boolean valid = true;
                if (value != null && !value.equals("")) {
                    try {
                        boolean accessible = field.canAccess(model);
                        field.setAccessible(true);
                        field.set(model, value.toCharArray());
                        field.setAccessible(accessible);
                        isValueSet = true;
                        valid = validateCharArrayField(field, model, value.toCharArray());
                    } catch (IllegalAccessException e) {
                        valid = false;
                    }
                }
                if (required && !isValueSet) {
                    valid = false;
                    errorMessage = field.getDeclaredAnnotation(Required.class).errorMessage();
                    setValidationMessage(field.getName(), model, errorMessage);
                }
                modelStateValid = valid && modelStateValid;
            }
        }

        request.setAttribute("model", model);
        return modelStateValid;
    }

    private static boolean validateStringField(Field field, Object model, String value)
    {
        System.out.println("Validating " + field.getName());
        boolean valid = true;
        String validationMessage = null;
        if (field.isAnnotationPresent(MinLength.class))
        {
            System.out.println("In Minlength");
            MinLength minLength = field.getDeclaredAnnotation(MinLength.class);
            System.out.println("Min: " + minLength.value());
            if (value.length() < minLength.value())
            {
                valid = false;
                validationMessage = (!minLength.errorMessage().equals("") ? minLength.errorMessage() : "Must be " + minLength.value() + " or more characters");
            }
        }
        if (valid && field.isAnnotationPresent(MaxLength.class))
        {
            MaxLength maxLength = field.getDeclaredAnnotation(MaxLength.class);
            if (value.length() > maxLength.value())
            {
                valid = false;
                validationMessage = (!maxLength.errorMessage().equals("") ? maxLength.errorMessage() : "Must be " + maxLength.value() + " or fewer characters");
            }
        }
        if (valid && field.isAnnotationPresent(Validate.class))
        {
            Validate validate = field.getDeclaredAnnotation(Validate.class);
            validationMessage = validate.errorMessage();
            String methodName = (validate.method().equals("") ? field.getName() + "Validation" : validate.method());

            try
            {
                Method method = model.getClass().getDeclaredMethod(methodName, String.class);
                boolean accessible = method.canAccess(Modifier.isStatic(method.getModifiers()) ? null : model);
                method.setAccessible(true);
                valid = (Boolean)method.invoke(Modifier.isStatic(method.getModifiers()) ? null : method, value);
                method.setAccessible(accessible);
            } catch (NoSuchMethodException e) {
                valid = false;
            } catch (InvocationTargetException e) {
                valid = false;
            } catch (IllegalAccessException e) {
                valid = false;
            }
        }
        if (!valid)
        {
            setValidationMessage(field.getName(), model, validationMessage);
        }

        return valid;
    }
    private static boolean validateCharArrayField(Field field, Object model, char[] value)
    {
        boolean valid = true;
        String validationMessage = null;
        if (field.isAnnotationPresent(MinLength.class))
        {
            MinLength minLength = field.getDeclaredAnnotation(MinLength.class);
            if (value.length < minLength.value())
            {
                valid = false;
                validationMessage = (!minLength.errorMessage().equals("") ? minLength.errorMessage() : "Must be " + minLength.value() + " or more characters");
            }
        }
        if (field.isAnnotationPresent(MaxLength.class))
        {
            MaxLength maxLength = field.getDeclaredAnnotation(MaxLength.class);
            if (value.length > maxLength.value())
            {
                valid = false;
                validationMessage = (!maxLength.errorMessage().equals("") ? maxLength.errorMessage() : "Must be " + maxLength.value() + " or fewer characters");
            }
        }
        if (field.isAnnotationPresent(Validate.class))
        {
            Validate validate = field.getDeclaredAnnotation(Validate.class);
            validationMessage = validate.errorMessage();
            String methodName = (validate.method().equals("") ? field.getName() + "Validation" : validate.method());

            try
            {
                Method method = model.getClass().getDeclaredMethod(methodName, char[].class);
                boolean accessible = method.canAccess(Modifier.isStatic(method.getModifiers()) ? null : model);
                method.setAccessible(true);
                valid = (Boolean)method.invoke((RegisterModel)model, value);
                method.setAccessible(accessible);
            } catch (NoSuchMethodException e) {
                valid = false;
            } catch (InvocationTargetException e) {
                valid = false;
            } catch (IllegalAccessException e) {
                valid = false;
            }
        }
        if (!valid)
        {
            setValidationMessage(field.getName(), model, validationMessage);
        }
        return valid;
    }

    private static boolean validateDoubleField(Field field, Object model, double value)
    {
        boolean valid = true;
        String validationMessage;
        if (field.isAnnotationPresent(Validate.class))
        {
            Validate validate = field.getDeclaredAnnotation(Validate.class);
            validationMessage = validate.errorMessage();
            String methodName = (validate.method().equals("") ? field.getName() + "Validation" : validate.method());

            try
            {
                Method method = model.getClass().getDeclaredMethod(methodName, double.class);
                boolean accessible = method.canAccess(Modifier.isStatic(method.getModifiers()) ? null : model);
                method.setAccessible(true);
                valid = (Boolean)method.invoke(Modifier.isStatic(method.getModifiers()) ? null : method, value);
                method.setAccessible(accessible);
            } catch (NoSuchMethodException e) {
                valid = false;
            } catch (InvocationTargetException e) {
                valid = false;
            } catch (IllegalAccessException e) {
                valid = false;
            }
            if (!valid)
            {
                setValidationMessage(field.getName(), model, validationMessage);
            }
        }
        return valid;
    }

    private static boolean validateIntField(Field field, Object model, int value)
    {
        boolean valid = true;
        String validationMessage;
        if (field.isAnnotationPresent(Validate.class))
        {
            Validate validate = field.getDeclaredAnnotation(Validate.class);
            validationMessage = validate.errorMessage();
            String methodName = (validate.method().equals("") ? field.getName() + "Validation" : validate.method());

            try
            {
                Method method = model.getClass().getDeclaredMethod(methodName, int.class);
                boolean accessible = method.canAccess(Modifier.isStatic(method.getModifiers()) ? null : model);
                method.setAccessible(true);
                valid = (Boolean)method.invoke(Modifier.isStatic(method.getModifiers()) ? null : method, value);
                method.setAccessible(accessible);
            } catch (NoSuchMethodException e) {
                valid = false;
            } catch (InvocationTargetException e) {
                valid = false;
            } catch (IllegalAccessException e) {
                valid = false;
            }
            if (!valid)
            {
                setValidationMessage(field.getName(), model, validationMessage);
            }
        }
        return valid;
    }

    private static void setValidationMessage(String fieldName, Object model, String message)
    {
        Field[] fields = model.getClass().getDeclaredFields();
        for(Field fld : fields)
        {

            if(fld.isAnnotationPresent(ErrorFor.class)
                    && fld.getDeclaredAnnotation(ErrorFor.class).field().equals(fieldName) || fld.getName().equals(fieldName + "Error"))
            {
                boolean accessible = fld.canAccess(model);
                fld.setAccessible(true);
                try {
                    fld.set(model, message);
                } catch (IllegalAccessException e) {
                }
                fld.setAccessible(accessible);
            }
        }
    }
}
