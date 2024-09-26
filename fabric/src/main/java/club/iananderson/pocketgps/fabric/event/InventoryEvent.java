package club.iananderson.pocketgps.fabric.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.util.FindItem;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.Optional;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class InventoryEvent {
  private static boolean findCurio(Player player, Item item) {
    if (player == null) {
      return false;
    }

    if (PocketGps.curiosLoaded()) {
      Optional<TrinketComponent> trinketInventory = TrinketsApi.getTrinketComponent(player);
      if (trinketInventory.isPresent()) {
        return trinketInventory.get().isEquipped(item);
      }
    }
    return false;
  }

  public static void register() {
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      LocalPlayer player = client.player;
      boolean hasGps = FindItem.itemFound(player, FabricRegistration.POCKET_GPS);

      CurrentMinimap.displayMinimap(player,hasGps);
    });
  }
}