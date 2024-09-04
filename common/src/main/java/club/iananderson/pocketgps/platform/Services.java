package club.iananderson.pocketgps.platform;

import club.iananderson.pocketgps.PocketGps;
import club.iananderson.pocketgps.platform.services.ICurioHelper;
import club.iananderson.pocketgps.platform.services.IPlatformHelper;
import java.util.ServiceLoader;

public class Services {
  public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
  public static final ICurioHelper CURIO_HELPER = load(ICurioHelper.class);

  private Services() {
  }

  public static <T> T load(Class<T> clazz) {
    final T loadedService = ServiceLoader.load(clazz)
                                         .findFirst()
                                         .orElseThrow(() -> new NullPointerException(
                                             "Failed to load service for " + clazz.getName()));
    PocketGps.LOG.debug("Loaded {} for service {}", loadedService, clazz);
    return loadedService;
  }
}
