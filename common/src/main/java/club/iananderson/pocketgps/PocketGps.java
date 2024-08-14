package club.iananderson.pocketgps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PocketGps {
  public static final String MOD_ID = "pocketgps";
  public static final String MOD_NAME = "PocketGPS";
  public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

  //public static final GpsItem POCKET_GPS = new GpsItem();
  private PocketGps() {
  }

  public static void init() {
    //Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "gps"), POCKET_GPS);
  }
}
