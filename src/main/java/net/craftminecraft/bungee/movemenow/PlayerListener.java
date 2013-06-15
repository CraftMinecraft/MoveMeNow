package net.craftminecraft.bungee.movemenow;

import java.util.Iterator;

import net.md_5.bungee.api.config.ServerInfo;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {
	MoveMeNow plugin;
	public PlayerListener(MoveMeNow plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onServerKickEvent(ServerKickEvent ev) {
        // Protection against NullPointerException
        ServerInfo kickedFrom = this.plugin.getProxy().getReconnectHandler().getServer(ev.getPlayer());
        if (ev.getPlayer().getServer() != null) {
            kickedFrom = ev.getPlayer().getServer().getInfo();
        }

        ServerInfo kickTo = this.plugin.getProxy().getServerInfo(plugin.getConfig().servername);

        // Avoid the loop
        if (kickedFrom.equals(kickTo)) {
            return;
        }

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
				ev.setCancelServer(kickTo);
				if (!plugin.getConfig().movemsg.trim().isEmpty())
					ev.getPlayer().sendMessage(plugin.getConfig().parsemovemsg(ev.getKickReason()));
			}
		} else {
			while (it.hasNext()) {
				String next = it.next();
				if (ev.getKickReason().contains(next)) {
					return;
				}
			}
			ev.setCancelled(true);
			ev.setCancelServer(kickTo);
			if (!plugin.getConfig().movemsg.trim().isEmpty())
				ev.getPlayer().sendMessage(plugin.getConfig().parsemovemsg(ev.getKickReason()));
		}
	}
}