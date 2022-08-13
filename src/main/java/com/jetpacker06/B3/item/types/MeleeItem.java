package com.jetpacker06.B3.item.types;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jetpacker06.B3.item.AbstractItem;
import com.jetpacker06.B3.item.ItemRegistry;
import com.jetpacker06.B3.util.Util;

public class MeleeItem extends AbstractItem {
    public int damage;
    public int shots_per_turn;
    public int hit_chance;
    public MeleeItem(String id, String display_name, int cooldown, int damage, int shots_per_turn, int hit_chance, Rarity rarity) {
        super(ItemType.GUN, id, display_name, cooldown, rarity);
        this.damage = damage;
        this.shots_per_turn = shots_per_turn;
        this.hit_chance = hit_chance;
    }
    @Override
    public JsonObject toJSONObject() {
        JsonObject o = new JsonObject();
        o.add("id", new JsonPrimitive(this.id));
        o.add("display_name", new JsonPrimitive(this.display_name));
        o.add("cooldown", new JsonPrimitive(this.cooldown));
        o.add("damage", new JsonPrimitive(this.damage));
        o.add("shots_per_turn", new JsonPrimitive(this.shots_per_turn));
        o.add("hit_chance", new JsonPrimitive(this.hit_chance));
        o.add("item_type", new JsonPrimitive(this.item_type.toString()));
        o.add("rarity", new JsonPrimitive(this.rarity.toString()));
        return o;
    }

    public static AbstractItem fromJSONObject(JsonObject json) {
        json = ItemRegistry.gameItems.get(json.get("id").getAsString()).getAsJsonObject();
        return new MeleeItem(
                json.get("id").getAsString(),
                json.get("display_name").getAsString(),
                json.get("cooldown").getAsInt(),
                json.get("damage").getAsInt(),
                json.get("shots_per_turn").getAsInt(),
                json.get("hit_chance").getAsInt(),
                Util.rarityFromString(json.get("rarity").getAsString())
        );
    }
}
