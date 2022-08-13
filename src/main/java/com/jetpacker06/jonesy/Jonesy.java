package com.jetpacker06.jonesy;

import com.jetpacker06.jonesy.command.FNCommands;
import com.jetpacker06.jonesy.save.SaveData;
import com.jetpacker06.jonesy.util.Util;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Jonesy {
    public static String saveFilePath = "data.json";
    public static boolean logspam = true;
    public static String BOT_KEY = System.getenv("B3DiscordBotKey");
    public static GenericEvent recentEvent;
    private static SlashCommandInteractionEvent recentCommandEvent;
    public static SlashCommandInteractionEvent getRecentCommandEvent() {
        try {
            if (!SaveData.userHasCompleteData(recentCommandEvent.getGuild().getId(), recentCommandEvent.getUser())) {
                SaveData.initializeInventory(recentCommandEvent.getGuild().getId(), recentCommandEvent.getUser());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recentCommandEvent;
    }
    public static void setRecentCommandEvent(SlashCommandInteractionEvent event) {
        recentCommandEvent = event;
    }
    public static void log(Object message) {
        if (logspam) System.out.println(message);
    }
    public static void main(String[] args) throws LoginException {

        JDA jda = JDABuilder.createDefault(BOT_KEY)
            .addEventListeners(new FNCommands())
            .setStatus(OnlineStatus.ONLINE)
            .setActivity(Activity.watching("\u200E"))
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .build();

        FNCommands.registerSlashCommands(jda);

        System.out.println("Roger roger.");
    }
}