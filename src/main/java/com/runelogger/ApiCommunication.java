package com.runelogger;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.VarPlayer;
import okhttp3.*;

import java.io.IOException;

import net.runelite.api.Client;
import net.runelite.api.Player;

import javax.inject.Inject;

@Slf4j
public class ApiCommunication
{
    @Inject
    private Client client;

    @Inject
    private OkHttpClient CurrentOkHttpClient;

    //SEND THE CHARACTER INFO TO THE API
    public boolean sendTutorialInfo(String status)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", client.getUsername())
                .add("status", status)
                .build();

        //API FILE TO LOAD
        String file = "processTutorialIsland.php";

        //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
        sendToApi(file, formBody);

        return true;
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendLevelingUpInfo(String skill, Integer level)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", client.getUsername())
                .add("skill", skill)
                .add("level", ""+level)
                .build();

        //API FILE TO LOAD
        String file = "processLevelUp.php";

        //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
        sendToApi(file, formBody);
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendSkillingBossInfo(String boss, Integer score)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", client.getUsername())
                .add("boss", boss)
                .add("score", ""+score)
                .build();

        //API FILE TO LOAD
        String file = "processSkillingBoss.php";

        //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
        sendToApi(file, formBody);
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendQuestCompletionInfo(String quest)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", client.getUsername())
                .add("quest", quest)
                .build();

        //API FILE TO LOAD
        String file = "processQuestCompletion.php";

        //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
        sendToApi(file, formBody);
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendBosses(String boss)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", client.getUsername())
                .add("boss", boss)
                .build();

        //API FILE TO LOAD
        String file = "processBosses.php";

        //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
        sendToApi(file, formBody);
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendCollectionLog(String item)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", client.getUsername())
                .add("item", item)
                .build();

        //API FILE TO LOAD
        String file = "processCollectionLog.php";

        //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
        sendToApi(file, formBody);
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendDuelArena(String outcome, String ownCombat, String opponent, String opponentCombat, String winnings)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", client.getUsername())
                .add("outcome", outcome)
                .add("ownCombat", ownCombat)
                .add("opponent", opponent)
                .add("opponentCombat", opponentCombat)
                .add("winnings", winnings)
                .build();

        //API FILE TO LOAD
        String file = "processDuelArena.php";

        //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
        sendToApi(file, formBody);
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendMusicUnlock(String track)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", client.getUsername())
                .add("track", track)
                .build();

        //API FILE TO LOAD
        String file = "processMusicUnlock.php";

        //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
        sendToApi(file, formBody);
    }

    //SEND THE CHARACTER INFO TO THE API
    public boolean sendCharacterInfo(Player player)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", client.getUsername())
                .add("characterName", player.getName())
                .add("characterType", client.getAccountType().name())
                .add("memberDays", ""+client.getVar(VarPlayer.MEMBERSHIP_DAYS))
                .build();

        //API FILE TO LOAD
        String file = "getCharacterInfo.php";

        //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
        sendToApi(file, formBody);

        return true;
    }

    //SEND API REQUEST (FILENAME AND VALUES NECESSARY)//
    private void sendToApi(String file, RequestBody formBody)
    {
        //CREATE REQUEST
        Request request = new Request.Builder()
                .addHeader("User-Agent", "RuneLite")
                .addHeader("ContentType", "application/x-www-form-urlencoded")
                .url("https://runelogger.com/api/" + file)
                .post(formBody)
                .build();

        //PERFORM REQUEST
        CurrentOkHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                log.warn("Error submitting update, caused by {}.", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                response.close();
            }
        });
    }
}
