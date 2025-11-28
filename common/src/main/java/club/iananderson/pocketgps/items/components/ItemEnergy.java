package club.iananderson.pocketgps.items.components;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

public class ItemEnergy {

  public static DataComponentType<Integer> ENERGY = DataComponentType.<Integer>builder().persistent(Codec.INT.orElse(0))
      .networkSynchronized(ByteBufCodecs.VAR_INT)
      .build();
}
