package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    //====================================================================
    //====================================================================
    //=========BU DOSYAYA BIR SEY YAZMAYIN VEYA DEGISTIRMEYIN=============
    //====================================================================
    //====================================================================

    static Properties properties;

    static {

        String filePath= "configuration.properties";
        try {

            FileInputStream fis= new FileInputStream(filePath);
            properties= new Properties();
            properties.load(fis);


        } catch (IOException e) {
            System.out.println("Properties dosyası okunamadı!!");
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);

    }


}

