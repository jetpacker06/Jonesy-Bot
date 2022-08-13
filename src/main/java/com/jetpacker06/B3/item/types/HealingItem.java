package com.jetpacker06.B3.item.types;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jetpacker06.B3.item.AbstractItem;
import com.jetpacker06.B3.item.ItemRegistry;
import com.jetpacker06.B3.util.Util;

public class HealingItem extends AbstractStackableItem {
    public int healing_amount;
    public HealingItem(String id, String display_name, int cooldown, int healing_amount, int max_stack_size, Rarity rarity) {
        super(ItemType.HEALING, id, display_name, cooldown, max_stack_size, rarity);
        this.healing_amount = healing_amount;
    }
    @Override
    public JsonObject toJSONObject() {
        JsonObject o = new JsonObject();
        o.add("id", new JsonPrimitive(this.id));
        o.add("display_name", new JsonPrimitive(this.display_name));
        o.add("cooldown", new JsonPrimitive(this.cooldown));
        o.add("healing_amount", new JsonPrimitive(this.healing_amount));
        o.add("max_stack_size", new JsonPrimitive(this.max_stack_size));
        o.add("item_type", new JsonPrimitive(this.item_type.toString()));
        o.add("rarity", new JsonPrimitive(this.rarity.toString()));
        return o;
    }

    public static AbstractItem fromJSONObject(JsonObject json) {
        json = ItemRegistry.gameItems.get(json.get("id").getAsString()).getAsJsonObject();
        return new HealingItem(
                json.get("id").getAsString(),
                json.get("display_name").getAsString(),
                json.get("cooldown").getAsInt(),
                json.get("healing_amount").getAsInt(),
                json.get("max_stack_size").getAsInt(),
                Util.rarityFromString(json.get("rarity").getAsString())
        );
    }
}
