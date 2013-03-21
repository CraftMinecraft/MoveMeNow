package net.craftminecraft.bungee.bungeekickmove;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.craftminecraft.bungee.bungeeyaml.supereasyconfig.Config;
import net.md_5.bungee.api.plugin.Plugin;

public class MainConfig extends Config {
	public MainConfig(Plugin plugin) {
		this.CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
	}
	
	public String mode = "blacklist";
	public String servername = "lobby";
	public List<String> list = new ArrayList<String>() {{add("ban");add("kick");}};
}
