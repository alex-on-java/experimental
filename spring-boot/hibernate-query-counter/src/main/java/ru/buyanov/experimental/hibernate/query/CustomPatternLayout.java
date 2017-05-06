package ru.buyanov.experimental.hibernate.query;

import ch.qos.logback.classic.PatternLayout;

/**
 * @author A.Buyanov 22.02.2016.
 */
public class CustomPatternLayout extends PatternLayout {
    static {
        PatternLayout.defaultConverterMap.put(
                "hibernate", HibernateSqlCounter.class.getName());
    }
}
