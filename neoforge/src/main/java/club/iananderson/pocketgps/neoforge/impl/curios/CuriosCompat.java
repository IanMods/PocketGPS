package club.iananderson.pocketgps.neoforge.impl.curios;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.neoforge.impl.curios.item.CuriosGps;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class CuriosCompat {
  public CuriosCompat() {
  }

  public void setup(final FMLCommonSetupEvent evt) {
    if (PocketGps.curiosLoaded()) {
      CuriosGps.init();
    }
  }

}
