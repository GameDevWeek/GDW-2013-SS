package de.fhtrier.gdw.ss2013.game.score;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.ScoreInfo;

public class HighscoreManager {

    private HighscoreManager() {
    }
    
    public static List<ScoreInfo> getHighscoresFromMap(String mapName) {
        List<ScoreInfo> scores = AssetLoader.getInstance().getScore(mapName);
        
        if (scores != null)
            scores = sort(scores);
        
        return scores; 
    }
    
    public static int getHighscoreRank(String mapName, int score) {
        List<ScoreInfo> scores = getHighscoresFromMap(mapName);
        
        int rank = 1;
        
        if (scores != null) {
            for (rank = 1; rank <= scores.size(); rank++) {
                if (score > scores.get(rank-1).score) {
                    break;
                }
            }
            
            if (rank > 10)
                rank = 0;
        }
        
        return rank;
    }
    
    public static void setHighscore(String mapName, ScoreInfo scoreInfo) {
        int rank = getHighscoreRank(mapName, scoreInfo.score);
        
        if (rank > 0) {
            List<ScoreInfo> scores = getHighscoresFromMap(mapName);
            
            if (scores == null) {
                scores = new ArrayList<>();
            } else {
                if (scores.size() >= 10)
                    scores.remove(scores.size()-1);
            }
            
            scores.add(rank-1, scoreInfo);
            AssetLoader.getInstance().writeScore(mapName, scores);
        }
    }
    
    protected static List<ScoreInfo> sort(List<ScoreInfo> scores) {
        ArrayList<ScoreInfo> sorted = new ArrayList<>();
        
        boolean found;
        for (ScoreInfo s : scores) {
            found = false;
            for (int i = 0; i < sorted.size(); i++) {
                if (sorted.get(i).score < s.score) {
                    sorted.add(i, s);
                    found = true;
                    break;
                }
            }
            if (!found) {
                sorted.add(s);
            }
        }
        
        return sorted;
    }
}
