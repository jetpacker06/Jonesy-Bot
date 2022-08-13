package com.jetpacker06.jonesy.inventory;


import com.google.gson.JsonObject;
import com.jetpacker06.jonesy.item.AbstractItem;
import com.jetpacker06.jonesy.item.ItemRegistry;
import com.jetpacker06.jonesy.item.types.*;

public class InventorySlot {
    public AbstractItem item;
    public int stack_size;
    public InventorySlot(AbstractItem item, int stack_size) {
        this.item = item;
        if (item instanceof AbstractStackableItem) {
            this.stack_size = stack_size;
            return;
        }
        this.stack_size = 1;
    }
    public void decrementStack() {
        this.stack_size--;
        if (this.stack_size == 0) {
            this.item = ItemRegistry.emptyItem;
        }
    }
    public void decrementStack(int amount) {
        this.stack_size -= amount;
        if (this.stack_size == 0) {
            this.item = ItemRegistry.emptyItem;
        }
    }
    public JsonObject toJSONObject() {
        JsonObject o = new JsonObject();
        o.addProperty("id", this.item.id);
        o.addProperty("stack_size", this.stack_size);
        return o;
    }
    public static InventorySlot fromJSONObject(JsonObject json) {
        String item_id = json.get("id").getAsString();
        int stack_size = json.get("stack_size").getAsInt();
        ItemType type = ItemRegistry.getItemByID(item_id).item_type;
        AbstractItem item;
        switch (type) {
            case GUN -> item = GunItem.fromJSONObject(json);
            case MELEE -> item = MeleeItem.fromJSONObject(json);
            case CONSUMABLE -> item = ConsumableItem.fromJSONObject(json);
            case HEALING -> item = HealingItem.fromJSONObject(json);
            case SHIELDING -> item = ShieldingItem.fromJSONObject(json);
            default -> item = EmptyItem.fromJSONObject(json);
        }

        return new InventorySlot(item, stack_size);
    }
    public AbstractItem getItem() {
        return this.item;
    }
}
