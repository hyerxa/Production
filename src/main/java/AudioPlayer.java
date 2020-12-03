public class AudioPlayer extends Product implements MultimediaControl {
  private String supportedAudioFormats;
  private String supportedPlaylistFormats;

  AudioPlayer(String name, String manufacturer, String supportedAudioFormats, String supportedPlaylistFormats, int id) {
    super(name, manufacturer, ItemType.AUDIO, id);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  AudioPlayer(String name, String manufacturer, int id) {
    super(name, manufacturer, ItemType.AUDIO, id);
  }

  AudioPlayer(String name, String manufacturer, ItemType itemType, String supportedAudioFormats, String supportedPlaylistFormats) {
    super(name, manufacturer, itemType, 0);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  public void play() {
    System.out.println("Playing");
  }

  public void stop() {
    System.out.println("Stopping");
  }

  public void previous() {
    System.out.println("Previous");
  }

  public void next() {
    System.out.println("Next");
  }

  public String toString() {
    return super.toString() + "\n" + "Supported Audio Formats: " + supportedAudioFormats + "\n" + "Supported Playlist Formats: " + supportedPlaylistFormats;
  }
}
