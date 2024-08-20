package club.iananderson.pocketgps.forge;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.event.InventoryEvent;
import club.iananderson.pocketgps.forge.impl.curios.CuriosCompat;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PocketGps.MOD_ID)
public final class PocketGpsForge {
  public PocketGpsForge() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    PocketGps.init();
    ForgeRegistration.init(modEventBus);
    MinecraftForge.EVENT_BUS.addListener(InventoryEvent::onPlayerTickEvent);
    modEventBus.addListener(ClientModEvents::commonSetup);
  }

  @Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
      if (PocketGps.curiosLoaded()) {
        PocketGps.LOG.info("Talking to Curios");
        new CuriosCompat().setup(event);
      }
    }

    @SubscribeEvent
    public static void curioTexture(TextureStitchEvent.Pre evt) {
      evt.addSprite(PocketGps.slotIcon);
    }
  }
}
