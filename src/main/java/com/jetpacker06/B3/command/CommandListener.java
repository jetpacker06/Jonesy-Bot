package com.jetpacker06.B3.command;

import com.jetpacker06.B3.B3;
import com.jetpacker06.B3.util.Util;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandListener extends ListenerAdapter {
    public static Map<String, Runnable> slashCommandsMap = new HashMap<>();
    public static void registerSlashCommands(JDA jda) {
        CommandListUpdateAction commands = jda.updateCommands();
        addCommand(commands, "hello", "say hello", () -> {
            SlashCommandInteractionEvent event = ((SlashCommandInteractionEvent) B3.recentCommandEvent);
            event.reply("Hello").queue();
            }
        );
        commands.queue();
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandSent = event.getName();
        if (Util.isStringInList(commandSent, slashCommandsMap.keySet().toArray(new String[0]))) {
            slashCommandsMap.get(commandSent).run();
        }
    }
    public static void addCommand(CommandListUpdateAction commands, String name, String description, Runnable runnable, CommandField... fields) {
        SlashCommandData command = Commands.slash(name, description);
        for (CommandField field : fields) {
            command.addOption(field.optionType(), field.name(), field.description());
        }
        commands.addCommands(command);
        slashCommandsMap.put(name, runnable);
    }
    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        B3.recentEvent = event;
        if (event instanceof SlashCommandInteractionEvent) {
            B3.recentCommandEvent = ((SlashCommandInteractionEvent) event);
        }
    }
}
