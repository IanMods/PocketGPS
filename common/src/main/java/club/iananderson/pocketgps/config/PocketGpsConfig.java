package club.iananderson.pocketgps.config;

import club.iananderson.pocketgps.energy.EnergyUnit;
import net.minecraftforge.common.ForgeConfigSpec;

public class PocketGpsConfig {
  public static final ForgeConfigSpec GENERAL_SPEC;
  private static ForgeConfigSpec.ConfigValue<EnergyUnit> energyUnit;
  private static ForgeConfigSpec.BooleanValue gpsNeedPower;
  private static ForgeConfigSpec.IntValue gpsEnergyCapacity;
  private static ForgeConfigSpec.IntValue gpsMaxInput;
  private static ForgeConfigSpec.IntValue gpsMaxOutput;
  private static ForgeConfigSpec.IntValue gpsEnergyCost;

  static {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    setupConfig(builder);
    GENERAL_SPEC = builder.build();
  }

  private PocketGpsConfig() {
  }

  private static void setupConfig(ForgeConfigSpec.Builder builder) {
    builder.push("PocketGps");
    builder.push("Power");
    energyUnit = builder.comment("Which energy unit to display for Forge?\n" + "Default is FE.")
        .defineEnum("energy_unit", EnergyUnit.FE);

    gpsNeedPower = builder.comment(
            "Require the Gps to have power in order to display the minimap?\n" + "(true/false)\n" + "Default is true.")
        .define("need_power", false);

    gpsEnergyCapacity = builder.comment("How much the power the GPS can hold.\n" + "Default 40000")
        .defineInRange("gps_capacity", 40000, 0, Integer.MAX_VALUE);
    gpsMaxInput = builder.comment("The maximum input the GPS can receive when charging.\n" + "Default 128")
        .defineInRange("gps_max_input", 128, 0, Integer.MAX_VALUE);
    ;
    gpsMaxOutput = builder.comment("How much power the GPS can output.\n" + "Default 128")
        .defineInRange("gps_max_output", 128, 0, Integer.MAX_VALUE);
    ;
    gpsEnergyCost = builder.comment("Power cost of the GPS while moving (per tick).\n" + "Default 2")
        .defineInRange("gps_energy_cost", 2, 0, Integer.MAX_VALUE);
    ;

    builder.pop();
    builder.pop();
  }

  //PocketGps
  //Power
  public static EnergyUnit getEnergyUnit() {
    return PocketGpsConfig.energyUnit.get();
  }

  public static void setEnergyUnit(EnergyUnit unit) {
    PocketGpsConfig.energyUnit.set(unit);
  }

  public static boolean getGpsNeedPower() {
    return PocketGpsConfig.gpsNeedPower.get();
  }

  public static void setGpsNeedPower(boolean enable) {
    PocketGpsConfig.gpsNeedPower.set(enable);
  }

  public static int getGpsEnergyCapacity() {
    return PocketGpsConfig.gpsEnergyCapacity.get();
  }

  public static void setGpsEnergyCapacity(int power) {
    PocketGpsConfig.gpsEnergyCapacity.set(power);
  }

  public static int getGpsMaxInput() {
    return PocketGpsConfig.gpsMaxInput.get();
  }

  public static void setGpsMaxInput(int power) {
    PocketGpsConfig.gpsMaxInput.set(power);
  }

  public static int getGpsMaxOutput() {
    return PocketGpsConfig.gpsMaxOutput.get();
  }

  public static void setGpsMaxOutput(int power) {
    PocketGpsConfig.gpsMaxOutput.set(power);
  }

  public static int getGpsEnergyCost() {
    return PocketGpsConfig.gpsEnergyCost.get();
  }

  public static void setGpsEnergyCost(int power) {
    PocketGpsConfig.gpsEnergyCost.set(power);
  }

}