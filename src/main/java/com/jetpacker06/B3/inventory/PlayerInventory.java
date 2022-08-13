package com.jetpacker06.B3.inventory;

import com.google.gson.JsonObject;

public class PlayerInventory {
    public InventorySlot slot1;
    public InventorySlot slot2;
    public InventorySlot slot3;
    public InventorySlot slot4;
    public InventorySlot slot5;
    public PlayerInventory(InventorySlot slot1, InventorySlot slot2, InventorySlot slot3, InventorySlot slot4, InventorySlot slot5) {
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        this.slot4 = slot4;
        this.slot5 = slot5;
    }
    //TODO: to/from JSON
    public static PlayerInventory fromJSON(JsonObject json) {
        return new PlayerInventory(
            InventorySlot.fromJSONObject(json.get("slot1").getAsJsonObject()),
            InventorySlot.fromJSONObject(json.get("slot2").getAsJsonObject()),
            InventorySlot.fromJSONObject(json.get("slot3").getAsJsonObject()),
            InventorySlot.fromJSONObject(json.get("slot4").getAsJsonObject()),
            InventorySlot.fromJSONObject(json.get("slot5").getAsJsonObject())
        );
    }
    public JsonObject toJSON() {
        JsonObject o = new JsonObject();
        o.add("slot1", this.slot1.toJSONObject());
        o.add("slot2", this.slot1.toJSONObject());
        o.add("slot3", this.slot1.toJSONObject());
        o.add("slot4", this.slot1.toJSONObject());
        o.add("slot5", this.slot1.toJSONObject());
        return o;
    }
}