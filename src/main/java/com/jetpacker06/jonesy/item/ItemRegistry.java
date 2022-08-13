package com.jetpacker06.jonesy.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jetpacker06.jonesy.item.types.*;
import com.jetpacker06.jonesy.util.Util;

import java.util.HashMap;

import static com.jetpacker06.jonesy.Jonesy.log;

public class ItemRegistry {
    public static HashMap<String, String> itemGIFMap = new HashMap<>();
    public static JsonObject gameItems = new JsonObject();
    public static JsonArray gameItemIDs = new JsonArray();

    private static ConsumableItem addConsumable(String id, String display_name, int cooldown, int damage, int hit_chance, int max_stack_size, Rarity rarity, String gif_link) {
        ConsumableItem item = new ConsumableItem(id, display_name, cooldown, max_stack_size, damage, hit_chance, rarity);
        itemGIFMap.put(id, gif_link);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    private static GunItem addGun(String id, String display_name, int cooldown, int damage, int shots_per_turn, int hit_chance, Rarity rarity, String gif_link) {
        GunItem item = new GunItem(id, display_name, cooldown, damage, shots_per_turn, hit_chance, rarity);
        itemGIFMap.put(id, gif_link);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    private static MeleeItem addMelee(String id, String display_name, int cooldown, int damage, int shots_per_turn, int hit_chance, Rarity rarity, String gif_link) {
        MeleeItem item = new MeleeItem(id, display_name, cooldown, damage,shots_per_turn, hit_chance, rarity);
        itemGIFMap.put(id, gif_link);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    private static HealingItem addHealing(String id, String display_name, int cooldown, int healing_amount, int max_stack_size, Rarity rarity, String gif_link) {
        HealingItem item = new HealingItem(id, display_name, cooldown, healing_amount, max_stack_size, rarity);
        itemGIFMap.put(id, gif_link);
        JsonObject o = item.toJSONObject();
        gameItems.add(id, o);
        gameItemIDs.add(id);
        log("Registered new item " + id);
        return item;
    }
    private static ShieldingItem addShielding(String id, String display_name, int cooldown, int shielding_amount, int max_stack_size, Rarity rarity, String gif_link) {
        ShieldingItem item = new ShieldingItem(id, display_name, cooldown, shielding_amount, max_stack_size, rarity);
        itemGIFMap.put(id, gif_link);
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

    public static ConsumableItem grenade = addConsumable("grenade", "Grenade", 1, 100, 50, 10, Rarity.COMMON, "https://heavy.com/wp-content/uploads/2020/04/fortnite-grenade-return.jpg?quality=65&strip=all&w=780");
    public static ConsumableItem boogie_bomb = addConsumable("boogie_bomb", "Boogie Bomb", 1, 100, 50, 10, Rarity.COMMON, "https://earlygame.com/uploads/images/_1200x630_crop_center-center_82_none/boogie-bombs.jpg?mtime=1658419882");

    public static GunItem shotgun =  addGun("shotgun", "Shotgun", 2, 70, 1, 75, Rarity.RARE, "https://library.sportingnews.com/styles/facebook_1200x630/s3/2021-08/pump-shotgun-fortnite-ftr_1uyjreuyqvygl1fz5rvaorr20k.png?itok=c7JyimDy");
    public static GunItem tactical_shotgun =  addGun("tactical_shotgun", "Tactical Shotgun", 1, 35, 3, 60, Rarity.UNCOMMON, "https://staticg.sportskeeda.com/editor/2020/09/e2bb8-15989089107238-800.jpg");
    public static GunItem assault_rifle =  addGun("assault_rifle", "Assault Rifle", 2, 35, 4, 60, Rarity.LEGENDARY, "https://cdn.dribbble.com/users/61153/screenshots/4771003/scar-loop-2.gif");
    public static GunItem bold_action_sniper_rifle =  addGun("bold_action_sniper_rifle", "Bolt-Action Sniper Rifle", 2, 70, 1, 75, Rarity.LEGENDARY, "");
    public static GunItem submachine_gun =  addGun("submachine_gun", "Submachine Gun", 2, 35, 4, 60, Rarity.COMMON, "https://cdn2.unrealengine.com/Fortnite%2Fpatch-notes%2Fv5-0-content-update%2Fheader-v5-0-content-update%2FBR05_Social_-MP5-1920x1080-92d8fbc439d938a06236ba319bbc64396ad2d3ca.jpg");

    public static HealingItem med_kit =  addHealing("med_kit", "Med Kit", 3, 100, 3, Rarity.UNCOMMON, "https://i.pinimg.com/originals/42/da/3e/42da3e66055e6bd322eb4dece17a0e02.png");
    public static HealingItem bandage =  addHealing("bandage", "Bandage", 1, 15, 15, Rarity.COMMON, "https://gameplace.b-cdn.net/wp-content/uploads/2022/02/image-4284.png");

    public static ShieldingItem mini_shield = addShielding("mini_shield", "Mini Shield Potion", 1, 25, 10, Rarity.UNCOMMON, "https://staticg.sportskeeda.com/editor/2019/01/cca02-15476973703577-800.jpg");
    public static ShieldingItem shield = addShielding("shield", "Shield Potion", 1, 50, 3, Rarity.UNCOMMON, "https://cdn1.dotesports.com/wp-content/uploads/2019/01/18061322/Shield.jpg");

    public static EmptyItem emptyItem = addEmpty("empty", "Empty", 0, Rarity.COMMON);
}