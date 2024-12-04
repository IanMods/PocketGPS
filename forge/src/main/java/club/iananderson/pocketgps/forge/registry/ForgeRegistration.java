package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.items.ChargeableGpsItem;
import club.iananderson.pocketgps.init.GpsItems;
import club.iananderson.pocketgps.items.BasicGps;
import club.iananderson.pocketgps.registry.CommonRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ForgeRegistration {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PocketGps.MOD_ID);

  public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(
      Registries.CREATIVE_MODE_TAB, PocketGps.MOD_ID);

  public static RegistryObject<Item> BASIC_GPS = ITEMS.register("basic_gps", () -> GpsItems.BASIC_GPS = new BasicGps());
  public static RegistryObject<Item> POCKET_GPS = ITEMS.register("gps", () -> GpsItems.POCKET_GPS = new ChargeableGpsItem());

  private static void entries(CreativeModeTab.Output entries) {
    entries.acceptAll(CommonRegistration.addPoweredItem(GpsItems.POCKET_GPS, true));
  }

  public static RegistryObject<CreativeModeTab> TAB = CREATIVE_TAB.register("tab", () -> CreativeModeTab.builder()
      .title(Component.translatable("tab.pocketgps"))
      .icon(() -> new ItemStack(GpsItems.BASIC_GPS))
      .displayItems((par, out) -> entries(out))
      .build());

  public static void register(IEventBus modEventBus) {
    ITEMS.register(modEventBus);
    CREATIVE_TAB.register(modEventBus);
  }
}
