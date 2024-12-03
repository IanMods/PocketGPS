package club.iananderson.pocketgps.items.properties;

import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.util.ItemUtil;
import club.iananderson.pocketgps.util.NBTUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GpsItemProperties implements ClampedItemPropertyFunction {
  @Override
  public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel,
      @Nullable LivingEntity livingEntity, int i) {
    return NBTUtil.getBoolean(itemStack, ItemEnergyStorage.TOGGLE_GPS_TAG) ? 1F : 0F;
  }
}
