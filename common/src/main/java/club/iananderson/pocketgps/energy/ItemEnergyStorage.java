package club.iananderson.pocketgps.energy;

import net.minecraft.world.item.ItemStack;

public interface ItemEnergyStorage {
  int receiveEnergy(ItemStack energyStorage, int maxReceive, boolean simulate);

  int extractEnergy(ItemStack energyStorage, int maxExtract, boolean simulate);

  int getEnergy(ItemStack energyStorage);

  int getCapacity();
}