package com.jetpacker06.B3;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jetpacker06.B3.command.CommandListener;
import com.jetpacker06.B3.inventory.PlayerInventory;
import com.jetpacker06.B3.item.ItemRegistry;
import com.jetpacker06.B3.util.Util;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class B3 {
    public static boolean logspam = true;
    public static String BOT_KEY = System.getenv("B3DiscordBotKey");
    public static GenericEvent recentEvent;
    public static SlashCommandInteractionEvent recentCommandEvent;
    public static JsonObject dict;
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(BOT_KEY)
            .addEventListeners(new CommandListener())
            .setStatus(OnlineStatus.ONLINE)
            .setActivity(Activity.competing("WW5"))
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .build();

        CommandListener.registerSlashCommands(jda);

        System.out.println("Roger roger.");
        Util.logJSONObject(ItemRegistry.getItemByID("grenade").toJSONObject());
    }
    public static void log(Object message) {
        if (logspam) System.out.println(message);
    }
}