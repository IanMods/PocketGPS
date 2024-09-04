package club.iananderson.pocketgps.neoforge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.items.PocketGpsItems;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistration {
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PocketGps.MOD_ID);

  public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(
      Registries.CREATIVE_MODE_TAB, PocketGps.MOD_ID);

  public static final DeferredItem<Item> POCKET_GPS = ITEMS.registerSimpleItem("gps", new Item.Properties());

  public static Supplier<CreativeModeTab> TAB = CREATIVE_TAB.register("tab", () -> CreativeModeTab.builder()
      .title(Component.translatable("tab.pocketgps"))
      .icon(() -> POCKET_GPS.get().getDefaultInstance()).displayItems((par, out) -> out.accept(POCKET_GPS.get()))
      .build());

  public static void init(IEventBus modEventBus) {
    ITEMS.register(modEventBus);
    CREATIVE_TAB.register(modEventBus);
  }
}
