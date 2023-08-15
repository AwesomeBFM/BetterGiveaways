package dev.awesomebfm.bettergiveaways.model;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public interface SlashCommand {

    String getName();
    String getDescription();
    OptionData[] getOptions();
    Permission getRequiredPermission();
    void execute(SlashCommandInteractionEvent e);

}
