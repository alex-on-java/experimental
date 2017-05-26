package ru.buyanov.experimental.bytecode.dto.generator;

import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author A.Buyanov 26.05.2017.
 */
public class PropertyExtractorTest {
    private PropertyExtractor service;

    @Before
    public void startUp() {
        service = new PropertyExtractor();
    }

    @Test
    public void testFromInterface_currentMethods() {
        List<PropertyInfo> propertyInfoList = service.fromInterface(Name.class);
        assertEquals(1, propertyInfoList.size());
        assertEquals("name", propertyInfoList.get(0).getName());
        assertEquals(String.class, propertyInfoList.get(0).getType());
    }

    @Test
    public void testFromInterface_includeInheritedMethods() {
        List<PropertyInfo> list = service.fromInterface(User.class);
        assertEquals(2, list.size());
        assertEquals(String.class, list.stream().filter(p -> "name".equals(p.getName())).findFirst().get().getType());
        assertEquals(int.class, list.stream().filter(p -> "id".equals(p.getName())).findFirst().get().getType());
    }

    public interface Name {
        String getName();
        void setName(String name);
    }

    public interface User extends Name {
        int getId();
        void setId(int id);
    }
}
