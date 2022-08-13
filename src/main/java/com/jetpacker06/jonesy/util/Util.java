package com.jetpacker06.jonesy.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jetpacker06.jonesy.Jonesy;
import com.jetpacker06.jonesy.item.ItemRegistry;
import com.jetpacker06.jonesy.item.types.ItemType;
import com.jetpacker06.jonesy.item.types.Rarity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static com.jetpacker06.jonesy.Jonesy.log;

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
        ((MessageReceivedEvent) Jonesy.recentEvent).getChannel().sendMessage(message).queue();
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
    public static String JSONObjectToString(JsonObject json) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(json);
    }
    public static String readFile(String path) throws IOException {
        new File(path).createNewFile();
        File data_file = new File(Jonesy.saveFilePath);
        Scanner fileScanner = new Scanner(data_file);
        String contents = "";
        while (fileScanner.hasNext()) {
            contents += (fileScanner.next());
        }
        fileScanner.close();
        return contents;
    }
    public static String getUsername(User user) {
        return user.getName() + user.getDiscriminator();
    }
    public static String getImageFor(String id) {
        return ItemRegistry.itemGIFMap.get(id);
    }
}
