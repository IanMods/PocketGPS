package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.items.ChargeableGpsItem;
import club.iananderson.pocketgps.registry.CommonRegistration;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;

public class FabricRegistration {
  public static ChargeableGpsItem POCKET_GPS = new ChargeableGpsItem();

  public static CreativeModeTab TAB = FabricItemGroupBuilder.create(PocketGps.location("tab"))
      .icon(() -> CommonRegistration.addIcon(PocketGps.GPS.get()))
      .build();

  static {
    PocketGps.GPS = () -> POCKET_GPS;
  }

  public static void register() {
    Registry.register(Registry.ITEM, PocketGps.location("gps"), POCKET_GPS);
  }
}