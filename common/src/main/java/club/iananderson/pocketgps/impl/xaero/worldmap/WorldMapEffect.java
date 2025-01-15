package club.iananderson.pocketgps.impl.xaero.worldmap;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import xaero.map.effects.Effects;

public class WorldMapEffect {
  public static MobEffect NO_WORLD_MAP = Effects.NO_WORLD_MAP;
  public static MobEffectInstance noWorldMap = new MobEffectInstance(NO_WORLD_MAP, -1, 0, false, false, false);
}
