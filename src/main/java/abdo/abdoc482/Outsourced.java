package abdo.abdoc482;
/** outsourced class child of Part class. */
public class Outsourced extends Part{
    String companyName;
    /** @return companyName. */
    public String getCompanyName() {
        return companyName;
    }
    /** sets company name. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
/** outsourced class constructor. */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }
}
