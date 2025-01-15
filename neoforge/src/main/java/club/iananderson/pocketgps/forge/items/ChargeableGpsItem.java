package club.iananderson.pocketgps.forge.items;

import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.forge.energy.EnergyStorageImpl;
import club.iananderson.pocketgps.forge.registry.ForgeRegistration;
import club.iananderson.pocketgps.items.BaseChargeableGps;
import club.iananderson.pocketgps.registry.CommonRegistration;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public class ChargeableGpsItem extends BaseChargeableGps implements ItemEnergyStorage {
  public ChargeableGpsItem(Properties properties) {
    super(properties);
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
    ItemEnergyStorage container = this;
    return new ICapabilityProvider() {
      @Nonnull
      @Override
      public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
          return LazyOptional.of(() -> new EnergyStorageImpl(stack, container)).cast();
        }
        return LazyOptional.empty();
      }
    };
  }

  @Override
  public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
    if (!allowedIn(group)) {
      return;
    }
    CommonRegistration.addPoweredItem(this, stacks, true);
  }
}
