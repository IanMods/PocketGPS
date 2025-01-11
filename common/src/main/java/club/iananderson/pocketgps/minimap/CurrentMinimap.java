package club.iananderson.pocketgps.minimap;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.client.PocketGpsClient;
import club.iananderson.pocketgps.platform.Services;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import journeymap.client.ui.UIManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class CurrentMinimap {
  private static boolean minimapLoaded(Minimaps minimap) {
    String modID = minimap.getModID();
    return Services.PLATFORM.isModLoaded(modID);
  }

  public static List<Minimaps> loadedMinimaps() {
    List<Minimaps> values = new ArrayList<>(List.of(Minimaps.values()));
    List<Minimaps> loaded = new ArrayList<>();

    values.forEach(minimaps -> {
      if (minimapLoaded(minimaps)) {
        loaded.add(minimaps);
      }
    });
    return loaded;
  }

  public static boolean noMinimapLoaded() {
    return loadedMinimaps().isEmpty();
  }

  public static boolean xaeroLoaded() {
    return minimapLoaded(Minimaps.XAERO) || minimapLoaded(Minimaps.XAERO_FAIRPLAY);
  }

  public static boolean journeyMapLoaded() {
    return minimapLoaded(Minimaps.JOURNEYMAP);
  }

  public static boolean onlyFtbChunksLoaded() {
    return (minimapLoaded(Minimaps.FTB_CHUNKS) && loadedMinimaps().size() == 1);
  }

  public static void displayMinimap(Player player) {
    if (player == null) {
      return;
    }

    boolean displayMap = PocketGpsClient.isDrawingMap();

    if (journeyMapLoaded()) {
      UIManager.INSTANCE.setMiniMapEnabled(displayMap);
    }

    if (xaeroLoaded()) {
      MobEffect NO_MINIMAP = xaero.common.effect.Effects.NO_MINIMAP;
      MobEffectInstance noMiniMap = new MobEffectInstance(NO_MINIMAP, -1, 0, false, false, false, null,
                                                          NO_MINIMAP.createFactorData());
      if (!displayMap && !player.hasEffect(NO_MINIMAP)) {
        player.addEffect(noMiniMap);
        //XaeroMinimap.INSTANCE.getSettings().setOptionValue(ModOptions.MINIMAP, displayMap);
      } else if (displayMap && player.hasEffect(NO_MINIMAP)) {
        player.removeEffect(NO_MINIMAP);
      }

    }

    if (PocketGps.worldMapLoaded()) {
      MobEffect NO_WORLD_MAP = xaero.map.effects.Effects.NO_WORLD_MAP;
      MobEffectInstance noWorldMap = new MobEffectInstance(NO_WORLD_MAP, -1, 0, false, false, false);

      if (!displayMap && !player.hasEffect(NO_WORLD_MAP)) {
        player.addEffect(noWorldMap);

      } else if (displayMap && player.hasEffect(NO_WORLD_MAP)) {
        player.removeEffect(NO_WORLD_MAP);
      }

    }

    if (onlyFtbChunksLoaded()) {
      FTBChunksClientConfig.MINIMAP_ENABLED.set(displayMap);

    }
  }

  public enum Minimaps {
    XAERO("xaerominimap", Component.translatable("minimap.pocketgps.xaero")),

    XAERO_FAIRPLAY("xaerominimapfair", Component.translatable("minimap.pocketgps.xaero")),

    JOURNEYMAP("journeymap", Component.translatable("minimap.pocketgps.journeymap")),

    FTB_CHUNKS("ftbchunks", Component.translatable("minimap.pocketgps.ftb"));

    private final String modID;

    private final MutableComponent modName;

    Minimaps(String modID, MutableComponent modName) {
      this.modID = modID;
      this.modName = modName;
    }

    public String getModID() {
      return this.modID;
    }

    public MutableComponent getModName() {
      return this.modName;
    }
  }
}
