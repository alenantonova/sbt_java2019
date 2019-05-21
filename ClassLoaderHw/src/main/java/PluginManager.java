import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) {
        URL[] classLoaderUrls;
        Plugin downloaded_class = new MyPlugin();
        try {
            classLoaderUrls = new URL[]{new URL("file:/" + pluginRootDirectory + "/" + pluginName + "/")};
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
            downloaded_class = (Plugin) urlClassLoader.loadClass(pluginClassName).getDeclaredConstructor().newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return downloaded_class;
    }
}
