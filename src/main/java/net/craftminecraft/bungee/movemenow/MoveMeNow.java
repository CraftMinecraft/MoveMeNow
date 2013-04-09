package net.craftminecraft.bungee.movemenow;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;


public class MoveMeNow extends Plugin {
	MainConfig config;
	Map<String,String> playerServer;
	@Override
	public void onEnable() {
		playerServer = new HashMap<>();
		for (ProxiedPlayer p : this.getProxy().getPlayers()) {
			if (p.getServer() != null)
				playerServer.put(p.getName(), p.getServer().getInfo().getName());
		}
		config = new MainConfig(this);
		this.getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
		this.getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this));
	}

	@Override
	public void onDisable() {
		config = null;
	}
	
	public MainConfig getConfig() {
		return this.config;
	}
	
	public Map<String,String> getPlayerServer() {
		return this.playerServer;
	}
}
