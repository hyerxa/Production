import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a record of a specific production.
 *
 * @author Haley Yerxa
 */

public class ProductionRecord {

  /**
   * Production number.
   */
  private int productionNumber;
  /**
   * Id of product being produced.
   */
  private int productId;
  /**
   * Serial number specific to production.
   */
  private String serialNumber;
  /**
   * Date produced.
   */
  private Date dateProduced;
  /**
   * Name of the product.
   */
  private String productName;

  /**
   * Constructor that takes all fields as parameters except name.
   * Name is defaulted to "name".
   *
   * @param productionNumber is production id number.
   * @param productId is product id.
   * @param serialNumber is specific to each product.
   * @param dateProduced is date of production.
   */
  ProductionRecord(int productionNumber, int productId, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productId = productId;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
    this.productName = "name";
  }

  /**
   * Constructor taking a full Product and number of products.
   * Creates a serial number based on number of current products.
   *
   * @param product is the product to be produced.
   * @param count is the number of current products.
   */
  ProductionRecord(Product product, int count) {
    // StringBuilder for serial number.
    StringBuilder countString = new StringBuilder(Integer.toString(count));
    // if there are less than 5 numbers, add leading zeros.
    if (countString.length() < 5) {
      int numZeros = 5 - countString.length();
      for (int i = 0; i < numZeros; i++) {
        countString.insert(0, "0");
      }
    }
    this.productId = product.getId();
    this.dateProduced = new Date();
    // If the manufacturer name is too short, keep the same, else only take first 3 letters.
    if (product.getManufacturer().length() < 3) {
      this.serialNumber =
          product.getManufacturer() + product.getType().getCode() + countString.toString();
    } else {
      this.serialNumber =
          product.getManufacturer().substring(0, 3) + product.getType().getCode() + countString
              .toString();
    }
    this.productName = product.getName();
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

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  final String pattern = "yyyy-MM-dd";
  final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

  public String toString() {
    return "Prod. Num: " + productionNumber + " Prod. Id: " + productId + " Serial Num: "
        + serialNumber + " Date: " + simpleDateFormat.format(dateProduced);
  }
}
