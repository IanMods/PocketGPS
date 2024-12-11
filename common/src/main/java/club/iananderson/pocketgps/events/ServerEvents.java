package club.iananderson.pocketgps.events;

import club.iananderson.pocketgps.client.PocketGpsClient;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ServerEvents {
  public static void onPlayerTick(Player player) {
    ServerPlayer serverPlayer = ((ServerPlayer) player);

    var server = serverPlayer.server;
    ItemStack gps = PocketGpsClient.getGpsFromPlayer(player);
    if (gps.isEmpty()) {
      return;
    }
  }
}
