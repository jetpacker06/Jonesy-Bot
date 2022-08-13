package com.jetpacker06.B3.command;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public record CommandField(OptionType optionType, String name, String description, boolean required) {
}
