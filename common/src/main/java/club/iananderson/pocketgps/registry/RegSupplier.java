package club.iananderson.pocketgps.registry;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public interface RegSupplier<T> extends Supplier<T> {

  @Override
  T get();

  ResourceLocation getId();

  ResourceKey<T> getKey();

  Holder<T> getHolder();

  default boolean is(TagKey<T> tag) {
    return this.getHolder().is(tag);
  }
}
