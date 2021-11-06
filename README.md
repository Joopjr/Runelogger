# Runelogger
The soul purpose of the plugin is to collect your player data/activities and display them on the [characters page](https://runelogger.com/character) at Runelogger.com.

![alt text](https://runelogger.com/assets/images/example_tracking.jpg)

## Character information
The plugin will send your character information to our server, this data will be stored in our DataBase. These include (but are not limited to) your character name, character status (iron man, normal), membership days, levels, quest points and quests status. Your username (which most likely is your email address) is received hashed from the Runelite plugin, that way we don't store it as plain text but can still use this hash to transfer your data when your character name changes.

### Activities
Depending on your plugin settings we may receive the following data from your RuneScape activities:

#### Skilling
* Reaching new skill levels.
* Defeating (skilling) bosses and the amount of points earned.

#### Combat
* Succesfully defeating bosses.
* Solo combat minigames (currently only TzHaar-Ket-Rak's Challenge).
* Your fight results from the Duel Arena.

#### Story / Lore
* Quest Completions.

#### Item unlocks/rewards
* Clue scroll completions.
* Receiving new entries in your collection log.

#### Other
* Unlocking new music tracks.

## Ip Address
Due to the way the internet is build we also receive your Ip Address when the plugin is sending data to our server. This Ip address is only stored in our server access logs (which are pruned automatically) and not in our DataBase.

## Third parties
We don't provide any data to third parties.

## Feedback
The plugin has been released for RuneLite on the 3rd of November. We are planning to keep expanding the plugin with new milestones/activities. If you've any suggestion for features you can contact us through Github or email.