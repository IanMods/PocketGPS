package club.iananderson.pocketgps.forge;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.event.InventoryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(PocketGps.MOD_ID)
public final class PocketGpsForge {
  public PocketGpsForge() {
    MinecraftForge.EVENT_BUS.addListener(InventoryEvent::onPlayerTickEvent);
  }
}
