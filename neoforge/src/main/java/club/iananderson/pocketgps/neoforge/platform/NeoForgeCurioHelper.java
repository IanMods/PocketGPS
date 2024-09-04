package club.iananderson.pocketgps.neoforge.platform;

import club.iananderson.pocketgps.platform.services.ICurioHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class NeoForgeCurioHelper implements ICurioHelper {
  @Override
  public boolean findCurios(Player player, Item item) {
    return false;
  }
}
