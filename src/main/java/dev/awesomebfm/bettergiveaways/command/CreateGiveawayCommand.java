package dev.awesomebfm.bettergiveaways.command;

import dev.awesomebfm.bettergiveaways.dto.CreateGiveawayDto;
import dev.awesomebfm.bettergiveaways.entity.Giveaway;
import dev.awesomebfm.bettergiveaways.model.SlashCommand;
import dev.awesomebfm.bettergiveaways.service.GiveawayService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateGiveawayCommand implements SlashCommand {

    private final GiveawayService giveawayService;

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a new giveaway";
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[] {
                new OptionData(OptionType.STRING, "prize", "The prize of the giveaway", true),
                new OptionData(OptionType.INTEGER, "winners", "The number of winners", true),
                new OptionData(OptionType.STRING, "end", "The end time of the giveaway", true),
                new OptionData(OptionType.USER, "donor", "The user who donated the prize", true),
                new OptionData(OptionType.ROLE, "required_role", "The role required to enter the giveaway", false)
        };
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.ADMINISTRATOR;
    }

    @Override
    public void execute(SlashCommandInteractionEvent e) {

        Long required_role;
        try {
            required_role = e.getOption("required_role").getAsLong();
        } catch (NullPointerException ex) {
            required_role = null;
        }


        CreateGiveawayDto dto = new CreateGiveawayDto(
                e.getOption("prize").getAsString(),
                e.getOption("winners").getAsInt(),
                e.getOption("end").getAsString(),
                e.getOption("donor").getAsUser().getIdLong(),
                e.getGuild().getIdLong(),
                required_role
        );

        Giveaway giveaway = giveawayService.createGiveaway(dto);
        EmbedBuilder embedBuilder = giveawayService.generateGiveawayEmbed(giveaway);
        e.replyEmbeds(embedBuilder.build()).addActionRow(
                Button.success("giveaway_" + giveaway.getId(), "Enter Giveaway!")
        ).queue();


    }
}
