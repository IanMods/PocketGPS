package club.iananderson.pocketgps.items;

import club.iananderson.pocketgps.minimap.CurrentMinimap;
import club.iananderson.pocketgps.minimap.CurrentMinimap.Minimaps;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class GpsItem extends Item {
  public GpsItem(Properties properties) {
    super(properties.stacksTo(1));
  }

  @Override
  public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
    List<Minimaps> currentMinimaps = CurrentMinimap.loadedMinimaps();
    MutableComponent loadedMinimap;

    if (currentMinimaps.isEmpty()) {
      loadedMinimap = new TranslatableComponent("minimap.pocketgps.none");
    } else {
      loadedMinimap = currentMinimaps.get(0).getModName();
    }

    if (Screen.hasShiftDown()) {
      tooltip.add(new TranslatableComponent("item.pocketgps.gps.tooltip").withStyle(ChatFormatting.GRAY));
      tooltip.add(new TextComponent(""));
      tooltip.add(
          new TranslatableComponent("item.pocketgps.gps.tooltip.minimap.current").withStyle(ChatFormatting.YELLOW)
              .append(loadedMinimap.withStyle(ChatFormatting.AQUA)));
    } else {
      tooltip.add(new TranslatableComponent("item.pocketgps.gps.tooltip.default").withStyle(ChatFormatting.YELLOW));
    }

    super.appendHoverText(stack, level, tooltip, flag);
  }
}