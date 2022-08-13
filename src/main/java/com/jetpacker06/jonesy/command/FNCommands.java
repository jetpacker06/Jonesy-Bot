package com.jetpacker06.jonesy.command;

import com.jetpacker06.jonesy.Jonesy;
import com.jetpacker06.jonesy.inventory.InventorySlot;
import com.jetpacker06.jonesy.item.types.AbstractStackableItem;
import com.jetpacker06.jonesy.item.types.EmptyItem;
import com.jetpacker06.jonesy.save.SaveData;
import com.jetpacker06.jonesy.util.Util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FNCommands extends ListenerAdapter {
    public static Map<String, Runnable> slashCommandsMap = new HashMap<>();
    public static void registerSlashCommands(JDA jda) {
        CommandListUpdateAction commands = jda.updateCommands();
        addCommand(commands, "inventory", "Lists your inventory.", () -> {
            SlashCommandInteractionEvent event = Jonesy.getRecentCommandEvent();
            User user = event.getUser();
            event.deferReply().queue();
            try {
                if (!SaveData.userHasCompleteData(event.getGuild().getId(), event.getUser())) {
                    SaveData.initializeInventory(event.getGuild().getId(), event.getUser());
                }
                EmbedBuilder inventory_builder = Util.blueBuilder();
                inventory_builder.setTitle(user.getName() + "'s Inventory");
                for (int i = 1; i < 6; i++) {
                    InventorySlot slot = SaveData.getInventory(event.getGuild().getId(), user).getSlot(i);
                    if (slot.item instanceof AbstractStackableItem) {
                        inventory_builder.addField(new MessageEmbed.Field(String.valueOf(i), slot.item.display_name + " x" + String.valueOf(slot.stack_size), false));
                    } else {
                        inventory_builder.addField(new MessageEmbed.Field(String.valueOf(i), slot.item.display_name, false));
                    }
                }
                event.getHook().sendMessageEmbeds(inventory_builder.build()).queue();

            } catch (IOException e) {
                event.getHook().sendMessage("An error occurred mb").queue();
                throw new RuntimeException(e);
            }
        });
        addCommand(commands, "highlight", "Show a specific item in your inventory.", () -> {
            SlashCommandInteractionEvent event = Jonesy.getRecentCommandEvent();
            User user = event.getUser();
            event.deferReply().queue();
            int slotnumber = event.getOption("slot").getAsInt();
            try {
                InventorySlot slot = SaveData.getInventory(event.getGuild().getId(), user).getSlot(slotnumber);
                EmbedBuilder builder = Util.coloredEmbedBuilder(Util.getColorForRarity(slot.item.rarity));
                if (slot.item instanceof EmptyItem) {
                    builder.setTitle("It's empty.");
                    event.getHook().sendMessageEmbeds(builder.build()).queue();
                    return;
                }
                builder.setImage(Util.getImageFor(slot.item.id));
                builder.setTitle(user.getName() + "'s " + slot.item.display_name);
                if (slot.item instanceof AbstractStackableItem) {
                    builder.addField("Amount: ", String.valueOf(slot.stack_size), true);
                }
                event.getHook().sendMessageEmbeds(builder.build()).queue();
            } catch (IOException e) {
                event.getHook().sendMessage("An error occurred mb").queue();
                throw new RuntimeException(e);
            }

        },
            new CommandField(OptionType.INTEGER, "slot", "Which slot to highlight.", true)
        );
        addCommand(commands, "health", "Display your health and shield", () -> {
            sendHealthAndShield();
        });
        addCommand(commands, "think", "Hmm...", () -> {
            SlashCommandInteractionEvent event = Jonesy.getRecentCommandEvent();
            event.deferReply().queue();
        });
        commands.queue();
    }
    private static void sendHealthAndShield() {
        try {
            SlashCommandInteractionEvent event = Jonesy.getRecentCommandEvent();
            User user = event.getUser();
            String guildID = event.getGuild().getId();
            EmbedBuilder builder = Util.blueBuilder().setTitle(Util.getUsername(user) + "'s stats");
            builder.addField("Health", String.valueOf(SaveData.getHealth(guildID, user)), true);
            builder.addField("Shield", String.valueOf(SaveData.getShield(guildID, user)), true);
            event.reply(new MessageBuilder(builder.build()).build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
            Jonesy.getRecentCommandEvent().reply("Something went wrong").queue();
        }
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandSent = event.getName();
        if (Util.isStringInList(commandSent, slashCommandsMap.keySet().toArray(new String[0]))) {
            slashCommandsMap.get(commandSent).run();
        }
    }
    public static void addCommand(CommandListUpdateAction commands, String name, String description, Runnable runnable, CommandField... fields) {
        SlashCommandData command = net.dv8tion.jda.api.interactions.commands.build.Commands.slash(name, description);
        for (CommandField field : fields) {
            command.addOption(field.optionType(), field.name(), field.description());
        }
        commands.addCommands(command);
        slashCommandsMap.put(name, runnable);
    }
    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        Jonesy.recentEvent = event;
        if (event instanceof SlashCommandInteractionEvent) {
            Jonesy.setRecentCommandEvent(((SlashCommandInteractionEvent) event));
        }
    }
}
