package club.iananderson.pocketgps.util;

import club.iananderson.pocketgps.platform.Services;
import java.util.ArrayList;
import java.util.List;

public class CurrentMinimap {
  private static boolean minimapLoaded(Minimaps minimap) {
    String modID = minimap.getModID();
    return Services.PLATFORM.isModLoaded(modID);
  }

  public static List<Minimaps> getLoadedMinimaps() {
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
    return getLoadedMinimaps().isEmpty();
  }

  public static boolean xaeroLoaded() {
    return getLoadedMinimaps().contains(Minimaps.XAERO) || getLoadedMinimaps().contains(Minimaps.XAERO_FAIRPLAY);
  }

  public static boolean journeyMapLoaded() {
    return getLoadedMinimaps().contains(Minimaps.JOURNEYMAP);
  }

  public static boolean ftbChunksLoaded() {
    return getLoadedMinimaps().contains(Minimaps.FTB_CHUNKS);
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
