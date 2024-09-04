package club.iananderson.pocketgps.neoforge;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.neoforge.event.InventoryEvent;
import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(PocketGps.MOD_ID)
public final class PocketGpsNeoForge {
  public PocketGpsNeoForge(IEventBus modEventBus) {
    PocketGps.init();
    NeoForgeRegistration.init(modEventBus);

    NeoForge.EVENT_BUS.addListener(InventoryEvent::onPlayerTickEvent);
    modEventBus.addListener(PocketGpsNeoForge::onInitialize);
  }

  public static void onInitialize(FMLCommonSetupEvent event) {
    if (PocketGps.accessoriesLoaded()) {
      PocketGps.LOG.info("Talking to Accessories");
      AccessoriesCompat.init(NeoForgeRegistration.POCKET_GPS.get());
    }
  }
}