package club.iananderson.pocketgps.forge.registry;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.forge.PocketGpsForge;
import club.iananderson.pocketgps.items.GpsItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ForgeRegistration {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PocketGps.MOD_ID);
  public static final RegistryObject<Item> POCKET_GPS = ITEMS.register("gps",
                                                                       () -> new Item(defaultProperties().stacksTo(1)));

  public static final CreativeModeTab TAB = new CreativeModeTab(PocketGps.MOD_ID + ".tab") {
    @Override
    public @NotNull ItemStack makeIcon() {
      return POCKET_GPS.get().getDefaultInstance();
    }
  };

  private static Item.Properties defaultProperties() {
    return new Item.Properties().tab(TAB);
  }

  public static void init(IEventBus modEventBus) {
    ITEMS.register(modEventBus);
  }
}
