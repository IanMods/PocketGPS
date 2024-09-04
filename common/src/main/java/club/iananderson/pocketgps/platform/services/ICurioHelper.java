package club.iananderson.pocketgps.platform.services;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public interface ICurioHelper {

  /**
   * @param player The player whose Curios/Trinket inventory will be searched.
   * @param item   The item that is being searched for.
   * @return The int for the Curios/Trinket inventory location
   */
  public boolean findCurios(Player player, Item item);
}
