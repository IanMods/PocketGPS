package club.iananderson.pocketgps.items;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.minimap.CurrentMinimap.Minimaps;
import club.iananderson.pocketgps.util.ItemUtil;
import club.iananderson.pocketgps.util.NBTUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public abstract class BaseGps extends Item {
  public BaseGps(Properties properties) {
    super(properties);
  }

  @Override
  public boolean isBarVisible(ItemStack stack) {
    return false;
  }

  @Override
  public void onCraftedBy(ItemStack itemStack, Level level, Player player) {
    ItemUtil.initGpsState(itemStack);
  }

  public List<Component> expandedTooltips() {
    List<Minimaps> currentMinimaps = CurrentMinimap.loadedMinimaps();
    MutableComponent loadedMinimap;

    if (currentMinimaps.isEmpty()) {
      loadedMinimap = new TranslatableComponent("minimap.pocketgps.none");
    } else {
      loadedMinimap = currentMinimaps.get(0).getModName();
    }

    return new ArrayList<>(
        List.of(new TranslatableComponent("item.pocketgps.gps.tooltip_1").withStyle(ChatFormatting.GRAY),
                new TranslatableComponent("item.pocketgps.gps.tooltip_2").withStyle(ChatFormatting.GRAY),
                new TextComponent(""),
                new TranslatableComponent("item.pocketgps.gps.tooltip_3").withStyle(ChatFormatting.GRAY),
                new TextComponent(""),
                new TranslatableComponent("item.pocketgps.gps.tooltip.minimap.current").withStyle(
                    ChatFormatting.YELLOW), loadedMinimap.withStyle(ChatFormatting.AQUA)));

  }

  @Override
  public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {

    if (Screen.hasShiftDown()) {
      tooltip.addAll(expandedTooltips());

    } else {
      tooltip.add(new TranslatableComponent("item.pocketgps.gps.tooltip.default").withStyle(ChatFormatting.YELLOW));
    }

    tooltip.add(new TextComponent(""));
    tooltip.add(CommonComponents.optionStatus(ItemUtil.isGpsOn(stack)).copy()
                    .withStyle(ItemUtil.isGpsOn(stack) ? ChatFormatting.GREEN : ChatFormatting.RED));

    super.appendHoverText(stack, level, tooltip, flag);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
    ItemStack heldItem = player.getItemInHand(usedHand);

    if (level.isClientSide()) {
      ItemUtil.toggleGps(heldItem, player);
    }

    if (PocketGps.gpsNeedPower() && heldItem.getTag().get(PocketGps.ENERGY_TAG) == null) {
      NBTUtil.setInt(heldItem, PocketGps.ENERGY_TAG, 0);
    }

    return InteractionResultHolder.sidedSuccess(heldItem, level.isClientSide());
  }
}
