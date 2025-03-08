package club.iananderson.pocketgps.impl.trinkets;

import club.iananderson.pocketgps.PocketGps;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TrinketsCompat {
  public static ItemStack getGpsInTrinket(Player player) {
    Optional<TrinketComponent> trinket = TrinketsApi.getTrinketComponent(player);
    if (trinket.isPresent()) {
      List<Tuple<SlotReference, ItemStack>> found = trinket.get().getEquipped(PocketGps.GPS.get());
      if (!found.isEmpty()) {
        return found.get(0).getB();
      }
    }
    return ItemStack.EMPTY;
  }
}
