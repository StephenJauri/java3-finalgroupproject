package com.arman_jaurigue.models;


import com.arman_jaurigue.data_objects.data_annotations.Ignore;
import com.arman_jaurigue.data_objects.data_annotations.Parameter;
import com.arman_jaurigue.data_objects.data_annotations.Required;
import org.apache.commons.lang3.EnumUtils;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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

}