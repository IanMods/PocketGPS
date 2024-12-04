package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.items.ChargeableGpsItem;
import club.iananderson.pocketgps.init.GpsItems;
import club.iananderson.pocketgps.items.BasicGps;
import club.iananderson.pocketgps.registry.CommonRegistration;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import techreborn.init.TRContent;

public class FabricRegistration {

  private static final ResourceKey<CreativeModeTab> ITEM_GROUP;

  private static void entries(FabricItemGroupEntries entries) {
    entries.acceptAll(CommonRegistration.addPoweredItem(GpsItems.POCKET_GPS, true));
  }

  public static void register() {
    CommonRegistration.registerItem(GpsItems.BASIC_GPS = new BasicGps(), "basic_gps");
    CommonRegistration.registerItem(GpsItems.POCKET_GPS = new ChargeableGpsItem(),"gps");

//    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(PocketGps.MOD_ID, "tab"), TAB);

    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
        .title(Component.translatable("tab.pocketgps"))
        .icon(() -> new ItemStack(GpsItems.BASIC_GPS))
        .build());

    ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(FabricRegistration::entries);
  }

  static {
    ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(PocketGps.MOD_ID, "item_group"));
  }
}
