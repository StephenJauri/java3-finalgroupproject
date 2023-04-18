package com.arman_jaurigue.models;


import com.arman_jaurigue.data_objects.Ignore;
import com.arman_jaurigue.data_objects.Parameter;
import com.arman_jaurigue.data_objects.Required;
import org.apache.commons.lang3.EnumUtils;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ModelAnnotated {
    @Ignore
    protected Map<String, Consumer<String>> parameterMaps = new HashMap<>();
    @Ignore
    private boolean modelStateValid = true;

    public boolean isModelStateValid() {
        return modelStateValid;
    }

    public ModelAnnotated(HttpServletRequest request)
    {
        associateData();
        for (Map.Entry<String, Consumer<String>> entry : parameterMaps.entrySet())
        {
            entry.getValue().accept(request.getParameter(entry.getKey()));
        }
        request.setAttribute("model", this);
    }

    private void associateData()
    {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(Ignore.class))
            {
                continue;
            }

            String parameterName;
            if (field.isAnnotationPresent(Parameter.class))
            {
                parameterName = field.getDeclaredAnnotation(Parameter.class).value();
            }
            else
            {
                parameterName = field.getName();
            }

            boolean required = field.isAnnotationPresent(Required.class);
            try
            {
                Class<?> cls = field.getType();
                if (cls.isAssignableFrom(String.class)) {
                    parameterMaps.put(parameterName, value ->
                    {
                        if (value != null)
                        {
                            try {
                                field.setAccessible(true);
                                field.set(this,value);
                                field.setAccessible(false);
                            } catch (IllegalAccessException e) {
                                modelStateValid = false;
                            }
                        }
                        else if (required)
                        {
                            modelStateValid = false;
                        }
                    });
                }
                if (cls.isAssignableFrom(Integer.class)) {

                    parameterMaps.put(parameterName, value ->
                    {
                        if (value != null)
                        {
                            try{
                                int num = Integer.parseInt(value);
                                field.setAccessible(true);
                                field.set(this, num);
                                field.setAccessible(false);
                            } catch (IllegalAccessException e) {
                                modelStateValid = false;
                            } catch (NumberFormatException ex) {
                                modelStateValid = false;
                            }
                        }
                        else if (required)
                        {
                            modelStateValid = false;
                        }
                    });
                }
                if (cls.isAssignableFrom(Double.class)) {
                    parameterMaps.put(parameterName, value ->
                    {
                        if (value != null)
                        {
                            try{
                                double num = Double.parseDouble(value);
                                field.setAccessible(true);
                                field.set(this, num);
                                field.setAccessible(false);
                            }
                            catch (NumberFormatException ex)
                            {
                                modelStateValid = false;
                            } catch (IllegalAccessException e) {
                                modelStateValid = false;
                            }
                        }
                        else if (required)
                        {
                            modelStateValid = false;
                        }
                    });
                }
                if (cls.isEnum()) {
                    parameterMaps.put(parameterName, value ->
                    {
                        Class<Enum> enumClass = (Class<Enum>)cls;
                        if (value != null)
                        {
                            if (EnumUtils.isValidEnum(enumClass,value))
                            {
                                try {
                                    field.setAccessible(true);
                                    field.set(this, Enum.valueOf(enumClass,value));
                                    System.out.println("Inside Model value: " + field.get(this));
                                    field.setAccessible(false);
                                } catch (IllegalAccessException e) {
                                    System.out.println("Error");
                                    modelStateValid = false;
                                }
                            }
                            else
                            {
                                modelStateValid = false;
                            }
                        }
                        else if (required)
                        {
                            modelStateValid = false;
                        }
                    });
                }
            } catch (IllegalArgumentException ex) {
                modelStateValid = false;
            }
        }
    }

    public static boolean CreateModel(Object object, HttpServletRequest request)
    {
        boolean modelStateValid = true;
        Map<String, Function<String,Boolean>> parameterMaps = new HashMap<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(Ignore.class))
            {
                continue;
            }

            String parameterName;
            if (field.isAnnotationPresent(Parameter.class))
            {
                parameterName = field.getDeclaredAnnotation(Parameter.class).value();
            }
            else
            {
                parameterName = field.getName();
            }

            boolean required = field.isAnnotationPresent(Required.class);
            try
            {
                Class<?> cls = field.getType();
                if (cls.isAssignableFrom(String.class)) {
                    parameterMaps.put(parameterName, value ->
                    {
                        boolean valid = true;
                        if (value != null)
                        {
                            try {
                                field.setAccessible(true);
                                field.set(object,value);
                                field.setAccessible(false);
                            } catch (IllegalAccessException e) {
                                valid = false;
                            }
                        }
                        else if (required)
                        {
                            valid = false;
                        }
                        return valid;
                    });
                }
                if (cls.isAssignableFrom(Integer.class)) {

                    parameterMaps.put(parameterName, value ->
                    {
                        boolean valid = true;
                        if (value != null)
                        {
                            try{
                                int num = Integer.parseInt(value);
                                field.setAccessible(true);
                                field.set(object, num);
                                field.setAccessible(false);
                            } catch (IllegalAccessException e) {
                                valid = false;
                            } catch (NumberFormatException ex) {
                                valid = false;
                            }
                        }
                        else if (required)
                        {
                            valid = false;
                        }
                        return valid;
                    });
                }
                if (cls.isAssignableFrom(Double.class)) {
                    parameterMaps.put(parameterName, value ->
                    {
                        boolean valid = true;
                        if (value != null)
                        {
                            try{
                                double num = Double.parseDouble(value);
                                field.setAccessible(true);
                                field.set(object, num);
                                field.setAccessible(false);
                            }
                            catch (NumberFormatException ex)
                            {
                                valid = false;
                            } catch (IllegalAccessException e) {
                                valid = false;
                            }
                        }
                        else if (required)
                        {
                            valid = false;
                        }
                        return valid;
                    });
                }
                if (cls.isEnum()) {
                    parameterMaps.put(parameterName, value ->
                    {
                        boolean valid = true;
                        Class<Enum> enumClass = (Class<Enum>)cls;
                        if (value != null)
                        {
                            if (EnumUtils.isValidEnum(enumClass,value))
                            {
                                try {
                                    field.setAccessible(true);
                                    field.set(object, Enum.valueOf(enumClass,value));
                                    field.setAccessible(false);
                                } catch (IllegalAccessException e) {
                                    System.out.println("Error");
                                    valid = false;
                                }
                            }
                            else
                            {
                                valid = false;
                            }
                        }
                        else if (required)
                        {
                            valid = false;
                        }
                        return valid;
                    });
                }
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
        for (Map.Entry<String, Function<String, Boolean>> entry : parameterMaps.entrySet())
        {
            modelStateValid = modelStateValid && entry.getValue().apply(request.getParameter(entry.getKey()));
        }
        request.setAttribute("model", object);
        return modelStateValid;
    }

}