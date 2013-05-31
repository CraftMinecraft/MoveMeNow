package net.craftminecraft.bungee.movemenow;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;


public class MoveMeNow extends Plugin {
	MainConfig config;
	@Override
	public void onEnable() {
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
}
