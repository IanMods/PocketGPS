package club.iananderson.pocketgps.forge.items;

import club.iananderson.pocketgps.forge.energy.EnergyCapabilityProvider;
import club.iananderson.pocketgps.items.GpsItem;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class ChargeableGpsItem extends GpsItem implements IEnergyStorage {
  protected int energy;
  protected int capacity;
  protected int maxReceive;
  protected int maxExtract;

  public ChargeableGpsItem(Properties properties, int capacity, int maxReceive, int maxExtract, int energy) {
    super(properties);
    this.capacity = capacity;
    this.maxReceive = maxReceive;
    this.maxExtract = maxExtract;
    this.energy = Math.max(0, Math.min(capacity, energy));
  }

  public ChargeableGpsItem(Properties properties, int capacity) {
    this(properties, capacity, capacity, capacity, 0);
  }

  public ChargeableGpsItem(Properties properties, int capacity, int maxTransfer) {
    this(properties, capacity, maxTransfer, maxTransfer, 0);
  }

  public ChargeableGpsItem(Properties properties, int capacity, int maxReceive, int maxExtract) {
    this(properties, capacity, maxReceive, maxExtract, 0);
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag tag) {
    return new EnergyCapabilityProvider(stack, this.capacity);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);

    tooltip.add(Component.literal(""));

    stack.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(energy -> tooltip.addAll(energyComponents(stack)));
  }

  public List<Component> energyComponents(ItemStack stack) {
    List<Component> energyTooltips = new ArrayList<>();
    DecimalFormat commaFormat = new DecimalFormat("#,###");
    DecimalFormat kFormat = new DecimalFormat("###.0k");
    DecimalFormat percentFormat = new DecimalFormat("###");

    float storedEnergy = this.getEnergyStored();
    float maxEnergy = this.getMaxEnergyStored();
    float percentage = ((storedEnergy / maxEnergy) * 100);

    String simpleStoredEnergy;
    String expandedStoredEnergy = commaFormat.format(storedEnergy);
    String simpleMaxEnergy = kFormat.format(maxEnergy / 1000);
    String expandedMaxEnergy = commaFormat.format(maxEnergy);
    String percentageText = percentFormat.format(percentage);

    if (storedEnergy < 1000) {
      simpleStoredEnergy = commaFormat.format(storedEnergy);
    } else {
      simpleStoredEnergy = kFormat.format((storedEnergy) / 1000);
    }

    //Todo -- Allow the unit to be changed by config
    if (!Screen.hasShiftDown()) {
      energyTooltips.add(
          Component.translatable("item.pocketgps.gps.tooltip.energy.stored", simpleStoredEnergy, simpleMaxEnergy, "FE")
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
    return Math.round((float) this.getEnergyStored() * 100.0F / (float) this.getMaxEnergyStored() * 13.0F) / 100;
  }

  public boolean isBarVisible(ItemStack stack) {
    return true;
  }

  public int getBarColor(ItemStack stack) {
    return 16744454;
  }

  @Override
  public int receiveEnergy(int maxReceive, boolean simulate) {
    if (!this.canReceive()) {
      return 0;
    } else {
      int energyReceived = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, maxReceive));
      if (!simulate) {
        this.energy += energyReceived;
      }

      return energyReceived;
    }
  }

  @Override
  public int extractEnergy(int maxExtract, boolean simulate) {
    if (!this.canExtract()) {
      return 0;
    } else {
      int energyExtracted = Math.min(this.energy, Math.min(this.maxExtract, maxExtract));
      if (!simulate) {
        this.energy -= energyExtracted;
      }

      return energyExtracted;
    }
  }

  @Override
  public int getEnergyStored() {
    return this.energy;
  }

  @Override
  public int getMaxEnergyStored() {
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

  public Tag serializeNBT() {
    return IntTag.valueOf(this.getEnergyStored());
  }

  public void deserializeNBT(Tag nbt) {
    if (nbt instanceof IntTag intNbt) {
      this.energy = intNbt.getAsInt();
    } else {
      throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
    }
  }
}
