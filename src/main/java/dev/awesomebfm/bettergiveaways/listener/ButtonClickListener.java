package dev.awesomebfm.bettergiveaways.listener;

import dev.awesomebfm.bettergiveaways.service.GiveawayService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ButtonClickListener extends ListenerAdapter {

    private final GiveawayService giveawayService;

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e) {
        if (!e.getComponentId().startsWith("giveaway_")) return;
        Long giveawayId = Long.parseLong(e.getComponentId().split("_")[1]);



    }


}
