package abdo.abdoc482;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/** This class is the controller for the modify part View. */

public class modifyPartController implements Initializable {

        @FXML
        private Label modifyPartViewInOutLbl;
        @FXML
        private TextField modifyPartViewIdTxt;
        @FXML
        private TextField modifyPartViewNameTxt;
        @FXML
        private TextField modifyPartViewInvTxt;
        @FXML
        private TextField modifyPartViewPriceTxt;
        @FXML
        private TextField modifyPartViewMaxTxt;
        @FXML
        private TextField modifyPartViewMIdTxt;
        @FXML
        private TextField modifyPartViewMinTxt;

        Stage stage;
        Parent scene;

        @FXML
        void modifyPartViewCancelBtn(ActionEvent event) throws IOException {
                stage =(Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("InventoryMainView.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

        }

        @FXML
        void modifyPartViewInHoussRdBtn(ActionEvent event) {
                modifyPartViewInOutLbl.setText("Machine ID");
                Inventory.inOrOut=true;
        }


        @FXML
        void modifyPartViewSaveBtn(ActionEvent event) throws IOException {
                int id= Integer.parseInt(modifyPartViewIdTxt.getText());
                String name= modifyPartViewNameTxt.getText();
                int machineId=0;
                String companyName="random";
                companyName = modifyPartViewMIdTxt.getText();
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
                        machineId = Integer.parseInt(modifyPartViewMIdTxt.getText());
                else
                        companyName = modifyPartViewMIdTxt.getText();
                String error="";
                try {
                        error="Inv";
                        int stock= Integer.parseInt(modifyPartViewInvTxt.getText());
                        error="Price";
                        double price =Double.parseDouble(modifyPartViewPriceTxt.getText());
                        error="Min";
                        int min =Integer.parseInt(modifyPartViewMinTxt.getText());
                        error="Max";
                        int max=Integer.parseInt(modifyPartViewMaxTxt.getText());
                        if(min>max || stock>max){
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Something wrong with Min Max and Inventory Values");
                                alert.setContentText("Min's value should be less than Max and Inventory should be between Min and Max");
                                alert.showAndWait();
                                return;
                        }
                        if(Inventory.inOrOut)
                                Inventory.updatePart(id, new InHouse( id, name, price, stock, min, max, machineId));
                        else
                                Inventory.updatePart(id, new Outsourced( id, name, price, stock, min, max, companyName));


                }catch (NumberFormatException e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Something wrong with "+error +" value");
                        alert.setContentText(error +" should be a number");
                        alert.showAndWait();
                        return;
                }
                stage =(Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("InventoryMainView.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

        }
        /** modify part initialize.
         * Methode initializes before the add Screen. */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
        /** PassPart receives selected part from main view.
         * sets the text fields  */
        public void passPart(Part part){
                modifyPartViewIdTxt.setText(Integer.toString(part.getId()));
                modifyPartViewNameTxt.setText(part.getName());
                modifyPartViewInvTxt.setText(Integer.toString(part.getStock()));
                modifyPartViewPriceTxt.setText(Double.toString(part.getPrice()));
                modifyPartViewMaxTxt.setText(Integer.toString(part.getMax()));
                modifyPartViewMinTxt.setText(Integer.toString(part.getMin()));
                try {
                        modifyPartViewMIdTxt.setText(((Outsourced)part).getCompanyName()); //casting outsource object into the part to get the company name or machine id, making use of the exception created to differentiate between the two types of In-house and Outsource
                }
                catch (ClassCastException e){
                        modifyPartViewMIdTxt.setText(Integer.toString(((InHouse)part).getMachineId()));
                }

        }

        /** sets label value depending on radio button state. */
        public void modifyPartViewOutSourcedRdBtn(ActionEvent actionEvent) {
                modifyPartViewInOutLbl.setText("Company Name");
                Inventory.inOrOut=false;
        }
}
