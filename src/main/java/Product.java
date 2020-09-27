public abstract class Product implements Item {
  private int id;
  private String type;
  private String manufacturer;
  private String name;

  Product(String name, String manufacturer, String type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  public String toString() {
    return "name: " + name + "\n" + "manufacturer: " + manufacturer + "\n" + "type: "
        + type;
  }

  public int getId() {
    return id;
  }

  public String getmanufacturer() {
    return manufacturer;
  }

  public void setmanufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getname() {
    return name;
  }

  public void setname(String name) {
    this.name = name;
  }
}
