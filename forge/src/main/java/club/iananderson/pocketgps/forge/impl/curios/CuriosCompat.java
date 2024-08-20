package club.iananderson.pocketgps.forge.impl.curios;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.impl.curios.item.CuriosGps;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

public class CuriosCompat {
  public CuriosCompat() {
    final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setup);
  }

  public static void registerSlots() {
    InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                         () -> new SlotTypeMessage.Builder("gps_slot").icon(PocketGps.slotIcon).size(1).build());
  }

  public void setup(final FMLCommonSetupEvent evt) {
    CuriosGps.initCapabilities();
    CuriosCompat.registerSlots();
  }

}
