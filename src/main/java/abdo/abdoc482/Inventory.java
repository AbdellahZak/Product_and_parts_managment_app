package abdo.abdoc482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** Inventory class. */

public class Inventory {
    /** integer to increment part id */
    public static int nextIntIdPart = 0;
    /** integer to increment product id */
    public static int nextIntIdProduct = 0;
    /** boolean to save In house or outsource radio buttons status */
    public static boolean inOrOut = false;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /** adds Part type element to all parts observable list. */
    public static void addPart(Part part) {
        allParts.add(part);
    }
    /** adds Product type element to all products observable list. */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }
    /** all parts list
     * @return all parts list */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /** returns all products lis
     * @return all product list */

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    /** lookup part */
    public static Part lookupPart(int partId) {
        return null;
    }
    /** lookup product */
    public static Product lookUpProduct(int productId) {
        return null;
    }
    /** lookup part */
    public static ObservableList<Part> lookUpPart(String partName) {
        return null;
    }
    /** lookup product */
    public static ObservableList<Product> lookUpProduct(String productName) {
        return null;
    }
    /** updates part in list. */
    public static void updatePart(int index, Part part) {
        int index1 = -1;
        for (Part part1 : getAllParts()) {
            index1++;
            if (part1.getId() == index) {
                getAllParts().set(index1, part);
            }
        }
    }
    /** updates product in list */
    public static void updateProduct(int index, Product product) {
        int index1 = -1;
        for (Product product1 : getAllProducts()) {
            index1++;
            if (product1.getId() == index) {
                getAllProducts().set(index1, product);
            }
        }
    }
    /** deletes part is list */
    public static boolean deletePart(Part part) {
        int id = part.getId();
        for (Part part1 : getAllParts()) {
            if (part1.getId() == id) {
                return getAllParts().remove(part1);
            }
        }
        return false;
    }
    /** deletes product in list */
    public static boolean deleteProduct(Product product) {
        int id = product.getId();
        for (Product product1 : getAllProducts()) {
            if (product1.getId() == id) {
                getAllProducts().remove(product1);
            }
        }
        return false;
    }
}
