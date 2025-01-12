package club.iananderson.pocketgps.fabric.items;

import club.iananderson.pocketgps.fabric.registry.FabricRegistration;
import club.iananderson.pocketgps.items.BaseChargeableGps;
import club.iananderson.pocketgps.registry.CommonRegistration;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import team.reborn.energy.api.base.SimpleBatteryItem;

public class ChargeableGpsItem extends BaseChargeableGps implements SimpleBatteryItem, FabricItem {
  public ChargeableGpsItem() {
    super(new Item.Properties().tab(FabricRegistration.TAB).stacksTo(1));
  }

  @Override
  public long getEnergyCapacity() {
    return this.getCapacity();
  }

  @Override
  public long getEnergyMaxInput() {
    return this.getEnergyReceive();
  }

  @Override
  public long getEnergyMaxOutput() {
    return this.getEnergyExtract();
  }

  @Override
  public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
    return false;
  }

  @Override
  public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
    if (!allowedIn(group)) {
      return;
    }
    CommonRegistration.addPoweredItem(this, stacks, true);
  }
}
