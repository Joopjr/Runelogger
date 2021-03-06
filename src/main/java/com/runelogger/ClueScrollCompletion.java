package com.runelogger;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ClueScrollCompletion
{
    @Inject private ApiCommunication apiCommunication;

    String completedClueScrollType = "";

    public void chatClueScrollCompletion(String message)
    {
        //EXTRACT CLUE SCROLL COMPLETED MESSAGE
        Pattern ClueScrollCompletionPatern = Pattern.compile("<col=ef1020>You have completed (\\d+) (.+) Treasure Trail(s)?.</col>");   //FROM SCREENSHOT API
        Matcher ClueScrollCompletionMatcher = ClueScrollCompletionPatern.matcher(message);

        //GET MATCHES FOR ITEM
        if (ClueScrollCompletionMatcher.find())
        {
            //SET COMPLETED CLUE SCROLL TYPE TO CURRENT
            completedClueScrollType = ClueScrollCompletionMatcher.group(2);
        }

        //CLUE SCROLL IS COMPLETED
        if(completedClueScrollType != "")
        {
            //EXTRACT CLUE SCROLL REWARD AMOUNT MESSAGE
            Pattern ClueScrollRewardPatern = Pattern.compile("<col=ef1020>Your treasure is worth around ([\\d,]+) coins!</col>");   //FROM SCREENSHOT API
            Matcher ClueScrollRewardMatcher = ClueScrollRewardPatern.matcher(message);

            //GET MATCHES FOR REWARD
            if (ClueScrollRewardMatcher.find())
            {
                //SET COMPLETED CLUE SCROLL REWARD TO CURRENT
                String completedClueScrollReward = ClueScrollRewardMatcher.group(1);

                //COMMUNICATE CLUE SCROLL COMPLETION//
                apiCommunication.sendClueScrollCompletion(completedClueScrollType, completedClueScrollReward);

                //RESET COMPLETED CLUE SCROLL TYPE
                completedClueScrollType = "";
            }
        }
    }
}
