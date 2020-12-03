/**
 * Represents the controls that a media device would have.
 *
 * @author Haley Yerxa
 */

public interface MultimediaControl {

  /**
   * play current media.
   */
  void play();

  /**
   * stop current media.
   */
  void stop();

  /**
   * go back to previous media.
   */
  void previous();

  /**
   * skip to next media.
   */
  void next();
}
