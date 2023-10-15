package br.com.luiscutilak.todolist2.utils;


import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;



//ABAIXO LÓGICA QUE COPIA OS DADOS, E ALTERA PARCIALMENTE A TASK. E DEVOLVE SOMENTE O QUE FOI ALTERADO PARCIAL.
public class Utils {
    //Quando utilizamos static não precisamos instanciar nossa classe
    public static void copyNonNullProperties(Object source, Object target) {
    
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));

    }    

    // Esse método retorna todas as propriedades que estão nulas
    public static String[] getNullPropertyNames(Object source) {
        //BeanWrapper, uma forma de acessar propriedades de um objeto
        //BeanWrapperImpl é a implementação da interface
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>(); 

        for(PropertyDescriptor pd : pds) {
           Object srcValue = src.getPropertyValue(pd.getName());
           if(srcValue == null) {
            emptyNames.add(pd.getName());
           }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);

    }
    
}
