package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.items.ChargeableGpsItem;
import club.iananderson.pocketgps.registry.CommonRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ForgeRegistration {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PocketGps.MOD_ID);

  public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(
      Registries.CREATIVE_MODE_TAB, PocketGps.MOD_ID);

  public static RegistryObject<Item> POCKET_GPS = ITEMS.register("gps", ChargeableGpsItem::new);
  public static RegistryObject<CreativeModeTab> TAB = CREATIVE_TAB.register("tab", () -> CreativeModeTab.builder()
      .title(Component.translatable("tab.pocketgps"))
      .icon(() -> CommonRegistration.addIcon(PocketGps.GPS.get())).displayItems((par, out) -> entries(out))
      .build());

  static {
    PocketGps.GPS = POCKET_GPS;
  }

  private static void entries(CreativeModeTab.Output entries) {
    entries.acceptAll(CommonRegistration.addPoweredItem(PocketGps.GPS.get(), true));
  }

  public static void register(IEventBus modEventBus) {
    ITEMS.register(modEventBus);
    CREATIVE_TAB.register(modEventBus);
  }
}
