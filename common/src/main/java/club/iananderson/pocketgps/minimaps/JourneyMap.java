package club.iananderson.pocketgps.minimaps;

import club.iananderson.pocketgps.util.CurrentMinimap;
import club.iananderson.pocketgps.util.FindItem;
import journeymap.client.JourneymapClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Items;

public class JourneyMap {
  public static void DisplayMap(LocalPlayer player) {
    if (CurrentMinimap.journeyMapLoaded()) {
      Minecraft mc = Minecraft.getInstance();

      if (mc.level == null || mc.player == null) {
        return;
      }
      boolean hasGps = FindItem.findItem(player.getInventory(), Items.SPYGLASS);

      JourneymapClient.getInstance().getActiveMiniMapProperties().setActive(hasGps);
    }
  }
}
