package club.iananderson.pocketgps.fabric.event;

import club.iananderson.pocketgps.minimap.CurrentMinimap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.player.LocalPlayer;

public class InventoryEvent {
  public static void register() {
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      LocalPlayer player = client.player;

      CurrentMinimap.displayMinimap(player);
    });
  }
}
