package ru.Ar4ikov;
import com.mojang.authlib.properties.Property;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;

public class SkinCash {

    private static HashMap<String, String>SkinsCash = new HashMap<>();
    public static String getSkin(String nickname) {
        if (SkinsCash.containsKey(nickname)) {
            return SkinsCash.get(nickname);
        } else {
            String texture = OnlinePlayer.getTexture(nickname);
            SkinsCash.put(nickname, texture);
            return texture;
        }
    }
}
