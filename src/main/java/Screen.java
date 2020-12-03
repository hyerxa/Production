/**
 * Represents a screen for a visual device.
 *
 * @author Haley Yerxa
 */

public class Screen implements ScreenSpec {

  /**
   * Screen Resolution.
   */
  final String resolution;
  /**
   * Screen refresh rate.
   */
  final int refreshRate;
  /**
   * Screen response time.
   */
  final int responseTime;

  /**
   * Constructor that initializes all fields.
   *
   * @param resolution screen resolution.
   * @param refreshRate screen refresh rate.
   * @param responseTime screen responseTime.
   */
  Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  /**
   * Getter for resolution field.
   *
   * @return String resolution.
   */
  public String getResolution() {
    return resolution;
  }

  /**
   * Getter for refresh rate field.
   *
   * @return int refresh rate.
   */
  public int getRefreshRate() {
    return refreshRate;
  }

  /**
   * Getter for response time field.
   *
   * @return int response time.
   */
  public int getResponseTime() {
    return responseTime;
  }

  /**
   * Override for toString method.
   *
   * @return String with screen details.
   */
  public String toString() {
    return "Screen:\n" + "Resolution: " + resolution + "\n" + "Refresh rate: " + refreshRate + "\n"
        + "Response time: " + responseTime;
  }
}
