package club.iananderson.pocketgps.items.properties;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.util.NBTUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GpsItemProperties implements ClampedItemPropertyFunction {
  @Override
  public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel clientLevel,
      @Nullable LivingEntity livingEntity, int i) {
    return (!NBTUtil.getBoolean(itemStack, PocketGps.TOGGLE_GPS_TAG) || (PocketGps.gpsNeedPower() && NBTUtil.getInt(itemStack, PocketGps.ENERGY_TAG) == 0)) ? 0F :
           1F;
  }
}
