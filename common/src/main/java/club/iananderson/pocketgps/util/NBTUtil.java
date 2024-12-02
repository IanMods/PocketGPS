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
}
