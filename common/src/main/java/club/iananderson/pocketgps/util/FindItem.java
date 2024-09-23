package club.iananderson.pocketgps.util;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.platform.Services;
import io.wispforest.accessories.api.AccessoriesCapability;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FindItem {
  private static boolean findItem(Player player, Item item) {
    return player.getInventory().contains(item.getDefaultInstance());
  }

  public static boolean itemFound(Player player, Item item) {
    if (player == null) {
      return false;
    }

    boolean accessoryFound = false;
    boolean curioFound = Services.CURIO_HELPER.findCurios(player, item);

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      Optional<AccessoriesCapability> accessoriesContainer = AccessoriesCapability.getOptionally(player);
      if (accessoriesContainer.isPresent()) {
        accessoryFound = !accessoriesContainer.get().getEquipped(item).isEmpty();
      }
    }

    return findItem(player, item) || accessoryFound || curioFound;
  }
}