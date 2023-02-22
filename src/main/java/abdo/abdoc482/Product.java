package abdo.abdoc482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** Product class */

public class Product {
    /** Constructor for product class */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    private ObservableList<Part> associatedParts= FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /** @return Id */
    public  int getId() {
        return id;
    }
    /** sets ID. */
    public void setId(int id) {
        this.id = id;
    }
    /** @return name. */
    public  String getName() {
        return name;
    }
    /** sets name.  */
    public void setName(String name) {
        this.name = name;
    }
    /** @return price */

    public double getPrice() {
        return price;
    }
    /** sets price */
    public void setPrice(double price) {
        this.price = price;
    }
    /** @return stock */
    public int getStock() {
        return stock;
    }
    /** sets stock */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /** @return min */
    public int getMin() {
        return min;
    }
    /** sets min */
    public void setMin(int min) {
        this.min = min;
    }
    /** @return max */
    public int getMax() {
        return max;
    }
    /** sets max */
    public void setMax(int max) {
        this.max = max;
    }
    /** adds part to product associated list of parts */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /** deletes part from associated list */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return false;
    }
    /** returns list of associated parts */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
   }
