public enum ItemType {
  AUDIO ("AU"),
  VISUAL ("VI"),
  AUDIOMOBILE ("AM"),
  VISUALMOBILE ("VM");

  private final String code;

  ItemType(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}
