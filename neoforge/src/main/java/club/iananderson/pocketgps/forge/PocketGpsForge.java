package club.iananderson.pocketgps.forge;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.client.PocketGpsClient;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.forge.impl.curios.CuriosCompat;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PocketGps.MOD_ID)
public final class PocketGpsForge {
  public PocketGpsForge() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    MinecraftForge.EVENT_BUS.register(this);

    PocketGps.init();
    ForgeRegistration.register(modEventBus);

    ModLoadingContext.get().registerConfig(Type.COMMON, PocketGpsConfig.GENERAL_SPEC, "pocketgps-common.toml");

    modEventBus.addListener(ClientModEvents::commonSetup);
  }

  @SubscribeEvent
  public void pocketGpsPlayerTick(TickEvent.PlayerTickEvent event) {
    if (event.phase != TickEvent.Phase.END) {
      return;
    }
    if (event.side == LogicalSide.CLIENT) {
      PocketGpsClient.cachePlayerState(event.player);
    }
  }

  @Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
      if (PocketGps.curiosLoaded()) {
        PocketGps.LOG.info("Talking to Curios");
        new CuriosCompat().setup(event);
      }
      PocketGps.clientInit();
    }
  }
}
