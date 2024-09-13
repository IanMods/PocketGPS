package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.items.GpsItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FabricRegistration {
  public static final FabricItemGroupBuilder TABS = FabricItemGroupBuilder.create(
      new ResourceLocation(PocketGps.MOD_ID, "tab"));

  private static Item.Properties defaultProperties() {
    return new Item.Properties().tab(TAB);
  }

  public static final GpsItem POCKET_GPS = new GpsItem(defaultProperties());

  public static CreativeModeTab TAB = TABS.icon(POCKET_GPS::getDefaultInstance).appendItems(stacks -> {
        stacks.add(new ItemStack(POCKET_GPS));
      })
      .build();

  public static void init() {
    Registry.register(Registry.ITEM, new ResourceLocation(PocketGps.MOD_ID, "gps"), POCKET_GPS);
  }  public static final GpsItem POCKET_GPS = new GpsItem(defaultProperties());
}