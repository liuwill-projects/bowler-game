package com.liuwill.testng.bowlerGame;

import com.liuwill.bowlerGame.BowlerGame;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class BowlerTest {

    @Test
    public void testBowlerGame(){
        System.out.println("*********BowlerGame 分数测试************");
        Assert.assertTrue(true);

        String fullStr = "X|X|X|X|X|X|X|X|X|X||XX";
        BowlerGame fullBowler = new BowlerGame(fullStr);
        Assert.assertEquals(300, fullBowler.getBowlerScore());
        System.out.println(fullStr + " => " + "SUCCESS:" + fullBowler.getBowlerScore());

        String firstStr = "9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||";
        BowlerGame firstBowler = new BowlerGame(firstStr);
        Assert.assertEquals(90, firstBowler.getBowlerScore());
        System.out.println(firstStr + " => " + "SUCCESS:" + firstBowler.getBowlerScore());

        String secondStr = "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5";
        BowlerGame secondBowler = new BowlerGame(secondStr);
        Assert.assertEquals(150, secondBowler.getBowlerScore());
        System.out.println(secondStr + " => " + "SUCCESS:" + secondBowler.getBowlerScore());

        String thirdStr = "X|7/|9-|X|-8|8/|-6|X|X|X||81";
        BowlerGame thirdBowler = new BowlerGame(thirdStr);
        Assert.assertEquals(167, thirdBowler.getBowlerScore());
        System.out.println(thirdStr + " => " + "SUCCESS:" + thirdBowler.getBowlerScore());
    }

    @Test
    public void testFormat(){
        System.out.println("*************格式验证*************");
        //BowlerGame rawBowler = new BowlerGame("");

        Assert.assertTrue(BowlerGame.wordFilter("XX"));
        Assert.assertTrue(BowlerGame.wordFilter("-X"));
        Assert.assertTrue(BowlerGame.wordFilter("5"));
        Assert.assertTrue(BowlerGame.wordFilter("87"));
        Assert.assertTrue(BowlerGame.wordFilter("-/"));
        Assert.assertFalse(BowlerGame.wordFilter("lX"));
        Assert.assertFalse(BowlerGame.wordFilter("io"));
        Assert.assertFalse(BowlerGame.wordFilter("50"));
    }
}
