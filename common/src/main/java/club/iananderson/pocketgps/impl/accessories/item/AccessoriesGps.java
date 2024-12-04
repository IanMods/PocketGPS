package club.iananderson.pocketgps.impl.accessories.item;

import club.iananderson.pocketgps.items.BaseChargeableGps;
import club.iananderson.pocketgps.util.ItemUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.client.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class AccessoriesGps implements Accessory {
  public AccessoriesGps() {
  }

  public static void clientInit(Item gps) {
    AccessoriesRendererRegistry.registerRenderer(gps, Renderer::new);
  }

  public static void init(Item gps) {
    AccessoriesAPI.registerAccessory(gps, new AccessoriesGps());
  }

  @Override
  public void tick(ItemStack stack, SlotReference reference) {
    LivingEntity entity = reference.entity();

    if (entity instanceof Player player && !player.isSpectator()) {
      Vec3 deltaMovement = player.getDeltaMovement();
      BaseChargeableGps gps = (BaseChargeableGps) stack.getItem();
      double deltaX = deltaMovement.x;
      double deltaZ = deltaMovement.z;
      double distance = Math.abs(deltaX) + Math.abs(deltaZ);
      float energyCost = gps.getEnergyCost();

      if (distance > 0.001 && ItemUtil.isGpsOn(stack)) {
        if (player.isCrouching()) {
          energyCost *= 0.5F;
        }
        if (player.isSprinting()) {
          energyCost *= 1.5F;
        }
        gps.useGPS(player, stack, (int) energyCost);
      }
    }
  }

  @Override
  public boolean canEquipFromUse(ItemStack stack) {
    return false;
  }

  public static class Renderer implements SimpleAccessoryRenderer {

    @Override
    public <M extends LivingEntity> void align(ItemStack stack, SlotReference reference, EntityModel<M> model,
        PoseStack matrices) {
      if (!(model instanceof HumanoidModel<? extends LivingEntity> humanoidModel)) {
        return;
      }

      matrices.scale(0.4F, 0.4F, 0.4F);
      AccessoryRenderer.transformToModelPart(matrices, humanoidModel.body, 0.75, -1, null);
      matrices.translate(-0.25F, -1.75F, -0.72F);
    }
  }
}