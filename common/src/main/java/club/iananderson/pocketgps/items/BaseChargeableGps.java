package club.iananderson.pocketgps.items;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.config.EnergyUnit;
import club.iananderson.pocketgps.config.PocketGpsConfig;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class BaseChargeableGps extends BaseGps {
  private final int capacity;
  private final int maxInput;
  private final int maxOutput;
  private final int cost;

  public BaseChargeableGps(int capacity, int maxInput, int maxOutput, int cost) {
    super();
    this.capacity = capacity;
    this.maxInput = maxInput;
    this.maxOutput = maxOutput;
    this.cost = cost;
  }

  public int getMaxEnergyCapacity(){
    return capacity;
  }

  public int getEnergyMaxInput(){
   return maxInput;
  }

  public int getEnergyMaxOutput(){
    return maxOutput;
  }

  public int getEnergyCost(){
    return cost;
  }

  public abstract int getEnergyStored(ItemStack stack);

  // Decimal
  public float getEnergyPercentage(ItemStack stack) {
    if (!PocketGps.gpsNeedPower()) {
      return 1;
    } else {
      return ((float) getEnergyStored(stack) / (float) getMaxEnergyCapacity());
    }
  }

  @Override
  public boolean isBarVisible(ItemStack stack) {
    return (getEnergyStored(stack) < getMaxEnergyCapacity());
  }

  @Override
  public int getBarColor(ItemStack stack) {
    return 16744454;
  }

  @Override
  public int getBarWidth(ItemStack stack) {
    return Math.round((getEnergyPercentage(stack) * 13.0F));
  }

  public List<Component> energyComponents(ItemStack stack) {
    List<Component> energyTooltips = new ArrayList<>();
    DecimalFormat commaFormat = new DecimalFormat("#,###");
    DecimalFormat kFormat = new DecimalFormat("###.0k");
    DecimalFormat percentFormat = new DecimalFormat("###");

    float storedEnergy = getEnergyStored(stack);

    String simpleStoredEnergy;
    String simpleMaxEnergy = kFormat.format(getMaxEnergyCapacity() / 1000);
    String percentageText = percentFormat.format(getEnergyPercentage(stack) * 100);
    String expandedStoredEnergy = commaFormat.format(storedEnergy);
    String expandedMaxEnergy = commaFormat.format(getMaxEnergyCapacity());

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

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);

    tooltip.add(Component.literal(""));

    tooltip.addAll(energyComponents(stack));

//    stack.getCapability(ForgeCapabilities.ENERGY, null)
//        .ifPresent(energy -> tooltip.addAll(energyComponents(stack, energy.getEnergyStored(),
//                                                             energy.getMaxEnergyStored())));
  }

  public float timeRemaining(ItemStack stack) {
    return (float) getEnergyStored(stack) / (getEnergyCost() * 20);
  }

  public void debug(ItemStack stack, Player player) {
    DecimalFormat commaFormat = new DecimalFormat("#,###");
    DecimalFormat kFormat = new DecimalFormat("###.0k");
    DecimalFormat percentFormat = new DecimalFormat("###");
    EnergyUnit unit = PocketGpsConfig.getEnergyUnit();
    int deltaX = (int) (player.getDeltaMovement().x() * 100F);
    int deltaZ = (int) (player.getDeltaMovement().z() * 100F);

    float storedEnergy = getEnergyStored(stack);

    String simpleStoredEnergy;
    String simpleMaxEnergy = kFormat.format(getMaxEnergyCapacity() / 1000);

    if (storedEnergy < 1000) {
      simpleStoredEnergy = commaFormat.format(storedEnergy);
    } else {
      simpleStoredEnergy = kFormat.format(storedEnergy / 1000);
    }

    MutableComponent storedEnergyText = Component.translatable("item.pocketgps.gps.tooltip.energy.stored",
                                                            simpleStoredEnergy, simpleMaxEnergy, unit.getDisplayName())
        .withStyle(ChatFormatting.GOLD);

    Component message = storedEnergyText.append(" | Walk Time: " + (int) timeRemaining(stack) / 60 + " minutes")
        .withStyle(ChatFormatting.GREEN);

    storedEnergyText.append(" | deltaX: " + deltaX + " deltaZ: " + deltaZ);

    player.displayClientMessage(message, true);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
    ItemStack stack = player.getItemInHand(hand);
    if (player.isCreative()) {
      debug(stack, player);
      return InteractionResultHolder.success(stack);
    } else {
      return InteractionResultHolder.fail(stack);
    }
  }
}
