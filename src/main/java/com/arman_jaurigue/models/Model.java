package com.arman_jaurigue.models;

import com.arman_jaurigue.data_objects.Ignore;
import com.arman_jaurigue.data_objects.Parameter;
import com.arman_jaurigue.data_objects.Required;
import org.apache.commons.lang3.EnumUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class Model {
    public static boolean BuildModel(Object model, HttpServletRequest request)
    {
        boolean modelStateValid = true;
        Map<String, Function<String,Boolean>> parameterMaps = new HashMap<>();

        Field[] fields = model.getClass().getDeclaredFields();
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
                                field.set(model,value);
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
                                field.set(model, num);
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
                                field.set(model, num);
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
                                    field.set(model, Enum.valueOf(enumClass,value));
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
        request.setAttribute("model", model);
        return modelStateValid;
    }
}
