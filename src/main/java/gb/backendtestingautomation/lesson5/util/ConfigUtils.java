package gb.backendtestingautomation.lesson5.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
    static Properties prop = new Properties();
    private static InputStream configFile;

    static {
        try {
            configFile = new FileInputStream("src/main/resources/properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        try{
            prop.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty("url");
    }

    private ConfigUtils(){}
}
