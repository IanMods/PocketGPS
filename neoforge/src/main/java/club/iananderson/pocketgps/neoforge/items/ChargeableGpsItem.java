package club.iananderson.pocketgps.neoforge.items;

import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.items.BaseChargeableGps;
import club.iananderson.pocketgps.neoforge.energy.EnergyStorageImpl;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ChargeableGpsItem extends BaseChargeableGps implements ItemEnergyStorage {
  public ChargeableGpsItem() {
    super();
  }

  @Override
  public long getEnergyCapacity(ItemStack stack) {
    return this.getCapacity();
  }

  @Override
  public long getEnergyMaxInput(ItemStack stack) {
    return this.getEnergyReceive();
  }

  @Override
  public long getEnergyMaxOutput(ItemStack stack) {
    return this.getEnergyExtract();
  }

  @Override
  public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
    return false;
  }
}
