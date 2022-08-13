package com.jetpacker06.B3.item.types;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jetpacker06.B3.item.AbstractItem;
import com.jetpacker06.B3.item.ItemRegistry;
import com.jetpacker06.B3.util.Util;

public class ConsumableItem extends AbstractStackableItem {
    public int damage;
    public int hit_chance;
    public ConsumableItem(String id, String display_name, int cooldown, int max_stack_size, int damage, int hit_chance, Rarity rarity) {
        super(ItemType.CONSUMABLE, id, display_name, cooldown, max_stack_size, rarity);
        this.damage = damage;
        this.hit_chance = hit_chance;
    }
    @Override
    public JsonObject toJSONObject() {
        JsonObject o = new JsonObject();
        o.add("id", new JsonPrimitive(this.id));
        o.add("display_name", new JsonPrimitive(this.display_name));
        o.add("cooldown", new JsonPrimitive(this.cooldown));
        o.add("max_stack_size", new JsonPrimitive(this.max_stack_size));
        o.add("damage", new JsonPrimitive(this.damage));
        o.add("hit_chance", new JsonPrimitive(this.hit_chance));
        o.add("item_type", new JsonPrimitive(this.item_type.toString()));
        o.add("rarity", new JsonPrimitive(this.rarity.toString()));
        return o;
    }

    public static AbstractItem fromJSONObject(JsonObject json) {
        json = ItemRegistry.gameItems.get(json.get("id").getAsString()).getAsJsonObject();
        return new ConsumableItem(
                json.get("id").getAsString(),
                json.get("display_name").getAsString(),
                json.get("cooldown").getAsInt(),
                json.get("max_stack_size").getAsInt(),
                json.get("damage").getAsInt(),
                json.get("hit_chance").getAsInt(),
                Util.rarityFromString(json.get("rarity").getAsString())
        );
    }
}
