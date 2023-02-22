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
/** This class is the controller for the Main View.
 FUTURE: ENHANCEMENT: adding a customer class and link parts and products to it*/

public class InventoryMainController implements Initializable {
    private TextField mainViewPartSearchTxtField;
    private TextField mainViewProductSearchTxtField;
    @FXML
    private TableView<Product> mainViewProductsTableView;
    @FXML
    private TableView<Part> mainViewPartsTableView;
    Stage stage;
    Parent scene;
    @FXML
    private TableColumn<Part, Integer> mainViewPartIdClmn;
    @FXML
    private TableColumn<Part,String> mainViewPartnamedClmn;
    @FXML
    private TableColumn<Part,Integer> mainViewPartInvClmn;
    @FXML
    private TableColumn<Part,Double> mainViewPartPriceClmn;
    @FXML
    private TableColumn<Product,Integer> mainViewProductIdClmn;
    @FXML
    private TableColumn<Product,String> mainViewProductnameClmn;
    @FXML
    private TableColumn<Product,Integer> mainViewProductInvClmn; //Integer wrapper class
    @FXML
    private TableColumn<Product,Double> mainViewProductPriceClmn; //Double wrapper Class

    /** search methode on parts name
     * returns the parts with matching String or substring. */
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
    /** search methode on parts id
     * returns the parts with matching id */
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
    /** search methode on produt name
     * returns the product with matching String or substring. */
    private ObservableList<Product> searchByProductName(String partialName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts= Inventory.getAllProducts();

        for(Product product: allProducts){
            if(product.getName().contains(partialName)){
                namedProducts.add(product);
            }
        }
        return namedProducts;
    }
    /** search methode on product id
     * returns the parts with matching id. */
    private Product getProductWithId(int id){
        ObservableList<Product> allProducts= Inventory.getAllProducts();
        for(int i=0; i<allProducts.size(); i++){
            Product product =allProducts.get(i);
            if(product.getId()== id){
                return product;
            }
        }
        return null;
    }
    /** Initializes before the scene.
     * setting up column values */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainViewPartsTableView.setItems(Inventory.getAllParts()); // filling tableView
        mainViewProductsTableView.setItems(Inventory.getAllProducts());
        mainViewPartIdClmn.setCellValueFactory(new PropertyValueFactory<>("id"));  //Javafx makes the i capital I and add a get before Id
        mainViewPartnamedClmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainViewPartInvClmn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainViewPartPriceClmn.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainViewProductIdClmn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainViewProductnameClmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainViewProductInvClmn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainViewProductPriceClmn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /** on action text for search input.
     * searched for string and ID and returns parts to searched parts list.
     * then sets the table with the result. */
    public void mainViewPartSearchTxt(ActionEvent actionEvent) {
        String q=mainViewPartSearchTxtField.getText();

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


        mainViewPartsTableView.setItems(searchedParts);

        mainViewPartSearchTxtField.setText("");

    }

    /** on action text for search input.
     * searched for string and ID and returns product to searched products list.
     * then sets the table with the result. */
    public void mainViewProductSearchTxt(ActionEvent actionEvent) {
        String q=mainViewProductSearchTxtField.getText();

        ObservableList<Product> searchedProducts = searchByProductName(q);
        try {
            if(searchedProducts.size()==0){
                int id= Integer.parseInt(q);
                Product product = getProductWithId(id);
                if(product != null)
                    searchedProducts.add(product);

            }
        }catch (NumberFormatException e){
            //ignoring exception.
        }


        mainViewProductsTableView.setItems(searchedProducts);

        mainViewProductSearchTxtField.setText("");
    }
    /** transfers screen to add part. */
    public void mainViewAddPartbtn(ActionEvent actionEvent) throws IOException {
        stage =(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("addPartView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** send selected part object and transfers view. */

    public void mainViewModifyPartbtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader();    //passing selecxted object when modify button is clicked to mod part view
        loader.setLocation(getClass().getResource("modifyPartView.fxml"));
        Parent modPartMenu =loader.load();
        Scene scene =new Scene(modPartMenu);
        modifyPartController pass=loader.getController();
        pass.passPart(mainViewPartsTableView.getSelectionModel().getSelectedItem());
        stage= (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /** deletes part */
    public void mainViewDeletePartbtn(ActionEvent actionEvent) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION );
        alert.setTitle("PLease confirm");
        alert.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result =alert.showAndWait();
        if (result.get()== ButtonType.OK){
            Part part=mainViewPartsTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(part);
        }
        else{
            return;
        }

    }
    /** transfers view to product add */
    public void mainViewAddProductbtn(ActionEvent actionEvent) throws IOException {
        stage =(Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("addProductView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** passes selected product object and transfers view to modify view. */
    public void mainViewModifyProductbtn(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader= new FXMLLoader();    //passing selected object when modify button is clicked to mod part view
        loader.setLocation(getClass().getResource("modifyProductView.fxml"));
        Parent modPartMenu =loader.load();
        Scene scene =new Scene(modPartMenu);
        modifyProductController pass=loader.getController();
        pass.passProduct(mainViewProductsTableView.getSelectionModel().getSelectedItem());
        stage= (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /** deletes selected product with confirmation */
    public void mainViewDeleteProductbtn(ActionEvent actionEvent) {
        Product product=mainViewProductsTableView.getSelectionModel().getSelectedItem();
        if(product.getAllAssociatedParts().size() != 0){
            Alert alert= new Alert(Alert.AlertType.ERROR );
            alert.setTitle("Product has associated parts");
            alert.setContentText("please delete associated parts first");
            alert.showAndWait();
            return;
        }
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION );
        alert.setTitle("PLease confirm");
        alert.setContentText("Are you sure you want to delete this product?");
        Optional<ButtonType> result =alert.showAndWait();
        if (result.get()== ButtonType.OK){
            product=mainViewProductsTableView.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(product);
        }
        else{
            return;
        }


    }
    /** exit button to exit the application */
    public void mainViewExitbtn(ActionEvent actionEvent) {
        System.exit(0);
    }
}