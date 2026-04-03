package com.framework.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private final Properties properties;

    private ConfigReader() {
        properties = new Properties();
        String env = System.getProperty("env", "qa");
        loadProperties("config/config.properties");
        loadProperties("config/" + env + ".properties");
        log.info("Configuration loaded for environment: {}", env);
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    private void loadProperties(String filePath) {
        try (FileInputStream fis = new FileInputStream(
                "src/test/resources/" + filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            log.error("Failed to load properties file: {}", filePath, e);
            throw new RuntimeException("Could not load config: " + filePath, e);
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property key not found: " + key);
        }
        return value.trim();
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public String getBaseUrl() {
        return get("base.url");
    }

    public String getApiVersion() {
        return get("api.version");
    }

    public String getAuthToken() {
        return get("auth.token");
    }
}