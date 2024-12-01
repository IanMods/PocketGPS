package club.iananderson.pocketgps.forge.energy;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnergyCapabilityProvider implements ICapabilityProvider {
  private ItemStack stack;
  private int capacity;
  private int maxReceive;
  private int maxExtract;
  private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new ItemEnergyStorage(stack, capacity,
                                                                                                maxReceive, maxExtract));

  public EnergyCapabilityProvider(ItemStack stack, int capacity, int maxReceive, int maxExtract) {
    this.stack = stack;
    this.capacity = capacity;
    this.maxReceive = maxReceive;
    this.maxExtract = maxExtract;
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    return cap == ForgeCapabilities.ENERGY ? capability.cast() : LazyOptional.empty();
  }
}
