package com.runelogger;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.VarPlayer;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.runelite.api.Client;
import net.runelite.api.Skill;

import javax.inject.Inject;

@Slf4j
public class ApiCommunication
{
    @Inject private Client client;
    @Inject private OkHttpClient CurrentOkHttpClient;

    //HASH THE USERNAME BEFORE SENDING IT SO WE DONT NEED TO STORE EMAIL ADDRESSES//
    private String getUsernameHash()
    {
        String username = "";

        //TRY HASHING USERNAME
        try {
            //GET MESSAGEDIGEST THAT IMPLEMENTS SHA-256
            MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");

            //COMPLETE THE HASH COMPUTATION (AND WRITE THE BYTES TO AN ARRAY)
            byte[] usernameHashedBytes = sha256Digest.digest(client.getUsername().getBytes(StandardCharsets.UTF_8));

            //CREATE A STRING BUILDER WITH TWICE THE AMOUNT OF THE NUMBER OF BYTES (BECAUSE EVERY BYTE IS REPRESENTED WITH A 2 CHARACTER HEXIDECIMAL CHARACTER)
            StringBuilder usernameBuiler = new StringBuilder(usernameHashedBytes.length * 2);

            //LOOP EVERY BYTE
            for(byte b: usernameHashedBytes)
            {
                //ADD THE NEW HEXIDECIMAL TO THE END OF THE STRING BUILDER
                usernameBuiler.append(String.format("%02x", b));
            }

            //CONVERT THE HEXIDECIMAL USERNAME TO A STRING
            username = usernameBuiler.toString();
        }catch(NoSuchAlgorithmException e) {
            System.out.println("Something is wrong");
        }

        return username;
    }

    //SEND THE CHARACTER INFO TO THE API
    public boolean sendTutorialInfo(String status)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("status", status)
                .build();

        String file = "processTutorialIsland.php";  //API FILE TO LOAD
        sendToApi(file, formBody);                  //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST

        return true;
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendLevelingUpInfo(String skill, Integer level)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("skill", skill)
                .add("level", ""+level)
                .build();

        String file = "processLevelUp.php"; //API FILE TO LOAD
        sendToApi(file, formBody);          //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST

        //SEND LEVEL INFO
        sendLevelInfo();
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendSkillingBossInfo(String boss, Integer score)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("boss", boss)
                .add("score", ""+score)
                .build();

        String file = "processSkillingBoss.php";    //API FILE TO LOAD
        sendToApi(file, formBody);                  //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendQuestCompletionInfo(String quest)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("quest", quest)
                .build();

        String file = "processQuestCompletion.php"; //API FILE TO LOAD
        sendToApi(file, formBody);                  //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST

        //SEND QUEST INFO
        sendQuestInfo();
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendBosses(String boss)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("boss", boss)
                .build();

        String file = "processBosses.php";  //API FILE TO LOAD
        sendToApi(file, formBody);          //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendCollectionLog(String item)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("item", item)
                .build();

        String file = "processCollectionLog.php";   //API FILE TO LOAD
        sendToApi(file, formBody);                  //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendDuelArena(String outcome, String ownCombat, String opponent, String opponentCombat, String winnings)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("outcome", outcome)
                .add("ownCombat", ownCombat)
                .add("opponent", opponent)
                .add("opponentCombat", opponentCombat)
                .add("winnings", winnings)
                .build();

        String file = "processDuelArena.php";   //API FILE TO LOAD
        sendToApi(file, formBody);              //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendMusicUnlock(String track)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("track", track)
                .build();

