package net.craftminecraft.bungee.movemenow;

import com.google.common.collect.ObjectArrays;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.craftminecraft.bungee.bungeeyaml.bukkitapi.InvalidConfigurationException;
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
    public List<String> list = new ArrayList<String>() {
        {
            add("ban");
            add("kick");
        }
    };
    
    public boolean sendmovemsg = true;
    public List<String> movemsg = new ArrayList<String>() {
        {
            add("&3[MoveMeNow] &4You have been moved : %kickmsg%");
        }
    };
    
    public String[] parsemovemsg(String kickmsg) {
        String[] msgs = new String[0];
        for (String i : getMoveMsg()) {
            msgs = ObjectArrays.concat(msgs,ChatColor.translateAlternateColorCodes('&',i).replaceAll("%kickmsg%", kickmsg));
        }
        return msgs;
    }
    
    public String[] getMoveMsg() {
        String[] msgs = new String[0];
        for (String i : movemsg) {
            msgs = ObjectArrays.concat(msgs, i.split("\\\\n"), String.class);
        }
        return msgs;
    }
}
