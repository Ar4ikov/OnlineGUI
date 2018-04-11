package ru.Ar4ikov;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class updateInventory {
    public static void updateInventory() {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                OnlineInventory.generateInventory(OnlineInventory.getInventory_owner(), OnlinePlayer.getPlayers());
                for (HumanEntity player : OnlineInventory.getInventory(OnlineInventory.getInventory_owner()).getViewers()) {
                    ((Player) player).updateInventory();
                }
            }
        };
        runnable.runTaskTimer(OnlineGUI.getPluginClass(), 0, 5);
    }
}
