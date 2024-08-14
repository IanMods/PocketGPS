package club.iananderson.pocketgps.fabric.client;

import net.minecraft.client.Minecraft;

public class XaerosMinimap {
  public void hideMinimap() {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return;
    }

//    if (CurrentMinimap.xaeroLoaded() && FindItem.findItem(mc.player, SpyglassItem)){
//      XaeroMinimap.INSTANCE.getSettings().setOptionValue(ModOptions.MINIMAP, false);
//    }
  }
}
