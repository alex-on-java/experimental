package ru.buyanov.experimental.bytecode.dto.generator;

import java.lang.reflect.Type;

/**
 * @author A.Buyanov 26.05.2017.
 */
public class PropertyInfo {
    private final String name;
    private final Type type;

    public PropertyInfo(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
