package ru.buyanov.experimental.bytecode.dto.generator;

import com.fasterxml.jackson.annotation.JsonCreator;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import ru.buyanov.experimental.bytecode.dto.generator.interfaces.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.bytebuddy.matcher.ElementMatchers.isGetter;
import static net.bytebuddy.matcher.ElementMatchers.isSetter;

/**
 * @author A.Buyanov 07.05.2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        new Main().init();
    }

    public void init() throws Exception {
        Class<User> interfaceClass = User.class;
        String dtoClassName = interfaceClass.getCanonicalName() + "DTO";
        File folder = Paths.get(".").toFile();
        Class<?> createdClass = new ByteBuddy()
                .subclass(Object.class)
                .name(dtoClassName)
                .implement(interfaceClass)
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();

        DynamicType.Builder<Object> builder = new ByteBuddy()
                .subclass(Object.class)
                .name(dtoClassName);

        for (PropertyDescriptor property : getPropertyDescriptors(interfaceClass)) {
            builder = builder.defineField(property.getName(), property.getPropertyType(), Visibility.PRIVATE);
        }

        builder
                .implement(interfaceClass)
                .method(isGetter().or(isSetter()))
                .intercept(FieldAccessor.ofBeanProperty())
                .defineMethod("create", interfaceClass, Visibility.PUBLIC)
                .intercept(MethodDelegation.toConstructor(createdClass))
                .annotateMethod(AnnotationDescription.Builder.ofType(JsonCreator.class).build())
                .make()
                .saveIn(folder);
    }


    /**
     * http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4275879
     */
    private List<PropertyDescriptor> getPropertyDescriptors(Class<User> interfaceClass) {
        List<PropertyDescriptor> propertyDescriptors = Stream.of(interfaceClass.getInterfaces())
                .flatMap(this::descriptorStream)
                .collect(Collectors.toList());
        propertyDescriptors.addAll(descriptorStream(interfaceClass).collect(Collectors.toList()));
        return propertyDescriptors;
    }

    Stream<PropertyDescriptor> descriptorStream(Class c) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(c);
            return Stream.of(beanInfo.getPropertyDescriptors());
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }
}
