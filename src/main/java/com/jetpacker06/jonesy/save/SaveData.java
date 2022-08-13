package com.jetpacker06.jonesy.save;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jetpacker06.jonesy.Jonesy;
import com.jetpacker06.jonesy.inventory.PlayerData;
import com.jetpacker06.jonesy.inventory.PlayerInventory;
import com.jetpacker06.jonesy.item.AbstractItem;
import com.jetpacker06.jonesy.util.Util;
import net.dv8tion.jda.api.entities.User;

import java.io.FileWriter;
import java.io.IOException;

import static com.jetpacker06.jonesy.Jonesy.log;
import static com.jetpacker06.jonesy.Jonesy.saveFilePath;

public class SaveData {
    public static void saveInventory(String guildID, User user, PlayerInventory inventory, int health, int shield) throws IOException {
        String contents = Util.readFile(Jonesy.saveFilePath);
        String complete_username = Util.getUsername(user);
        JsonObject entireJson = new Gson().fromJson(contents, JsonObject.class);
        //if player already has inventory then save new inventory, else save new inventory
        if (userHasCompleteData(guildID, user)) {
            JsonObject dataForInputPlayer = entireJson.get("players").getAsJsonObject().get(complete_username).getAsJsonObject();
            dataForInputPlayer.add("inventory", inventory.toJSON());
            FileWriter writer = new FileWriter(saveFilePath);
            entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().add(complete_username, dataForInputPlayer);
            writer.write(Util.JSONObjectToString(entireJson));
            writer.close();
        } else {
            if (!entireJson.get("servers").getAsJsonObject().has(guildID)) {
                entireJson.get("servers").getAsJsonObject().add(guildID, new JsonObject());
            }
            if (!entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().has("players")) {
                entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().add("players", new JsonObject());
            }
            if (!entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().has(complete_username)) {
                entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().add(complete_username, new JsonObject());
            }
            entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().get(complete_username).getAsJsonObject().add("inventory", inventory.toJSON());
            entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().get(complete_username).getAsJsonObject().addProperty("health", health);
            entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().get(complete_username).getAsJsonObject().addProperty("shield", shield);
            FileWriter writer = new FileWriter(saveFilePath);
            writer.write(Util.JSONObjectToString(entireJson));
            writer.close();
        }
    }
    public static PlayerInventory getInventory(String guildID, User user) throws IOException {
        String contents = Util.readFile(Jonesy.saveFilePath);
        String complete_username = Util.getUsername(user);
        JsonObject entireJson = new Gson().fromJson(contents, JsonObject.class);
        return PlayerInventory.fromJSON(entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().get(complete_username).getAsJsonObject());
    }
    public static void initializeInventory(String guildID, User user) throws IOException {
        saveInventory(guildID, user, defaultInventory(), 100, 0);
    }
    public static boolean userHasCompleteData(String guildID, User user) throws IOException {
        String contents = Util.readFile(Jonesy.saveFilePath);
        JsonObject entireJson = new Gson().fromJson(contents, JsonObject.class);
        if (!entireJson.get("servers").getAsJsonObject().has(guildID)) return false;
        if (!entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().has("players")) return false;
        if (!entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().has(Util.getUsername(user))) return false;
        if (!entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().get(Util.getUsername(user)).getAsJsonObject().has("inventory")) return false;
        if (!entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().get(Util.getUsername(user)).getAsJsonObject().has("health")) return false;
        if (!entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().get(Util.getUsername(user)).getAsJsonObject().has("shield")) return false;
        return true;
    }
    public static PlayerInventory defaultInventory() {
        return PlayerInventory.fromJSON(new Gson().fromJson("" +
            "{\"slot1\":{\"id\":\"empty\",\"stack_size\":1},\"slot2\":{\"id\":\"empty\",\"stack_size\":1},\"slot3\":{\"id\":\"empty\",\"stack_size\":1},\"slot4\":{\"id\":\"empty\",\"stack_size\":1},\"slot5\":{\"id\":\"empty\",\"stack_size\":1}}",
            JsonObject.class));
    }
    public static AbstractItem getItem(String guildID, User user, int index) throws IOException {
        PlayerInventory inventory = getInventory(guildID, user);
        return inventory.getItemInSlot(index);
    }
    public static PlayerData getPlayerData(String guildID, User user) throws IOException {
        if (!userHasCompleteData(guildID, user)) {
            initializeInventory(guildID, user);
        }
        String contents = Util.readFile(Jonesy.saveFilePath);
        String complete_username = Util.getUsername(user);
        JsonObject entireJson = new Gson().fromJson(contents, JsonObject.class);
        return PlayerData.fromJSON(entireJson.get("servers").getAsJsonObject().get(guildID).getAsJsonObject().get("players").getAsJsonObject().get(complete_username).getAsJsonObject());
    }
    public static int getHealth(String guildID, User user) throws IOException {
        return getPlayerData(guildID, user).health;
    }
    public static int getShield(String guildID, User user) throws IOException {
        return getPlayerData(guildID, user).shield;
    }
    public static void savePlayerData(String guildID, User user, PlayerData data, int health, int shield) throws IOException {
        saveInventory(guildID, user, data.inventory, health, shield);
    }
}
