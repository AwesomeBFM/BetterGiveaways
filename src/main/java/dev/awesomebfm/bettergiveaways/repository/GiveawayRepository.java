package dev.awesomebfm.bettergiveaways.repository;

import dev.awesomebfm.bettergiveaways.entity.Giveaway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiveawayRepository extends JpaRepository<Giveaway, Long> {

    List<Giveaway> findGiveawaysByGuildId(long guildId);
}
