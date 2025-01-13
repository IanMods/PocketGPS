package club.iananderson.pocketgps;

import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.energy.EnergyUnit;
import club.iananderson.pocketgps.platform.Services;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PocketGps {
  public static final String MOD_ID = "pocketgps";
  public static final String MOD_NAME = "PocketGPS";
  public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
  public static ResourceLocation slotIcon = new ResourceLocation(MOD_ID, "slot/gps_slot");
  public static String ENERGY_TAG = "energy";
  public static String TOGGLE_GPS_TAG = "toggle_gps";
  public static Supplier<Item> BASIC_GPS;
  public static Supplier<Item> GPS;
  public static ResourceLocation TOGGLE_GPS = new ResourceLocation(MOD_ID, TOGGLE_GPS_TAG);
  private static boolean accessoriesLoaded;
  private static boolean curiosLoaded;
  private static boolean trinketsLoaded;
  private static boolean worldMapLoaded;
  private static EnergyUnit energyUnit;
  private static boolean gpsNeedPower;
  private static int gpsEnergyCapacity;
  private static int gpsMaxInput;
  private static int gpsMaxOutput;
  private static int gpsEnergyCost;

  //public static final GpsItem POCKET_GPS = new GpsItem();
  private PocketGps() {
  }

  public static void init() {
    curiosLoaded = Services.PLATFORM.isModLoaded("curios");
    trinketsLoaded = Services.PLATFORM.isModLoaded("trinkets");
    accessoriesLoaded = Services.PLATFORM.isModLoaded("accessories");
    worldMapLoaded = Services.PLATFORM.isModLoaded("xaeroworldmap");
  }

  public static void clientInit() {
    energyUnit = PocketGpsConfig.getEnergyUnit();
    gpsNeedPower = PocketGpsConfig.getGpsNeedPower();
    gpsEnergyCapacity = PocketGpsConfig.getGpsEnergyCapacity();
    gpsMaxInput = PocketGpsConfig.getGpsMaxInput();
    gpsMaxOutput = PocketGpsConfig.getGpsMaxOutput();
    gpsEnergyCost = PocketGpsConfig.getGpsEnergyCost();
  }

  public static boolean curiosLoaded() {
    return PocketGps.curiosLoaded;
  }

  public static boolean trinketsLoaded() {
    return PocketGps.trinketsLoaded;
  }

  public static boolean accessoriesLoaded() {
    return PocketGps.accessoriesLoaded;
  }

  public static boolean worldMapLoaded() {
    return PocketGps.worldMapLoaded;
  }

  public static EnergyUnit energyUnit() {
    return energyUnit;
  }

  public static boolean gpsNeedPower() {
    return gpsNeedPower;
  }

  public static int gpsEnergyCapacity() {
    return gpsEnergyCapacity;
  }

  public static int gpsMaxInput() {
    return gpsMaxInput;
  }

  public static int gpsMaxOutput() {
    return gpsMaxOutput;
  }

  public static int gpsEnergyCost() {
    return gpsEnergyCost;
  }

  public static ResourceLocation location(String path) {
    return new ResourceLocation(MOD_ID, path);
  }
}
