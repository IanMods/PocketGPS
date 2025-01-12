package club.iananderson.pocketgps.impl.curios;

import club.iananderson.pocketgps.PocketGps;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class CuriosCompat {
  public static ItemStack getGpsInCurio(Player player) {
    List<SlotResult> found = CuriosApi.getCuriosHelper().findCurios(player, PocketGps.GPS.get());
    if (!found.isEmpty()) {
      return found.get(0).stack();
    }
    return ItemStack.EMPTY;
  }
}
