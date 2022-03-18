package com.tamazian.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    private static final String PATH = "application.properties";

    static {
        loadProperties();
    }

    private PropertiesUtil() {

    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }


    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PATH)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
