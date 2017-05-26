package ru.buyanov.experimental.bytecode.dto.generator;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * @author A.Buyanov 07.05.2017.
 */
public class Main {
    private final DtoGenerator dtoGenerator;

    public Main(DtoGenerator dtoGenerator) {
        this.dtoGenerator = dtoGenerator;
    }

    /**
     * @param mapper Mapper that would be used for reading object from JSON.
     *               Warning! Mapper will be modified inside method - new Module will be registered.
     * @param json Object representation in JSON
     * @param interfaceClass Interface class that would be implemented by newly created class.
     * @return Object that implements <code>interfaceClass</code> and contains data from <code>json</code>
     */
    public <T> T parseJson(ObjectMapper mapper, String json, Class<T> interfaceClass) {
        T dto = dtoGenerator.generateAndLoad(interfaceClass, interfaceClass.getCanonicalName() + "DTO");
        mapper.registerModule(createAbstractTypeResolverModule(interfaceClass, dto.getClass()));
        try {
            return mapper.readValue(json, interfaceClass);
        } catch (IOException e) {
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