        String file = "processMusicUnlock.php"; //API FILE TO LOAD
        sendToApi(file, formBody);              //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
    }

    //SEND THE CHARACTER INFO TO THE API
    public void sendClueScrollCompletion(String type, String reward)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("type", type)
                .add("reward", reward)
                .build();

        String file = "processClueScrollCompletion.php";    //API FILE TO LOAD
        sendToApi(file, formBody);                          //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
    }

    //SEND THE CHARACTER LEVEL INFO TO THE API
    public void sendMinigameTime(String minigame, String type, String time)
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("minigame", minigame)
                .add("type", type)
                .add("time", time)
                .build();

        String file = "processMinigameTime.php";   //API FILE TO LOAD
        sendToApi(file, formBody);          //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
    }

    //SEND THE CHARACTER INFO TO THE API
    public boolean sendCharacterInfo()
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("characterName", client.getLocalPlayer().getName())
                .add("characterType", client.getAccountType().name())
                .add("memberDays", ""+client.getVar(VarPlayer.MEMBERSHIP_DAYS))
                .build();

        String file = "getCharacterInfo.php";   //API FILE TO LOAD
        sendToApi(file, formBody);              //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST

        sendLevelInfo();
        sendQuestInfo();

        return true;
    }

    //SEND THE CHARACTER LEVEL INFO TO THE API
    private void sendLevelInfo()
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("combatLevel", ""+client.getLocalPlayer().getCombatLevel())
                .add("agility", ""+client.getRealSkillLevel(Skill.AGILITY))
                .add("attack", ""+client.getRealSkillLevel(Skill.ATTACK))
                .add("construction", ""+client.getRealSkillLevel(Skill.CONSTRUCTION))
                .add("cooking", ""+client.getRealSkillLevel(Skill.COOKING))
                .add("crafting", ""+client.getRealSkillLevel(Skill.CRAFTING))
                .add("defence", ""+client.getRealSkillLevel(Skill.DEFENCE))
                .add("farming", ""+client.getRealSkillLevel(Skill.FARMING))
                .add("firemaking", ""+client.getRealSkillLevel(Skill.FIREMAKING))
                .add("fishing", ""+client.getRealSkillLevel(Skill.FISHING))
                .add("fletching", ""+client.getRealSkillLevel(Skill.FLETCHING))
                .add("herblore", ""+client.getRealSkillLevel(Skill.HERBLORE))
                .add("hitpoints", ""+client.getRealSkillLevel(Skill.HITPOINTS))
                .add("hunter", ""+client.getRealSkillLevel(Skill.HUNTER))
                .add("magic", ""+client.getRealSkillLevel(Skill.MAGIC))
                .add("mining", ""+client.getRealSkillLevel(Skill.MINING))
                .add("prayer", ""+client.getRealSkillLevel(Skill.PRAYER))
                .add("ranged", ""+client.getRealSkillLevel(Skill.RANGED))
                .add("runecraft", ""+client.getRealSkillLevel(Skill.RUNECRAFT))
                .add("slayer", ""+client.getRealSkillLevel(Skill.SLAYER))
                .add("smithing", ""+client.getRealSkillLevel(Skill.SMITHING))
                .add("strenght", ""+client.getRealSkillLevel(Skill.STRENGTH))
                .add("thieving", ""+client.getRealSkillLevel(Skill.THIEVING))
                .add("woodcutting", ""+client.getRealSkillLevel(Skill.WOODCUTTING))
                .add("overall", ""+client.getTotalLevel())
                .build();

        String file = "getSkillingInfo.php";    //API FILE TO LOAD
        sendToApi(file, formBody);              //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
    }

    //SEND THE CHARACTER LEVEL INFO TO THE API
    private void sendQuestInfo()
    {
        //FORM PARAMETERS
        RequestBody formBody = new FormBody.Builder()
                .add("username", getUsernameHash())
                .add("questPoints", ""+client.getVar(VarPlayer.QUEST_POINTS))
                .build();

        String file = "getQuestInfo.php";   //API FILE TO LOAD
        sendToApi(file, formBody);          //SEND THE NEEDED FILENAME AND VALUES TO THE API REQUEST
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
                log.debug("Succesfully send request to api "+file);
                response.close();
            }
        });
    }
}
