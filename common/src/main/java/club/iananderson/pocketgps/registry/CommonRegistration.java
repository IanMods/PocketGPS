package club.iananderson.pocketgps.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.items.BaseChargeableGps;
import club.iananderson.pocketgps.util.ItemUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CommonRegistration {
  public static Collection<ItemStack> addPoweredItem(Item item, boolean includeUncharged) {
    ItemStack uncharged = new ItemStack(item);
    ItemStack charged = new ItemStack(item);
    BaseChargeableGps gps = (BaseChargeableGps) item;

    ItemUtil.initGpsState(uncharged);
    ItemUtil.initGpsState(charged);

    gps.setEnergyStored(uncharged, 0);
    gps.setEnergyStored(charged, gps.getCapacity());

    if (includeUncharged && PocketGps.gpsNeedPower()) {
      return new ArrayList<>(List.of(uncharged, charged));
    } else {
      return new ArrayList<>(List.of(charged));
    }
  }

  public static ItemStack addIcon(Item item) {
    ItemStack charged = new ItemStack(item);
    BaseChargeableGps gps = (BaseChargeableGps) item;

    ItemUtil.initGpsState(charged);

    gps.setEnergyStored(charged, gps.getCapacity());

    return charged;
  }
}
