package club.iananderson.pocketgps.items;

import club.iananderson.pocketgps.PocketGps;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Row;
import net.minecraft.world.item.Item;

public class PocketGpsItems {
  public static ResourceKey<Item> key(String itemName) {
    return ResourceKey.create(Registries.ITEM, PocketGps.location(itemName));
  }

  public static Supplier<Item> GPS_ITEM_SUPPLIER =
      () -> new GpsItem(new Item.Properties().setId(key("gps")));

  public static Supplier<CreativeModeTab> POCKET_GPS_TAB(Item gps) {
    return () -> CreativeModeTab.builder(Row.TOP, 0)
        .title(Component.translatable("tab.pocketgps"))
        .icon(gps::getDefaultInstance)
        .displayItems((parameters, output) -> output.accept(gps))
        .build();
  }

}
