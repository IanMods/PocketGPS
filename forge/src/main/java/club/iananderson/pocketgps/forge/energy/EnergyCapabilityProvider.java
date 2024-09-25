package club.iananderson.pocketgps.forge.energy;

import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class EnergyCapabilityProvider implements ICapabilityProvider {
  private final LazyOptional<IEnergyStorage> capability;

  public EnergyCapabilityProvider(ItemStack stack, int energyCapacity) {
    this.capability = LazyOptional.of(() -> new ItemEnergyStorage(stack, energyCapacity));
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction dir) {
    if (cap == ForgeCapabilities.ENERGY) {
      return capability.cast();
    }

    return LazyOptional.empty();
  }
}
