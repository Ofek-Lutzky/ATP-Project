package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesController implements Initializable {

    @FXML
    private ChoiceBox<String> searchAlgorithm;

    @FXML
    private ChoiceBox<String> generateAlgorithm;

    @FXML
    private TextField poolSizeTxt;



    @FXML
    void saveProperties(javafx.event.ActionEvent event) {

        Configurations.getInstance().setSearchingAlgo(searchAlgorithm.getValue());
        Configurations.getInstance().setThreadPoolSize(poolSizeTxt.getText());
        Configurations.getInstance().setGeneratingAlgo(generateAlgorithm.getValue());

        closeStage(event);
    }
    //



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        generateAlgorithm.setValue(Configurations.getInstance().getGenerateAlgo());
        searchAlgorithm.setValue(Configurations.getInstance().getSearcheAlgo());
        poolSizeTxt.setText(String.valueOf(Configurations.getInstance().getThreadPoolSize()));

        try{
            Properties properties = new Properties();
            properties.load(new FileInputStream("./resources/config.properties"));

            String a1= properties.getProperty("searchingAlgorithm");

            String a2= properties.getProperty("generator");

            if(a1.equals("BEST")){
                searchAlgorithm.setValue("BEST");}

            else if(a1.equals("BREADTH")){
                searchAlgorithm.setValue("BREADTH");}

            else if(a1.equals("DFS")){
                searchAlgorithm.setValue("DFS");}
            else{

            }



            if(a2.equals("MyMazeGenerator")){
                generateAlgorithm.setValue("MyMazeGenerator");}

            else if(a2.equals("EmptyMazeGenerator")){
                generateAlgorithm.setValue("EmptyMazeGenerator");}

            else if(a2.equals("SimpleMazeGenerator")){
                generateAlgorithm.setValue("SimpleMazeGenerator");}
            else{

            }




        }
        catch (Exception e){}

    }

    private void closeStage(javafx.event.ActionEvent event) {
        Node source = (Node) event.getSource();
        var stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
