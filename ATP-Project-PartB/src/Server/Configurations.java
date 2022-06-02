package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configurations {
    private static Configurations configuration = null;
    private final Properties P;
//    private OutputStream outPutS;
//    private InputStream inputPutS;
    private final String pathOfFile = "config.properties";

    private Configurations(){

        this.P = new Properties();
        try {
            InputStream inputStream = Configurations.class.getClassLoader().getResourceAsStream(pathOfFile);
            if (inputStream != null) {
                this.P.load(inputStream);
            }
            else {
                System.out.println("File Not Exist");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Configurations getInstance()
    {
        if(configuration==null)
        {
            configuration = new Configurations();
        }
        return configuration;
    }

    public void setGeneratingAlgo(String algoName)
    {
        P.setProperty("mazeGeneratingAlgorithm", algoName);
    }

    public void setSearchingAlgo(String algoName)
    {
        P.setProperty("mazeSearchingAlgorithm",algoName);
    }

    public void setThreadPoolSize(String n)
    {
            P.setProperty("threadPoolSize",n);
    }

    public String getThreadPoolSize(){
        return P.getProperty("threadPoolSize");
    }

    public String getGenerateAlgo(){
        return P.getProperty("mazeGeneratingAlgorithm");
    }

    public String getSearcheAlgo(){
        return P.getProperty("mazeSearchingAlgorithm");
    }

}
