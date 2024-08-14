package club.iananderson.pocketgps.forge.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InventoryEvent {

  @SubscribeEvent
  public static void onPlayerTickEvent(PlayerTickEvent event) {
    if (event.player instanceof LocalPlayer player) {
      CurrentMinimap.displayMinimap(player, ForgeRegistration.POCKET_GPS.get());
    }
  }
}
