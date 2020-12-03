/**
 * Enum for types of items that may be stored.
 *
 * @author Haley Yerxa
 */

public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIOMOBILE("AM"),
  VISUALMOBILE("VM");

  /**
   * Type code.
   */
  private final String code;

  /**
   * Sets the code for the item type.
   *
   * @param code to set
   */
  ItemType(String code) {
    this.code = code;
  }

  /**
   * Gets the code for the item type.
   *
   * @return String code of type
   */
  public String getCode() {
    return this.code;
  }
}
