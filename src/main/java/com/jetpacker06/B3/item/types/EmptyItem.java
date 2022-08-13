package com.jetpacker06.B3.item.types;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jetpacker06.B3.item.AbstractItem;
import com.jetpacker06.B3.item.ItemRegistry;

public class EmptyItem extends AbstractItem {
    public EmptyItem(String id, String display_name, int cooldown, Rarity rarity) {
        super(ItemType.EMPTY, id, display_name, cooldown, rarity);
    }
    @Override
    public JsonObject toJSONObject() {
        JsonObject o = new JsonObject();
        o.add("id", new JsonPrimitive(this.id));
        o.add("display_name", new JsonPrimitive(this.display_name));
        o.add("cooldown", new JsonPrimitive(this.cooldown));
        o.add("item_type", new JsonPrimitive(this.item_type.toString()));
        o.add("rarity", new JsonPrimitive(this.rarity.toString()));
        return o;
    }

    public static AbstractItem fromJSONObject(JsonObject json) {
        return ItemRegistry.emptyItem;
    }
}
