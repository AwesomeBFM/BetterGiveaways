package dev.awesomebfm.bettergiveaways.service;

import dev.awesomebfm.bettergiveaways.dto.CreateGiveawayDto;
import dev.awesomebfm.bettergiveaways.entity.Giveaway;
import dev.awesomebfm.bettergiveaways.repository.GiveawayRepository;
import dev.awesomebfm.bettergiveaways.entity.GiveawayEntry;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class GiveawayService {

    private final GiveawayRepository giveawayRepository;


    public Giveaway createGiveaway(CreateGiveawayDto createGiveawayDto) {
        Giveaway giveaway = new Giveaway();
        giveaway.setPrize(createGiveawayDto.prize());
        giveaway.setWinners(createGiveawayDto.winners());
        giveaway.setGiveawayEnd(convertDurationToTimestamp(createGiveawayDto.duration()));
        giveaway.setDonorId(createGiveawayDto.donorId());
        giveaway.setGuildId(createGiveawayDto.guildId());
        giveaway.setRequiredRoleId(createGiveawayDto.requiredRoleId());
        return giveawayRepository.save(giveaway);
    }

    public EmbedBuilder generateGiveawayEmbed(Giveaway giveaway) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("New Giveaway!");
        embed.setColor(Color.ORANGE);
        embed.addField("Prize", giveaway.getPrize(), true);
        embed.addField("Winners", String.valueOf(giveaway.getWinners()), true);
        embed.addField(
                "Donor",
                "<@" + giveaway.getDonorId() + ">",
                false
        );
        embed.addField(
                "Ends",
                "<t:" + ( giveaway.getGiveawayEnd().getTime() / 1000 ) + ":R>",
                false
        );
        embed.setFooter(String.valueOf(giveaway.getId()));
        return embed;
    }

    public GiveawayEntry addGiveawayEntry(Long giveawayId, Member user) {
        Optional<Giveaway> optionalGiveaway = giveawayRepository.findById(giveawayId);
        if (optionalGiveaway.isEmpty()) { return null; }
        Giveaway giveaway = optionalGiveaway.get();

        Long requiredRoleId = giveaway.getRequiredRoleId();
        return null;
    }


    private Timestamp convertDurationToTimestamp(String duration) {
        Pattern pattern = Pattern.compile("(\\d+)([yMwdhms])");
        Matcher matcher = pattern.matcher(duration);

        LocalDateTime now = LocalDateTime.now();
        Duration durationToAdd = Duration.ZERO;

        while (matcher.find()) {
            int amount = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "y":
                    durationToAdd = durationToAdd.plus(amount, ChronoUnit.YEARS);
                    break;
                case "M":
                    durationToAdd = durationToAdd.plus(amount, ChronoUnit.MONTHS);
                    break;
                case "w":
                    durationToAdd = durationToAdd.plus(amount, ChronoUnit.WEEKS);
                    break;
                case "d":
                    durationToAdd = durationToAdd.plus(amount, ChronoUnit.DAYS);
                    break;
                case "h":
                    durationToAdd = durationToAdd.plus(amount, ChronoUnit.HOURS);
                    break;
                case "m":
                    durationToAdd = durationToAdd.plus(amount, ChronoUnit.MINUTES);
                    break;
                case "s":
                    durationToAdd = durationToAdd.plus(amount, ChronoUnit.SECONDS);
                    break;
            }
        }

        LocalDateTime futureDateTime = now.plus(durationToAdd);
        return Timestamp.valueOf(futureDateTime);
    }

}
