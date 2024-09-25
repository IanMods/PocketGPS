package club.iananderson.pocketgps.util;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FindItem {
  private static boolean usePowerMode = true;   //Todo - move to config once created


  public static boolean findItem(Inventory inv, Item item) {
    if(usePowerMode){
      return containsCharged(inv, item.getDefaultInstance());
    }
    else return contains(inv, item.getDefaultInstance());
  }

  public static boolean containsCharged(Inventory inv, ItemStack itemStack) {
    List<NonNullList<ItemStack>> compartments = ImmutableList.of(inv.items, inv.armor, inv.offhand);

    for (NonNullList<ItemStack> compartment : compartments) {
      for (ItemStack invItemStack : compartment) {
        if (!invItemStack.isEmpty() && ItemStack.isSameItem(invItemStack, itemStack) && invItemStack.hasTag()) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean contains(Inventory inv, ItemStack itemStack) {
    List<NonNullList<ItemStack>> compartments = ImmutableList.of(inv.items, inv.armor, inv.offhand);

    for (NonNullList<ItemStack> compartment : compartments) {
      for (ItemStack invItemStack : compartment) {
        if (!invItemStack.isEmpty() && ItemStack.isSameItem(invItemStack, itemStack)) {
          return true;
        }
      }
    }
    return false;
  }
}
