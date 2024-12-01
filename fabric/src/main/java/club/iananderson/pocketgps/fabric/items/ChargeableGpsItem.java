package club.iananderson.pocketgps.fabric.items;

import club.iananderson.pocketgps.items.BaseChargeableGps;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.reborn.energy.api.base.SimpleEnergyItem;

public class ChargeableGpsItem extends BaseChargeableGps implements SimpleEnergyItem {
  public ChargeableGpsItem(int capacity, int maxInput, int maxOutput, int cost) {
    super(capacity, maxInput, maxOutput, cost);
  }

  @Override
  public long getEnergyCapacity(ItemStack stack) {
    return this.getMaxEnergyCapacity();
  }

  @Override
  public long getEnergyMaxInput(ItemStack stack) {
    return this.getEnergyMaxInput();
  }

  @Override
  public long getEnergyMaxOutput(ItemStack stack) {
    return this.getEnergyMaxOutput();
  }

  @Override
  public int getEnergyStored(ItemStack stack) {
    return (int) this.getStoredEnergy(stack);
  }

  @Override
  public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
    return false;
  }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
    if (level.isClientSide) {
      if (entity instanceof Player player) {
        int deltaX = (int) (player.getDeltaMovement().x() * 1000);
        int deltaZ = (int) (player.getDeltaMovement().z() * 1000);

        if (deltaX != 0 || deltaZ != 0) {
          if (this.getEnergyStored(stack) < this.getEnergyCost()) {
            this.setStoredEnergy(stack, 0);
          }

          this.tryUseEnergy(stack, this.getEnergyCost());

          if (player.isHolding(stack.getItem()) && player.isCreative()) {
            debug(stack, player);
          }
        }
      }
    }

    if (!level.isClientSide) {
      if (entity instanceof ServerPlayer serverPlayer) {
        int deltaX = (int) (serverPlayer.getDeltaMovement().x() * 1000);
        int deltaZ = (int) (serverPlayer.getDeltaMovement().z() * 1000);

        if (deltaX != 0 || deltaZ != 0) {
          if (this.getEnergyStored(stack) < this.getEnergyCost()) {
            this.setStoredEnergy(stack, 0);
          }

          this.tryUseEnergy(stack, this.getEnergyCost());

          if (serverPlayer.isHolding(stack.getItem()) && serverPlayer.isCreative()) {
            debug(stack, serverPlayer);
          }
        }
      }
    }
  }
}
