package com.sanat.crystallball;

import java.util.Random;

public class CrystallBall {
	
	String mAnswers[] = {"It is certain","It is decidedly so",
			"All signs says Yes","The stars are not aligned",
			"My reply is no","It is doubtful", "Better not tell you now",
			"Concentrate and ask again", "Unable to answer now"};
	
	public String getAnAnswer()
	{	
		String answer = "";
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(mAnswers.length);//it will choose a random integer number betw 2 and 0
		answer = mAnswers[randomNumber];
		return answer;
	}
}
