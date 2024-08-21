package club.iananderson.pocketgps.neoforge.impl.curios.item;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosGps implements ICurioItem {
  public CuriosGps() {
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }
}