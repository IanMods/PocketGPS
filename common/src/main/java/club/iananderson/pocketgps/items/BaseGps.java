package club.iananderson.pocketgps.items;

import club.iananderson.pocketgps.energy.ItemEnergyStorage;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.minimap.CurrentMinimap.Minimaps;
import club.iananderson.pocketgps.util.ItemUtil;
import club.iananderson.pocketgps.util.NBTUtil;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class BaseGps extends Item {
  public BaseGps() {
    super(new Properties().stacksTo(1));
  }

  //Todo Make texture for when it's off

  @Override
  public void onCraftedBy(ItemStack itemStack, Level level, Player player) {
    NBTUtil.setInitBoolean(itemStack, ItemEnergyStorage.TOGGLE_GPS_TAG);
  }

  @Override
  public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
    List<Minimaps> currentMinimaps = CurrentMinimap.loadedMinimaps();
    MutableComponent loadedMinimap;

    if (currentMinimaps.isEmpty()) {
      loadedMinimap = Component.translatable("minimap.pocketgps.none");
    } else {
      loadedMinimap = currentMinimaps.get(0).getModName();
    }

    if (Screen.hasShiftDown()) {
      tooltip.add(Component.translatable("item.pocketgps.gps.tooltip_1").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.translatable("item.pocketgps.gps.tooltip_2").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.literal(""));
      tooltip.add(Component.translatable("item.pocketgps.gps.tooltip_3").withStyle(ChatFormatting.GRAY));
      tooltip.add(Component.literal(""));
      tooltip.add(
          Component.translatable("item.pocketgps.gps.tooltip.minimap.current").withStyle(ChatFormatting.YELLOW));
      tooltip.add(loadedMinimap.withStyle(ChatFormatting.AQUA));
    } else {
      tooltip.add(Component.translatable("item.pocketgps.gps.tooltip.default").withStyle(ChatFormatting.YELLOW));
    }

    tooltip.add(Component.literal(""));
    tooltip.add(CommonComponents.optionStatus(ItemUtil.isGpsOn(stack)).copy()
                    .withStyle(ItemUtil.isGpsOn(stack) ? ChatFormatting.GREEN : ChatFormatting.RED));

    super.appendHoverText(stack, level, tooltip, flag);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
    ItemStack heldItem = player.getItemInHand(usedHand);

    if (level.isClientSide() && player.isShiftKeyDown()) {
      ItemUtil.toggleGps(heldItem, player);
    }
    return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide());
  }
}
