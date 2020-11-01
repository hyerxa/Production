import java.util.Date;

public class ProductionRecord {

    private int productionNumber;
    private int productId;
    private String serialNumber;
    private Date dateProduced;

    ProductionRecord(int productId) {
        this.productionNumber = 0;
        this.productId = productId;
        this.serialNumber = "0";
        this.dateProduced = new Date();
    }

    ProductionRecord(int productionNumber, int productId, String serialNumber, Date dateProduced) {
        this.productionNumber = productionNumber;
        this.productId = productId;
        this.serialNumber = serialNumber;
        this.dateProduced = dateProduced;
    }

    ProductionRecord(Product product, int count) {
        StringBuilder countString = new StringBuilder(Integer.toString(count));
        if (countString.length() < 5) {
            int numZeros = 5 - countString.length();
            for (int i = 0; i < numZeros; i++) {
                countString.insert(0, "0");
            }
        }
        this.productId = product.getId();
        this.productionNumber = 0;
        this.dateProduced = new Date();
        this.serialNumber = product.getManufacturer().substring(0,3) + product.getType().getCode() + countString.toString();
    }

    public int getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(int productionNumber) {
        this.productionNumber = productionNumber;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getDateProduced() {
        return dateProduced;
    }

    public void setDateProduced(Date dateProduced) {
        this.dateProduced = dateProduced;
    }

    public String toString() {
        return "Prod. Num: " + productionNumber + " Prod. ID: " + productId + " Serial Num: " + serialNumber + " Date: " + dateProduced;
    }
}
