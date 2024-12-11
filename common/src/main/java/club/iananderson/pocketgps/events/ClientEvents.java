package club.iananderson.pocketgps.events;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.client.PocketGpsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;

public class ClientEvents {
  public static void onClientTick(Minecraft client, ClientLevel level) {
      ItemStack gps = PocketGpsClient.getCurrentActiveGps();
      if (gps.isEmpty()) {
        return;
      }
  }
}
