package club.iananderson.pocketgps.util;

import club.iananderson.pocketgps.items.components.GpsToggle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemUtil {
  public static void initGpsState(ItemStack itemStack) {
    NBTUtil.setBoolean(itemStack, GpsToggle.TOGGLE, GpsToggle.initState);
  }

  public static boolean isGpsOn(ItemStack stack) {
    return NBTUtil.getBoolean(stack, GpsToggle.TOGGLE, GpsToggle.initState);
  }

  public static void toggleGps(ItemStack stack, Player player) {
    boolean current = NBTUtil.getBoolean(stack, GpsToggle.TOGGLE, GpsToggle.initState);
    NBTUtil.flipBoolean(stack, GpsToggle.TOGGLE, GpsToggle.initState);
  }
}
