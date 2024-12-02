package club.iananderson.pocketgps.fabric.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.util.ItemUtil;
import club.iananderson.pocketgps.util.NBTUtil;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.wispforest.accessories.api.AccessoriesCapability;
import java.util.Optional;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryEvent {
  private static boolean findCurio(Player player, Item item) {
    if (player == null) {
      return false;
    }

    if (PocketGps.curiosLoaded()) {
      Optional<TrinketComponent> trinketInventory = TrinketsApi.getTrinketComponent(player);
      if (trinketInventory.isPresent()) {
        if (trinketInventory.get().isEquipped(item)) {
          ItemStack foundTrinketGPS = trinketInventory.get().getEquipped(item).get(0).getB();
          return NBTUtil.getInt(foundTrinketGPS, ItemEnergyStorage.ENERGY_TAG) > 0;
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

  public static void register() {
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      LocalPlayer player = client.player;

      if (player != null) {
        boolean hasGpsInv = ItemUtil.hasGps(player, FabricRegistration.POCKET_GPS);
        boolean hasGpsCurio = findCurio(player, FabricRegistration.POCKET_GPS);

        CurrentMinimap.displayMinimap(player, (hasGpsInv || hasGpsCurio));
      }
    });
  }
}