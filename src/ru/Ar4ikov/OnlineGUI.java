package ru.Ar4ikov;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class OnlineGUI extends JavaPlugin implements Listener {
    public static Plugin instance;

    public static Plugin getPluginClass() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        Bukkit.getServer().getPluginManager().registerEvents((Listener) getPluginClass(), this);
        updateInventory.updateInventory();

        for (Player bukkit_player : Bukkit.getOnlinePlayers()) {
            OnlinePlayer player = OnlinePlayer.getPlayer(bukkit_player.getName());
            player.toggleOnline();
            OnlinePlayer.updatePlayer(bukkit_player.getPlayer().getName(), player);
        }

    }

    public void onDisable() {

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(Language.GUI_NAME)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        OnlinePlayer player = OnlinePlayer.getPlayer(event.getPlayer().getName());
        player.toggleOnline();
        OnlinePlayer.updatePlayer(event.getPlayer().getName(), player);
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        OnlinePlayer player = OnlinePlayer.getPlayer(event.getPlayer().getName());
        player.toggleOnline();
        OnlinePlayer.updatePlayer(event.getPlayer().getName(), player);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.equals(Bukkit.getConsoleSender())) {
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("players")) {
            ((Player) sender).openInventory(OnlineInventory.getInventory((Player) sender));
        }
        return true;
    }

}
