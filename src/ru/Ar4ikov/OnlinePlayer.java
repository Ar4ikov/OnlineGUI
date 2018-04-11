package ru.Ar4ikov;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

public class OnlinePlayer {
    private Boolean ONLINE = false;
    private World WORLD = Bukkit.getWorld("world");
    private Integer LAST_LOGIN = 0;
    private ItemStack HEAD;

    private static HashMap<String, OnlinePlayer> Players = new HashMap();
    public OnlinePlayer(Boolean online, World world, int last_login, ItemStack head) {
        ONLINE = online;
        WORLD = world;
        LAST_LOGIN = last_login;
        HEAD = head;
    }

    public ItemStack getHead() {
        return HEAD;
    }

    public Boolean getOnline() {
        return ONLINE;
    }

    public World getWorld() {
        return WORLD;
    }

    public void toggleOnline() {
        if (ONLINE) {
            ONLINE = false;
        } else {
            ONLINE = true;
        }
    }

    /**
     *
     * Получение головы игрока по нику
     *
     * @param item - Предмет черепа
     * @param nick - Ник игрока
     * @return ItemStack с владельцем его головы
     */
    private static ItemStack setSkin(ItemStack item, String nick) {
//        SkullMeta skull = (SkullMeta) item.getItemMeta();
//        skull.setOwner(nick);
//        item.setItemMeta(skull);
//        return item;
        return setSkullMeta(item, SkinCash.getSkin(nick));
    }

    /**
     *
     * Получение GameProfile игрока из Mojang API
     *
     * @param nickname - Ник игрока
     * @return GameProfile игрока, если таковой есть
     */
    public static GameProfile getProfile(String nickname) {
        Player player = Bukkit.getPlayer(nickname);
        return ((CraftPlayer) player).getHandle().getProfile();
    }

    /**
     *
     * Получение текстуры
     *
     * @param nick
     * @return
     */
    public static String getTexture(String nick) {
        GameProfile gameProfile = getProfile(nick);
        return gameProfile.getProperties().get("textures").iterator().next().getValue();
    }

    public static void setDeclaredField(Object object, Class cls, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    public static ItemStack setSkullMeta(ItemStack itemStack, String value) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta) itemMeta;

            try {
                GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
                PropertyMap propertyMap = gameProfile.getProperties();
                propertyMap.put("textures", new Property("textures", value));

                Class craftMetaSkull = Class.forName("org.bukkit.craftbukkit.v1_10_R1.inventory.CraftMetaSkull");

                setDeclaredField(skullMeta, craftMetaSkull, "profile", gameProfile);

                itemStack.setItemMeta(skullMeta);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return itemStack;
    }

    /**
     *
     * Получение игрока по нику
     *
     * @param nickname - Ник игрока
     * @return Игрока
     */
    public static OnlinePlayer getPlayer(String nickname) {
        if (Players.containsKey(nickname)) {
            return Players.get(nickname);
        } else {
            ItemStack HEAD = setSkin(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), nickname);
            OnlinePlayer player = new OnlinePlayer(false, Bukkit.getWorld("world"), 0, HEAD);
            Players.put(nickname, player);
            return player;
        }
    }

    public static void updatePlayer(String nickname, OnlinePlayer player) {
        if (Players.containsKey(nickname)) {
            Players.remove(player);
            Players.put(nickname, player);
        } else {
            Players.put(nickname, player);
        }
    }

    public static HashMap getPlayers() {
        return Players;
    }

    public static Integer getOnlineCount() {
        return Bukkit.getOnlinePlayers().toArray().length;
    }
}
