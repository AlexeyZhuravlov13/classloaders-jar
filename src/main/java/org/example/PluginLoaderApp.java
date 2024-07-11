package org.example;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

public class PluginLoaderApp {
    private static final Map<String, String> pathToClassMap = Map.of(
            "C:\\Users\\alexz\\CODE\\plugin-loader\\FormattingPlugin.jar",
            "org.example.FormattingPlugin",
            "C:\\Users\\alexz\\CODE\\plugin-loader\\AnotherPlugin.jar",
            "org.example.AnotherPlugin");

    public static void main(String[] args) throws Exception {
        for (String pluginJarPath : pathToClassMap.keySet()) {
            URL jarUrl = new URL("file:///" + pluginJarPath);
            String pluginName = pathToClassMap.get(pluginJarPath);
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