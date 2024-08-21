package club.iananderson.pocketgps.neoforge.impl.curios;

import club.iananderson.pocketgps.neoforge.impl.curios.item.CuriosGps;
import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompat {
  public CuriosCompat() {
    NeoForge.EVENT_BUS.addListener(this::setup);
  }

  public void setup(final FMLCommonSetupEvent evt) {
    CuriosApi.registerCurio(NeoForgeRegistration.POCKET_GPS.get(), new CuriosGps());
  }

}
