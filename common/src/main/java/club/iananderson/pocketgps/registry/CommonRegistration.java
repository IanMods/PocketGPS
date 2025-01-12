package club.iananderson.pocketgps.registry;

import club.iananderson.pocketgps.items.BaseChargeableGps;
import club.iananderson.pocketgps.util.ItemUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CommonRegistration {
  public static void addPoweredItem(Item item, NonNullList<ItemStack> itemList, boolean includeUncharged) {
    ItemStack uncharged = new ItemStack(item);
    ItemStack charged = new ItemStack(item);
    BaseChargeableGps gps = (BaseChargeableGps) item;

    ItemUtil.initGpsState(uncharged);
    ItemUtil.initGpsState(charged);

    gps.setEnergyStored(uncharged, 0);
    gps.setEnergyStored(charged, gps.getCapacity());

    if (includeUncharged) {
      itemList.add(uncharged);
      itemList.add(charged);
    } else {
      itemList.add(charged);
    }
  }
}
