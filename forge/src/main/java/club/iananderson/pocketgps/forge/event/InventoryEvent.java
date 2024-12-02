package club.iananderson.pocketgps.forge.event;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import com.google.common.collect.ImmutableList;
import io.wispforest.accessories.api.AccessoriesCapability;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.Nullable;
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
        return curiosInventory.get().isEquipped(item);
      }
    }

    if (PocketGps.accessoriesLoaded() && !PocketGps.curiosLoaded()) {
      Optional<AccessoriesCapability> accessoriesInventory = AccessoriesCapability.getOptionally(player);
      if (accessoriesInventory.isPresent()) {

        return accessoriesInventory.get().isEquipped(item);
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

  @SubscribeEvent
  public static void onPlayerTickEvent(PlayerTickEvent event) {
    if (event.side == LogicalSide.CLIENT) {
      Player player = event.player;

      if (player != null) {

        @Nullable ItemStack powerGps = findCharged(player);
        boolean hasGpsInv = CurrentMinimap.hasGps(player, FabricRegistration.POCKET_GPS);
        boolean hasGpsCurio = findCurio(player, FabricRegistration.POCKET_GPS);

        CurrentMinimap.displayMinimap(player, (hasGpsInv || hasGpsCurio) && (!PocketGps.gpsNeedPower()
            || ChargeableGpsItem.(powerGps) > 0F));
      }
    });
  }
      boolean hasGpsInv = CurrentMinimap.hasGps(player, ForgeRegistration.POCKET_GPS.get());
      boolean hasGpsCurio = findCurio(player, ForgeRegistration.POCKET_GPS.get());

      CurrentMinimap.displayMinimap(player, hasGpsInv || hasGpsCurio);
    }
  }
}
