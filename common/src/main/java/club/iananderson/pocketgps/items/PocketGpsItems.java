package club.iananderson.pocketgps.items;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Row;
import net.minecraft.world.item.Item;

public class PocketGpsItems {
  public static Supplier<Item> GPS_ITEM_SUPPLIER = () -> new GpsItem(new Item.Properties());

  public static Supplier<CreativeModeTab> POCKET_GPS_TAB(Item gps) {
    return () -> CreativeModeTab.builder(Row.TOP, 0)
        .title(Component.translatable("tab.pocketgps"))
        .icon(gps::getDefaultInstance)
        .displayItems((parameters, output) -> output.accept(gps))
        .build();
  }

}
