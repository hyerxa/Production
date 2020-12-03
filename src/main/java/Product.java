/**
 * Abstract Class for a product that will either be audio or visual.
 *
 * @author Haley Yerxa
 */

public abstract class Product implements Item {

  /**
   * Product Id.
   */
  private int id;
  /**
   * Product Type.
   */
  private ItemType type;
  /**
   * Product Manufacturer.
   */
  private String manufacturer;
  /**
   * Product Name.
   */
  private String name;
  /**
   * Static field to keep track of number of audio products.
   */
  private static int audioCount = 0;
  /**
   * Static field to keep track of number of visual products.
   */
  private static int visualCount = 0;

  /**
   * Constructor that takes all fields as parameters.
   *
   * @param name is product name.
   * @param manufacturer is product manufacturer.
   * @param type is Enum ItemType.
   * @param id is product id.
   */
  Product(String name, String manufacturer, ItemType type, int id) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
    this.id = id;
  }

  /**
   * Override the toString method to get product details.
   *
   * @return String product details.
   */
  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: "
        + type.getCode();
  }

  /**
   * Getter for type.
   *
   * @return Type AU, AM, VI, VM.
   */
  public ItemType getType() {
    return type;
  }

  /**
   * Setter for type.
   * Defined despite warning so that it is available if required.
   *
   * @param type ItemType.
   */
  public void setType(ItemType type) {
    this.type = type;
  }

  /**
   * Getter for id.
   *
   * @return id number.
   */
  public int getId() {
    return id;
  }

  /**
   * Setter for id.
   *
   * @param id number.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Getter for manufacturer.
   *
   * @return manufacturer name.
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Setter for manufacturer.
   *
   * @param manufacturer name.
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Getter for name.
   *
   * @return String name.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for name.
   *
   * @param name of product.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for audioCount.
   *
   * @return int audioCount current count.
   */
  public static int getAudioCount() {
    return audioCount;
  }

  /**
   * Setter for audio count.
   *
   * @param audioCount int to hold count.
   */
  public static void setAudioCount(int audioCount) {
    Product.audioCount = audioCount;
  }

  /**
   * Getter for visualCount.
   *
   * @return int visualMobileCount
   */
  public static int getVisualCount() {
    return visualCount;
  }

  /**
   * Setter for visual count.
   *
   * @param visualCount int to hold count.
   */
  public static void setVisualCount(int visualCount) {
    Product.visualCount = visualCount;
  }
}

