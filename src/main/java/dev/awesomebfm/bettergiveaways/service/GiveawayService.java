package dev.awesomebfm.bettergiveaways.service;

import dev.awesomebfm.bettergiveaways.dto.CreateGiveawayDto;
import dev.awesomebfm.bettergiveaways.entity.Giveaway;
import dev.awesomebfm.bettergiveaways.repository.GiveawayRepository;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Giveaway");
        embedBuilder.setDescription("React with \uD83C\uDF89 to enter!");
        embedBuilder.addField("Prize", giveaway.getPrize(), false);
        embedBuilder.addField("Winners", String.valueOf(giveaway.getWinners()), false);
        embedBuilder.addField("Ends", giveaway.getGiveawayEnd().toString(), false);
        embedBuilder.addField("Donor", "<@" + giveaway.getDonorId() + ">", false);
        return embedBuilder;
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
