package club.iananderson.pocketgps.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.items.BaseChargeableGps;
import club.iananderson.pocketgps.util.ItemUtil;
import club.iananderson.pocketgps.util.NBTUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CommonRegistration {
  public static void registerItem(Item item, String name) {

    Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(PocketGps.MOD_ID, name), item);
  }

  public static Collection<ItemStack> addPoweredItem(Item item, boolean includeUncharged) {
    ItemStack uncharged = new ItemStack(item);
    ItemStack charged = new ItemStack(item);
    BaseChargeableGps gps= (BaseChargeableGps)item;

    ItemUtil.initGpsState(uncharged);
    ItemUtil.initGpsState(charged);

    gps.setEnergyStored(uncharged, 0);
    gps.setEnergyStored(charged, gps.getCapacity());

    if (includeUncharged) {
      return new ArrayList<>(List.of(uncharged, charged));
    } else {
      return new ArrayList<>(List.of(charged));
    }
  }
}
