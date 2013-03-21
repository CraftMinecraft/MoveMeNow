package net.craftminecraft.bungee.bungeekickmove;

import java.util.Iterator;

import com.google.common.eventbus.Subscribe;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.event.ServerKickEvent;

public class PlayerListener implements Listener {
	BungeeKickMove plugin;
	public PlayerListener(BungeeKickMove plugin) {
		this.plugin = plugin;
	}
	
	@Subscribe
	public void onServerKickEvent(ServerKickEvent ev) {
		Iterator<String> it = this.plugin.getConfig().list.iterator();
		if (this.plugin.getConfig().mode.equals("whitelist")) {
			boolean good = false;
			while (it.hasNext()) {
				String next = it.next();
				if (ev.getKickReason().contains(next)) {
					good = true;
				}
			}
			if (good) {
				ev.setCancelled(true);
				ev.setCancelServer(plugin.getProxy().getServerInfo(plugin.getConfig().servername));
			}
		} else {
			while (it.hasNext()) {
				String next = it.next();
				if (ev.getKickReason().contains(next)) {
					return;
				}
			}
			ev.setCancelled(true);
			ev.setCancelServer(plugin.getProxy().getServerInfo(plugin.getConfig().servername));
		}
	}
}
