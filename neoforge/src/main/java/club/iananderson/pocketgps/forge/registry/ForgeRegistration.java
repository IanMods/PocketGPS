package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.items.ChargeableGpsItem;
import club.iananderson.pocketgps.items.BasicGps;
import club.iananderson.pocketgps.registry.CommonRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ForgeRegistration {
  public static final CreativeModeTab TAB = new CreativeModeTab(-1, "tab." + PocketGps.MOD_ID) {
    @Override
    public @NotNull ItemStack makeIcon() {
      return CommonRegistration.addIcon(PocketGps.GPS.get());
    }

    @Override
    public @NotNull Component getDisplayName() {
      return Component.translatable("tab." + PocketGps.MOD_ID);
    }
  };

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PocketGps.MOD_ID);
  public static RegistryObject<Item> POCKET_GPS = ITEMS.register("gps",
                                                                 () -> new ChargeableGpsItem(defaultProperties()));

  static {
    PocketGps.GPS = POCKET_GPS;
  }

  private static Item.Properties defaultProperties() {
    return new Item.Properties().tab(TAB);
  }

  public static void register(IEventBus modEventBus) {
    ITEMS.register(modEventBus);
  }
}