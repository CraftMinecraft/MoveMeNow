package net.craftminecraft.bungee.movemenow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.craftminecraft.bungee.bungeeyaml.InvalidConfigurationException;
import net.craftminecraft.bungee.bungeeyaml.supereasyconfig.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

public class MainConfig extends Config {
	public MainConfig(Plugin plugin) {
		this.CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		try {
			this.init();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String mode = "blacklist";
	public String servername = "lobby";
	public List<String> list = new ArrayList<String>() {{add("ban");add("kick");}};
	public String movemsg = "&3[MoveMeNow] &4You have been moved : %kickmsg%";
	
	public String parsemovemsg(String kickmsg) {
		movemsg = ChatColor.translateAlternateColorCodes('&', movemsg);
		return movemsg.replaceAll("%kickmsg%", kickmsg);
	}
}
