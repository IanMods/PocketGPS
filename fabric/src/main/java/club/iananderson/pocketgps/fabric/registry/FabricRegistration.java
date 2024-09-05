package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.items.PocketGpsItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class FabricRegistration {
  public static final Item POCKET_GPS = PocketGpsItems.GPS_ITEM_SUPPLIER.get();

  public static CreativeModeTab TAB = PocketGpsItems.POCKET_GPS_TAB(POCKET_GPS).get();

  public static void init() {
    Registry.register(BuiltInRegistries.ITEM, PocketGps.location("gps"), POCKET_GPS);
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, PocketGps.location("tab"), TAB);
  }
}
