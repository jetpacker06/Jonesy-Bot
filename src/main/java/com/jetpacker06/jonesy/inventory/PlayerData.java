package com.jetpacker06.jonesy.inventory;

import com.google.gson.JsonObject;

public class PlayerData {
    public PlayerInventory inventory;
    public int health;
    public int shield;
    public PlayerData(PlayerInventory inventory, int health, int shield) {
        this.inventory = inventory;
        this.health = health;
        this.shield = shield;
    }
    public static PlayerData fromJSON(JsonObject json) {
        return new PlayerData(
                PlayerInventory.fromJSON(json),
                json.get("health").getAsInt(),
                json.get("shield").getAsInt()
        );
    }
    public JsonObject toJSON() {
        JsonObject json = new JsonObject();
        json.add("inventory", this.inventory.toJSON());
        json.addProperty("health", this.health);
        json.addProperty("shield", this.shield);
        return json;
    }
}
