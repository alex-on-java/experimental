package ru.buyanov.experimental.bytecode.dto.generator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author A.Buyanov 26.05.2017.
 */
public class PropertyExtractor {
    public List<PropertyInfo> fromInterface(Class<?> interfaceClass) {
        if (!interfaceClass.isInterface()) {
            throw new RuntimeException("This method only for interfaces!");
        }
        return getPropertyDescriptors(interfaceClass).stream()
                .map(p -> new PropertyInfo(p.getName(), p.getPropertyType()))
                .collect(Collectors.toList());
    }

    /**
     * http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4275879
     */
    protected List<PropertyDescriptor> getPropertyDescriptors(Class<?> interfaceClass) {
        List<PropertyDescriptor> propertyDescriptors = Stream.of(interfaceClass.getInterfaces())
                .flatMap(this::descriptorStream)
                .collect(Collectors.toList());
        propertyDescriptors.addAll(descriptorStream(interfaceClass).collect(Collectors.toList()));
        return propertyDescriptors;
    }

    protected Stream<PropertyDescriptor> descriptorStream(Class c) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(c);
            return Stream.of(beanInfo.getPropertyDescriptors());
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }
}
