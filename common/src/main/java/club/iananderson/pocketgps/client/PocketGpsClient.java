package club.iananderson.pocketgps.client;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.impl.accessories.AccessoriesCompat;
import club.iananderson.pocketgps.impl.curios.CuriosCompat;
import club.iananderson.pocketgps.impl.trinkets.TrinketsCompat;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.util.ItemUtil;
import club.iananderson.pocketgps.util.NBTUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PocketGpsClient {
  private static ItemStack currentActiveGps = ItemStack.EMPTY;
  private static boolean isDrawingMap;
  private static boolean initializedMapState;

  @NotNull
  private static ItemStack getGpsFromInventory(Inventory inventory) {
    int max = inventory.getContainerSize();
    for (int i = 0; i < max; ++i) {
      ItemStack itemstack = inventory.getItem(i);
      if (itemstack.is(PocketGps.GPS.get())) {
        return itemstack;
      }
    }
    return ItemStack.EMPTY;
  }

  @NotNull
  public static ItemStack getGpsFromPlayer(Player player) {
    Inventory inv = player.getInventory();
    // first scan hand
    ItemStack mainHandItem = player.getMainHandItem();
    ItemStack offhandItem = player.getOffhandItem();
    ItemStack inventoryItem = getGpsFromInventory(inv);

    if (mainHandItem.is(PocketGps.GPS.get())) {
      return mainHandItem;
    }
    // then offhand
    if (offhandItem.is(PocketGps.GPS.get())) {
      return offhandItem;
    }
    //then Accessories
    if (PocketGps.accessoriesLoaded()) {
      ItemStack accessoryItem = AccessoriesCompat.getGpsInAccessory(player);
      if (!accessoryItem.isEmpty()) {
        return accessoryItem;
      }
    }
    //then Curio
    if (PocketGps.curiosLoaded()) {
      ItemStack curioItem = CuriosCompat.getGpsInCurio(player);
      if (!curioItem.isEmpty()) {
        return curioItem;
      }
    }
    //Then Trinkets
    if (PocketGps.trinketsLoaded()) {
      ItemStack trinketItem = TrinketsCompat.getGpsInTrinket(player);
      if (!trinketItem.isEmpty()) {
        return trinketItem;
      }
    }
    return inventoryItem;
  }

  public static void cachePlayerState(Player player) {
//    if (player != Minecraft.getInstance().player) {
//      return;
//    }

    currentActiveGps = getGpsFromPlayer(player);
    boolean validGps = isValidGPS(currentActiveGps);

    if(!initializedMapState){
      setIsDrawingMap(!validGps);
      setInitializedMapState(true);
    }

    if (getCurrentActiveGps().isEmpty()) {
      CurrentMinimap.removeMinimap(player);
    }
    else if (!getCurrentActiveGps().isEmpty()) {
      if (!validGps) {
        CurrentMinimap.removeMinimap(player);
      }
      else {
        CurrentMinimap.displayMinimap(player);
      }
    }
  }

  public static ItemStack getCurrentActiveGps() {
    return currentActiveGps;
  }

  public static boolean isValidGPS(ItemStack gps) {
    boolean hasPower = NBTUtil.getInt(gps, PocketGps.ENERGY_TAG) > 0;
    boolean gpsOn = ItemUtil.isGpsOn(getCurrentActiveGps());

    if (PocketGps.gpsNeedPower()) {
      return gpsOn && hasPower;
    }
    else {
      return gpsOn;
    }
  }

  public static boolean isDrawingMap() {
    return isDrawingMap;
  }

  public static void setIsDrawingMap(boolean state) {
    PocketGpsClient.isDrawingMap = state;
  }

  public static boolean isInitializedMapState() {
    return initializedMapState;
  }

  public static void setInitializedMapState(boolean state) {
    PocketGpsClient.initializedMapState = state;
  }
}
