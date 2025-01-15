package club.iananderson.pocketgps.impl.xaero.minimap;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import xaero.common.effect.Effects;

public class MinimapEffect {
  public static MobEffect NO_MINIMAP = Effects.NO_MINIMAP;
  public static MobEffectInstance noMiniMap = new MobEffectInstance(NO_MINIMAP, -1, 0, false, false, false, null,
                                                      NO_MINIMAP.createFactorData());
}
