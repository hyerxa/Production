/**
 * Represents a product with audio fields and capabilities.
 * Implements multimediaControl methods.
 *
 * @author Haley Yerxa
 */

public class AudioPlayer extends Product implements MultimediaControl {

  /**
   * Supported Audio Formats.
   */
  private String supportedAudioFormats;
  /**
   * Supported Playlist Formats.
   */
  private String supportedPlaylistFormats;

  /**
   * Constructor that takes all fields as parameters except item type.
   * ItemType defaults to audio.
   *
   * @param name is product name.
   * @param manufacturer given manufacturer.
   * @param supportedAudioFormats given list of audio formats.
   * @param supportedPlaylistFormats given list of playlist formats.
   * @param id given id number.
   */
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats, int id) {
    super(name, manufacturer, ItemType.AUDIO, id);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Constructor that doesn't require supported formats and sets item type.
   *
   * @param name is product name.
   * @param manufacturer is product manufacturer.
   * @param itemType will be audio or audioMobile.
   * @param id is product id.
   */
  AudioPlayer(String name, String manufacturer, ItemType itemType, int id) {
    super(name, manufacturer, itemType, id);
  }

  /**
   * Constructor that takes all fields as parameters except id, which is set to 0.
   *
   * @param name is product name.
   * @param manufacturer is product manufacturer.
   * @param itemType will be audio or audioMobile.
   * @param supportedAudioFormats given list of audio formats.
   * @param supportedPlaylistFormats given list of playlist formats.
   */
  AudioPlayer(String name, String manufacturer, ItemType itemType, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, itemType, 0);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Implementation of play method from MultimediaControl interface.
   */
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Implementation of stop method from MultimediaControl interface.
   */
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Implementation of previous method from MultimediaControl interface.
   */
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Implementation of next method from MultimediaControl interface.
   */
  public void next() {
    System.out.println("Next");
  }

  /**
   * Override toString method to display field values.
   *
   * @return String which is fields and labels to be displayed.
   */
  public String toString() {
    return super.toString() + "\n" + "Supported Audio Formats: " + supportedAudioFormats + "\n"
        + "Supported Playlist Formats: " + supportedPlaylistFormats;
  }
}
