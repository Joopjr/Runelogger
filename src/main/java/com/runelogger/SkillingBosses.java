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
    @Inject
    private Client client;

    @Inject
    private ApiCommunication apiCommunication;

    boolean startCoutingWintertodtScore = false;
    private String message;
    private Integer wintertodtScore = 0;

    public void gametickSkillingBoss()
    {
        //PLAYER IS IN WINTERTODT REGION//
        if(client.getLocalPlayer().getWorldLocation().getRegionID() == 6462) {
            //GET WINTERTODT WIDGET
            Widget wintertodtScoreWidget = client.getWidget(396, 7);

            //THERE IS A WINTERTODT SCORE WIDGET; PLAYER IS IN WINTERTODT
            if(wintertodtScoreWidget != null && startCoutingWintertodtScore) {
                //EXTRACT THE SCORE FROM THE WIDGET USING REGEX
                Pattern winterTodtscorePatern = Pattern.compile("Points<br>(\\d+)");
                Matcher winterTodtscoreMatcher = winterTodtscorePatern.matcher(wintertodtScoreWidget.getText());

                //GET MATCHES FOR SCORE ON WINTERTODT WIDGET//
                if (winterTodtscoreMatcher.find()) {
                    //SCORE HAS GONE BACKWARDS (END OF ROUND?)//
                    if(Integer.parseInt(winterTodtscoreMatcher.group(1)) > wintertodtScore) {
                        wintertodtScore = Integer.parseInt(winterTodtscoreMatcher.group(1));
                    }
                }
            }
        }
        //PLAYER NOT IN WINTERTODT BUT STILL HAS HIGH ENOUGH SCORE
        else if(startCoutingWintertodtScore) {
            stopCountingWintertodtScore();
        }
    }

    public void chatSkillingBoss(String message)
    {
        //CHECK IF THE USE HAS ENOUGH POINTS TO EARN A SUPPLY CRATE
        if(message.contains("You have helped enough to earn a supply crate.")) {
            startCoutingWintertodtScore = true;
        }

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

    //STOP COUNTING WINTERTODT SCORE AND RESET SCORE//
    private void stopCountingWintertodtScore()
    {
        wintertodtScore = 0;    //RESET SCORE
        startCoutingWintertodtScore = false;
    }
}