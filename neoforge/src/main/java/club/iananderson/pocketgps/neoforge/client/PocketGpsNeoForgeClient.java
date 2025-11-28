package club.iananderson.pocketgps.neoforge.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.items.properties.GpsItemProperties;
import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = PocketGps.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PocketGpsNeoForgeClient {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
    ItemProperties.register(PocketGps.GPS.get(), PocketGps.TOGGLE_GPS, new GpsItemProperties());

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      PocketGps.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit(NeoForgeRegistration.POCKET_GPS.get());
    }
  }
}
