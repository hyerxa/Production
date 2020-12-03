/**
 * Interface to implement methods relating to Items.
 *
 * @author Haley Yerxa
 */

public interface Item {

  /**
   * Get the item id.
   *
   * @return int id number.
   */
  int getId();

  /**
   * Set the item name.
   *
   * @param name to set.
   */
  void setName(String name);

  /**
   * Get the item name.
   *
   * @return String name.
   */
  String getName();

  /**
   * Set the item manufacturer.
   *
   * @param manufacturer name.
   */
  void setManufacturer(String manufacturer);

  /**
   * Get the manufacturer name.
   *
   * @return String manufacturer name.
   */
  String getManufacturer();
}
