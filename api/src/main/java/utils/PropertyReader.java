package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    public String propertydata(String filepath, String key) {
        String value = null;
        try {
            InputStream input = new FileInputStream(filepath);
            Properties prop = new Properties();
            prop.load(input);

            value = prop.getProperty(key);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return value;
    }
}
