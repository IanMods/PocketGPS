package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
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

  public static final RegistryObject<Item> POCKET_GPS = ITEMS.register("gps", () -> new Item(
      new Item.Properties().stacksTo(1)));

  public static RegistryObject<CreativeModeTab> TAB = CREATIVE_TAB.register("tab", () -> CreativeModeTab.builder()
      .title(Component.translatable("tab.pocketgps"))
      .icon(() -> POCKET_GPS.get().getDefaultInstance())
      .displayItems((par, out) -> out.accept(POCKET_GPS.get()))
      .build());

  public static void init(IEventBus modEventBus) {
    ITEMS.register(modEventBus);
    CREATIVE_TAB.register(modEventBus);
  }
}
