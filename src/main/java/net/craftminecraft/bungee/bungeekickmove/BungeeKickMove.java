package net.craftminecraft.bungee.bungeekickmove;
import net.md_5.bungee.api.plugin.Plugin;


public class BungeeKickMove extends Plugin {
	MainConfig config;
	@Override
	public void onEnable() {
		config = new MainConfig(this);
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
