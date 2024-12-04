package club.iananderson.pocketgps.fabric.items;

import club.iananderson.pocketgps.items.BaseChargeableGps;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.reborn.energy.api.base.SimpleEnergyItem;

public class ChargeableGpsItem extends BaseChargeableGps implements SimpleEnergyItem {
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
