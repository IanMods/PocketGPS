package club.iananderson.pocketgps.forge.impl.accessories.item;

import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.item.ItemStack;

public class AccessoriesGps implements Accessory {
  public AccessoriesGps() {
  }

  @Override
  public void tick(ItemStack stack, SlotReference reference) {
  }

  @Override
  public boolean canEquipFromUse(ItemStack stack) {
    return true;
  }
}