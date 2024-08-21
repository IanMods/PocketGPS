package club.iananderson.pocketgps.neoforge;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.neoforge.event.InventoryEvent;
import club.iananderson.pocketgps.neoforge.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.neoforge.impl.curios.CuriosCompat;
import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(PocketGps.MOD_ID)
public final class PocketGpsNeoForge {
  public PocketGpsNeoForge(IEventBus modEventBus, ModContainer modContainer) {
    PocketGps.init();

    NeoForgeRegistration.init(modEventBus);
    NeoForge.EVENT_BUS.addListener(InventoryEvent::onPlayerTickEvent);
    modEventBus.addListener(ClientModEvents::commonSetup);
  }

  @EventBusSubscriber(modid = PocketGps.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
      if (PocketGps.curiosLoaded()) {
        PocketGps.LOG.info("Talking to Curios");
        new CuriosCompat().setup(event);
      }
      if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
        PocketGps.LOG.info("Talking to Accessories");
        new AccessoriesCompat().setup(event);
      }
    }
  }
}
