package org.example;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class MainApplication {
    public static void main(String[] args) throws Exception {
        // Список путей к .jar файлам плагинов (можно хардкод оставить, можно загрузить из конфигурационного файла)
        List<String> pluginJarPaths = List.of(
                "C:\\Users\\alexz\\CODE\\plugin-loader\\FormattingPlugin.jar",
                "C:\\Users\\alexz\\CODE\\plugin-loader\\AnotherPlugin.jar");
        for (String pluginJarPath : pluginJarPaths) {
            System.out.println("->> Loading plugin");
            URL jarUrl = new URL("file:///" + pluginJarPath);
            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl})) {
                String pluginName1 = "org.example.FormattingPlugin";
                String pluginName2 = "org.example.AnotherPlugin";
                extracted(classLoader, pluginName1);
                extracted(classLoader, pluginName2);
            }
        }
    }

    private static void extracted(URLClassLoader classLoader, String pluginName) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        try {
            Class<?> formattedClass = classLoader.loadClass(pluginName);
            Plugin formattingPlugin = (Plugin) formattedClass.getDeclaredConstructor().newInstance();
            formattingPlugin.execute();
        } catch (ClassNotFoundException e) {
            System.out.println(pluginName + " not found");
        }
    }
}