package com.runelogger;

import com.google.inject.Provides;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.WorldType;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;


@Slf4j
@PluginDescriptor(
	name = "Runelogger",
	description = "Publishes your RuneScape adventures on Runelogger.com.",
	tags = {"runelogger", "journey"}
)

public class RuneloggerPlugin extends Plugin
{
	@Inject private Client client;
	@Inject private RuneloggerConfig config;

	@Inject private SkillingBosses skillingBosses;
	@Inject private LevelingUp levelingUp;
	@Inject private QuestCompletion questCompletion;
	@Inject private MusicUnlock musicUnlock;
	@Inject private TutorialIsland tutorialIsland;
	@Inject private CollectionLog collectionLog;
	@Inject private Bosses bosses;
	@Inject private DuelArena duelArena;
	@Inject private ClueScrollCompletion clueScrollCompletion;
	@Inject private SoloCombatMinigame soloCombatMinigame;

	@Inject private ApiCommunication apiCommunication;

	private boolean characterInfoSend = false;
	private boolean suportedWorld = false;

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
	VALUABLE_DROP_PATTERN = Pattern.compile(".*Valuable drop: ([^<>]+?\\(((?:\\d+,?)+) coins\\))(?:</col>)?");
	UNTRADEABLE_DROP_PATTERN = Pattern.compile(".*Untradeable drop: ([^<>]+)(?:</col>)?");
	PET_MESSAGES = ImmutableList.of("You have a funny feeling like you're being followed",
		"You feel something weird sneaking into your backpack");
	 */

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		//GAME CHANGED TO USER LOGGED IN
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			//PLAYER ISN'T ON AN UNSUPORTED WORLD
			if(!client.getWorldType().contains(WorldType.DEADMAN) && !client.getWorldType().contains(WorldType.NOSAVE_MODE) && !client.getWorldType().contains(WorldType.SEASONAL) && !client.getWorldType().contains(WorldType.TOURNAMENT_WORLD))
				suportedWorld = true;
		}
	}

	//EXECUTE EVERY GAME TICK//
	@Subscribe
	public void onGameTick(GameTick tick)
	{
		//PLAYER IS ON A SUPORTED WORLD
		if(suportedWorld)
		{
			//CHECK IF PLAYER NAME IS NOT NULL
			if(client.getLocalPlayer().getName() != null && !characterInfoSend) {
				//CLONE PLAYER OBJECT
				Player player = client.getLocalPlayer();

				//SEND THE CHARACTER INFO TO THE API
				characterInfoSend = apiCommunication.sendCharacterInfo();
			}

			if(config.skillingBosses()) skillingBosses.gametickSkillingBoss();		//SKILLING BOSSES ARE ENABLED
			if(config.levelingUp()) levelingUp.gametickLevelingUp();				//LEVELING UP IS ENABLED
			if(config.questCompletion()) questCompletion.gametickQuestCompletion();	//QUEST COMPLETION IS ENABLED
			if(config.tutorialIsland()) tutorialIsland.gametickTutorialIsland();	//TUTORIAL ISLAND IS ENABLED
			if(config.duelArena()) duelArena.gametickDuelArena();					//DUEL ARENA IS ENABLED
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		//PLAYER IS ON A SUPORTED WORLD
		if(suportedWorld)
		{
			String message = chatMessage.getMessage();

			if(config.skillingBosses()) skillingBosses.chatSkillingBoss(message);						//SKILLING BOSSES ARE ENABLED
			if(config.musicUnlocks()) musicUnlock.chatMusicUnlock(message);								//MUSIC UNLOCKS ARE ENABLED
			if(config.collectionLog()) collectionLog.chatCollectionLog(message);						//COLLECTION LOG IS ENABLED
			if(config.bosses()) bosses.chatBosses(message);												//BOSS KILLS ARE ENABLED
			if(config.clueScrollCompletion()) clueScrollCompletion.chatClueScrollCompletion(message);	//CLUE SCROLL COMPLETIONS ARE ENABLED
			if(config.soloCombatMinigame()) soloCombatMinigame.chatSoloCombatMinigame(message);			//COMBAT CHALLENGES ARE ENABLED
		}
	}

	@Provides
    RuneloggerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RuneloggerConfig.class);
	}
}