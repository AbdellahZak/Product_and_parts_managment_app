package abdo.abdoc482;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/** This class is the controller for the addPart View. */

public class addPartController implements Initializable {

    @FXML
    private TextField addPartViewNameTxt;
    @FXML
    private TextField addPartViewInvTxt;
    @FXML
    private TextField addPartViewPriceTxt;
    @FXML
    private TextField addPartViewMaxTxt;
    @FXML
    private TextField addPartViewInOutTxt;
    @FXML
    private TextField addPartViewMinTxt;
    @FXML
    private Label addPartInOutLbl;
    Stage stage;
    Parent scene;
    @FXML
    void addPartViewSaveBtn(ActionEvent event) throws IOException {
        int id= ++Inventory.nextIntIdPart;  //getting values from text fields. exception Id is auto generated
        String name= addPartViewNameTxt.getText();//wrapper converts text field inputs
        int machineId=0;
        String companyName="random";
        companyName = addPartViewInOutTxt.getText();

        if(name.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("name is blank");
            alert.setContentText("Ooops, Please enter a name");
            alert.showAndWait();
            return;
        }
        if(companyName.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("name is blank");
            alert.setContentText("Ooops, Please enter a name");
            alert.showAndWait();
            return;
        }
        if (Inventory.inOrOut)
            machineId = Integer.parseInt(addPartViewInOutTxt.getText());
        else
            companyName = addPartViewInOutTxt.getText();
        String error="";
        try {
            error="Inv";
            int stock= Integer.parseInt(addPartViewInvTxt.getText());
            error="Price";
            double price =Double.parseDouble(addPartViewPriceTxt.getText());
            error="Min";
            int min =Integer.parseInt(addPartViewMinTxt.getText());
            error="Max";
            int max=Integer.parseInt(addPartViewMaxTxt.getText());
            if(min>max || stock>max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something wrong with Min Max and Inventory Values");
                alert.setContentText("Min's value should be less than Max and Inventory should be between Min and Max");
                alert.showAndWait();
                return;
            }
            if(Inventory.inOrOut)
                Inventory.addPart(new InHouse( id, name, price, stock, min, max, machineId));
            else
                Inventory.addPart(new Outsourced( id, name, price, stock, min, max, companyName));

        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something wrong with "+error +" value");
            alert.setContentText(error +" should be a number");
            alert.showAndWait();
            return;
        }

        stage =(Stage)((Button)event.getSource()).getScene().getWindow();  //switching screens
        scene = FXMLLoader.load(getClass().getResource("InventoryMainView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void addPartViewCancelBtn(ActionEvent event) throws IOException {

        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("InventoryMainView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }



    @FXML
    void addPartViewInhouseRadioBtn(ActionEvent event) {
        addPartInOutLbl.setText("Machine ID");
        Inventory.inOrOut=true;

    }


    @FXML
    void addPartViewOutsourcedRadioBtn(ActionEvent event) {
        addPartInOutLbl.setText("Company Name");
        Inventory.inOrOut=false;
    }

/** Add part initialize.
 * Methode initializes before the add Screen */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
