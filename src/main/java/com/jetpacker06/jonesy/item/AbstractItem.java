package com.jetpacker06.jonesy.item;

import com.google.gson.JsonObject;
import com.jetpacker06.jonesy.item.types.ItemType;
import com.jetpacker06.jonesy.item.types.Rarity;

public abstract class AbstractItem {
    public AbstractItem(ItemType item_type, String id, String display_name, int cooldown, Rarity rarity) {
        this.item_type = item_type;
        this.id = id;
        this.display_name = display_name;
        this.cooldown = cooldown;
        this.rarity = rarity;
    }
    public String id;
    public String display_name;
    public ItemType item_type;
    public int cooldown;
    public Rarity rarity;
    public abstract JsonObject toJSONObject();
}
