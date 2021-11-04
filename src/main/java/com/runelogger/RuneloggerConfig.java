package com.runelogger;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("runelogger")
public interface RuneloggerConfig extends Config
{
    @ConfigItem(
            position = 1,
            keyName = "levelingUp",
            name = "Level up",
            description = "Share reached new skill (and combat) levels."
    )
    default boolean levelingUp() {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "questCompletion",
            name = "Quest Completion",
            description = "Share your quest completions."
    )
    default boolean questCompletion() {
        return true;
    }

    @ConfigItem(
            position = 3,
            keyName = "bosses",
            name = "Bosses",
            description = "Share defeating bosses."
    )
    default boolean bosses() {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "skillingBosses",
            name = "Skilling Bosses",
            description = "Share defeating skilling bosses."
    )
    default boolean skillingBosses() {
        return true;
    }

    @ConfigItem(
            position = 5,
            keyName = "collectionLog",
            name = "Collection Log",
            description = "Share new additions to your collection log."
    )
    default boolean collectionLog() {
        return true;
    }

    @ConfigItem(
            position = 6,
            keyName = "clueScrollCompletion",
            name = "Clue Scroll Completions",
            description = "Share completing clue scrolls."
    )
    default boolean clueScrollCompletion() {
        return true;
    }

    @ConfigItem(
            position = 7,
            keyName = "duelArena",
            name = "Duel Arena",
            description = "Share result from your duels in the Duel Arena."
    )
    default boolean duelArena() {
        return false;
    }

    @ConfigItem(
            position = 8,
            keyName = "musicUnlocks",
            name = "Music Unlocks",
            description = "Share unlocking new music tracks."
    )
    default boolean musicUnlocks() {
        return false;
    }

    @ConfigItem(
            position = 9,
            keyName = "tutorialIsland",
            name = "Tutorial Island",
            description = "Share finishing Tutorial Island."
    )
    default boolean tutorialIsland() {
        return true;
    }
}