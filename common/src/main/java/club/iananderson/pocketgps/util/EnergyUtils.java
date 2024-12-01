package club.iananderson.pocketgps.util;

import club.iananderson.pocketgps.items.BaseChargeableGps;
import net.minecraft.world.item.ItemStack;

public class EnergyUtils {
  public static int getEnergyStored(ItemStack gps) {
    if (gps.getItem() instanceof BaseChargeableGps baseChargeableGps) {
      baseChargeableGps.getEnergyStored(gps);
    }
    return 0;
  }

  public static int getEnergyCost(ItemStack gadget) {
    if (gadget.getItem() instanceof BaseChargeableGps baseChargeableGps) {
      return baseChargeableGps.getEnergyCost();
    }
    return -1;
  }

  public static boolean hasEnoughEnergy(ItemStack gps) {
    int energyStored = getEnergyStored(gps);
    int energyCost = getEnergyCost(gps);
    return energyCost <= energyStored;
  }

  public static boolean hasEnoughEnergy(ItemStack gps, int cost) {
    int energyStored = getEnergyStored(gps);
    return cost <= energyStored;
  }
}
