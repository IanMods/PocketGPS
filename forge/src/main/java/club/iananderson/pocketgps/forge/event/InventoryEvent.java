package club.iananderson.pocketgps.forge.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.util.ItemUtil;
import club.iananderson.pocketgps.util.NBTUtil;
import io.wispforest.accessories.api.AccessoriesCapability;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InventoryEvent {
  private static boolean findCurio(Player player, Item item) {
    if (player == null) {
      return false;
    }

    if (PocketGps.curiosLoaded()) {
      Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player).resolve();
      if (curiosInventory.isPresent()) {
        if (curiosInventory.get().findFirstCurio(item).isPresent()) {
          ItemStack foundCurioGPS = curiosInventory.get().findFirstCurio(item).get().stack();
          return NBTUtil.getInt(foundCurioGPS, ItemEnergyStorage.ENERGY_TAG) > 0;
        }
      }
    }
    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      Optional<AccessoriesCapability> accessoriesInventory = AccessoriesCapability.getOptionally(player);
      if (accessoriesInventory.isPresent()) {
        if (accessoriesInventory.get().isEquipped(item)) {
          ItemStack foundAccessoriesGPS = accessoriesInventory.get().getEquipped(item).get(0).stack();
          return NBTUtil.getInt(foundAccessoriesGPS, ItemEnergyStorage.ENERGY_TAG) > 0;
        }
      }
    }
    return false;
  }

  @SubscribeEvent
  public static void onPlayerTickEvent(PlayerTickEvent event) {
    if (event.side == LogicalSide.CLIENT) {
      Player player = event.player;

      if (player != null) {
        boolean hasGpsInv = ItemUtil.hasGps(player, ForgeRegistration.POCKET_GPS.get());
        boolean hasGpsCurio = findCurio(player, ForgeRegistration.POCKET_GPS.get());

        CurrentMinimap.displayMinimap(player, (hasGpsInv || hasGpsCurio));
      }
    }
  }
}