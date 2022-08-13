package com.jetpacker06.jonesy.inventory;

import com.google.gson.JsonObject;
import com.jetpacker06.jonesy.item.AbstractItem;
import com.jetpacker06.jonesy.util.Util;

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
    public static PlayerInventory fromJSON(JsonObject json) {
        if (json.has("inventory")) {
            json = json.get("inventory").getAsJsonObject();
        }
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
        o.add("slot2", this.slot2.toJSONObject());
        o.add("slot3", this.slot3.toJSONObject());
        o.add("slot4", this.slot4.toJSONObject());
        o.add("slot5", this.slot5.toJSONObject());
        return o;
    }
    public AbstractItem getItemInSlot(int slot) {
        return switch (slot) {
            case 1 -> slot1.item;
            case 2 -> slot2.item;
            case 3 -> slot3.item;
            case 4 -> slot4.item;
            case 5 -> slot5.item;
            default -> throw new IllegalArgumentException("Parameter should be more than zero and less than six.");
        };
    }
    public void setItemInSlot(int slot, AbstractItem item) {
        switch (slot) {
            case 1 -> slot1.item = item;
            case 2 -> slot2.item = item;
            case 3 -> slot3.item = item;
            case 4 -> slot4.item = item;
            case 5 -> slot5.item = item;
            default -> throw new IllegalArgumentException("Parameter should be more than zero and less than six.");
        }
    }
    public InventorySlot getSlot(int index) {
        return switch (index) {
            case 1 -> slot1;
            case 2 -> slot2;
            case 3 -> slot3;
            case 4 -> slot4;
            case 5 -> slot5;
            default -> throw new IllegalArgumentException("Parameter should be more than zero and less than six.");
        };
    }
}