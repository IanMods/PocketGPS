package club.iananderson.pocketgps.fabric.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import com.google.common.collect.ImmutableList;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.List;
import java.util.Optional;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InventoryEvent {
  //Todo -- This needs to check for charged gps if it is enabled in the config
  // Also need to have it tick while in the curio slot
  // Setup for accessories as well

  private static boolean findCurio(Player player, Item item) {
    if (player == null) {
      return false;
    }

    if (PocketGps.curiosLoaded()) {
      Optional<TrinketComponent> trinketInventory = TrinketsApi.getTrinketComponent(player);
      if (trinketInventory.isPresent()) {
        return trinketInventory.get().isEquipped(item);
      }
    }
    return false;
  }

  @Nullable
  public static ItemStack findCharged(Player player) {
    Inventory inv = player.getInventory();
    List<NonNullList<ItemStack>> compartments = ImmutableList.of(inv.items, inv.armor, inv.offhand);

    if (PocketGps.curiosLoaded()) {
      Optional<TrinketComponent> trinketInventory = TrinketsApi.getTrinketComponent(player);
      trinketInventory.ifPresent(trinketComponent -> trinketComponent.forEach(
          (slotReference, stack) -> compartments.add(NonNullList.of(stack))));
    }

    for (NonNullList<ItemStack> compartment : compartments) {
      for (ItemStack invItemStack : compartment) {
        if (!invItemStack.isEmpty() && ItemStack.isSameItem(invItemStack,
                                                            FabricRegistration.POCKET_GPS.getDefaultInstance())) {

          return invItemStack;
        }
      }
    }
    return null;
  }

  public static void register() {
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      LocalPlayer player = client.player;

      if (player != null) {

        @Nullable ItemStack powerGps = findCharged(player);
        boolean hasGpsInv = CurrentMinimap.hasGps(player, FabricRegistration.POCKET_GPS);
        boolean hasGpsCurio = findCurio(player, FabricRegistration.POCKET_GPS);

        CurrentMinimap.displayMinimap(player, (hasGpsInv || hasGpsCurio) && (!PocketGps.gpsNeedPower()
            || FabricRegistration.POCKET_GPS.getEnergyPercentage(powerGps) > 0F));
      }
    });
  }
}