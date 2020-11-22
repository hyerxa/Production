public class Employee {
    private String name;
    private String userName;
    private String password;
    private String email;

    Employee(String name, String password) {
        this.name = name;
        if (checkName(name)) {
            setUsername(name);
            setEmail(name);
        } else {
            this.userName = "default";
            this.email = "user@oracleacademy.Test";
        }

        if (isValidPassword(password)) {
            this.password = password;
        } else this.password = "pw";
    }

    private boolean checkName(String name) {
        if (name.contains(" ")) {
            return true;
        } else return false;
    }

    private void setUsername(String name) {
        int indexOfSpace = name.indexOf(" ");
        String firstName = name.substring(0, indexOfSpace);
        String lastName = name.substring(indexOfSpace + 1);
        this.userName = (firstName.charAt(0) + lastName).toLowerCase();
    }

    private void setEmail(String name) {
        int indexOfSpace = name.indexOf(" ");
        String firstName = name.substring(0, indexOfSpace);
        String lastName = name.substring(indexOfSpace + 1);
        this.email = (firstName + "." + lastName).toLowerCase() + "@oracleacademy.Test";
    }

    private boolean isValidPassword(String password) {
        String specialCharacters = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
        boolean hasSpecialCharacter = false;
        for (int i = 0; i < password.length(); i++) {
            if (specialCharacters.indexOf(password.charAt(i)) != -1) {
                hasSpecialCharacter = true;
                break;
            }
        }
        String passwordUpper = password.toUpperCase();
        String passwordLower = password.toLowerCase();
        if (password.equals(passwordLower) || password.equals(passwordUpper) || !hasSpecialCharacter) return false;
        else return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String toString() {
        return "Employee Details" + "\n" +
                "Name : " + this.name + "\n" +
                "Username : " + this.userName + "\n" +
                "Email : " + this.email + "\n" +
                "Initial Password : " + this.password;
    }
}
