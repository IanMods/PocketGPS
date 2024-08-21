package club.iananderson.pocketgps.neoforge.impl.accessories;

import club.iananderson.pocketgps.neoforge.impl.accessories.item.AccessoriesGps;
import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public void setup(final FMLCommonSetupEvent evt) {
    AccessoriesAPI.registerAccessory(NeoForgeRegistration.POCKET_GPS.get(), new AccessoriesGps());
  }
}