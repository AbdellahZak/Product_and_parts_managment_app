package abdo.abdoc482;
/** This class is a child of the Part Class. */

public class InHouse extends Part{

    /** Private member of Inhouse Class*/
    private int MachineId;
    /** returns machine id
     * @return machine ID. */
    public int getMachineId() {
        return MachineId;
    }
    /**
     * sets machine ID.
     */
    public void setMachineId(int machineId) {
        this.MachineId = machineId;
    }
    /** InHouse constructor.
     * @param id, name, price, stock, min, max, machineId*/

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.MachineId = machineId;
    }
}
