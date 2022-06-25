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
    MyViewModel viewModel;

    @FXML
    private ChoiceBox<String> searchAlgorithm;

    @FXML
    private ChoiceBox<String> generateAlgorithm;

    @FXML
    private TextField poolSizeTxt;



    @FXML
    void saveProperties(javafx.event.ActionEvent event) {

        Configurations.getInstance().setGeneratingAlgo(generateAlgorithm.getValue());
        Configurations.getInstance().setSearchingAlgo(searchAlgorithm.getValue());
        Configurations.getInstance().setThreadPoolSize(poolSizeTxt.getText());
        closeStage(event);
    }
    //
    private void closeStage(javafx.event.ActionEvent event) {
        Node source = (Node) event.getSource();
        var stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


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
            if(a1.equals("BestFirstSearch")){
                searchAlgorithm.setValue("BestFirstSearch");}
            else if(a1.equals("DepthFirstSearch")){
                searchAlgorithm.setValue("DepthFirstSearch");}
            else if(a1.equals("BreadthFirstSearch")){
                searchAlgorithm.setValue("BreadthFirstSearch");}
            if(a2.equals("MyMazeGenerator")){
                generateAlgorithm.setValue("MyMazeGenerator");}
            else if(a2.equals("SimpleMazeGenerator")){
                generateAlgorithm.setValue("SimpleMazeGenerator");}
            else if(a2.equals("EmptyMazeGenerator")){
                generateAlgorithm.setValue("EmptyMazeGenerator");}


        }
        catch (Exception e){}

    }



//    public Stage getStage() {
//        return stage;
//    }
//    public void setStage(Stage stage) {
//        this.stage = stage;
//    }

}
