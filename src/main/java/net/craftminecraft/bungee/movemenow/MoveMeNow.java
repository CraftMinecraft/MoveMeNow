package net.craftminecraft.bungee.movemenow;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;


public class MoveMeNow extends Plugin {
    private Configuration config;

    @Override
    public void onEnable() {
        loadConfig();
        this.getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
        this.getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
        config = null;
    }

    public void loadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(loadResource("config.yml"));
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "Exception while reading config", e);
        }
    }

    public File loadResource(String resource) {
        File folder = this.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = this.getResourceAsStream(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            this.getLogger().log(Level.SEVERE, "Exception while writing default config", e);
        }
        return resourceFile;
    }

    public Configuration getConfig() {
        return this.config;
    }
}