package club.iananderson.pocketgps.neoforge.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = PocketGps.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class SeasonHudNeoForgeClient {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      PocketGps.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit(NeoForgeRegistration.POCKET_GPS.get());
    }
  }
}
