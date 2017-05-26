package ru.buyanov.experimental.bytecode.dto.generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author A.Buyanov 26.05.2017.
 */
public class DtoGeneratorTest {
    private DtoGenerator service;

    @Before
    public void startUp() {
        service = new DtoGenerator(new PropertyExtractor());
    }

    @Test
    public void testGenerateAndLoad_defaultClassLoader() {
        User user = service.generateAndLoad(User.class, "aaa");
        Assert.assertEquals("aaa", user.getClass().getCanonicalName());
        user.setId(1);
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testGenerateAndLoad_ourClassLoader() {
        User user = service.generateAndLoad(User.class, "bbb", this.getClass().getClassLoader());
        Assert.assertTrue(user.getClass().getClassLoader() == this.getClass().getClassLoader());
    }

    @Test
    public void testGenerateAndSave_saveOnRoot() throws IOException {
        final TemporaryFolder testFolder = new TemporaryFolder();
        testFolder.create();
        service.generateAndSave(User.class, "dto", testFolder.getRoot());
        File[] files = testFolder.getRoot().listFiles();
        Assert.assertNotNull(files);
        Assert.assertEquals("dto.class", files[0].getName());
    }

    @Test
    public void testGenerateAndSave_saveToPackages() throws IOException {
        final TemporaryFolder testFolder = new TemporaryFolder();
        testFolder.create();
        File directory = testFolder.getRoot();
        service.generateAndSave(User.class, "ru.buyanov.UserDto", directory);
        Assert.assertEquals(testFolder.getRoot().getName() + "/ru/buyanov/UserDto.class", deepFileName(directory));
    }

    private String deepFileName(File file) {
        if (file.isFile())
            return file.getName();
        File[] files = file.listFiles();
        if (files == null || files.length == 0)
            throw new RuntimeException("No files in directory " + file.getPath());
        return file.getName() + "/" + deepFileName(files[0]);
    }

    public interface User {
        int getId();
        void setId(int id);
    }
}
