package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.items.ChargeableGpsItem;
import club.iananderson.pocketgps.items.BasicGps;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;

public class FabricRegistration {
  public static CreativeModeTab TAB = FabricItemGroupBuilder.create(PocketGps.location("tab"))
      .icon(() -> PocketGps.GPS.get().getDefaultInstance())
      .build();

  public static BasicGps BASIC_GPS = new BasicGps();
  public static ChargeableGpsItem POCKET_GPS = new ChargeableGpsItem();

  static {
    PocketGps.BASIC_GPS = () -> BASIC_GPS;
    PocketGps.GPS = () -> POCKET_GPS;
  }

  public static void register() {
    Registry.register(Registry.ITEM, PocketGps.location("basic_gps"), BASIC_GPS);
    Registry.register(Registry.ITEM, PocketGps.location("gps"), POCKET_GPS);
  }

}