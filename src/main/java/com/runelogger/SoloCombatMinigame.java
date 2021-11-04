package com.runelogger;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SoloCombatMinigame
{
    @Inject private ApiCommunication apiCommunication;

    private String completedTzHaarChallengeType = "";

    public void chatCombatChallenges(String message)
    {
        //TzHaar-Ket-Rak's Challenges == UNTESTED BUT SHOULD WORK //

        //EXTRACT THE UNLOCKED ITEM
        Pattern TzHaarChallengePatern = Pattern.compile("Your completion count for TzHaar-Ket-Rak's (.+) Challenge is: <col=[a-zA-Z0-9]{6}>[0-9]+(</col>|$)");
        Matcher TzHaarChallengeMatcher = TzHaarChallengePatern.matcher(message);

        // GET FINISHED CHALLENGE TYPE //
        if (TzHaarChallengeMatcher.find()) completedTzHaarChallengeType = TzHaarChallengeMatcher.group(1);

        // TZHAAR CHALLENGE IS COMPLETE //
        if(completedTzHaarChallengeType != "")
        {
            //EXTRACT TIME MESSAGE
            Pattern TzHaarChallengesTimePatern = Pattern.compile("Challenge duration: <col=[a-zA-Z0-9]{6}>([\\d:]+)</col>");   //FROM SCREENSHOT API
            Matcher TzHaarChallengesTimeMatcher = TzHaarChallengesTimePatern.matcher(message);

            //GET MATCHES FOR TIME
            if (TzHaarChallengesTimeMatcher.find())
            {
                //SET COMPLETED CLUE SCROLL REWARD TO CURRENT
                String completedTzHaarChallengeTime = TzHaarChallengesTimeMatcher.group(1);

                //COMMUNICATE CLUE SCROLL COMPLETION//
                apiCommunication.sendMinigameTime("TzHaar-Ket-Rak's", completedTzHaarChallengeType, completedTzHaarChallengeTime);

                //RESET COMPLETED CHALLENGE TYPE
                completedTzHaarChallengeType = "";
            }
        }
    }
}
