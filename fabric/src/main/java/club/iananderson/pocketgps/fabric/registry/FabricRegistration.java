package club.iananderson.pocketgps.fabric.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.fabric.items.ChargeableGpsItem;
import club.iananderson.pocketgps.registry.CommonRegistration;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class FabricRegistration {

  public static final CreativeModeTab.Builder TABS = FabricItemGroup.builder();
  private static final ResourceKey<CreativeModeTab> ITEM_GROUP;

  public static ChargeableGpsItem POCKET_GPS = new ChargeableGpsItem();

  public static CreativeModeTab TAB = TABS.title(Component.translatable("tab.pocketgps"))
      .icon(() -> CommonRegistration.addIcon(PocketGps.GPS.get())).displayItems((par, out) -> entries(out))
      .build();

  static {
    PocketGps.GPS = () -> POCKET_GPS;
    ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(PocketGps.MOD_ID, "item_group"));
  }

  private static void entries(CreativeModeTab.Output entries) {
    entries.acceptAll(CommonRegistration.addPoweredItem(PocketGps.GPS.get(), true));
  }

  public static void register() {
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, PocketGps.location("tab"), TAB);
    Registry.register(BuiltInRegistries.ITEM, PocketGps.location("gps"), POCKET_GPS);

    ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP)
        .register(FabricRegistration::entries);
  }
}
