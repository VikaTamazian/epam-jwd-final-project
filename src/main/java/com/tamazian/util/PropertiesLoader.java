package com.tamazian.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertiesLoader {
//    private static final Logger LOGGER = LogManager.getLogger();

    public static Properties loadPropertiesData(URL url) {
        Properties properties = new Properties();
        try {
            properties.load(url.openStream());
        } catch (IOException e) {
//            LOGGER.log(Level.FATAL, "Error with loading properties from file. ", e);
            throw new RuntimeException("Error with loading properties from file: " + url, e);
        }
        return properties;
    }
}
