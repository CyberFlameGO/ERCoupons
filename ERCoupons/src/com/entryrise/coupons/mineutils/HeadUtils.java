package com.entryrise.coupons.mineutils;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class HeadUtils {

    public static ItemStack getSkull(String skinURL) {
        ItemStack head = getSkullItem();
        if(skinURL.isEmpty())return head;
       
       
        ItemMeta headMeta = head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", skinURL).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }
    
    public static ItemStack getPlayerSkull(String username) {
    	ItemStack head = getSkullItem();
    	ItemMeta imeta = head.getItemMeta();
    	
    	SkullMeta smeta = (SkullMeta) imeta;
    	
    	smeta.setOwner(username);
    	head.setItemMeta(smeta);
		return head;
    }
	
	public static Material getSkull() {
		Material mat = Material.getMaterial("SKULL_ITEM");
		
		return (mat != null) ? mat : Material.getMaterial("PLAYER_HEAD");
	}
	
	public static ItemStack getSkullItem() {
		return new ItemStack(getSkull(), 1, (short) 3);
	}
}
