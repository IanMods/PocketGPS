package club.iananderson.pocketgps.forge.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.PocketGpsForge;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InventoryEvent {
  private static boolean findCurio(Player player, Item item) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    int slot = 0;

    if (PocketGps.curiosLoaded()) {
      ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).resolve().get();
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
  public static void onPlayerTickEvent(PlayerTickEvent event) {
    if (event.player instanceof LocalPlayer player) {
      boolean hasGpsInv = CurrentMinimap.hasGps(player, PocketGpsForge.POCKET_GPS.get());
      boolean hasGpsCurio = findCurio(player, PocketGpsForge.POCKET_GPS.get());

      CurrentMinimap.displayMinimap(hasGpsInv || hasGpsCurio);
    }
  }
}
