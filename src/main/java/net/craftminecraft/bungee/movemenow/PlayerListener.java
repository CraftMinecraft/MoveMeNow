package net.craftminecraft.bungee.movemenow;

import java.util.Iterator;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ReconnectHandler;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
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

        ServerInfo kickedFrom = null;

        if (ev.getPlayer().getServer() != null) {
            kickedFrom = ev.getPlayer().getServer().getInfo();
        } else if (this.plugin.getProxy().getReconnectHandler() != null) {// If first server and recohandler
            kickedFrom = this.plugin.getProxy().getReconnectHandler().getServer(ev.getPlayer());
        } else { // If first server and no recohandler
            kickedFrom = AbstractReconnectHandler.getForcedHost(ev.getPlayer().getPendingConnection());
            if (kickedFrom == null) // Can still be null if vhost is null... 
            {
                kickedFrom = ProxyServer.getInstance().getServerInfo(ev.getPlayer().getPendingConnection().getListener().getDefaultServer());
            }
        }

        ServerInfo kickTo = this.plugin.getProxy().getServerInfo(plugin.getConfig().getString("servername"));

        // Avoid the loop
        if (kickedFrom != null && kickedFrom.equals(kickTo)) {
            return;
        }

        String reason = BaseComponent.toLegacyText(ev.getKickReasonComponent());
        String[] moveMsg = plugin.getConfig().getString("message").replace("%kickmsg%", reason).split("\n");

        Iterator<String> it = this.plugin.getConfig().getStringList("list").iterator();
        if (this.plugin.getConfig().getString("mode").equals("whitelist")) {
            while (it.hasNext()) {
                String next = it.next();
                if (reason.contains(next)) {
                    ev.setCancelled(true);
                    ev.setCancelServer(kickTo);
                    if (!(moveMsg.length == 1 && moveMsg[0].equals(""))) {
                        for (String line : moveMsg) {
                            ev.getPlayer().sendMessage(TextComponent.fromLegacyText(
                                    ChatColor.translateAlternateColorCodes('&', line)));
                        }
                    }
                    break; // no need to keep this up !
                }
            }
        } else {
            while (it.hasNext()) {
                String next = it.next();
                if (reason.contains(next)) {
                    return;
                }
            }
            ev.setCancelled(true);
            ev.setCancelServer(kickTo);
            if (!(moveMsg.length == 1 && moveMsg[0].equals(""))) {
                for (String line : moveMsg) {
                    ev.getPlayer().sendMessage(TextComponent.fromLegacyText(
                            ChatColor.translateAlternateColorCodes('&', line)));
                }
            }
        }
    }
}
