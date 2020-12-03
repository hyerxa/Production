/**
 * Interface for methods to get screen specs.
 *
 * @author Haley Yerxa.
 */

public interface ScreenSpec {

  /**
   * Getter for resolution field.
   *
   * @return String resolution.
   */
  String getResolution();

  /**
   * Getter for refresh rate field.
   *
   * @return int refresh rate.
   */
  int getRefreshRate();

  /**
   * Getter for response time field.
   *
   * @return int response time.
   */
  int getResponseTime();
}
