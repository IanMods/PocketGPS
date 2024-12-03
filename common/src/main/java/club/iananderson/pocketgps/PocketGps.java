package club.iananderson.pocketgps;

import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.energy.EnergyUnit;
import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.platform.Services;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PocketGps {
  public static final String MOD_ID = "pocketgps";
  public static final String MOD_NAME = "PocketGPS";
  public static ResourceLocation TOGGLE_GPS = new ResourceLocation(MOD_ID, ItemEnergyStorage.TOGGLE_GPS_TAG);
  public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
  private static boolean accessoriesLoaded;
  private static boolean curiosLoaded;
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
    curiosLoaded = Services.PLATFORM.isModLoaded("trinkets") || Services.PLATFORM.isModLoaded("curios");
    accessoriesLoaded = Services.PLATFORM.isModLoaded("accessories");
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

  public static boolean accessoriesLoaded() {
    return PocketGps.accessoriesLoaded;
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
}
