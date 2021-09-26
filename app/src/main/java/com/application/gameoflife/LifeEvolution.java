package com.application.gameoflife;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import java.util.concurrent.Executor;

public class LifeEvolution {
    private DrawGame drawGame;
    private Executor executor;
    private int[][] life;
    private boolean evolutionFlag=true;
    private int evolutionTime;
    public LifeEvolution(Executor executor, View view,int[][] life){
        this.executor=executor;
        this.drawGame=(DrawGame)view;
        this.life=life;
        lifeEvolution();
    }
    public void setEvolutionFlag(boolean evolutionFlag){
        this.evolutionFlag=evolutionFlag;
    }
    public boolean isEvolutionFlag(){
        return evolutionFlag;
    }
    public void OneEvolution(){
        int[][] nextEvolution=GameOfLifeSolution.Solution(drawGame.getLife());
        drawGame.setLife(nextEvolution);
        drawGame.postInvalidate();
    }
    public void lifeEvolution(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=drawGame.getContext().getSharedPreferences("setting_data", Context.MODE_PRIVATE);
                evolutionFlag=sharedPreferences.getBoolean("auto_run",true);
                evolutionTime=sharedPreferences.getInt("evolution_time",1000);
                    try {
                        while (true) {
                            if(evolutionFlag) {
                                int[][] nextEvolution = GameOfLifeSolution.Solution(life);
                                drawGame.setLife(nextEvolution);
                                drawGame.postInvalidate();
                                Thread.sleep(evolutionTime);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
    }
}
