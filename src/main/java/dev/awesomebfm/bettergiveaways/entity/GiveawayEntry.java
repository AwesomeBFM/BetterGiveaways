package dev.awesomebfm.bettergiveaways.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class GiveawayEntry {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long giveawayId;
    private Timestamp entryTimestamp;

}
