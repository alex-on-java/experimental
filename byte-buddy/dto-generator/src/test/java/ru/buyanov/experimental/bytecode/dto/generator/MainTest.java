package ru.buyanov.experimental.bytecode.dto.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * @author A.Buyanov 10.05.2017.
 */
public class MainTest {

    @Test
    public void testParseJson() {
        String json = "{\"id\":1,\"name\":\"John\",\"birthday\":\"1990-10-20\"}";
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        User user = new Main(new DtoGenerator(new PropertyExtractor())).parseJson(mapper, json, User.class);
        assertEquals(1, user.getId());
        assertEquals("John", user.getName());
        assertEquals(LocalDate.of(1990, 10, 20), user.getBirthday());
    }

    public interface Id {
        int getId();
        void setId(int id);
    }

    public interface Name {
        String getName();
        void setName(String name);
    }

    public interface User extends Id, Name {
        LocalDate getBirthday();
        void setBirthday(LocalDate birthday);
    }
}
