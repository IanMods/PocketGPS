package club.iananderson.pocketgps.items.components;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

public class GpsToggle {
  public static final boolean initState = true;

  public static DataComponentType<Boolean> TOGGLE = DataComponentType.<Boolean>builder()
      .persistent(Codec.BOOL.orElse(initState)).networkSynchronized(ByteBufCodecs.BOOL)
      .build();
}
