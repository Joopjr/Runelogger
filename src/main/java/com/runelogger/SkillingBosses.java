package com.runelogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Integer;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;

import javax.inject.Inject;


@Slf4j
public class SkillingBosses
{
    @Inject private Client client;

    @Inject private ApiCommunication apiCommunication;

    private String message;

    boolean startCoutingWintertodtScore = false;
    private Integer wintertodtScore = 0;
    boolean startCoutingTemporossScore = false;
    private Integer temporossScore = 0;

    public void gametickSkillingBoss()
    {
        //PLAYER IS IN WINTERTODT REGION//
        if(client.getLocalPlayer().getWorldLocation().getRegionID() == 6462)
        {
            //GET WINTERTODT WIDGET
            Widget wintertodtScoreWidget = client.getWidget(396, 7);

            //THERE IS A WINTERTODT SCORE WIDGET; PLAYER IS IN WINTERTODT
            if(wintertodtScoreWidget != null && startCoutingWintertodtScore) {
                //EXTRACT THE SCORE FROM THE WIDGET USING REGEX
                Pattern winterTodtscorePatern = Pattern.compile("Points<br>(\\d+)");
                Matcher winterTodtscoreMatcher = winterTodtscorePatern.matcher(wintertodtScoreWidget.getText());

                //GET MATCHES FOR SCORE ON WINTERTODT WIDGET//
                if (winterTodtscoreMatcher.find()) {
                    //SCORE INCREASED, SET NEW SCORE//
                    if(Integer.parseInt(winterTodtscoreMatcher.group(1)) > wintertodtScore) wintertodtScore = Integer.parseInt(winterTodtscoreMatcher.group(1));
                }
            }
        }
        //PLAYER NOT IN WINTERTODT BUT STILL HAS HIGH ENOUGH SCORE
        else if(startCoutingWintertodtScore)
            stopCountingWintertodtScore();

        //GET TEMPOROSS WIDGET
        Widget temporossScoreWidget = client.getWidget(437, 25);

        //THERE IS A TEMPOROSS SCORE WIDGET; PLAYER IS IN TEMPOROSS
        if(temporossScoreWidget != null && startCoutingTemporossScore) {
            //EXTRACT THE SCORE FROM THE WIDGET USING REGEX
            Pattern temporossscorePatern = Pattern.compile("Points: (\\d+)");
            Matcher temporossscoreMatcher = temporossscorePatern.matcher(temporossScoreWidget.getText());

            //GET MATCHES FOR SCORE ON TEMPOROSS WIDGET//
            if (temporossscoreMatcher.find()) {
                //SCORE INCREASED, SET NEW SCORE//
                if(Integer.parseInt(temporossscoreMatcher.group(1)) > temporossScore) temporossScore = Integer.parseInt(temporossscoreMatcher.group(1));
            }
        }
        //TEMPOROSS SCORE WIDGET NOT ACTIVE
        else if(startCoutingTemporossScore)
            stopCountingTemporossScore();
    }

    public void chatSkillingBoss(String message)
    {
        //CHECK IF THE USE HAS ENOUGH POINTS TO EARN A SUPPLY CRATE
        if(message.contains("You have helped enough to earn a supply crate.")) startCoutingWintertodtScore = true;

        //WINTERTODT SCORE IS COUNTING//
        if(startCoutingWintertodtScore)
        {
            //EXTRACT THE TOTAL AMOUNT WINTERTODT HAS BEEN SUBDUED
            Pattern winterTodtSubduedPatern = Pattern.compile("Your subdued Wintertodt count is: <col=ff0000>(\\d+)</col>.");
            Matcher winterTodtSubduedMatcher = winterTodtSubduedPatern.matcher(message);

            //GET MATCHES FOR SUBDUEING WINTERTODT
            if (winterTodtSubduedMatcher.find()) {
                //COMMUNICATE SCORE//
                apiCommunication.sendSkillingBossInfo("Wintertodt", wintertodtScore);
                stopCountingWintertodtScore();
            }
        }

        //CHECK IF THE USE HAS ENOUGH POINTS TO EARN A REWARD PERMIT
        if(message.contains("You have earned enough points for a reward permit.")) startCoutingTemporossScore = true;

        //TEMPOROSS SCORE IS COUNTING//
        if(startCoutingTemporossScore)
        {
            //EXTRACT THE TOTAL AMOUNT WINTERTODT HAS BEEN SUBDUED
            Pattern temporossSubduedPatern = Pattern.compile("Your Tempoross kill count is: <col=ff0000>(\\d+)</col>.");
            Matcher temporossSubduedMatcher = temporossSubduedPatern.matcher(message);

            //GET MATCHES FOR SUBDUEING WINTERTODT
            if (temporossSubduedMatcher.find()) {
                //COMMUNICATE SCORE//
                apiCommunication.sendSkillingBossInfo("Tempoross", temporossScore);
                stopCountingTemporossScore();
            }
        }
    }

    //STOP COUNTING WINTERTODT SCORE AND RESET SCORE//
    private void stopCountingWintertodtScore()
    {
        wintertodtScore = 0;    //RESET SCORE
        startCoutingWintertodtScore = false;
    }

    //STOP COUNTING WINTERTODT SCORE AND RESET SCORE//
    private void stopCountingTemporossScore()
    {
        temporossScore = 0;    //RESET SCORE
        startCoutingTemporossScore = false;
    }
}