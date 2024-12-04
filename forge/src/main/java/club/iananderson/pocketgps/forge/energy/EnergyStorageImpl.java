package club.iananderson.pocketgps.forge.energy;

import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorageImpl implements IEnergyStorage {
  public ItemStack stack;
  public ItemEnergyStorage container;

  public EnergyStorageImpl(ItemStack stack, ItemEnergyStorage container) {
    this.stack = stack;
    this.container = container;
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    return container.receiveEnergy(stack, maxReceive, simulate);
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    return container.extractEnergy(stack, maxExtract, simulate);
  }

  @Override
  public int getEnergyStored() {
    return container.getEnergy(stack);
  }

  @Override
  public int getMaxEnergyStored() {
    return container.getCapacity();
  }

  @Override
  public boolean canExtract() {
    return true;
  }

  @Override
  public boolean canReceive() {
    return true;
  }
}
