package net.craftminecraft.bungee.movemenow;

import java.util.Iterator;

import net.md_5.bungee.api.config.ServerInfo;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {

    MoveMeNow plugin;

    public PlayerListener(MoveMeNow plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerKickEvent(ServerKickEvent ev) {
        // Protection against NullPointerException
        ServerInfo kickedFrom;
        if (ev.getPlayer().getServer() != null) { // If player finished connected, this will be set
            kickedFrom = ev.getPlayer().getServer().getInfo();
        } else if (this.plugin.getProxy().getReconnectHandler() != null) { // If first server and recohandler
            kickedFrom = this.plugin.getProxy().getReconnectHandler().getServer(ev.getPlayer());
        } else { // If first server and no recohandler
            kickedFrom = AbstractReconnectHandler.getForcedHost(ev.getPlayer().getPendingConnection());
            if (kickedFrom == null) // Can still be null if vhost is null... 
            {
                kickedFrom = ProxyServer.getInstance().getServerInfo(ev.getPlayer().getPendingConnection().getListener().getDefaultServer());
            }
        }

        ServerInfo kickTo = this.plugin.getProxy().getServerInfo(plugin.getConfig().servername);

        // Avoid the loop
        if (kickedFrom.equals(kickTo)) {
            return;
        }

        Iterator<String> it = this.plugin.getConfig().list.iterator();
        if (this.plugin.getConfig().mode.equals("whitelist")) {
            while (it.hasNext()) {
                String next = it.next();
                if (ev.getKickReason().contains(next)) {
                    ev.setCancelled(true);
                    ev.setCancelServer(kickTo);
                    if (!plugin.getConfig().movemsg.trim().isEmpty()) {
                        ev.getPlayer().sendMessage(plugin.getConfig().parsemovemsg(ev.getKickReason()));
                    }

                    break; // no need to keep this up !
                }
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
            if (!plugin.getConfig().movemsg.trim().isEmpty()) {
                ev.getPlayer().sendMessage(plugin.getConfig().parsemovemsg(ev.getKickReason()));
            }
        }
    }
}