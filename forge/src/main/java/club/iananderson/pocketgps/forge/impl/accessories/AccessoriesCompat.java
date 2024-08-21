package club.iananderson.pocketgps.forge.impl.accessories;

import club.iananderson.pocketgps.forge.impl.accessories.item.AccessoriesGps;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public void setup(final FMLCommonSetupEvent evt) {
    AccessoriesAPI.registerAccessory(ForgeRegistration.POCKET_GPS.get(), new AccessoriesGps());
  }

}