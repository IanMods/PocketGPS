package club.iananderson.pocketgps.neoforge.impl.curios.item;

import club.iananderson.pocketgps.neoforge.registry.NeoForgeRegistration;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosGps implements ICurioItem {
  public CuriosGps() {
  }

  public static void init() {
    CuriosApi.registerCurio(NeoForgeRegistration.POCKET_GPS.get(), new CuriosGps());
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }
}