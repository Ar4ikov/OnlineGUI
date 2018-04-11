package ru.Ar4ikov;

import java.util.List;

public class Language {
    public Language() {

    }

    public static final String GUI_NAME = "Игроки";
    public static final String GUI_ONLINE_STRING = "§8Онлайн: §f";

    public static final String ONLINE_PLAYERS_ITEM_NAME = "§eОбщий онлайн";
    public static final String[] ALL_ONLINE_PLAYERS(Integer players) {
            String[] lore = {
                    "",
                    "§7В сети: §a" + players.toString()
            };
            return lore;
    };

    public static final String[] HEAD_LORE(Boolean online, String world_name) {
        String onLine = "";
        if (online) {
            onLine = "§aДа";
        } else {
            onLine = "§cНет";
        }

        String[] lore = {
                "§8------------------",
                "§8| §7В сети: §a" + onLine,
                "§8| §7Мир: §e" + world_name,
                "§8------------------"
        };

        return lore;
    }
}
