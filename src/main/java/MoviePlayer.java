/**
 * Represents a product with visual fields and capabilities.
 * Implements multimediaControl methods.
 *
 * @author Haley Yerxa
 */


public class MoviePlayer extends Product implements MultimediaControl {

  /**
   * Video Screen.
   */
  Screen screen;
  /**
   * Type of monitor.
   */
  MonitorType monitorType;

  /**
   * Constructor that takes all fields as parameters except item type.
   * ItemType defaults to visual.
   *
   * @param name is product name.
   * @param manufacturer given manufacturer.
   * @param screen includes screen details.
   * @param monitorType enum type.
   * @param id given id number.
   */
  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType, int id) {
    super(name, manufacturer, ItemType.VISUAL, id);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /**
   * Constructor that takes basic fields.
   * Does not include screen details.
   *
   * @param name is product name.
   * @param manufacturer given manufacturer.
   * @param id given id number.
   * @param itemType either VISUAL or VISUALMOBILE
   */
  MoviePlayer(String name, String manufacturer, int id, ItemType itemType) {
    super(name, manufacturer, itemType, id);
  }

  /**
   * Constructor that takes basic fields.
   * Does not include screen details or id.
   * Id is defaulted to 0.
   *
   * @param name is product name.
   * @param manufacturer given manufacturer.
   * @param itemType either VISUAL or VISUALMOBILE
   */
  MoviePlayer(String name, String manufacturer, ItemType itemType) {
    super(name, manufacturer, itemType, 0);
  }

  /**
   * Constructor that takes all fields but id.
   * Id is defaulted to zero.
   *
   * @param name is product name.
   * @param manufacturer given manufacturer.
   * @param itemType either VISUAL or VISUALMOBILE
   * @param screen includes screen details.
   * @param monitorType enum type.
   */
  MoviePlayer(String name, String manufacturer, ItemType itemType, Screen screen,
      MonitorType monitorType) {
    super(name, manufacturer, itemType, 0);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /**
   * Implementation of play method from MultimediaControl interface.
   */
  public void play() {
    System.out.println("Playing movie");
  }

  /**
   * Implementation of stop method from MultimediaControl interface.
   */
  public void stop() {
    System.out.println("Stopping movie");
  }

  /**
   * Implementation of previous method from MultimediaControl interface.
   */
  public void previous() {
    System.out.println("Previous movie");
  }

  /**
   * Implementation of next method from MultimediaControl interface.
   */
  public void next() {
    System.out.println("Next movie");
  }

  /**
   * Override toString method to contain formatted values.
   *
   * @return String moviePlayer details.
   */
  public String toString() {
    return super.toString() + "\n" + "Monitor Type:" + monitorType + "\n" + screen;
  }
}
