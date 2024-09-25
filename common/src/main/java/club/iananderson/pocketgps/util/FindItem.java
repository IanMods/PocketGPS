package club.iananderson.pocketgps.util;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FindItem {
  private static boolean needPower = true;   //Todo - move to config once created

  public static boolean findItem(Inventory inv, Item item) {
    return contains(inv, item.getDefaultInstance(), needPower);
  }

  public static boolean contains(Inventory inv, ItemStack itemStack, boolean needPower) {
    List<NonNullList<ItemStack>> compartments = ImmutableList.of(inv.items, inv.armor, inv.offhand);

    for (NonNullList<ItemStack> compartment : compartments) {
      for (ItemStack invItemStack : compartment) {
        if (!invItemStack.isEmpty() && ItemStack.isSameItem(invItemStack, itemStack)) {
          if(needPower){
            return invItemStack.hasTag();
          }
          else return true;
        }
      }
    }
    return false;
  }
}
