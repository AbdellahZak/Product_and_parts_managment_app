package abdo.abdoc482;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** This class is the controller for the modify product View. */
public class modifyProductController implements Initializable {
    public TableView modifyProductViewPartTableView;
    public TableView modifyProductViewPartCurrentTableView;
    public TextField modifyProductPartSearchTxtStat;
    private ObservableList<Part> associatedParts=null;
    private Product product=null;
    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<?, ?> modifyProductViewCurrentPartIdClmn;

    @FXML
    private TableColumn<?, ?> modifyProductViewCurrentPartInvClmn;

    @FXML
    private TableColumn<?, ?> modifyProductViewCurrentPartNameClmn;

    @FXML
    private TableColumn<?, ?> modifyProductViewCurrentPartPriceClmn;

    @FXML
    private TextField modifyProductViewIdTxt;

    @FXML
    private TextField modifyProductViewInvTxt;

    @FXML
    private TextField modifyProductViewMaxTxt;

    @FXML
    private TextField modifyProductViewMinTxt;

    @FXML
    private TextField modifyProductViewNameTxt;

    @FXML
    private TableColumn<?, ?> modifyProductViewPartIdClmn;

    @FXML
    private TableColumn<?, ?> modifyProductViewPartInvClmn;

    @FXML
    private TableColumn<?, ?> modifyProductViewPartNameClmn;

    @FXML
    private TableColumn<?, ?> modifyProductViewPartPriceClmn;

    @FXML
    private TextField modifyProductViewPriceTxt;
    private ObservableList<Part> searchByPartName(String partialName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts= Inventory.getAllParts();

        for(Part part: allParts){
            if(part.getName().contains(partialName)){
                namedParts.add(part);
            }
        }
        return namedParts;
    }
    private Part getPartWithId(int id){
        ObservableList<Part> allParts= Inventory.getAllParts();
        for(int i=0; i<allParts.size(); i++){
            Part part =allParts.get(i);
            if(part.getId()== id){
                return part;
            }
        }
        return null;
    }



    @FXML
    void modifyProductPartSearchTxt(ActionEvent event) {
        String q=modifyProductPartSearchTxtStat.getText();

        ObservableList<Part> searchedParts = searchByPartName(q);
        try {
            if(searchedParts.size()==0){
                int id= Integer.parseInt(q);
                Part part = getPartWithId(id);
                if(part != null)
                    searchedParts.add(part);

            }
        }catch (NumberFormatException e){
            //ignoring exception.
        }


        modifyProductViewPartTableView.setItems(searchedParts);

        modifyProductPartSearchTxtStat.setText("");

    }
    @FXML
    /** removes part from product associated list. */
    public void modifyProductViewRemoveBtn(ActionEvent actionEvent) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION );
        alert.setTitle("PLease confirm");
        alert.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result =alert.showAndWait();
        if (result.get()== ButtonType.OK){
            Part part= (Part) modifyProductViewPartCurrentTableView.getSelectionModel().getSelectedItem(); //why did this need to be casted?
            associatedParts.remove(part);
        }
    }
    /** returns specific part from associated list. */
    Part getPartFromPreAssociatedList(int i){
        return associatedParts.get(i);
    }
    @FXML
    /** on action save button processes Data entry/validation.
     * RUNTIME ERROR: index out of bounds in the for loop.
     * fixed it by adjusting the id and index and subtracting 1 from both*/
    public void modifyProductViewSaveBtn(ActionEvent actionEvent) throws IOException {
        int id= Integer.parseInt(modifyProductViewIdTxt.getText());  //getting values from text fields
        String name= modifyProductViewNameTxt.getText();//wrapper converts text field inputs
        if(name.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("name is blank");
            alert.setContentText("Ooops, Please enter a name");
            alert.showAndWait();
            return;
        }
        String error="";
        try{
            error="Inv";
            int stock= Integer.parseInt(modifyProductViewInvTxt.getText());
            error="Price";
            double price =Double.parseDouble(modifyProductViewPriceTxt.getText());
            error="Min";
            int min =Integer.parseInt(modifyProductViewMinTxt.getText());
            error="Max";
            int max=Integer.parseInt(modifyProductViewMaxTxt.getText());
            if(min>max || stock>max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something wrong with Min Max and Inventory Values");
                alert.setContentText("Min's value should be less than Max and Inventory should be between Min and Max");
                alert.showAndWait();
                return;
            }
            Inventory.updateProduct(id, new Product(id,name,price,stock,min,max));
            for(int index=1;index<=associatedParts.size();index++){
                Product product =Inventory.getAllProducts().get(id-1);
                System.out.println(product);
                product.addAssociatedPart(getPartFromPreAssociatedList(index-1));
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something wrong with "+error +" value");
            alert.setContentText(error +" should be a number");
            alert.showAndWait();
            return;

        }
        stage =(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("InventoryMainView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /** cancels screen. */
    public void modifyProductViewCancelBtn(ActionEvent actionEvent) throws IOException {
        stage =(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("InventoryMainView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** gets selected part from top table view.
     * adds the part to the product associated list.  */
    public void modifyProductViewAddBtn(ActionEvent actionEvent) {
        Part part= (Part) modifyProductViewPartTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(0,part);
    }
    /** modify product initialize.
     * Methode initializes before the Screen. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductViewPartTableView.setItems(Inventory.getAllParts());
        modifyProductViewPartIdClmn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductViewPartNameClmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductViewPartInvClmn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductViewPartPriceClmn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    /** PassProduct receives selected product from main view.
     * sets the text fields  */
    public void passProduct(Product product){
        modifyProductViewIdTxt.setText(Integer.toString(product.getId()));
        modifyProductViewNameTxt.setText(product.getName());
        modifyProductViewInvTxt.setText(Integer.toString(product.getStock()));
        modifyProductViewPriceTxt.setText(Double.toString(product.getPrice()));
        modifyProductViewMaxTxt.setText(Integer.toString(product.getMax()));
        modifyProductViewMinTxt.setText(Integer.toString(product.getMin()));
        associatedParts =product.getAllAssociatedParts();
        modifyProductViewPartCurrentTableView.setItems(associatedParts);  //associated parts passed with product
        modifyProductViewCurrentPartIdClmn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductViewCurrentPartNameClmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductViewCurrentPartInvClmn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductViewCurrentPartPriceClmn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

}
