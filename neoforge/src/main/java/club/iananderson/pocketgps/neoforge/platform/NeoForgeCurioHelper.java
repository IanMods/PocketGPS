package club.iananderson.pocketgps.neoforge.platform;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.platform.services.ICurioHelper;
import io.wispforest.accessories.api.AccessoriesCapability;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class NeoForgeCurioHelper implements ICurioHelper {
  @Override
  public boolean findCurios(Player player, Item item) {
    Minecraft mc = Minecraft.getInstance();
    boolean curioEquipped = false;

    if (mc.level == null || mc.player == null || item == null) {
      return false;
    }

    if (PocketGps.curiosLoaded()) {
      Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
      if (curiosInventory.isPresent()) {
        curioEquipped = curiosInventory.get().findFirstCurio(item).isPresent();
      }
    } else if (PocketGps.accessoriesLoaded()) {
      Optional<AccessoriesCapability> accessoriesContainer = AccessoriesCapability.getOptionally(player);
      if (accessoriesContainer.isPresent()) {
        curioEquipped = !accessoriesContainer.get().getEquipped(item).isEmpty();
      }
    }
    return curioEquipped;
  }
}
