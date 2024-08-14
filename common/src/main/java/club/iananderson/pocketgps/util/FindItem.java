package club.iananderson.pocketgps.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class FindItem {
  public static boolean findItem(Inventory inv, Item item) {
    return inv.contains(item.getDefaultInstance());
  }
}
