package club.iananderson.pocketgps.items;

import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.minimap.CurrentMinimap.Minimaps;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public abstract class BaseGps extends Item {
  public BaseGps() {
    super(new Properties().stacksTo(1));
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
      tooltip.add(
          Component.translatable("item.pocketgps.gps.tooltip.minimap.current").withStyle(ChatFormatting.YELLOW));
      tooltip.add(loadedMinimap.withStyle(ChatFormatting.AQUA));
    } else {
      tooltip.add(Component.translatable("item.pocketgps.gps.tooltip.default").withStyle(ChatFormatting.YELLOW));
    }

    super.appendHoverText(stack, level, tooltip, flag);
  }
}
