package ru.Ar4ikov;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OnlineInventory {
    public static Inventory inventory = null;
    public static Player inventory_owner = null;

    public OnlineInventory() {

    }

    public static Player getInventory_owner() {
        return inventory_owner;
    }

    public static Inventory getInventory(Player player) {
        if (inventory != null) {
            return inventory;
        } else {
            inventory_owner = player;
            OnlineInventory.generateInventory(inventory_owner, OnlinePlayer.getPlayers());
            return inventory;
        }
    }

    private static ItemStack getOnlinePlayersItem() {
        ItemStack item = new ItemStack(Material.SLIME_BALL, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Language.ONLINE_PLAYERS_ITEM_NAME);
        meta.setLore(Arrays.asList(Language.ALL_ONLINE_PLAYERS(OnlinePlayer.getOnlineCount())));
        item.setItemMeta(meta);

        return item;
    }

    public static void generateInventory(Player player, HashMap<String, OnlinePlayer> onlinePlayers) {
        Inventory inv = null;
        if (inventory == null) {
            inv = (Inventory) Bukkit.createInventory(player, InventoryType.CHEST, Language.GUI_NAME);
        } else {
            inv = inventory;
        }
        inv.setItem(4, getOnlinePlayersItem());
        Integer i = 9;
        for (Map.Entry<String, OnlinePlayer> entry : onlinePlayers.entrySet()) {
            if ((i % 9 == 0) || ((i - 1) % 9 == 0)) {
                i++;
            }
            OnlinePlayer onlinePlayer = entry.getValue();
            ItemStack head = onlinePlayer.getHead();
            ItemMeta meta = head.getItemMeta();
            meta.setDisplayName("§c" + entry.getKey());
            String[] lore = Language.HEAD_LORE(onlinePlayer.getOnline(), onlinePlayer.getWorld().getName());
            meta.setLore(Arrays.asList(lore));
            head.setItemMeta(meta);
            inv.setItem(i, head);
            i++;
        }

        inventory = inv;

    }
}
