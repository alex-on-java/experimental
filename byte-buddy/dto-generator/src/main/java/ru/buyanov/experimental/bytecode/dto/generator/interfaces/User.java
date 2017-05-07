package ru.buyanov.experimental.bytecode.dto.generator.interfaces;


import java.time.LocalDate;

/**
 * @author A.Buyanov 07.05.2017.
 */
public interface User extends Id, Name {
    LocalDate getBirthday();
    void setBirthday(LocalDate birthday);
}
