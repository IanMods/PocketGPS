package club.iananderson.pocketgps.forge.energy;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

public class ItemEnergyStorage extends EnergyStorage {
  private static final String ENERGY_TAG = "Energy";

  private ItemStack stack;

  public ItemEnergyStorage(ItemStack stack, int capacity, int maxReceive, int maxExtract) {
    super(capacity, maxReceive, maxExtract);

    this.stack = stack;
    this.energy = stack.hasTag() && stack .getTag().contains(ENERGY_TAG) ? stack.getTag().getInt(ENERGY_TAG) : 0;
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    int extractedEnergy = super.extractEnergy(maxExtract, simulate);

    if (extractedEnergy != 0) {
      onEnergyChanged();
    }

    return extractedEnergy;
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    int receivedEnergy = super.receiveEnergy(maxExtract, simulate);

    if (receivedEnergy != 0) {
      onEnergyChanged();
    }

    return receivedEnergy;
  }

  public void onEnergyChanged() {
    stack.getOrCreateTag().putInt(ENERGY_TAG, this.energy);
  }
}