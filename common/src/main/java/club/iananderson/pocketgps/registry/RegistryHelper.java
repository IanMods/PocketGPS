package club.iananderson.pocketgps.registry;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class RegistryHelper {
  public static <T, E extends T> RegSupplier<E> register(ResourceLocation name, Supplier<E> supplier,
      ResourceKey<? extends Registry<T>> regKey) {
    throw new AssertionError();
  }
}

