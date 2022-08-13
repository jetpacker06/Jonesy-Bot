package com.jetpacker06.B3.item.types;

import com.jetpacker06.B3.item.AbstractItem;

public abstract class AbstractStackableItem extends AbstractItem {
    public AbstractStackableItem(ItemType item_type, String id, String display_name, int cooldown, int max_stack_size, Rarity rarity) {
        super(item_type, id, display_name, cooldown, rarity);
        this.max_stack_size = max_stack_size;
    }
    public int max_stack_size;
}
