package club.iananderson.pocketgps.forge.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.client.PocketGpsClient;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.items.properties.GpsItemProperties;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PocketGpsForgeClient {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
    ItemProperties.register(PocketGps.GPS.get(), PocketGps.TOGGLE_GPS, new GpsItemProperties());

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      PocketGps.LOG.info("Talking to Accessories Client");
      AccessoriesCompat.clientInit(ForgeRegistration.POCKET_GPS.get());
    }
  }
}
