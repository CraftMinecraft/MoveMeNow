package net.craftminecraft.bungee.movemenow;
import net.craftminecraft.bungee.bungeeyaml.InvalidConfigurationException;
import net.md_5.bungee.api.plugin.Plugin;


public class MoveMeNow extends Plugin {
	MainConfig config;
	@Override
	public void onEnable() {
		config = new MainConfig(this);
		try {
			config.init();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		this.getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
	}

	@Override
	public void onDisable() {
		config = null;
	}
	
	public MainConfig getConfig() {
		return this.config;
	}
}
