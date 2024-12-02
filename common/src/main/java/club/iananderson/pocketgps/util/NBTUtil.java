package club.iananderson.pocketgps.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class NBTUtil {
  public static void validateCompound(ItemStack stack) {
    if (!stack.hasTag()) {
      CompoundTag tag = new CompoundTag();
      stack.setTag(tag);
    }
  }

  public static boolean hasKey(ItemStack stack, String key) {
    return stack.hasTag() && stack.getTag().contains(key);
  }

  public static CompoundTag getTagCompound(ItemStack stack) {
    validateCompound(stack);
    return stack.getTag();
  }

  public static void setTag(ItemStack stack, String key, Tag value) {
    validateCompound(stack);
    stack.getTag().put(key, value);
  }

  public static Tag getTag(ItemStack stack, String key) {
    return stack.hasTag() ? stack.getTag().get(key) : null;
  }

  public static void removeTag(ItemStack stack, String key) {
    if (hasKey(stack, key)) {
      stack.getTag().remove(key);
    }
  }

  public static void setInt(ItemStack stack, String key, int value) {
    validateCompound(stack);
    stack.getTag().putInt(key, value);
  }

  public static int getInt(ItemStack stack, String key) {
    return stack.hasTag() ? stack.getTag().getInt(key) : 0;
  }

  public static ItemStack setInitBoolean(ItemStack stack, String key) {
    validateCompound(stack);
    stack.getTag().putBoolean(key, true);
    return stack;
  }

  public static void setBoolean(ItemStack stack, String key, boolean value) {
    validateCompound(stack);
    stack.getTag().putBoolean(key, value);
  }

  public static boolean getBoolean(ItemStack stack, String key) {
    return stack.hasTag() && stack.getTag().getBoolean(key);
  }

  public static void flipBoolean(ItemStack stack, String key) {
    validateCompound(stack);
    setBoolean(stack, key, !getBoolean(stack, key));
  }
}
