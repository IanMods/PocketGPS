package club.iananderson.pocketgps.forge.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import io.wispforest.accessories.api.AccessoriesCapability;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InventoryEvent {
//  private static boolean findCurio(Player player, Item item) {
//    if (player == null) {
//      return false;
//    }
//
//    if (PocketGps.curiosLoaded()) {
//      Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player).resolve();
//      if (curiosInventory.isPresent()) {
//        return curiosInventory.get().isEquipped(item);
//      }
//    }
//
//    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
//      Optional<AccessoriesCapability> accessoriesInventory = AccessoriesCapability.getOptionally(player);
//      if (accessoriesInventory.isPresent()) {
//
//        return accessoriesInventory.get().isEquipped(item);
//      }
//    }
//    return false;
//  }

  @SubscribeEvent
  public static void onPlayerTickEvent(PlayerTickEvent event) {
    if (event.side == LogicalSide.CLIENT) {
      Player player = event.player;

      boolean hasGpsInv = CurrentMinimap.hasGps(player, ForgeRegistration.POCKET_GPS.get());
//      boolean hasGpsCurio = findCurio(player, ForgeRegistration.POCKET_GPS.get());
      boolean hasGpsCurio= false;

      CurrentMinimap.displayMinimap(player, hasGpsInv || hasGpsCurio);
    }
  }
}
