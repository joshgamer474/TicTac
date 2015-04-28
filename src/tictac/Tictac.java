/*
    A console tic tac toe game made by Josh Childers
 */
package tictac;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Tictac {

    
    static boolean gameOver;
    static List<Integer> tracker;
    
    
    public static void main(String[] args) {
        
        boolean keepGoing = true;
        Scanner reader = new Scanner(System.in);
        String input = "";
        
        while(keepGoing){

            System.out.println("Starting Tic Tac Toe game!");
            gameOver = false;
        
            //create the gameboards
            String[][] gameboard = new String[3][3];
            tracker = new ArrayList<>();
        
            //fill the gameboards with initial values
            fillGameboard(gameboard);
            fillTracker(tracker);
        
            //print the current initial gameboard
            printGameboard(gameboard);
        
        
        
            //loop that runs the game
            while(!gameOver){
        
                getPlayerMove(gameboard, tracker);
                printGameboard(gameboard);
                gameOver = gameOver(gameboard);
            
                if(!gameOver){
                    getBotMove(gameboard, tracker);
                    printGameboard(gameboard);
                    gameOver = gameOver(gameboard);
                }
            
            }//end gameOver while loop
        
            //get input from user to see if they want to play again
            System.out.print("Play another round? (y/n): ");
            input = reader.nextLine();
            input.toLowerCase();
            
            if(!input.equals("y")){
                keepGoing = false;
            }
            System.out.println("");
            
        }//end keepGoing while loop
        
        
    }//end main method
    
    
    /*
    Fills the initial gameboard with "."s
    */
    public static void fillGameboard(String[][] gameboard){
        for(int i = 0; i < gameboard.length; i++){
            for(int j = 0; j < gameboard.length; j++){
                gameboard[i][j] = ".";
            }
        }
    }//end method fillGameboard
    
    
    
    /*
    Fills the initial tracker board with "0"s
    */
    public static void fillTracker(List tracker){
        for(int i = 0; i < 9; i++){
            tracker.add(i);
        }
    }//end method fillTracker
    
    
    
    /*
    Prints the current gameboard to console
    */
    public static void printGameboard(String[][] gameboard){
        System.out.println("Current gameboard:");
        
        for(int i = 0; i < gameboard.length; i++){
            for(int j = 0; j < gameboard.length; j++){
                System.out.print(gameboard[i][j]);
                System.out.print("\t");
            }
            System.out.println("");
        }
        System.out.println("\n");
    }//end method printGameboard
    
    
    
    /*
    Method to get the player's move and to put the player's move onto the gameboard
    */
    public static void getPlayerMove(String[][] gameboard, List tracker){
        
        Scanner reader = new Scanner(System.in);
        int x = 0;
        int y = 0;
        boolean bad = false;
      
        System.out.println("Player's Turn");
        
        while(!bad){
            System.out.println("Enter \"x\" coordinate: ");
       
            //get user input coordinate for x (from gameboard perspective)
            y = reader.nextInt();
        
            System.out.println("Enter \"y\" coordinate: ");
       
            //get user input coordinate for x (from gameboard perspective)
            x = reader.nextInt();
            
            //make x and y array coordinates instead of being from 0 to 2
            x = x - 1;
            y = y - 1;
        
            //check if x and y values are within bounds or already taken
            if(x > 2 || x < 0 || y > 2 || y < 0){
                System.out.println("X or Y coordinate was out of bounds...\n");
            }else if(spotTaken(gameboard, x, y)){
                System.out.println("Spot already played...\n");
            }else{
                bad = true;
            }
        
        }//end check coordinates while loop
        
        //add player mark to board
        gameboard[x][y] = "X";
        
        //remove player's spot from available spot list
        tracker.remove(Integer.valueOf(coordinateToInt(x,y)));
        //System.out.println("coordinateToInt: " + coordinateToInt(x,y));
        
    
    
    }//end method getPlayerMove
    
    
    
    
    /*
    Plays a move for the bot
    */
    public static void getBotMove(String[][] gameboard, List tracker){
        
        int x = 0;
        int y = 0;
        int[] xy = new int[2];
        int[] temp = {-1, -1};
        int[] temp2 = new int[2];
        int[] temp3 = new int[2];
        
        System.out.println("Bot's turn");
        
        //set temp = {x, y} of where the winning move is that could be played
        temp2 = playerGoingToWin(gameboard, "X", "O");
        temp3 = playerGoingToWin(gameboard, "O", "X");
        
        
        //check if bot could win first. If the bot can win first, let the bot win
        if(!(temp[0] == temp3[0] && temp[1] == temp3[1])){
        
            x = temp3[0];
            y = temp3[1];
            
            //put down bot's move onto gameboard
            gameboard[x][y] = "O";
            
            int gameboardSpot = coordinateToInt(x, y);
            
            //remove bot's spot from available spot list
            tracker.remove(Integer.valueOf(gameboardSpot));
            

        //check if player could win first and if bot could block the player's next move
        }else if(!(temp[0] == temp2[0] && temp[1] == temp2[1])){ 
        
            x = temp2[0];
            y = temp2[1];
            
            //put down bot's move onto gameboard
            gameboard[x][y] = "O";
            
            int gameboardSpot = coordinateToInt(x, y);
            
            //remove bot's spot from available spot list
            tracker.remove(Integer.valueOf(gameboardSpot));
            
        }else{
        
            Random r = new Random();
        
            //pick random value from availble spot list
            Object randomValue = tracker.get(r.nextInt(tracker.size()));
        
            //get gameboard coordinates from random value on available spot list
            xy = intToCoordinate((int) randomValue);
        
            x = xy[0];
            y = xy[1];
        
            //put down bot's move onto gameboard
            gameboard[x][y] = "O";
        
            //remove bot's spot from available spot list
            tracker.remove(Integer.valueOf((int) randomValue));
        
        }//end if-else statement
    }//end method getBotMove
    
    
    
    
    /*
    Checks if a spot is already 'taken' or is already marked
    */
    public static boolean spotTaken(String[][] gameboard, int x, int y){
        
        //check if spot is taken
        if(gameboard[x][y] != "."){
            return true;
        }else{
            return false;
        }
    }//end method spotTaken 
    
    
    
    
    /*
    Checks if someone is going to win the next round
    String a = X or O
    String b = X or O
    Example: String a = X, String b = O
    Returns 1D int array with {-1, -1} if player isn't going to win, or {x, y} of winning spot for the next round
    */
    public static int[] playerGoingToWin(String[][] gameboard, String a, String b){
    
        int[] xy = {-1, -1};
        int[] temp = new int[2];
    
        temp = goingToWinVertically(gameboard, a, b);
        
        for(int i = 0; i < 1; i++){
            if(temp[0] != -1 && temp[1] != -1){
                System.out.println("someone is going to win");
                xy[0] = temp[0];
                xy[1] = temp[1];  
                break;
            }
        
        
            temp = goingToWinHorizontally(gameboard, a, b);
        
            if(temp[0] != -1 && temp[1] != -1){ 
               System.out.println("someone is going to win");
               xy[0] = temp[0];
               xy[1] = temp[1];
               break;
            }
        
        
            temp = goingToWinDiagonally(gameboard, a, b);
        
            if(temp[0] != -1 && temp[1] != -1){ 
                System.out.println("someone is going to win");
                xy[0] = temp[0];
                xy[1] = temp[1];
                break;
            }
        
        }//end for loop
 
        return xy;
    }//end method playerGoingToWin
    
    
 
    /*
    Checks if someone is going to win the next round vertically
    String a = X or O
    String b = X or O
    Example: String a = X, String b = O
    Returns 1D int array with {-1, -1} if player isn't going to win, or {x, y} of winning spot for the next round
    */
    public static int[] goingToWinVertically(String[][] gameboard, String a, String b){
    
        int[] c = new int[2];
    
        for(int i = 0; i < 3; i++){
            //create boolean arrays to check if one row/column is completely played on to ignore 'going to win'
            boolean[] checkRowFor3X = {false, false, false};
            
           
            
            //check if board spots are filled by Xs or Os
            int yesX = 0;
            int yesO = 0;
            
            if(gameboard[0][i].equals(a)){
                yesX++;
                checkRowFor3X[0] = true;
            }
            
            if(gameboard[1][i].equals(a)){
                yesX++;
                checkRowFor3X[1] = true;
            }
            
            if(gameboard[2][i].equals(a)){
                yesX++;
                checkRowFor3X[2] = true;
            }
            
            //check if O is placed in the same row
            if(gameboard[0][i].equals(b)){
                yesO++;
            }
            
            if(gameboard[1][i].equals(b)){
                yesO++;
            }
            
            if(gameboard[2][i].equals(b)){
                yesO++;
            }
            

             //if there are exactly 2 Xs in a row, return the coordinates, if not set coordinates to -1, -1
            if(yesX == 2 && yesO < 1){
                for(int k = 0; k < 3; k++){
                    if(checkRowFor3X[k] == false){
                        c[0] = k;
                        c[1] = i;
                    }
                }
            }else{
                c[0] = -1;
                c[1] = -1;
            }
            
            if(yesX == 2 && yesO < 1){
                break;
            }
        }
        return c;
    }
    
    
    
    
    
     /*
    Checks if someone is going to win the next round horizontally
    String a = X or O
    String b = X or O
    Example: String a = X, String b = O
    Returns 1D int array with {-1, -1} if player isn't going to win, or {x, y} of winning spot for the next round
    */
    public static int[] goingToWinHorizontally(String[][] gameboard, String a, String b){
    
        int[] c = new int[2];
    
        //create boolean arrays to check if one row/column is completely played on to ignore 'going to win'
        boolean[] checkRowFor3X = {false, false, false};

            
        //check if board spots are filled by Xs or Os
        for(int i = 0; i < 3; i++){
            
            int yesX = 0;
            int yesO = 0;
            
            
            if(gameboard[i][0].equals(a)){
                yesX++;
                checkRowFor3X[0] = true;
            }
            
            if(gameboard[i][1].equals(a)){
                yesX++;
                checkRowFor3X[1] = true;
            }
            
            if(gameboard[i][2].equals(a)){
                yesX++;
                checkRowFor3X[2] = true;
            }


            //check if O is placed in the same row
            if(gameboard[i][0].equals(b)){
                yesO++;
            }
            
            if(gameboard[i][1].equals(b)){
                yesO++;
            }
            
            if(gameboard[i][2].equals(b)){
                yesO++;
            }
            
            
            //if there are exactly 2 Xs in a row, return the coordinates, if not set coordinates to -1, -1
            if(yesX == 2 && yesO < 1){
                for(int k = 0; k < 3; k++){
                    if(checkRowFor3X[k] == false){
                        c[0] = i;
                        c[1] = k;
                    }
                }
            }else{
                c[0] = -1;
                c[1] = -1;
            }
            
            
            if(yesX == 2 && yesO < 1){
                break;
            }
        }
        return c;
    }
    
    
    
    
     /*
    Checks if someone is going to win the next round diagonally
    String a = X or O
    Returns 1D int array with {-1, -1} if player isn't going to win, or {x, y} of winning spot for the next round
    */
    public static int[] goingToWinDiagonally(String[][] gameboard, String a, String b){
    
        int[] c = new int[2];
    
        //create boolean arrays to check if one row/column is completely played on to ignore 'going to win'
        boolean[] checkRowFor3X = {false, false, false};

            
        //check if board spots are filled by Xs or Os
        for(int i = 0; i < 2; i++){
            
            int yesX = 0;
            int yesO = 0;
            
            //check String a spots
            if(gameboard[0][0].equals(a) && i == 0){
                yesX++;
                checkRowFor3X[0] = true;
            }
            
            if(gameboard[1][1].equals(a)){
                yesX++;
                checkRowFor3X[1] = true;
            }
            
            if(gameboard[2][2].equals(a) && i == 0){
                yesX++;
                checkRowFor3X[2] = true;
            }

            if(gameboard[0][2].equals(a) && i == 1){
                yesX++;
                checkRowFor3X[0] = true;
            }
            
            if(gameboard[2][0].equals(a) && i == 1){
                yesX++;
                checkRowFor3X[2] = true;
            }
            

            //check String b spots
            if(gameboard[0][0].equals(b) && i == 0){
                yesO++;
            }
            
            if(gameboard[1][1].equals(b)){
                yesO++;
            }
            
            if(gameboard[2][2].equals(b) && i == 0){
                yesO++;
            }

            if(gameboard[0][2].equals(b) && i == 1){
                yesO++;
            }
            
            if(gameboard[2][0].equals(b) && i == 1){
                yesO++;
            }
            

            //if there are exactly 2 Xs in a row, return the coordinates, if not set coordinates to -1, -1
            if(yesX == 2 && yesO < 1){
                for(int k = 0; k < 3; k++){
                    if(checkRowFor3X[k] == false && i == 0){
                        c[0] = k;
                        c[1] = k;
                    }else if(checkRowFor3X[k] == false && i == 1 && k == 1){
                        c[0] = k;
                        c[1] = k;
                    }else if(checkRowFor3X[k] == false && i == 1 && k == 0){
                        c[0] = 0;
                        c[1] = 2;
                    }else if(checkRowFor3X[k] == false && i == 1 && k == 2){
                        c[0] = 2;
                        c[1] = 0;
                    }
                }
            }else{
                c[0] = -1;
                c[1] = -1;
            }
            
            
            if(yesX == 2 && yesO < 1){
                break;
            }
        }
        return c;
    }
    
    
    /*
    Returns a int value based if someone won the game
    */
    public static boolean gameOver(String[][] gameboard){
    
        boolean won = false;
        
        //check if player won
        if(wonVertically(gameboard, "X") || wonHorizontally(gameboard, "X") || wonDiagonally(gameboard, "X")){
            won = true;
            System.out.println("Player wins!\n");
        }
        
        //check if bot won
        if(wonVertically(gameboard, "O") || wonHorizontally(gameboard, "O") || wonDiagonally(gameboard, "O")){
            won = true;
            System.out.println("Bot won!\n");
        }
        
        if(tracker.isEmpty() && won == false){
            won = true;
            System.out.println("Game tied!\n");
        }
        
        return won;
    }//end method gameOver
    
    
    
    /*
    Checks if String s (X or O) won diagonally, returns boolean where true = s won
    */
    public static boolean wonDiagonally(String[][] gameboard, String s){
    
        boolean won = false;

        //check if board spots are filled by String s
        for(int i = 0; i < 2; i++){
            
            int yes = 0;
                       
            if(gameboard[0][0].equals(s) && i == 0){
                yes++;
            }
            
            if(gameboard[1][1].equals(s)){
                yes++;
            }
            
            if(gameboard[2][2].equals(s) && i == 0){
                yes++;
            }

            if(gameboard[0][2].equals(s) && i == 1){
                yes++;
            }
            
            if(gameboard[2][0].equals(s) && i == 1){
                yes++;
            }


            //if there are 3 of String s in a row, set won = true
            if(yes == 3){
                won = true;
                break;
            }
        }//end for loop
        
        return won;
    }//end method wonDiagonally
    
    
    
    /*
    Checks if String s (X or O) won vertically, returns boolean where true = s won
    */
    public static boolean wonVertically(String[][] gameboard, String s){
    
        boolean won = false;

        //check if board spots are filled by String s
        for(int i = 0; i < 3; i++){
            
            int yes = 0;

            if(gameboard[0][i].equals(s)){
                yes++;
            }
            
            if(gameboard[1][i].equals(s)){
                yes++;
            }
            
            if(gameboard[2][i].equals(s)){
                yes++;
            }

            //if there are 3 of String s in a row, set won = true
            if(yes == 3){
                won = true;
                break;
            }
        }//end for loop
        
        return won;
    }//end method wonVertically
    
    
    
    /*
    Checks if String s (X or O) won horizontally, returns boolean where true = s won
    */
    public static boolean wonHorizontally(String[][] gameboard, String s){
    
        boolean won = false;

        //check if board spots are filled by String s
        for(int i = 0; i < 3; i++){
            
            int yes = 0;

            if(gameboard[i][0].equals(s)){
                yes++;
            }
            
            if(gameboard[i][1].equals(s)){
                yes++;
            }
            
            if(gameboard[i][2].equals(s)){
                yes++;
            }

            //if there are 3 of String s in a row, set won = true
            if(yes == 3){
                won = true;
                break;
            }
        }//end for loop
        
        return won;
    }//end method wonHorizontally
    
    
    
    
    
    /*
    Takes array coordinates gameboard[x][y] and returns an int of the gameboard spot
    Used for conversion of 2D int array to int gameboard 'spot' for the gameboard 'spot' List tracker
    
    Gameboard 'spots':
    0   1   2
    3   4   5
    6   7   8
    
    Examples: coordinateToInt(2, 2) --> int[2][2] --> returns 8
    coordinateToInt(0, 1) --> int[0][1] --> returns 1
    */
    public static int coordinateToInt(int x, int y){
        
        //setup initial variable that will be returned
        int value = 0;
        
        switch(x){
            case 0: 
                switch(y){
                    case 0:
                        value = 0;
                        break;
                        
                    case 1:
                        value = 1;
                        break;
                        
                    case 2:
                        value = 2;
                        break;
                }
                break;
                
            case 1:
                switch(y){
                    case 0:
                        value = 3;
                        break;
                        
                    case 1:
                        value = 4;
                        break;
                        
                    case 2:
                        value = 5;
                        break;
                }
                break;
                
            case 2:
                switch(y){
                    case 0:
                        value = 6;
                        break;
                        
                    case 1:
                        value = 7;
                        break;
                        
                    case 2:
                        value = 8;
                        break;
                }
            break;
        
        }//end switch x statement
    
        return value;
    }//end method coordinateToInt
    
    
    
    /*
    Takes int of gameboard 'spot' and returns array coordinates gameboard[x][y] 
    Used for conversion of int spot to 2D int array for the gameboard 'spot' List tracker
    
    Gameboard 'spots':
    0   1   2
    3   4   5
    6   7   8
    
    Examples: intToCoordinate(2) --> returns int[0][2]
    intToCoordinate(7) --> returns int[2][1]
    */
    public static int[] intToCoordinate(int value){
    
        //setup initial variables that will be returned
        int[] coor = new int[2];
        int x = 0;
        int y = 0;
        
        switch(value){
            case 0:
                x = 0;
                y = 0;
                break;
               
            case 1:
                x = 0;
                y = 1;
                break;
                
            case 2:
                x = 0;
                y = 2;
                break;
               
            case 3:
                x = 1;
                y = 0;
                break;
                
            case 4:
                x = 1;
                y = 1;
                break;
               
            case 5:
                x = 1;
                y = 2;
                break;
                
            case 6:
                x = 2;
                y = 0;
                break;
               
            case 7:
                x = 2;
                y = 1;
                break;
                
            case 8:
                x = 2;
                y = 2;
                break;

        }//end switch statement
    
        coor[0] = x;
        coor[1] = y;
        
        return coor;
    
    }//end method intToCoordinate
    
}//end class Tictac
