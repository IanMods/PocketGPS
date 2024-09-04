package club.iananderson.pocketgps.fabric.platform;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.platform.services.ICurioHelper;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricCurioHelper implements ICurioHelper {
  @Override
  public boolean findCurios(Player player, Item item) {
    boolean curioEquipped = false;

    if (player == null || item == null) {
      return false;
    }

    if (PocketGps.curiosLoaded()) {
      Optional<TrinketComponent> trinketsInv = TrinketsApi.getTrinketComponent(player);

      if (trinketsInv.isPresent()) {
        curioEquipped = trinketsInv.get().isEquipped(item);
      }
    }

    return curioEquipped;
  }
}
