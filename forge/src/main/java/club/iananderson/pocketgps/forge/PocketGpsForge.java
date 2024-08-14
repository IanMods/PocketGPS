package club.iananderson.pocketgps.forge;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.event.InventoryEvent;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PocketGps.MOD_ID)
public final class PocketGpsForge {
  public PocketGpsForge() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    ForgeRegistration.init(modEventBus);
    MinecraftForge.EVENT_BUS.addListener(InventoryEvent::onPlayerTickEvent);
  }
}
