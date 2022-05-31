package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    private static Configurations configuration = null;
    private Properties properties;

    private Configurations(){
        properties = new Properties();
        try (InputStream inputStream = Configurations.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
            else {
                System.out.println("file not found");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Configurations getConf()
    {
        if(configuration==null)
        {
            configuration = new Configurations();
        }
        return configuration;
    }
    public String getNumOfThreads(){
        return properties.getProperty("threadPoolSize");
    }
    public void setNumOfThreads(String n)
    {
        properties.setProperty("threadPoolSize",n);
    }
    public String getGeneratingAlgo(){
        return properties.getProperty("mazeGeneratingAlgorithm");
    }
    public void setGeneratingAlgo(String algo)
    {
        properties.setProperty("mazeGeneratingAlgorithm",algo);
    }
    public String getSearchingAlgo(){
        return properties.getProperty("mazeSearchingAlgorithm");
    }
    public void setSearchingAlgo(String algo)
    {
        properties.setProperty("mazeSearchingAlgorithm",algo);
    }
}
