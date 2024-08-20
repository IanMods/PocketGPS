package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.items.GpsItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ForgeRegistration {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PocketGps.MOD_ID);

  private static Item.Properties defaultProperties() {
    return new Item.Properties().tab(TAB);
  }

  public static void init(IEventBus modEventBus) {
    ITEMS.register(modEventBus);
  }

  public static final RegistryObject<Item> POCKET_GPS = ITEMS.register("gps", () -> new GpsItem(
      defaultProperties().stacksTo(1)));

  public static final CreativeModeTab TAB = new CreativeModeTab(PocketGps.MOD_ID + ".tab") {
    @Override
    public @NotNull ItemStack makeIcon() {
      return POCKET_GPS.get().getDefaultInstance();
    }
  };
}