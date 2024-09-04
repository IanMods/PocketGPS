package club.iananderson.pocketgps.neoforge.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.items.PocketGpsItems;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import club.iananderson.pocketgps.util.FindItem;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = PocketGps.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class InventoryEvent extends Event implements ICancellableEvent {

  @SubscribeEvent
  public static void onPlayerTickEvent(PlayerTickEvent.Post event) {
    Player player = event.getEntity();

    if (player.level().isClientSide) {
      boolean hasGps = FindItem.itemFound(player, NeoForgeRegistration.POCKET_GPS.get());

      CurrentMinimap.displayMinimap(hasGps);
    }
  }
}