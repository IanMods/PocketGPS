package club.iananderson.pocketgps.forge.impl.curios.item;

import club.iananderson.pocketgps.PocketGps;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosGps implements ICurioItem {
  public CuriosGps() {
  }

  public static ICapabilityProvider initCapabilities() {
    ICurio curio = new ICurio() {
      final ItemStack stack = new ItemStack(PocketGps.GPS.get());

      @Override
      public ItemStack getStack() {
        return stack;
      }

    };
    return new ICapabilityProvider() {
      private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

      @Nonnull
      @Override
      public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
      }
    };
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
  }

  @Override
  public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
    return true;
  }
}