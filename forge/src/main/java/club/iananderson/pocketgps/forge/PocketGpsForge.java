package club.iananderson.pocketgps.forge;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.forge.event.InventoryEvent;
import club.iananderson.pocketgps.forge.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.forge.impl.curios.CuriosCompat;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PocketGps.MOD_ID)
public final class PocketGpsForge {
  public PocketGpsForge(FMLJavaModLoadingContext context) {
    IEventBus modEventBus = context.getModEventBus();
    PocketGps.init();
    MinecraftForge.EVENT_BUS.addListener(InventoryEvent::onPlayerTickEvent);

    ForgeRegistration.init(modEventBus);
    context.registerConfig(Type.COMMON, PocketGpsConfig.GENERAL_SPEC, "pocketgps-common.toml");

    modEventBus.addListener(ClientModEvents::commonSetup);
    MinecraftForge.EVENT_BUS.register(this);
  }

  @Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
      PocketGps.clientInit();
    }
  }
}
