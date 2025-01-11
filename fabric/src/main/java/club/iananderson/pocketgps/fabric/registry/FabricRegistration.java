package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.items.ChargeableGpsItem;
import club.iananderson.pocketgps.items.BasicGps;
import club.iananderson.pocketgps.registry.CommonRegistration;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FabricRegistration {

  public static final FabricItemGroupBuilder TABS = FabricItemGroupBuilder.create(
      new ResourceLocation(PocketGps.MOD_ID, "tab"));
  private static final ResourceKey<CreativeModeTab> ITEM_GROUP;
  ;
  public static BasicGps BASIC_GPS = new BasicGps();
  public static ChargeableGpsItem POCKET_GPS = new ChargeableGpsItem();

  public static CreativeModeTab TAB = TABS.title(Component.translatable("tab.pocketgps"))
      .icon(() -> new ItemStack(PocketGps.BASIC_GPS.get())).displayItems((par, out) -> entries(out))
      .build();

  static {
    PocketGps.BASIC_GPS = () -> BASIC_GPS;
    PocketGps.GPS = () -> POCKET_GPS;
    ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(PocketGps.MOD_ID, "item_group"));
  }

  private static void entries(CreativeModeTab.Output entries) {
    entries.acceptAll(CommonRegistration.addPoweredItem(PocketGps.GPS.get(), true));
  }

  public static void register() {
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, PocketGps.location("tab"), TAB);
    Registry.register(BuiltInRegistries.ITEM, PocketGps.location("basic_gps"), BASIC_GPS);
    Registry.register(BuiltInRegistries.ITEM, PocketGps.location("gps"), POCKET_GPS);

    ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP)
        .register(FabricRegistration::entries);
  }
}