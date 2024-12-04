package club.iananderson.pocketgps.util;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.items.BaseChargeableGps;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemUtil {
  public static boolean hasGps(Player player, Item item) {
    if (player == null) {
      return false;
    }

    return FindItem.findItem(player.getInventory(), item, PocketGps.gpsNeedPower());
  }

  public static void initGpsState(ItemStack itemStack){
    NBTUtil.setBoolean(itemStack, PocketGps.TOGGLE_GPS_TAG, true);
  }

  public static boolean isGpsOn(ItemStack stack) {
    return NBTUtil.getBoolean(stack, PocketGps.TOGGLE_GPS_TAG);
  }

  public static void toggleGps(ItemStack stack, Player player) {
    boolean current = NBTUtil.getBoolean(stack, PocketGps.TOGGLE_GPS_TAG);
    NBTUtil.flipBoolean(stack, PocketGps.TOGGLE_GPS_TAG);
  }
}
