public abstract class Product implements Item {

  private int Id;
  private ItemType Type;
  private String Manufacturer;
  private String Name;

  private static int audioCount = 0;
  private static int visualCount = 0;
  private static int audioMobileCount = 0;
  private static int visualMobileCount = 0;


  Product(String name, String manufacturer, ItemType type, int id) {
    this.Name = name;
    this.Manufacturer = manufacturer;
    this.Type = type;
    this.Id = id;
  }

  public String toString() {
    return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: "
            + Type.getCode();
  }

  public ItemType getType() {
    return Type;
  }

  public void setType(ItemType type) {
    Type = type;
  }

  public void setId(int id) {
    Id = id;
  }

  public int getId() {
    return Id;
  }

  public String getManufacturer() {
    return Manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    Manufacturer = manufacturer;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public static int getAudioCount() {
    return audioCount;
  }

  public static void setAudioCount(int audioCount) {
    Product.audioCount = audioCount;
  }

  public static int getVisualMobileCount() {
    return visualMobileCount;
  }

  public static void setVisualMobileCount(int visualMobileCount) {
    Product.visualMobileCount = visualMobileCount;
  }

  public static int getAudioMobileCount() {
    return audioMobileCount;
  }

  public static void setAudioMobileCount(int audioMobileCount) {
    Product.audioMobileCount = audioMobileCount;
  }

  public static int getVisualCount() {
    return visualCount;
  }

  public static void setVisualCount(int visualCount) {
    Product.visualCount = visualCount;
  }
}

class Widget extends Product {

  Widget(String name, String manufacturer, ItemType type, int id) {
    super(name, manufacturer, type, id);
  }

}
