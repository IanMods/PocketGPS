package club.iananderson.pocketgps.neoforge.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@EventBusSubscriber(modid = PocketGps.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class InventoryEvent {
  private static boolean findCurio(Player player, Item item) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    int slot = 0;

    if (PocketGps.curiosLoaded()) {
      ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).get();
      if (curiosInventory.findFirstCurio(item).isPresent()) {
        slot += 1;
      }
    }
    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      AccessoriesContainer accessoriesContainer = AccessoriesCapability.get(player).getContainers().get("gps_slot");
      slot += accessoriesContainer.getAccessories().countItem(item);
    }
    return slot > 0;
  }

  @SubscribeEvent
  public static void onPlayerTickEvent(PlayerTickEvent.Post event) {
    if (event.getEntity() instanceof LocalPlayer player) {
      boolean hasGpsInv = CurrentMinimap.hasGps(player, NeoForgeRegistration.POCKET_GPS.get());
      boolean hasGpsCurio = findCurio(player, NeoForgeRegistration.POCKET_GPS.get());

      CurrentMinimap.displayMinimap(hasGpsInv || hasGpsCurio);
    }
  }
}
