package club.iananderson.pocketgps.neoforge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.items.components.GpsToggle;
import club.iananderson.pocketgps.items.components.ItemEnergy;
import club.iananderson.pocketgps.neoforge.items.ChargeableGpsItem;
import club.iananderson.pocketgps.registry.CommonRegistration;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistration {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(PocketGps.MOD_ID);

  public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.createDataComponents(
      Registries.DATA_COMPONENT_TYPE, PocketGps.MOD_ID);

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY = COMPONENTS.register(
      PocketGps.ENERGY_TAG, () -> ItemEnergy.ENERGY);

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> TOGGLE = COMPONENTS.register(
      PocketGps.TOGGLE_GPS_TAG, () -> GpsToggle.TOGGLE);

  public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(
      Registries.CREATIVE_MODE_TAB, PocketGps.MOD_ID);

  public static final Supplier<Item> POCKET_GPS = ITEMS.register("gps", ChargeableGpsItem::new);

  public static Supplier<CreativeModeTab> TAB = CREATIVE_TAB.register("tab", () -> CreativeModeTab.builder()
      .title(Component.translatable("tab.pocketgps"))
      .icon(() -> CommonRegistration.addIcon(PocketGps.GPS.get())).displayItems((par, out) -> entries(out))
      .build());

  static {
    PocketGps.GPS = POCKET_GPS;
  }

  private static void entries(CreativeModeTab.Output entries) {
    entries.acceptAll(CommonRegistration.addPoweredItem(PocketGps.GPS.get(), true));
  }

  @SubscribeEvent  // on the mod event bus
  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerItem(Capabilities.EnergyStorage.ITEM,
                       (itemStack, context) -> new ComponentEnergyStorage(itemStack, ItemEnergy.ENERGY,
                                                                          PocketGps.gpsEnergyCapacity(),
                                                                          PocketGps.gpsMaxInput(),
                                                                          PocketGps.gpsMaxOutput()),
                       // blocks to register for
                       POCKET_GPS.get());
  }

  public static void register(IEventBus modEventBus) {
    ITEMS.register(modEventBus);
    COMPONENTS.register(modEventBus);
    CREATIVE_TAB.register(modEventBus);
  }
}
