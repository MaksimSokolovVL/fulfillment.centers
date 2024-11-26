package com.propvuebrand.fulfillment.centers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.shaded.org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class BaseDataTest {
    public final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public <T> T readJson(String fileName, Class<T> aClass) throws IOException {
        var json = readFile(fileName);
        return OBJECT_MAPPER.readValue(json, aClass);
    }

    public <T> List<T> readJsonList(String fileName, Class<T> listClass) throws IOException {
        var json = readFile(fileName);
        JavaType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, listClass);
        return OBJECT_MAPPER.readValue(json, listType);
    }

    public JsonNode readTree(String fileName) throws IOException {
        var json = readFile(fileName);
        return OBJECT_MAPPER.readTree(json);
    }

    private String readFile(String fileName) throws IOException {
        var fullFileName = getResourcePath(fileName);
        var resource = new ClassPathResource(fullFileName);
        List<String> lines = Files.readAllLines(resource.getFile().toPath());
        return StringUtils.join(lines, "");
    }

    private String getResourcePath(String fileName) {
        StackWalker walker = StackWalker.getInstance();
        Optional<String> methodName = walker.walk(flames ->
                flames
                        .filter(it -> {
                            try {
                                return Class.forName(it.getClassName())
                                        .getMethod(it.getMethodName())
                                        .getAnnotation(Test.class) != null;
                            } catch (NoSuchMethodException | ClassNotFoundException e) {
                                return false;
                            }
                        })
                        .map(StackWalker.StackFrame::getMethodName)
                        .findFirst()
        );
        Path pathToClass = Paths.get(getClass().getName().replace(".", "/"));
        Path pathToTestResource = pathToClass.resolve(methodName.orElse(""));

        return pathToTestResource.resolve(fileName).toString();
    }
}
