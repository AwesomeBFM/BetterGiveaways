package dev.awesomebfm.bettergiveaways.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "giveaways")
public class Giveaway {

    @Id
    @GeneratedValue
    private long id;

    private String prize;
    private int winners;
    private Timestamp giveawayEnd;
    private long donorId;
    private long guildId;
    private Long requiredRoleId;
}
