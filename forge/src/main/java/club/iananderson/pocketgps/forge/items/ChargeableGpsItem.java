package club.iananderson.pocketgps.forge.items;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.energy.EnergyCapabilityProvider;
import club.iananderson.pocketgps.items.BaseChargeableGps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class ChargeableGpsItem extends BaseChargeableGps {
  public ChargeableGpsItem(int capacity, int maxInput, int maxOutput, int cost) {
    super(capacity, maxInput, maxOutput, cost);
  }

  @Override
  public int getMaxDamage(ItemStack stack) {
    return this.getMaxEnergyCapacity();
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {

    return new EnergyCapabilityProvider(stack, this.getMaxEnergyCapacity(), this.getEnergyMaxInput(), this.getEnergyMaxOutput());
  }

  @Override
  public int getEnergyStored(ItemStack stack){
    return stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null).getEnergyStored();
  }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
    if (!level.isClientSide) {
      if (entity instanceof ServerPlayer serverPlayer) {
        int deltaX = (int) (serverPlayer.getDeltaMovement().x() * 1000);
        int deltaZ = (int) (serverPlayer.getDeltaMovement().z() * 1000);
        IEnergyStorage energy = stack.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);

        if (deltaX != 0 || deltaZ != 0) {
          energy.extractEnergy(this.getEnergyCost(), false);

          if (serverPlayer.isHolding(stack.getItem()) && serverPlayer.isCreative()) {
            debug(stack, serverPlayer);
          }
        }
      }
    }
  }
}
