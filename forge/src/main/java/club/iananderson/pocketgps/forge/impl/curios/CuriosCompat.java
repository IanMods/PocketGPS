package club.iananderson.pocketgps.forge.impl.curios;

import club.iananderson.pocketgps.forge.impl.curios.item.CuriosGps;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosCompat {
  public CuriosCompat() {
    final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setup);
  }

  public void setup(final FMLCommonSetupEvent evt) {
    CuriosApi.registerCurio(ForgeRegistration.POCKET_GPS.get(), new CuriosGps());
  }

}
