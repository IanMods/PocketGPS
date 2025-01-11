package club.iananderson.pocketgps.impl.accessories;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.impl.accessories.item.AccessoriesGps;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public static void clientInit(Item gps) {
    if (PocketGps.accessoriesLoaded()) {
      AccessoriesGps.clientInit(gps);
    }
  }

  public static void init(Item gps) {
    if (PocketGps.accessoriesLoaded()) {
      AccessoriesGps.init(gps);
    }
  }

  public static ItemStack getGpsInAccessory(Player player) {
    Optional<AccessoriesCapability> accessoriesInventory = AccessoriesCapability.getOptionally(player);
    if (accessoriesInventory.isPresent()) {
      List<SlotEntryReference> found = accessoriesInventory.get().getEquipped(PocketGps.GPS.get());
      if (!found.isEmpty()) {
        return found.get(0).stack();
      }
    }
    return ItemStack.EMPTY;
  }
}
