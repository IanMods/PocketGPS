package club.iananderson.pocketgps.forge.event;

import club.iananderson.pocketgps.PocketGps;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = PocketGps.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class InventoryEvent {
}