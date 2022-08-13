package com.jetpacker06.B3.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jetpacker06.B3.B3;
import com.jetpacker06.B3.item.types.ItemType;
import com.jetpacker06.B3.item.types.Rarity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

import static com.jetpacker06.B3.B3.log;

public class Util {
    public static String[] types_list = {"GUN", "MELEE", "CONSUMABLE", "HEALING", "SHIELDING", "EMPTY"};
    public static String[] rarities_list = {"COMMON", "UNCOMMON", "RARE", "EPIC", "LEGENDARY"};
    public static boolean isStringInList(String thing, String[] list) {
        boolean flag = false;
        for (String s : list) {
            if (Objects.equals(s, thing)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    public static void sendMessage(String message) {
        ((MessageReceivedEvent) B3.recentEvent).getChannel().sendMessage(message).queue();
    }
    public static EmbedBuilder blueBuilder() {
        return new EmbedBuilder().setColor(Color.BLUE);
    }
    public static EmbedBuilder coloredEmbedBuilder(int color) {
        return new EmbedBuilder().setColor(color);
    }
    public static MessageEmbed createImageEmbed(String url) {
        return blueBuilder().setImage(url).build();
    }
    public static void logJSONObject(JsonObject obj) {
        log(new GsonBuilder().setPrettyPrinting().create().toJson(obj));
    }
    public static ItemType itemTypeFromString(String input) {
        if (!isStringInList(input.toUpperCase(), types_list)) {
            throw new IllegalArgumentException("String does not represent an ItemType!");
        }
        return switch (input.toUpperCase()) {
            case "GUN" -> ItemType.GUN;
            case "MELEE" -> ItemType.MELEE;
            case "CONSUMABLE" -> ItemType.CONSUMABLE;
            case "HEALING" -> ItemType.HEALING;
            case "SHIELDING" -> ItemType.SHIELDING;
            default -> ItemType.EMPTY;
        };
    }
    public static Rarity rarityFromString(String input) {
        if (!isStringInList(input.toUpperCase(), rarities_list)) {
            throw new IllegalArgumentException("String does not represent a Rarity!");
        }
        return switch (input.toUpperCase()) {
            case "COMMON" -> Rarity.COMMON;
            case "UNCOMMON" -> Rarity.UNCOMMON;
            case "RARE" -> Rarity.RARE;
            case "EPIC" -> Rarity.EPIC;
            default -> Rarity.LEGENDARY;
        };
    }
    public static int getColorForRarity(Rarity rarity) {
        return switch (rarity) {
            case COMMON -> Color.GRAY;
            case UNCOMMON -> Color.GREEN;
            case RARE -> Color.LIGHT_BLUE;
            case EPIC -> Color.PURPLE;
            case LEGENDARY -> Color.GOLD;
        };
    }
}
