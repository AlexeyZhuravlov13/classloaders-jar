package org.example;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

public class PluginLoaderApp {
    private static final Map<String, String> PATH_TO_CLASS_MAP = Map.of(
            "C:\\Users\\alexz\\CODE\\plugin-loader\\FormattingPlugin.jar",
            "org.example.FormattingPlugin",
            "C:\\Users\\alexz\\CODE\\plugin-loader\\AnotherPlugin.jar",
            "org.example.AnotherPlugin");

    public static void main(String[] args) throws Exception {
        for (Map.Entry<String, String> stringStringEntry : PATH_TO_CLASS_MAP.entrySet()) {
            String pluginJarPath = stringStringEntry.getKey();
            String pluginName = stringStringEntry.getValue();
            URL jarUrl = new URL("file:///" + pluginJarPath);
            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl})) {
                Class<?> formattedClass = classLoader.loadClass(pluginName);
                Plugin formattingPlugin = (Plugin) formattedClass.getDeclaredConstructor().newInstance();
                formattingPlugin.execute();
            } catch (ClassNotFoundException e) {
                System.out.println(pluginName + " not found");
            }
        }
    }
}