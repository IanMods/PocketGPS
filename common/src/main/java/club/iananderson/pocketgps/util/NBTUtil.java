package club.iananderson.pocketgps.util;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public class NBTUtil {

  public static void setInt(ItemStack stack, DataComponentType<Integer> dataComponentType, int value) {
    stack.set(dataComponentType, value);
  }

  public static int getInt(ItemStack stack, DataComponentType<Integer> dataComponentType) {
    return stack.getComponents().getOrDefault(dataComponentType, 0);
  }

  public static void setBoolean(ItemStack stack, DataComponentType<Boolean> dataComponentType, boolean value) {
    stack.set(dataComponentType, value);
  }

  public static boolean getBoolean(ItemStack stack, DataComponentType<Boolean> dataComponentType,
      boolean defaultValue) {
    return stack.getComponents().getOrDefault(dataComponentType, defaultValue);
  }

  public static void flipBoolean(ItemStack stack, DataComponentType<Boolean> dataComponentType, boolean defaultValue) {
    setBoolean(stack, dataComponentType, !getBoolean(stack, dataComponentType, defaultValue));
  }
}
