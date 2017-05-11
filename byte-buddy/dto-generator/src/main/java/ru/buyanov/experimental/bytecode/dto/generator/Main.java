package ru.buyanov.experimental.bytecode.dto.generator;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.bytebuddy.matcher.ElementMatchers.isGetter;
import static net.bytebuddy.matcher.ElementMatchers.isSetter;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author A.Buyanov 07.05.2017.
 */
public class Main {

    /**
     * @param mapper Mapper that would be used for reading object from JSON.
     *               Warning! Mapper will be modified inside method - new Module will be registered.
     * @param json Object representation in JSON
     * @param interfaceClass Interface class that would be implemented by newly created class.
     * @return Object that implements <code>interfaceClass</code> and contains data from interfaceClass <code>json</code>
     */
    public <T> T parseJson(ObjectMapper mapper, String json, Class<T> interfaceClass) {
        T dto = createDTO(interfaceClass);
        mapper.registerModule(createAbstractTypeResolverModule(interfaceClass, dto.getClass()));
        try {
            return mapper.readValue(json, interfaceClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T createDTO(Class<T> interfaceClass) {
        String dtoClassName = interfaceClass.getCanonicalName() + "DTO";

        DynamicType.Builder<Object> builder = new ByteBuddy()
                .subclass(Object.class)
                .name(dtoClassName);

        for (PropertyDescriptor property : getPropertyDescriptors(interfaceClass)) {
            builder = builder.defineField(property.getName(), property.getPropertyType(), Visibility.PRIVATE);
        }

        Class<?> createdClass = builder
                .implement(interfaceClass)
                .method(isGetter().or(isSetter()))
                .intercept(FieldAccessor.ofBeanProperty())
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
        try {
            return interfaceClass.cast(createdClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4275879
     */
    private List<PropertyDescriptor> getPropertyDescriptors(Class<?> interfaceClass) {
        List<PropertyDescriptor> propertyDescriptors = Stream.of(interfaceClass.getInterfaces())
                .flatMap(this::descriptorStream)
                .collect(Collectors.toList());
        propertyDescriptors.addAll(descriptorStream(interfaceClass).collect(Collectors.toList()));
        return propertyDescriptors;
    }

    private Stream<PropertyDescriptor> descriptorStream(Class c) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(c);
            return Stream.of(beanInfo.getPropertyDescriptors());
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }

    private Module createAbstractTypeResolverModule(Class interfaceClass, Class dtoClass) {
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        SimpleModule module = new SimpleModule("DtoFromInterface");
        module.setAbstractTypes(resolver);
        resolver.addMapping(interfaceClass, dtoClass);
        return module;
    }

}
