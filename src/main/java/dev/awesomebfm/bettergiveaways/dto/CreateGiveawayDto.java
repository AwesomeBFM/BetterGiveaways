package dev.awesomebfm.bettergiveaways.dto;

public record CreateGiveawayDto(
        String prize,
        int winners,
        String duration,
        long donorId,
        long guildId,
        Long requiredRoleId
) { }
