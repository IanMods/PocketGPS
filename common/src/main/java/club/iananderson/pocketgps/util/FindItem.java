package club.iananderson.pocketgps.util;

import club.iananderson.pocketgps.PocketGps;
import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FindItem {

  public static boolean findItem(Inventory inv, Item item, boolean needPower) {
    return contains(inv, item.getDefaultInstance(), needPower);
  }

  public static boolean contains(Inventory inv, ItemStack itemStack, boolean needPower) {
    List<NonNullList<ItemStack>> compartments = ImmutableList.of(inv.items, inv.armor, inv.offhand);

    for (NonNullList<ItemStack> compartment : compartments) {
      for (ItemStack invItemStack : compartment) {
        if (!invItemStack.isEmpty() && ItemStack.isSameItem(invItemStack, itemStack)) {
          if (!needPower) {
            return ItemUtil.isGpsOn(invItemStack);
          } else if (invItemStack.hasTag()) {
            return ItemUtil.isGpsOn(invItemStack) && NBTUtil.getInt(invItemStack, PocketGps.ENERGY_TAG) > 0;
          }
        }
      }
    }
    return false;
  }
}
