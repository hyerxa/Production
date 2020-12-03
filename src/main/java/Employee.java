/**
 * Represents an employee who has access to the system. Creates Username and Password for employee.
 *
 * @author Haley Yerxa
 */

public class Employee {

  /**
   * Employee name.
   */
  private String name;
  /**
   * Automated Username.
   */
  private String userName;
  /**
   * Given or default password.
   */
  private String password;
  /**
   * Employee email.
   */
  private String email;

  /**
   * Constructor that creates username and email given name. Checks to see if given password is
   * valid.
   *
   * @param name     is employee entered name.
   * @param password is user given password.
   */
  Employee(String name, String password) {
    this.name = name;
    // check to see if name includes space (first and last given)
    if (checkName(name)) {
      setUsername(name);
      setEmail(name);
    } else {
      // set username and email to default values
      this.userName = "default";
      this.email = "user@oracleacademy.Test";
    }

    // check to see if password is valid, if not set to default
    if (isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * Checks to see if name contains space.
   *
   * @param name is employee entered name.
   * @return boolean true if containing space.
   */
  private boolean checkName(String name) {
    return name.contains(" ");
  }

  /**
   * Sets username given employee inputted name.
   *
   * @param name is employee entered name.
   */
  private void setUsername(String name) {
    // determine where the split in the names is.
    int indexOfSpace = name.indexOf(" ");
    // first name is before space.
    String firstName = name.substring(0, indexOfSpace);
    // last name after space.
    String lastName = name.substring(indexOfSpace + 1);
    // set username to first letter of first name and whole last name
    this.userName = (firstName.charAt(0) + lastName).toLowerCase();
  }

  /**
   * Sets email given employee inputted name.
   *
   * @param name is employee entered name.
   */
  private void setEmail(String name) {
    int indexOfSpace = name.indexOf(" ");
    String firstName = name.substring(0, indexOfSpace);
    String lastName = name.substring(indexOfSpace + 1);
    // email is first name, ., lastname, and oracle domain name.
    this.email = (firstName + "." + lastName).toLowerCase() + "@oracleacademy.Test";
  }

  /**
   * Checks to see if password meets minimum requirements.
   *
   * @param password is employee entered password.
   */
  private boolean isValidPassword(String password) {
    // must have a special character
    String specialCharacters = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    // flag for special character
    boolean hasSpecialCharacter = false;
    for (int i = 0; i < password.length(); i++) {
      // charAt will return -1 if string doesn't contain char
      // check each character of password against specialCharacter string.
      if (specialCharacters.indexOf(password.charAt(i)) != -1) {
        hasSpecialCharacter = true;
        break;
      }
    }
    // if password is fully upper or lower case, it will not meet criteria.
    String passwordUpper = password.toUpperCase();
    String passwordLower = password.toLowerCase();
    // if one of the three criteria isn't met, it will return false.
    return !password.equals(passwordLower) && !password.equals(passwordUpper)
        && hasSpecialCharacter;
  }

  /**
   * Getter for name field.
   *
   * @return String employee name.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for name field.
   *
   * @param name employee name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for username field. Kept despite warnings so that is can be easily used later.
   *
   * @return String employee username.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Setter for userName field. Kept despite warnings so that is can be easily used later.
   *
   * @param userName employee username.
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * Getter for password field. Kept despite warnings so that is can be easily used later.
   *
   * @return String employee password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Setter for password field. Kept despite warnings so that is can be easily used later.
   *
   * @param password employee password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Getter for email field. Kept despite warnings so that is can be easily used later.
   *
   * @return String employee email.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Override toString method Create string with all fields labeled.
   *
   * @return String of employee details.
   */
  public String toString() {
    return "Employee Details" + "\n"
        + "Name : " + this.name + "\n"
        + "Username : " + this.userName + "\n"
        + "Email : " + this.email + "\n"
        + "Initial Password : " + this.password;
  }
}
