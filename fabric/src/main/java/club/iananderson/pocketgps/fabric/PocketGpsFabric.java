package club.iananderson.pocketgps.fabric;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.init.GpsItems;
import club.iananderson.pocketgps.items.properties.GpsItemProperties;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.renderer.item.ItemProperties;

public final class PocketGpsFabric implements ModInitializer {
  //Todo ---- Find out why it wont receive power on servers only

  @Override
  public void onInitialize() {
    PocketGps.init();
    FabricRegistration.register();

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      PocketGps.LOG.info("Talking to Accessories");
      AccessoriesCompat.init(GpsItems.POCKET_GPS);
    }
  }
}
