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
/** This class is the controller for the addProduct View. */
public class addProductController implements Initializable {
    private ObservableList<Part> preAssociatedParts= FXCollections.observableArrayList();
    /** element of the gui Interface. */
    public TableView<Part> addProductViewPartTableView;
    /** element of the gui Interface. */
    public TableView<Part> addProductViewPartCurrentTableView;
    /** element of the gui Interface. */
    public TextField addProductViewNameTxt;
    /** element of the gui Interface. */
    public TextField addProductViewInvTxt;
    /** element of the gui Interface. */
    public TextField addProductViewPriceTxt;
    /** element of the gui Interface. */
    public TextField addProductViewMaxTxt;
    /** element of the gui Interface. */
    public TextField addProductViewMinTxt;
    /** element of the gui Interface. */
    public TextField addProductViewPartSearchTxtStat;
    Stage stage;
    Parent scene;
    @FXML
    private TextField addProductViewIdTxt;

    @FXML
    private TableColumn<Part, Integer> addProductViewInvLevelClmn;

    @FXML
    private TableColumn<?, ?> addProductViewPartCurrentPriceClmn;

    @FXML
    private TableColumn<Part, Integer> addProductViewPartIdClmn;

    @FXML
    private TableColumn<?, ?> addProductViewPartIdCurrentClmn;

    @FXML
    private TableColumn<?, ?> addProductViewPartInvCurrentClmn;

    @FXML
    private TableColumn<Part,String> addProductViewPartNameClmn;

    @FXML
    private TableColumn<?, ?> addProductViewPartNameCurrentClmn;

    @FXML
    private TableColumn<Part, Double> addProductViewPartPriceClmn;

    @FXML
    void addProductViewAddBtn(ActionEvent event) {
        Part part= addProductViewPartTableView.getSelectionModel().getSelectedItem();
        preAssociatedParts.add(0,part);
    }

    @FXML
    void addProductViewCancelPartBtn(ActionEvent event) throws IOException {
        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("InventoryMainView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

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
    void addProductViewPartSearchTxt(ActionEvent event) {
        String q=addProductViewPartSearchTxtStat.getText();

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


        addProductViewPartTableView.setItems(searchedParts);

        addProductViewPartSearchTxtStat.setText("");

    }
    Part getPartFromPreAssociatedList(int i){
       return preAssociatedParts.get(i);
    }

    @FXML
    void addProductViewRemovePartBtn(ActionEvent event) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION );
        alert.setTitle("PLease confirm");
        alert.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result =alert.showAndWait();
        if (result.get()== ButtonType.OK){
            Part part= addProductViewPartCurrentTableView.getSelectionModel().getSelectedItem();
            preAssociatedParts.remove(part);
        }
        else{
            return;
        }

    }

    @FXML
    void addProductViewSavePartBtn(ActionEvent event) throws IOException {

        int id= ++Inventory.nextIntIdProduct;  //getting values from text fields. exception Id is auto generated
        String name= addProductViewNameTxt.getText();//wrapper converts text field inputs
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
            int stock= Integer.parseInt(addProductViewInvTxt.getText());
            error="Price";
            double price =Double.parseDouble(addProductViewPriceTxt.getText());
            error="Min";
            int min =Integer.parseInt(addProductViewMinTxt.getText());
            error="Max";
            int max=Integer.parseInt(addProductViewMaxTxt.getText());
            if(min>max || stock>max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something wrong with Min Max and Inventory Values");
                alert.setContentText("Min's value should be less than Max and Inventory should be between Min and Max");
                alert.showAndWait();
                return;
            }
            Inventory.addProduct(new Product(id,name,price,stock, min, max));
            for(int index=1;index<=preAssociatedParts.size();index++){
                Product product =Inventory.getAllProducts().get(id-1);
                System.out.println(product);
                product.addAssociatedPart(getPartFromPreAssociatedList(index-1));}

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
    /** Add product initialize.
     * Methode initializes before the add Screen */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductViewPartTableView.setItems(Inventory.getAllParts());
        addProductViewPartIdClmn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductViewPartNameClmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductViewInvLevelClmn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductViewPartPriceClmn.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductViewPartCurrentTableView.setItems(preAssociatedParts);  //preassociated parts
        addProductViewPartIdCurrentClmn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductViewPartNameCurrentClmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductViewPartInvCurrentClmn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductViewPartCurrentPriceClmn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
