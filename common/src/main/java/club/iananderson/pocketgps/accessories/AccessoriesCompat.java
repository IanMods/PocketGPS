package club.iananderson.pocketgps.accessories;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.accessories.item.AccessoriesGps;
import net.minecraft.world.item.Item;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public static void clientInit(Item gps) {
    if (PocketGps.accessoriesLoaded()) {
      AccessoriesGps.clientInit(gps);
    }
  }

  public static void init(Item gps) {
    if (PocketGps.accessoriesLoaded()) {
      AccessoriesGps.init(gps);
    }
  }
}