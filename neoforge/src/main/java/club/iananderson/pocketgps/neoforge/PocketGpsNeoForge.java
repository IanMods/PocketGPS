package club.iananderson.pocketgps.neoforge;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.client.PocketGpsClient;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.neoforge.impl.curios.CuriosCompat;
import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@Mod(PocketGps.MOD_ID)
public final class PocketGpsNeoForge {
  public PocketGpsNeoForge(IEventBus modEventBus, ModContainer modContainer) {
    PocketGps.init();
//    MinecraftForge.EVENT_BUS.addListener(InventoryEvent::onPlayerTickEvent);

    modContainer.registerConfig(Type.COMMON, PocketGpsConfig.GENERAL_SPEC, "pocketgps-common.toml");

    modEventBus.addListener(PocketGpsNeoForge.ClientModEvents::commonSetup);
  }

  @SubscribeEvent
  public void pocketGpsPlayerTick(PlayerTickEvent.Post event) {
    if (event.getEntity().isLocalPlayer()) {
      PocketGpsClient.cachePlayerState(event.getEntity());
    }
  }

  @SubscribeEvent
  public void pocketGpsOnPlayerLoad(PlayerLoggedInEvent event) {
    Player player = event.getEntity();

    PocketGpsClient.setInitializedMapState(false);
    PocketGpsClient.setIsDrawingMap(false);
    PocketGpsClient.cachePlayerState(player);
  }

  @SubscribeEvent
  public void pocketGpsOnPlayerRespawn(PlayerRespawnEvent event) {
    Player player = event.getEntity();

    PocketGpsClient.setInitializedMapState(false);
    PocketGpsClient.setIsDrawingMap(false);
    PocketGpsClient.cachePlayerState(player);
  }

  @EventBusSubscriber(value = Dist.CLIENT, modid = PocketGps.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
      if (PocketGps.curiosLoaded()) {
        PocketGps.LOG.info("Talking to Curios");
        new CuriosCompat().setup(event);
      }
      if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
        PocketGps.LOG.info("Talking to Accessories");
        AccessoriesCompat.init(NeoForgeRegistration.POCKET_GPS.get());
      }
      PocketGps.clientInit();
    }
  }
}
