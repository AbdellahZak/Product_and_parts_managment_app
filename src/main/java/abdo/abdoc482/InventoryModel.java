package abdo.abdoc482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/** main entry class.
 * @author  Abdellah Zakani
 * JavaDocs in zip. */

public class InventoryModel extends Application {

    @Override
    /** start methode.
     * sets up and configures the main FXML. */
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryModel.class.getResource("InventoryMainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 450);
        stage.setTitle("Inventory management application");
        stage.setScene(scene);
        stage.show();
    }
    /** main entry point of the application.
     * sample data added. */
    public static void main(String[] args) {
        InHouse part1= new InHouse(++Inventory.nextIntIdPart,"tire",2.5,14,15,20,15); //sample data
        InHouse part2= new InHouse(++Inventory.nextIntIdPart,"brakes",5,4,2,6,2);
        InHouse part3= new InHouse(++Inventory.nextIntIdPart,"rotor",6,14,1,15,6);
        Outsourced part4=new Outsourced(++Inventory.nextIntIdPart, "wheel",15.2,2,1,3,"BabyBoo");
        Product product1= new Product(++Inventory.nextIntIdProduct,"Bicycle",10.0,10,10,13);
        Product product2= new Product(++Inventory.nextIntIdProduct,"car",4000,3,5,6);
        Product product3= new Product(++Inventory.nextIntIdProduct,"motorcycle",2000,2,3,4);


        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);


        launch();
    }
}