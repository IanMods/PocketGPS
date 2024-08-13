package club.iananderson.pocketgps.forge;

import club.iananderson.pocketgps.PocketGps;
import net.minecraftforge.fml.common.Mod;

@Mod(PocketGps.MOD_ID)
public final class PocketGpsForge {
	public PocketGpsForge() {
		// Run our common setup.
		PocketGps.init();
	}
}
