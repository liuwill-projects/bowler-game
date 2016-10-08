package com.liuwill.bowlerGame;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class BowlerFrame {
    public final static String STRIKE= "strike";
    public final static String SPARE= "spare";

    private int firstBall = 0;
    private int secondBall = 0;
    private String ballStatus;
    private int frameScore;
    private int addScore;

    public BowlerFrame(String frameStr){
        this.addScore = 0;
        this.ballStatus = "";
        if("X".equals(frameStr)){
            this.ballStatus = STRIKE;
            firstBall = 10;
            secondBall = 0;
        }else{
            String firstStr = frameStr.substring(0,1);
            String secondStr = frameStr.substring(1,2);

            firstBall = BowlerGame.getScoreByWord(firstStr,"");
            secondBall = BowlerGame.getScoreByWord(secondStr,firstStr);
        }

        if(frameStr.length() == 2 && "/".equals(frameStr.substring(1,2))){
            this.ballStatus = SPARE;
        }

        this.frameScore = calcFrameScore(frameStr);
    }

    public int calcFrameScore(String frameStr){
        if("X".equals(frameStr)){
            return 10;
        }else if("/".equals(frameStr.substring(1,2))){
            return 10;
        }

        int theScore = 0;
        char[] singleBall = frameStr.toCharArray();
        if(singleBall[0] != '-'){
            theScore += Integer.parseInt(String.valueOf(singleBall[0]));
        }else if(singleBall[1] != '-'){
            theScore += Integer.parseInt(String.valueOf(singleBall[1]));
        }
        return theScore;
    }

    public int getFirstBall() {
        return firstBall;
    }

    public void setFirstBall(int firstBall) {
        this.firstBall = firstBall;
    }

    public int getSecondBall() {
        return secondBall;
    }

    public void setSecondBall(int secondBall) {
        this.secondBall = secondBall;
    }

    public String getBallStatus() {
        return ballStatus;
    }

    public void setBallStatus(String ballStatus) {
        this.ballStatus = ballStatus;
    }

    public int getFrameScore() {
        return frameScore;
    }

    public int getFullFrameScore() {
        return frameScore + addScore;
    }

    public void addScore(int score){
        this.addScore += score;
    }

    public int getAddScore() {
        return addScore;
    }
}
