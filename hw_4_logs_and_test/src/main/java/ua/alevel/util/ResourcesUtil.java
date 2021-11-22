package ua.alevel.util;

import ua.alevel.config.PropertiesConfig;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ResourcesUtil {

    private ResourcesUtil() { }

    public static Map<String, String> getProperties(ClassLoader classLoader) {
        try(InputStream inputStream = classLoader.getResourceAsStream(PropertiesConfig.APPLICATION_PROPERTIES.getProperties())) {
            Properties properties = new Properties();
            properties.load(inputStream);
            HashMap<String, String> propertiesMap = new HashMap<>();
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                propertiesMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
            return propertiesMap;
        } catch (IOException e) {
            throw new RuntimeException("file " + PropertiesConfig.APPLICATION_PROPERTIES.getProperties() + " not found");
        }
    }
}
