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
   * Pattern for date format.
   */
  final String pattern = "yyyy-MM-dd";
  /**
   * Date format to be used in toString method.
   */
  final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

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

  /**
   * Getter for production number field.
   * Defined despite warnings for easy usage in future code
   *
   * @return production number.
   */
  public int getProductionNumber() {
    return productionNumber;
  }

  /**
   * Setter for production number field.
   *
   * @param productionNumber number of this production.
   */
  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * Getter for product id field.
   *
   * @return production id.
   */
  public int getProductId() {
    return productId;
  }

  /**
   * Setter for product id field.
   * Defined despite warnings for easy usage in future code
   *
   * @param productId id associated with product.
   */
  public void setProductId(int productId) {
    this.productId = productId;
  }

  /**
   * Getter for serial number field.
   *
   * @return serial number.
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Setter for serial number field.
   * Defined despite warnings for easy usage in future code
   *
   * @param serialNumber unique serial number of product.
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Getter for date produced field.
   *
   * @return date produced.
   */
  public Date getDateProduced() {
    return dateProduced;
  }

  /**
   * Setter for date produced field.
   * Defined despite warnings for easy usage in future code
   *
   * @param dateProduced date that production was recorded.
   */
  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  /**
   * Getter for product name field.
   * Defined despite warnings for easy usage in future code
   *
   * @return product name.
   */
  public String getProductName() {
    return productName;
  }

  /**
   * Setter for product name field.
   * Defined despite warnings for easy usage in future code
   *
   * @param productName name of product.
   */
  public void setProductName(String productName) {
    this.productName = productName;
  }

  /**
   * Override toString method to contain fields and labels.
   *
   * @return String with Production Record details.
   */
  public String toString() {
    return "Prod. Num: " + productionNumber + " Prod. Id: " + productId + " Serial Num: "
        + serialNumber + " Date: " + simpleDateFormat.format(dateProduced);
  }
}
