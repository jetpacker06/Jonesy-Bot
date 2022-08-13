package com.jetpacker06.jonesy.item.types;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jetpacker06.jonesy.item.AbstractItem;
import com.jetpacker06.jonesy.item.ItemRegistry;
import com.jetpacker06.jonesy.util.Util;

public class ShieldingItem extends AbstractStackableItem {
    public int shielding_amount;
    public ShieldingItem(String id, String display_name, int cooldown, int shielding_amount, int max_stack_size, Rarity rarity) {
        super(ItemType.SHIELDING, id, display_name, cooldown, max_stack_size, rarity);
        this.shielding_amount = shielding_amount;
    }
    @Override
    public JsonObject toJSONObject() {
        JsonObject o = new JsonObject();
        o.add("id", new JsonPrimitive(this.id));
        o.add("display_name", new JsonPrimitive(this.display_name));
        o.add("cooldown", new JsonPrimitive(this.cooldown));
        o.add("shielding_amount", new JsonPrimitive(this.shielding_amount));
        o.add("max_stack_size", new JsonPrimitive(this.max_stack_size));
        o.add("item_type", new JsonPrimitive(this.item_type.toString()));
        o.add("rarity", new JsonPrimitive(this.rarity.toString()));
        return o;
    }

    public static AbstractItem fromJSONObject(JsonObject json) {
        json = ItemRegistry.gameItems.get(json.get("id").getAsString()).getAsJsonObject();
        return new ShieldingItem(
                json.get("id").getAsString(),
                json.get("display_name").getAsString(),
                json.get("cooldown").getAsInt(),
                json.get("shielding_amount").getAsInt(),
                json.get("max_stack_size").getAsInt(),
                Util.rarityFromString(json.get("rarity").getAsString())
        );
    }
}
