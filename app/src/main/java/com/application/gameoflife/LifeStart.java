package com.application.gameoflife;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class LifeStart {
    // 打开软件时初始化一开始时的生命状态,随机生成每个格子是否有生命
    public static int[][] getLife(int xNum,int yNum){
        int[][] start = new int[xNum][yNum];
        Random random=new Random();
        for(int i=0;i<xNum;i++)
            for(int j=0;j<yNum;j++) {
                start[i][j] = random.nextBoolean() ? 1 : 0;
            }
        return start;
    }

}
