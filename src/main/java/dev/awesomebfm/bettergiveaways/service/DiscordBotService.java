package dev.awesomebfm.bettergiveaways.service;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy(false)
public class DiscordBotService {

    @PostConstruct
    public void initializeBot() {
        JDA jda = JDABuilder.createDefault("").build();
    }

}
