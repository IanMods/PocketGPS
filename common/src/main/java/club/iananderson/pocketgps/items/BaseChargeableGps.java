package club.iananderson.pocketgps.items;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.items.components.ItemEnergy;
import club.iananderson.pocketgps.platform.Services;
import club.iananderson.pocketgps.util.ItemUtil;
import club.iananderson.pocketgps.util.NBTUtil;
import club.iananderson.pocketgps.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class BaseChargeableGps extends BaseGps implements ItemEnergyStorage {
  protected int energyStored;
  protected int capacity;
  protected int maxReceive;
  protected int maxExtract;

  public BaseChargeableGps() {
    this(PocketGps.gpsEnergyCapacity(), PocketGps.gpsMaxInput(), PocketGps.gpsMaxOutput(), 0);
  }

  public BaseChargeableGps(int capacity, int maxReceive, int maxExtract, int energy) {
    super();
    this.capacity = capacity;
    this.maxReceive = maxReceive;
    this.maxExtract = maxExtract;
    this.energyStored = Math.max(0, Math.min(capacity, energy));
  }

  public static int clamp(int min, int value, int max) {
    if (value < min) {
      return min;
    }
    return Math.min(value, max);
  }

  @Override
  public void onCraftedBy(ItemStack itemStack, Level level, Player player) {
    if (PocketGps.gpsNeedPower()) {
      NBTUtil.setInt(itemStack, ItemEnergy.ENERGY, 0);
    }

    super.onCraftedBy(itemStack, level, player);
  }

  public void setEnergyStored(ItemStack energyStorage, int value) {
    NBTUtil.setInt(energyStorage, ItemEnergy.ENERGY, clamp(value, 0, getCapacity()));
  }

  public int getEnergyCost() {
    return PocketGps.gpsEnergyCost();
  }

  @Override
  public int receiveEnergy(ItemStack energyStorage, int toReceive, boolean simulate) {
    if (!canReceive() || toReceive <= 0) {
      return 0;
    }
    int energyStored = getEnergyStored(energyStorage);
    int energyReceived = Math.min(getCapacity() - energyStored, Math.min(this.maxReceive, toReceive));
    if (!simulate) {
      setEnergyStored(energyStorage, energyStored + energyReceived);
    }
    return energyReceived;
  }

  @Override
  public void extractEnergy(ItemStack energyStorage, int toExtract, boolean simulate) {
    if (!canExtract() || toExtract <= 0) {
      return;
    }

    int energyExtracted = Math.min(this.energyStored, Math.min(this.maxExtract, toExtract));
    if (!simulate) {
      this.energyStored -= energyExtracted;
    }
  }

  @Override
  public int getEnergyStored(ItemStack energyStorage) {
    return NBTUtil.getInt(energyStorage, ItemEnergy.ENERGY);
  }

  public int getCapacity() {
    return this.capacity;
  }

  @Override
  public boolean canExtract() {
    return this.maxExtract > 0;
  }

  @Override
  public boolean canReceive() {
    return this.maxReceive > 0;
  }

  // Decimal
  public float getEnergyPercentage(ItemStack energyStorage) {
    if (!PocketGps.gpsNeedPower()) {
      return 1;
    }
    else {
      float energyStored = getEnergyStored(energyStorage);

      return (energyStored / getCapacity());
    }
  }

  public String getStoredEnergyText(ItemStack energyStorage) {
    float storedEnergy = getEnergyStored(energyStorage);

    if (storedEnergy < 1000 || Screen.hasShiftDown()) {
      return TextUtil.commaFormat.format(storedEnergy);
    }
    else {
      return TextUtil.kFormat.format(storedEnergy / 1000);
    }
  }

  public String getEnergyCapacityText() {
    if (getCapacity() < 1000 || Screen.hasShiftDown()) {
      return TextUtil.commaFormat.format(getCapacity());
    }
    else {
      return TextUtil.kFormat.format(getCapacity() / 1000);
    }
  }

  public String getPercentageText(ItemStack energyStorage) {
    float percentage = getEnergyPercentage(energyStorage);

    return TextUtil.percentFormat.format(percentage * 100);
  }

  public void useGPS(Player player, ItemStack energyStorage, int cost) {
    extractEnergy(energyStorage, cost, false);
  }

  @Override
  public boolean isPowerBarVisible(ItemStack energyStorage) {
    return PocketGps.gpsNeedPower() && NBTUtil.getInt(energyStorage, ItemEnergy.ENERGY) < getCapacity();
  }

  @Override
  public int getPowerBarColor(ItemStack stack) {
    return Mth.hsvToRgb(Math.max(0.0F, getEnergyPercentage(stack)) / 3.0F, 1.0F, 1.0F);
  }

  @Override
  public int getPowerBarWidth(ItemStack stack) {
    return Math.round((getEnergyPercentage(stack) * 13.0F));
  }

  public List<Component> energyTooltips(ItemStack energyStorage) {
    List<Component> energyTooltips = new ArrayList<>();

    String storedEnergy = getStoredEnergyText(energyStorage);
    String energyCapacity = getEnergyCapacityText();
    String energyUnit = PocketGps.energyUnit().getDisplayName();
    String percentageText = getPercentageText(energyStorage);

    if (Services.PLATFORM.getPlatformName().equals("Fabric")) {
      energyUnit = "E";
    }

    energyTooltips.add(
        Component.translatable("item.pocketgps.gps.tooltip.energy.stored", storedEnergy, energyCapacity, energyUnit)
            .withStyle(ChatFormatting.GOLD));

    if (Screen.hasShiftDown()) {
      energyTooltips.add(Component.translatable("item.pocketgps.gps.tooltip.energy.percent", percentageText)
                             .withStyle(ChatFormatting.DARK_GRAY));
    }

    return energyTooltips;
  }

  @Override
  public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
      @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
    super.appendHoverText(stack, context, tooltip, flag);
    if (PocketGps.gpsNeedPower()) {
      tooltip.addAll(energyTooltips(stack));
    }
  }

  public float timeRemaining(ItemStack energyStorage) {
    int energyStored = getEnergyStored(energyStorage);
    int energyCost = getEnergyCost();
    return (float) energyStored / (energyCost * 20);
  }

  public void debug(ItemStack energyStorage, Player player, double distance, int energyCost) {
    String storedEnergy = getStoredEnergyText(energyStorage);
    String energyCapacity = getEnergyCapacityText();
    String energyUnit = PocketGps.energyUnit().getDisplayName();

    MutableComponent storedEnergyText = Component.translatable("item.pocketgps.gps.tooltip.energy.stored", storedEnergy,
                                                               energyCapacity, energyUnit)
        .withStyle(ChatFormatting.GOLD);

    Component message = storedEnergyText.append(
            " | Walk Time: " + (int) timeRemaining(energyStorage) / 60 + " " + "minutes" + " Cost: " + energyCost)
        .withStyle(ChatFormatting.GREEN);

    storedEnergyText.append(" | Distance: " + TextUtil.thousandths.format(distance));

    player.displayClientMessage(message, true);
  }

  @Override
  public void inventoryTick(@NotNull ItemStack energyStorage, @NotNull Level level, @NotNull Entity entity, int slot,
      boolean selected) {
    if (entity instanceof Player player && !player.isSpectator()) {
      Vec3 deltaMovement = player.getDeltaMovement();
      double deltaX = deltaMovement.x;
      double deltaZ = deltaMovement.z;
      double distance = Math.abs(deltaX) + Math.abs(deltaZ);
      float energyCost = getEnergyCost();

      if (distance > 0.001 && ItemUtil.isGpsOn(energyStorage)) {
        if (player.isCrouching()) {
          energyCost *= 0.5F;
        }
        if (player.isSprinting()) {
          energyCost *= 1.5F;
        }
        useGPS(player, energyStorage, (int) energyCost);

//        if (player.isHolding(energyStorage.getItem()) && player.isCreative()) {
//          debug(energyStorage, player, distance, Math.round(energyCost));
//        }
      }
    }
    super.inventoryTick(energyStorage, level, entity, slot, selected);
  }
}
