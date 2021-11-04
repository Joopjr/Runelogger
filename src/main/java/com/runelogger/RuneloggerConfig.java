package com.runelogger;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("runelogger")
public interface RuneloggerConfig extends Config
{
    @ConfigSection(
            name = "Skilling",
            description = "Choose the skilling activities to send to Runelogger.",
            position = 0,
            closedByDefault = false
    )
    String skilling = "skilling";
    @ConfigItem(
            position = 1,
            keyName = "levelingUp",
            name = "Leveling up skills",
            description = "Send reaching new skill (and combat) levels to Runelogger.",
            section = skilling
    )
    default boolean levelingUp() {
        return true;
    }
    @ConfigItem(
            position = 2,
            keyName = "skillingBosses",
            name = "Defeating skilling bosses",
            description = "Send defeating skilling bosses and your score to Runelogger.",
            section = skilling
    )
    default boolean skillingBosses() {
        return true;
    }

    @ConfigSection(
            name = "Combat",
            description = "Choose the combat activities to send to Runelogger.",
            position = 10,
            closedByDefault = false
    )
    String combat = "combat";
    @ConfigItem(
            position = 11,
            keyName = "bosses",
            name = "Killing bosses",
            description = "Send defeating bosses to Runelogger.",
            section = combat
    )
    default boolean bosses() {
        return true;
    }
    @ConfigItem(
            position = 12,
            keyName = "duelArena",
            name = "Duel arena results",
            description = "Send results from your duels in the Duel Arena to Runelogger.",
            section = combat
    )
    default boolean duelArena() {
        return false;
    }

    @ConfigSection(
            name = "Story / Lore",
            description = "Choose the story activities to send to Runelogger.",
            position = 20,
            closedByDefault = false
    )
    String stories = "stories";
    @ConfigItem(
            position = 21,
            keyName = "questCompletion",
            name = "Quest completions",
            description = "Send your quest completions to Runelogger.",
            section = stories
    )
    default boolean questCompletion() { return true; }

    @ConfigSection(
            name = "Item unlocks/rewards",
            description = "Choose the item unlocks/rewards to send to Runelogger.",
            position = 30,
            closedByDefault = false
    )
    String items = "items";
    @ConfigItem(
            position = 31,
            keyName = "clueScrollCompletion",
            name = "Clue scroll completions",
            description = "Send completing clue scrolls to Runelogger.",
            section = items
    )
    default boolean clueScrollCompletion() { return true; }
    @ConfigItem(
            position = 32,
            keyName = "collectionLog",
            name = "Collection log entries",
            description = "Send new additions to your collection log to Runelogger.",
            section = items
    )
    default boolean collectionLog() { return true; }

    @ConfigSection(
            name = "Other",
            description = "Choose other smaller activities/unlocks to send to Runelogger.",
            position = 40,
            closedByDefault = true
    )
    String others = "others";
    @ConfigItem(
            position = 41,
            keyName = "musicUnlocks",
            name = "Music Unlocks",
            description = "Send unlocking new music tracks to Runelogger.",
            section = others
    )
    default boolean musicUnlocks() { return false; }
    @ConfigItem(
            position = 42,
            keyName = "tutorialIsland",
            name = "Tutorial Island",
            description = "Send finishing Tutorial Island to Runelogger.",
            section = others
    )
    default boolean tutorialIsland() { return false; }
}