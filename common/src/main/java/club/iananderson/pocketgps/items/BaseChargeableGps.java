package club.iananderson.pocketgps.items;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.energy.ItemEnergyStorage;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class BaseChargeableGps extends BaseGps implements ItemEnergyStorage {
  public BaseChargeableGps(Properties properties) {
    super(properties.stacksTo(1));
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
      NBTUtil.setInt(itemStack, PocketGps.ENERGY_TAG, 0);
    }

    super.onCraftedBy(itemStack, level, player);
  }

  public void setEnergyStored(ItemStack energyStorage, int value) {
    NBTUtil.setInt(energyStorage, PocketGps.ENERGY_TAG, clamp(value, 0, getCapacity()));
  }

  public int getEnergyReceive() {
    return PocketGps.gpsMaxInput();
  }

  public int getEnergyExtract() {
    return PocketGps.gpsMaxOutput();
  }

  public int getEnergyCost() {
    return PocketGps.gpsEnergyCost();
  }

  @Override
  public int receiveEnergy(ItemStack energyStorage, int maxReceive, boolean simulate) {
    if (getEnergyReceive() == 0) {
      return 0;
    }
    int energyStored = getEnergy(energyStorage);
    int energyReceived = Math.min(getCapacity() - energyStored, Math.min(getEnergyReceive(), maxReceive));
    if (!simulate) {
      setEnergyStored(energyStorage, energyStored + energyReceived);
    }
    return energyReceived;
  }

  @Override
  public int extractEnergy(ItemStack energyStorage, int maxExtract, boolean simulate) {
    if (getEnergyExtract() == 0) {
      return 0;
    }
    int energyStored = getEnergy(energyStorage);
    int energyExtracted = Math.min(energyStored, Math.min(getEnergyExtract(), maxExtract));
    if (!simulate) {
      setEnergyStored(energyStorage, energyStored - energyExtracted);
    }
    return energyExtracted;
  }

  @Override
  public int getEnergy(ItemStack energyStorage) {
    return energyStorage.getOrCreateTag().getInt(PocketGps.ENERGY_TAG);
  }

  @Override
  public int getCapacity() {
    return PocketGps.gpsEnergyCapacity();
  }

  // Decimal
  public float getEnergyPercentage(ItemStack energyStorage) {
    if (!PocketGps.gpsNeedPower()) {
      return 1;
    } else {
      float energyStored = getEnergy(energyStorage);
      float energyCapacity = getCapacity();

      return (energyStored / energyCapacity);
    }
  }

  public String getStoredEnergyText(ItemStack energyStorage) {
    float storedEnergy = getEnergy(energyStorage);

    if (storedEnergy < 1000 || Screen.hasShiftDown()) {
      return TextUtil.commaFormat.format(storedEnergy);
    } else {
      return TextUtil.kFormat.format(storedEnergy / 1000);
    }
  }

  public String getEnergyCapacityText(ItemStack energyStorage) {
    int energyCapacity = getCapacity();

    if (energyCapacity < 1000 || Screen.hasShiftDown()) {
      return TextUtil.commaFormat.format(energyCapacity);
    } else {
      return TextUtil.kFormat.format(energyCapacity / 1000);
    }
  }

  public String getPercentageText(ItemStack energyStorage) {
    float percentage = getEnergyPercentage(energyStorage);

    return TextUtil.percentFormat.format(percentage * 100);
  }

  public void useEnergy(ItemStack energyStorage, int amount) {
    if (energyStorage.getTag() != null || energyStorage.getTag().contains(PocketGps.ENERGY_TAG)) {
      int stored = Math.min(energyStorage.getTag().getInt(PocketGps.ENERGY_TAG), getCapacity());
      stored -= amount;
      if (stored < 0) {
        stored = 0;
      }
      energyStorage.getTag().putInt(PocketGps.ENERGY_TAG, stored);
    }
  }

  public void useGPS(Player player, ItemStack energyStorage, int cost) {
    if (getEnergy(energyStorage) > 0) {
      useEnergy(energyStorage, cost);
    }
  }

  @Override
  public boolean isBarVisible(ItemStack energyStorage) {
    return PocketGps.gpsNeedPower() && NBTUtil.getInt(energyStorage, PocketGps.ENERGY_TAG) < getCapacity();
  }

  @Override
  public int getBarColor(ItemStack stack) {
    return 16744454;
  }

  @Override
  public int getBarWidth(ItemStack stack) {
    return Math.round((getEnergyPercentage(stack) * 13.0F));
  }

  public List<Component> energyTooltips(ItemStack energyStorage) {
    List<Component> energyTooltips = new ArrayList<>();

    String storedEnergy = getStoredEnergyText(energyStorage);
    String energyCapacity = getEnergyCapacityText(energyStorage);
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
  public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
    if (PocketGps.gpsNeedPower()) {
      tooltip.addAll(energyTooltips(stack));
    }
  }

  public float timeRemaining(ItemStack energyStorage) {
    int energyStored = getEnergy(energyStorage);
    int energyCost = getEnergyCost();
    return (float) energyStored / (energyCost * 20);
  }

  public void debug(ItemStack energyStorage, Player player, double distance, int energyCost) {
    String storedEnergy = getStoredEnergyText(energyStorage);
    String energyCapacity = getEnergyCapacityText(energyStorage);
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
  public void inventoryTick(ItemStack energyStorage, Level level, Entity entity, int slot, boolean selected) {
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
