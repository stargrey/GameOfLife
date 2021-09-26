package com.application.gameoflife;


public class GameOfLifeSolution {
    /*
    作者：小浩算法
    链接：https://juejin.cn/post/6895711037501374472
    来源：掘金
    */

    // 根据上次生命状态计算出下次的生命状态并返回
    public static int[][] Solution(int[][] board){
        int m=board.length,n=board[0].length;
        // 原始状态 -> 过渡状态
        for(int i=0; i<m ;i++){
            for (int j=0; j<n ;j++){
                int liveNeighbors = 0;
                // 判断上边
                if(i>0){
                    liveNeighbors+=board[i-1][j] ==1 || board[i-1][j] ==2 ? 1:0;
                }
                // 判断左边
                if(j>0){
                    liveNeighbors+=board[i][j-1] ==1 || board[i][j-1] ==2 ? 1:0;
                }
                // 判断下边
                if(i < m - 1){
                    liveNeighbors  += board[i + 1][j] == 1 || board[i + 1][j] == 2 ? 1 : 0;
                }
                // 判断右边
                if(j < n - 1){
                    liveNeighbors  += board[i][j + 1] == 1 || board[i][j + 1] == 2 ? 1 : 0;
                }
                // 判断左上角
                if(i > 0 && j > 0){
                    liveNeighbors  += board[i - 1][j - 1] == 1 || board[i - 1][j - 1] == 2 ? 1 : 0;
                }
                //判断右下角
                if(i < m - 1 && j < n - 1){
                    liveNeighbors  += board[i + 1][j + 1] == 1 || board[i + 1][j + 1] == 2 ? 1 : 0;
                }
                // 判断右上角
                if(i > 0 && j < n - 1){
                    liveNeighbors  += board[i - 1][j + 1] == 1 || board[i - 1][j + 1] == 2 ? 1 : 0;
                }
                // 判断左下角
                if(i < m - 1 && j > 0){
                    liveNeighbors  += board[i + 1][j - 1] == 1 || board[i + 1][j - 1] == 2 ? 1 : 0;
                }
                // 根据周边存活数量更新当前点，结果是 0 和 1 的情况不用更新
                if(board[i][j] == 0 && liveNeighbors  == 3){
                    board[i][j] = 3;
                } else if(board[i][j] == 1){
                    if(liveNeighbors  < 2 || liveNeighbors  > 3) board[i][j] = 2;
                }
            }
        }
        // 过渡状态 -> 真实状态
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                board[i][j] = board[i][j] % 2;
            }
        }
        return board;
    }
}
