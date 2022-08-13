package com.jetpacker06.B3.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jetpacker06.B3.item.types.*;
import com.jetpacker06.B3.util.Util;

import static com.jetpacker06.B3.B3.log;

public class ItemRegistry {
    public static JsonObject gameItems = new JsonObject();
    public static JsonArray gameItemIDs = new JsonArray();

    private static ConsumableItem addConsumable(String id, String display_name, int cooldown, int damage, int hit_chance, int max_stack_size, Rarity rarity) {
        ConsumableItem item = new ConsumableItem(id, display_name, cooldown, max_stack_size, damage, hit_chance, rarity);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    private static GunItem addGun(String id, String display_name, int cooldown, int damage, int shots_per_turn, int hit_chance, Rarity rarity) {
        GunItem item = new GunItem(id, display_name, cooldown, damage, shots_per_turn, hit_chance, rarity);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    private static MeleeItem addMelee(String id, String display_name, int cooldown, int damage, int shots_per_turn, int hit_chance, Rarity rarity) {
        MeleeItem item = new MeleeItem(id, display_name, cooldown, damage,shots_per_turn, hit_chance, rarity);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    private static HealingItem addHealing(String id, String display_name, int cooldown, int healing_amount, int max_stack_size, Rarity rarity) {
        HealingItem item = new HealingItem(id, display_name, cooldown, healing_amount, max_stack_size, rarity);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    private static ShieldingItem addShielding(String id, String display_name, int cooldown, int shielding_amount, int max_stack_size, Rarity rarity) {
        ShieldingItem item = new ShieldingItem(id, display_name, cooldown, shielding_amount, max_stack_size, rarity);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    private static EmptyItem addEmpty(String id, String display_name, int cooldown, Rarity rarity) {
        EmptyItem item = new EmptyItem(id, display_name, cooldown, rarity);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    public static boolean doesItemExist(String item_id) {
        return gameItemIDs.contains(new JsonPrimitive(item_id));
    }
    public static String getItemDisplayName(String item_id) {
        if (!doesItemExist(item_id)) {
            throw new NullPointerException("Item does not exist: " + item_id);
        }
        return gameItems.get(item_id).getAsJsonObject().get("display_name").getAsString();
    }
    public static AbstractItem getItemByID(String item_id) {
        if (!doesItemExist(item_id)) {
            throw new NullPointerException("Item does not exist: " + item_id);
        }
        JsonObject json = gameItems.get(item_id).getAsJsonObject();
        AbstractItem item;
        switch (Util.itemTypeFromString(gameItems.get(item_id).getAsJsonObject().get("item_type").getAsString())) {
            case GUN -> item = GunItem.fromJSONObject(json);
            case MELEE -> item = MeleeItem.fromJSONObject(json);
            case CONSUMABLE -> item = ConsumableItem.fromJSONObject(json);
            case HEALING -> item = HealingItem.fromJSONObject(json);
            case SHIELDING -> item = ShieldingItem.fromJSONObject(json);
            default -> item = EmptyItem.fromJSONObject(json);
        }
        return item;
    }

    public static ConsumableItem grenade = addConsumable("grenade", "Grenade", 1, 100, 50, 10, Rarity.COMMON);
    public static GunItem shotgun =  addGun("shotgun", "Shotgun", 2, 70, 1, 75, Rarity.COMMON);
    public static HealingItem med_kit =  addHealing("med_kit", "Med Kit", 3, 100, 3, Rarity.UNCOMMON);
    public static ShieldingItem mini_shield = addShielding("mini_shield", "mini_shield", 1, 25, 10, Rarity.UNCOMMON);
    public static EmptyItem emptyItem = addEmpty("empty", "Empty", 0, Rarity.COMMON);

}
