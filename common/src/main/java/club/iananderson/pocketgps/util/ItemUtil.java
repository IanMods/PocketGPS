package club.iananderson.pocketgps.util;

import club.iananderson.pocketgps.PocketGps;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemUtil {
  public static void initGpsState(ItemStack itemStack) {
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
