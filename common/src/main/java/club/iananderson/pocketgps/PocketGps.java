package club.iananderson.pocketgps;

import club.iananderson.pocketgps.platform.Services;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PocketGps {
  public static final String MOD_ID = "pocketgps";
  public static final String MOD_NAME = "PocketGPS";
  public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
  private static boolean accessoriesLoaded;
  private static boolean curiosLoaded;
  private static String platformName;

  private PocketGps() {
  }

  public static ResourceLocation location(String path) {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
  }

  public static void init() {
    platformName = Services.PLATFORM.getPlatformName();
    curiosLoaded = Services.PLATFORM.isModLoaded("trinkets") || Services.PLATFORM.isModLoaded("curios");
    accessoriesLoaded = Services.PLATFORM.isModLoaded("accessories");
  }

  public static String platformName() {
    return PocketGps.platformName;
  }

  public static boolean curiosLoaded() {
    return PocketGps.curiosLoaded;
  }

  public static boolean accessoriesLoaded() {
    return PocketGps.accessoriesLoaded;
  }
}
