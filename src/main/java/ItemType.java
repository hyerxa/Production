public enum ItemType {
  AUDIO ("AU"),
  VISUAL ("VI"),
  AUDIO_MOBILE ("AM"),
  VISUAL_MOBILE ("VM");

  private final String code;

  ItemType(String code) {
    this.code = code;
  }

  private String code() { return code; }
}
