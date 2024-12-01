package club.iananderson.pocketgps.config;

public enum EnergyUnit {
  FE(0, "FE"), RF(1, "RF"), E(2, "E");

  private final int idNum;
  private final String displayName;

  EnergyUnit(int idNum, String displayName) {
    this.idNum = idNum;
    this.displayName = displayName;
  }

  public int getId() {
    return this.idNum;
  }

  public String getDisplayName() {
    return this.displayName;
  }
}
