package com.liuwill.bowlerGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class BowlerGame {

    private String bowlerStr;
    public List<BowlerFrame> frameList;

    public BowlerGame(String bowlerStr){
        this.bowlerStr = bowlerStr;
        this.frameList = new ArrayList<>();

        calcBowlerScore();
    }

    public String getBowlerStr() {
        return bowlerStr;
    }

    public void setBowlerStr(String bowlerStr) {
        this.bowlerStr = bowlerStr;
    }

    public List<BowlerFrame> getFrameList() {
        return frameList;
    }

    public void setFrameList(List<BowlerFrame> frameList) {
        this.frameList = frameList;
    }

    public String[] filterBowlerStr(String bowlerStr){
        String[] gameArr = bowlerStr.split("[|]");
        if(gameArr.length != 10 && gameArr.length != 12){
            throw new IllegalArgumentException("BowlerGame格式不正确");
        }
        return gameArr;
    }

    public int getBowlerScore(){
        int bowlerScore = 0;
        for(BowlerFrame singleBowlerFrame : frameList){
            bowlerScore += singleBowlerFrame.getFullFrameScore();
            //System.out.println(singleBowlerFrame.getFullFrameScore());
        }
        return bowlerScore;
    }

    private void calcBowlerScore() {
        String[] gameArr = filterBowlerStr(bowlerStr);
        String bonusStr = "";
        if ( gameArr.length == 10 ) {
            bonusStr = "";
        } else {
            bonusStr = gameArr[ gameArr.length - 1 ];
        }

        int itr = 0;
        for(String frameStr : gameArr){
            if(!isFrameLegal(frameStr)){
                throw new IllegalArgumentException("Frame格式不正确:" + frameStr);
            }

            // 每个Frame生成之后，都会检查之前的frame，根据规则给之前的frame加上相应的分数
            int frameItr = itr;
            BowlerFrame bowlerFrame = new BowlerFrame(frameStr);
            if(frameItr-1 >= 0){
                BowlerFrame preBowlerFrame = frameList.get(frameItr - 1);
                if(preBowlerFrame.getBallStatus().equals(BowlerFrame.STRIKE)){
                    preBowlerFrame.addScore(bowlerFrame.getFrameScore());
                }else if(preBowlerFrame.getBallStatus().equals(BowlerFrame.SPARE)){
                    preBowlerFrame.addScore(bowlerFrame.getFirstBall());
                }

                if(preBowlerFrame.getBallStatus().equals(BowlerFrame.STRIKE) && frameItr-2 >= 0){
                    BowlerFrame earlyBowlerFrame = frameList.get(frameItr - 2);
                    if(earlyBowlerFrame.getBallStatus().equals(BowlerFrame.STRIKE)){
                        earlyBowlerFrame.addScore(bowlerFrame.getFirstBall());
                    }
                }
            }

            frameList.add(bowlerFrame);

            itr++;
            if(itr >= 10){
                break;
            }
        }

        if(!"".equals(bonusStr)){
            int bonusFirst = 0;
            int bonusSecond = 0;
            if(bonusStr.length() == 1){
                bonusFirst = getScoreByWord(bonusStr,"");
            }else{
                String firstStr = bonusStr.substring(0,1);
                String secondStr = bonusStr.substring(1,2);

                bonusFirst = getScoreByWord(firstStr,"");
                bonusSecond = getScoreByWord(secondStr,firstStr);
            }

            BowlerFrame lastFrame = frameList.get(frameList.size() - 1);
            BowlerFrame beforeFrame = frameList.get(frameList.size() - 2);

            if(lastFrame.getBallStatus().equals(BowlerFrame.STRIKE)){
                if(beforeFrame.getBallStatus().equals(BowlerFrame.STRIKE)){
                    beforeFrame.addScore(bonusFirst);
                }
                lastFrame.addScore(bonusFirst + bonusSecond);
            }else if(lastFrame.getBallStatus().equals(BowlerFrame.SPARE)){
                lastFrame.addScore(bonusFirst );
            }
        }
        //System.out.println("++++++");
    }

    public static boolean wordFilter(String wordStr){
        String regStr = "-123456789/X";
        for(char aLetter: wordStr.toCharArray()){
            char[] letterCharList = {aLetter};
            if(!regStr.contains(new String(letterCharList))){
                return false;
            }
        }
        return true;
    }

    public boolean isBonusLegal(String bonusStr){
        if(bonusStr.length() == 0){
            return true;
        }
        if(bonusStr.length() > 2){
            return false;
        }else if(!wordFilter(bonusStr)){
            return false;
        }
        return true;
    }

    public static boolean isFrameLegal(String frameStr){
        if(frameStr.length() > 2 || frameStr.length() == 0){
            return false;
        }else if(!wordFilter(frameStr)){
            return false;
        }else if(frameStr.length() == 1 && !"X".equals(frameStr)){
            return false;
        }else if(frameStr.length() == 2){
            if("X/".contains(frameStr.substring(0,1))){
                return false;
            }else if("X".equals(frameStr.substring(1,2))){
                return false;
            }
        }
        return true;
    }

    public static int getScoreByWord(String word,String nearBy){
        if("X".equals(word)){
            return 10;
        }else if("-".equals(word)){
            return 0;
        }else if("/".equals(word)){
            if("/".equals(nearBy)){
                throw new IllegalArgumentException("Frame Word Nearby");
            }
            return 10 - getScoreByWord(nearBy,"");
        }
        return Integer.parseInt(word);
    }
}
