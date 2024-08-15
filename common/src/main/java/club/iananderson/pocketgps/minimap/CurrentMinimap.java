package club.iananderson.pocketgps.minimap;

import club.iananderson.pocketgps.platform.Services;
import club.iananderson.pocketgps.util.FindItem;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import journeymap.client.ui.UIManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import xaero.common.settings.ModOptions;
import xaero.minimap.XaeroMinimap;

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

  public static boolean hasGps(LocalPlayer player, Item item) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    return FindItem.findItem(player.getInventory(), item);
  }

  public static void displayMinimap(Boolean displayMap) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return;
    }

    if (journeyMapLoaded()) {
      UIManager.INSTANCE.setMiniMapEnabled(displayMap);
    }
    if (xaeroLoaded()) {
      XaeroMinimap.INSTANCE.getSettings().setOptionValue(ModOptions.MINIMAP, displayMap);
    }
    if (onlyFtbChunksLoaded()) {
      FTBChunksClientConfig.MINIMAP_ENABLED.set(displayMap);
    }
  }

  public enum Minimaps {
    XAERO("xaerominimap"),

    XAERO_FAIRPLAY("xaerominimapfair"),

    JOURNEYMAP("journeymap"),

    FTB_CHUNKS("ftbchunks");

    private final String modID;

    Minimaps(String modID) {
      this.modID = modID;
    }

    public String getModID() {
      return this.modID;
    }
  }
}
