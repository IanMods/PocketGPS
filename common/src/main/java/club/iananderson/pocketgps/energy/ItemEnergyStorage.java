package club.iananderson.pocketgps.energy;

import net.minecraft.world.item.ItemStack;

public interface ItemEnergyStorage {
  int receiveEnergy(ItemStack energyStorage, int toReceive, boolean simulate);

  void extractEnergy(ItemStack energyStorage, int toExtract, boolean simulate);

  int getEnergyStored(ItemStack energyStorage);

  boolean canExtract();

  boolean canReceive();

  boolean isPowerBarVisible(ItemStack stack);

  int getPowerBarWidth(ItemStack stack);

  int getPowerBarColor(ItemStack stack);
}