package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.items.ChargeableGpsItem;
import club.iananderson.pocketgps.registry.CommonRegistration;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item.Properties;

public class FabricRegistration {
  public static CreativeModeTab TAB = FabricItemGroupBuilder.create(PocketGps.location("tab"))
      .icon(() -> CommonRegistration.addIcon(PocketGps.GPS.get()))
      .build();

  public static ChargeableGpsItem POCKET_GPS = new ChargeableGpsItem(new Properties().tab(TAB));

  static {
    PocketGps.GPS = () -> POCKET_GPS;
  }

  public static void register() {
    Registry.register(Registry.ITEM, PocketGps.location("gps"), POCKET_GPS);
  }
}