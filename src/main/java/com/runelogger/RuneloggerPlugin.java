package com.runelogger;

import com.google.inject.Provides;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;


@Slf4j
@PluginDescriptor(
	name = "Runelogger",
	description = "Log your RuneScape journey.",
	tags = {"runelogger", "journey"}
)

public class RuneloggerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private RuneloggerConfig config;

	@Inject
	private SkillingBosses skillingBosses;

	@Inject
	private LevelingUp levelingUp;

	@Inject
	private QuestCompletion questCompletion;

	@Inject
	private MusicUnlock musicUnlock;

	@Inject
	private TutorialIsland tutorialIsland;

	@Inject
	private CollectionLog collectionLog;

	@Inject
	private Bosses bosses;

	@Inject
	private DuelArena duelArena;

	@Inject
	private ApiCommunication apiCommunication;

	private boolean characterInfoSend = false;

	//PLUGIN STARTED//
	@Override
	protected void startUp()
	{
		log.debug("Plugin active!");
	}

	//PLUGIN STOPPED//
	@Override
	protected void shutDown()
	{
		characterInfoSend = false;
		log.debug("Plugin inactive!");
	}

	/*
	ADD:
	String CHEST_LOOTED_MESSAGE = "You find some treasure in the chest!";
	BOSSKILL_MESSAGE_PATTERN = Pattern.compile("Your (.+) kill count is: <col=ff0000>(\\d+)</col>.");
	VALUABLE_DROP_PATTERN = Pattern.compile(".*Valuable drop: ([^<>]+?\\(((?:\\d+,?)+) coins\\))(?:</col>)?");
	UNTRADEABLE_DROP_PATTERN = Pattern.compile(".*Untradeable drop: ([^<>]+)(?:</col>)?");
	DUEL_END_PATTERN = Pattern.compile("You have now (won|lost) ([0-9]+) duels?\\.");
	PET_MESSAGES = ImmutableList.of("You have a funny feeling like you're being followed",
		"You feel something weird sneaking into your backpack",
		"You have a funny feeling like you would have been followed");


	 */



	//EXECUTE EVERY GAME TICK//
	@Subscribe
	public void onGameTick(GameTick tick)
	{
		//CHECK IF PLAYER NAME IS NOT NULL
		if(client.getLocalPlayer().getName() != null && !characterInfoSend) {
			//CLONE PLAYER OBJECT
			Player player = client.getLocalPlayer();

			//SEND THE CHARACTER INFO TO THE API
			characterInfoSend = apiCommunication.sendCharacterInfo();
		}

		//SKILLING BOSSES ARE ENABLED
		if(config.skillingBosses()) {
			skillingBosses.gametickSkillingBoss();
		}

		//LEVELING UP IS ENABLED
		if(config.levelingUp()) {
			levelingUp.gametickLevelingUp();
		}

		//LEVELING UP IS ENABLED
		if(config.questCompletion()) {
			questCompletion.gametickQuestCompletion();
		}

		//LEVELING UP IS ENABLED
		if(config.tutorialIsland()) {
			tutorialIsland.gametickTutorialIsland();
		}

		//LEVELING UP IS ENABLED
		if(config.duelArena()) {
			duelArena.gametickDuelArena();
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
        String message = chatMessage.getMessage();

		if(config.skillingBosses()) {
			skillingBosses.chatSkillingBoss(message);
		}

		if(config.musicUnlocks()) {
			musicUnlock.chatMusicUnlock(message);
		}

		if(config.collectionLog()) {
			collectionLog.chatCollectionLog(message);
		}

		if(config.bosses()) {
			bosses.chatBosses(message);
		}
	}

	@Provides
    RuneloggerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RuneloggerConfig.class);
	}

}
