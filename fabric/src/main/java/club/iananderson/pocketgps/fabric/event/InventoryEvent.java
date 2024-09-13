package club.iananderson.pocketgps.fabric.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
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
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    int slot = 0;

    if (PocketGps.curiosLoaded()) {
      Optional<TrinketComponent> curiosInventory = TrinketsApi.getTrinketComponent(player);
      if (curiosInventory.isPresent()) {
        if (curiosInventory.get().isEquipped(item)) {
          slot += 1;
        }
      }
    }
    return slot > 0;
  }

  public static void register() {
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      LocalPlayer player = client.player;
      boolean hasGpsInv = CurrentMinimap.hasGps(player, FabricRegistration.POCKET_GPS);
      boolean hasGpsCurio = findCurio(player, FabricRegistration.POCKET_GPS);

      CurrentMinimap.displayMinimap(hasGpsInv || hasGpsCurio);
    });
  }
}