package club.iananderson.pocketgps.registry;

import club.iananderson.pocketgps.items.BaseChargeableGps;
import club.iananderson.pocketgps.util.ItemUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CommonRegistration {
  public static <T extends Item> RegSupplier<T> registerItem(ResourceLocation name, Supplier<T> item) {
    return RegistryHelper.register(name, item, Registries.ITEM);
  }

  public static Collection<ItemStack> addPoweredItem(Item item, boolean includeUncharged) {
    ItemStack uncharged = new ItemStack(item);
    ItemStack charged = new ItemStack(item);
    BaseChargeableGps gps = (BaseChargeableGps) item;

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
