package club.iananderson.pocketgps.fabric.items;

import club.iananderson.pocketgps.items.GpsItem;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyItem;

public class ChargeableGpsItem extends GpsItem implements SimpleEnergyItem {
  private int energyCapacity;
  private int maxInput;
  private int maxOutput;
  private int cost;

  public ChargeableGpsItem(Properties properties, int energyCapacity, int maxInput, int maxOutput, int cost) {
    super(properties);
    this.energyCapacity = energyCapacity;
    this.maxInput = maxInput;
    this.maxOutput = maxOutput;
    this.cost = cost;
  }

  @Override
  public long getEnergyCapacity(ItemStack stack) {
    return energyCapacity;
  }

  @Override
  public long getEnergyMaxInput(ItemStack stack) {
    return maxInput;
  }

  @Override
  public long getEnergyMaxOutput(ItemStack stack) {
    return maxOutput;
  }

  // Decimal
  public float getEnergyPercentage(ItemStack stack) {
    return ((float) this.getStoredEnergy(stack) / (float) this.getEnergyCapacity(stack));
  }

  public float timeRemaining(ItemStack stack) {
    return (float) this.getStoredEnergy(stack) / (this.cost * 20);
  }

  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (player.isCreative()) {
      debug(stack, player);
      return InteractionResultHolder.success(stack);
    } else {
      return InteractionResultHolder.fail(stack);
    }
  }

  public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
    if (entity instanceof Player player) {
      if (player.getDeltaMovement().x() != 0 || player.getDeltaMovement().z() != 0) {
        this.tryUseEnergy(stack, this.cost);
        if (player.isHolding(stack.getItem()) && player.isCreative()) {
          debug(stack, player);
        }
        if (this.getStoredEnergy(stack) < this.cost) {
          setStoredEnergy(stack, 0);
        }
      }
    }
  }

  public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
    return false;
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);

    tooltip.add(Component.literal(""));
    tooltip.addAll(energyComponents(stack));
  }

  public List<Component> energyComponents(ItemStack stack) {
    List<Component> energyTooltips = new ArrayList<>();
    DecimalFormat commaFormat = new DecimalFormat("#,###");
    DecimalFormat kFormat = new DecimalFormat("###.0k");
    DecimalFormat percentFormat = new DecimalFormat("###");

    float storedEnergy = this.getStoredEnergy(stack);

    String simpleStoredEnergy;
    String simpleMaxEnergy = kFormat.format(this.getEnergyCapacity(stack) / 1000);
    String percentageText = percentFormat.format(this.getEnergyPercentage(stack) * 100);
    String expandedStoredEnergy = commaFormat.format(storedEnergy);
    String expandedMaxEnergy = commaFormat.format(this.getEnergyCapacity(stack));

    if (storedEnergy < 1000) {
      simpleStoredEnergy = commaFormat.format(storedEnergy);
    } else {
      simpleStoredEnergy = kFormat.format(storedEnergy / 1000);
    }

    if (!Screen.hasShiftDown()) {
      energyTooltips.add(
          Component.translatable("item.pocketgps.gps.tooltip.energy.stored", simpleStoredEnergy, simpleMaxEnergy, "E")
              .withStyle(ChatFormatting.GOLD));
    } else {
      energyTooltips.add(
          Component.translatable("item.pocketgps.gps.tooltip.energy.stored", expandedStoredEnergy, expandedMaxEnergy,
                                 "E").withStyle(ChatFormatting.GOLD));
      energyTooltips.add(Component.translatable("item.pocketgps.gps.tooltip.energy.percent", percentageText)
                             .withStyle(ChatFormatting.DARK_GRAY));
    }

    return energyTooltips;
  }

  public int getBarWidth(ItemStack stack) {
    return Math.round((this.getEnergyPercentage(stack) * 13.0F));

  }

  public boolean isBarVisible(ItemStack stack) {
    return true;
  }

  public int getBarColor(ItemStack stack) {
    return 16744454;
  }

  public void debug(ItemStack stack, Player player) {
    DecimalFormat commaFormat = new DecimalFormat("#,###");
    DecimalFormat kFormat = new DecimalFormat("###.0k");
    DecimalFormat percentFormat = new DecimalFormat("###");

    float storedEnergy = this.getStoredEnergy(stack);

    String simpleStoredEnergy;
    String simpleMaxEnergy = kFormat.format(this.getEnergyCapacity(stack) / 1000);

    if (storedEnergy < 1000) {
      simpleStoredEnergy = commaFormat.format(storedEnergy);
    } else {
      simpleStoredEnergy = kFormat.format(storedEnergy / 1000);
    }

    MutableComponent storgedEnergy = Component.translatable("item.pocketgps.gps.tooltip.energy.stored",
                                                            simpleStoredEnergy, simpleMaxEnergy, "E")
        .withStyle(ChatFormatting.GOLD);

    Component message = storgedEnergy.append(" | Walk Time: " + (int)this.timeRemaining(stack)/60 + " minutes")
        .withStyle(ChatFormatting.GREEN);

    player.displayClientMessage(message, true);
  }
}
