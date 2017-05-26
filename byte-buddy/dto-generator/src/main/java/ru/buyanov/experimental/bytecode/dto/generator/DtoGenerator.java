package ru.buyanov.experimental.bytecode.dto.generator;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static net.bytebuddy.matcher.ElementMatchers.isGetter;
import static net.bytebuddy.matcher.ElementMatchers.isSetter;

/**
 * @author A.Buyanov 26.05.2017.
 */
public class DtoGenerator {
    private final PropertyExtractor propertyExtractor;

    public DtoGenerator(PropertyExtractor propertyExtractor) {
        this.propertyExtractor = propertyExtractor;
    }

    public <T> void generateAndSave(Class<T> interfaceClass, String className, File pathToSave) {
        if (!pathToSave.isDirectory()) {
            throw new RuntimeException("pathToSave should be directory");
        }
        try {
            generateUnloaded(interfaceClass, className).saveIn(pathToSave);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T generateAndLoad(Class<T> interfaceClass, String className) {
        return generateAndLoad(interfaceClass, className, this.getClass().getClassLoader());
    }

    public <T> T generateAndLoad(Class<T> interfaceClass, String className, ClassLoader classLoader) {
        Class<? extends T> dtoClass = generateUnloaded(interfaceClass, className)
                .load(classLoader)
                .getLoaded();
        try {
            return dtoClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> DynamicType.Unloaded<T> generateUnloaded(Class<T> interfaceClass, String className) {
        DynamicType.Builder<T> builder = new ByteBuddy()
                .subclass(interfaceClass)
                .name(className);

        for (PropertyInfo property : propertyExtractor.fromInterface(interfaceClass)) {
            builder = builder.defineField(property.getName(), property.getType(), Visibility.PRIVATE);
        }


        return builder
                .method(isGetter().or(isSetter()))
                .intercept(FieldAccessor.ofBeanProperty())
                .make();
    }
}
