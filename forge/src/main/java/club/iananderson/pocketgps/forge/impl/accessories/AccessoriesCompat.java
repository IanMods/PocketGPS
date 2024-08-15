package club.iananderson.pocketgps.forge.impl.accessories;

import club.iananderson.pocketgps.forge.PocketGpsForge;
import club.iananderson.pocketgps.forge.impl.accessories.item.AccessoriesGps;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public void setup(final FMLCommonSetupEvent evt) {
    AccessoriesAPI.registerAccessory(PocketGpsForge.POCKET_GPS.get(), new AccessoriesGps());
  }

}