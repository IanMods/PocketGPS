package club.iananderson.pocketgps.minimaps;

import club.iananderson.pocketgps.util.CurrentMinimap;
import club.iananderson.pocketgps.util.FindItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Items;
import xaero.common.settings.ModOptions;
import xaero.minimap.XaeroMinimap;

public class XaerosMinimap {
  public static void DisplayMap(LocalPlayer player) {
    if (CurrentMinimap.xaeroLoaded()) {
      Minecraft mc = Minecraft.getInstance();

      if (mc.level == null || mc.player == null) {
        return;
      }

      boolean hasGps = FindItem.findItem(player.getInventory(), Items.SPYGLASS);

      XaeroMinimap.INSTANCE.getSettings().setOptionValue(ModOptions.MINIMAP, hasGps);
    }
  }
}
