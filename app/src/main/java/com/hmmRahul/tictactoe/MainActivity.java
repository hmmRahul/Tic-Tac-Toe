package com.hmmRahul.tictactoe;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.hmmRahul.tictactoe.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //used ViewBinding
    private ActivityMainBinding activityMainBinding;

    // 0 - RED
    // 1 - BLUE
    int activePlayer;
    String winner = "";
    int totalSelectedBoxes = 0;
    final Handler handler = new Handler();
    // StateMeaning 0 -> RED, 1 -> BLUE, 2 -> NULL
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //all winning cell combination
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //hiding the Status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hiding action bar
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //randomly select first person to start
        randomFirstPlayer();
        //set the screen color and turn according to  active player
        initialScreen();

        //for resetting the game
        activityMainBinding.resetView.setOnClickListener(v -> resetGame());
    }

    //this function calls whenever user click on any cell
    public void playerTap(View view) {
        showTurn();
        ImageView imageView = (ImageView) view;
        int tappedImage = Integer.parseInt(imageView.getTag().toString());
        if (gameState[tappedImage] == 2) {
            totalSelectedBoxes++;
            gameState[tappedImage] = activePlayer;
            if (activePlayer == 0) {
                imageView.setImageResource(R.drawable.ic_cross);
                activePlayer = 1;
            } else {
                imageView.setImageResource(R.drawable.ic_round);
                activePlayer = 0;
            }
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_out);
            imageView.startAnimation(animation);
        }

        //check if any player has WON
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                //if true this means someone WON
                if (gameState[winPosition[0]] == 0) {
                    // 0 -> RED
                    winner = "RED WINS";
                } else {
                    // 1 -> BLUE
                    winner = "BLUE WINS";
                }
            }
        }
        if(totalSelectedBoxes==9 && winner.equals(""))
        {
            winner = "MATCH DRAW";
        }
        if (!winner.equals("")) {
            Fragment fragment = new WinnerDeclarationFrag();
            getSupportFragmentManager().beginTransaction().add(activityMainBinding.mainRelativeLayout.getId(), fragment).commit();
            //to make back activity not touchable while displaying result
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            //get main screen back after result display
            getMainScreenBack();
        }
    }

    //used by fragment for getting the winner and displaying it
    public String getWinner() {
        return winner;
    }

    public void resetGame() {
        if (isAlreadyReset()) {
            return;
        }
        totalSelectedBoxes = 0;
        randomFirstPlayer();
        winner = "";
        //resetting all values to 2 i.e. Empty cells
        Arrays.fill(gameState, 2);
        activityMainBinding.yourTurnP1.animate().translationY(activityMainBinding.yourTurnP1.getHeight());
        activityMainBinding.yourTurnP2.animate().translationY(-activityMainBinding.yourTurnP2.getHeight());
        activityMainBinding.btnImg1.setImageResource(0);
        activityMainBinding.btnImg2.setImageResource(0);
        activityMainBinding.btnImg3.setImageResource(0);
        activityMainBinding.btnImg4.setImageResource(0);
        activityMainBinding.btnImg5.setImageResource(0);
        activityMainBinding.btnImg6.setImageResource(0);
        activityMainBinding.btnImg7.setImageResource(0);
        activityMainBinding.btnImg8.setImageResource(0);
        activityMainBinding.btnImg9.setImageResource(0);
        initialScreen();
    }

    //get back to main Screen after 2 sec
    public void getMainScreenBack() {
        handler.postDelayed(() -> {
            resetGame();
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.mainRelativeLayout)).commit();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }, 2000);
    }

    //alternate turn display
    public void showTurn() {
        if (activePlayer == 0) {
            activityMainBinding.yourTurnP2.animate().translationY(0);
            activityMainBinding.yourTurnP1.animate().translationY(activityMainBinding.yourTurnP1.getHeight());
            activityMainBinding.mainRelativeLayout.setBackgroundColor(getResources().getColor(R.color.player2));
        } else {
            activityMainBinding.yourTurnP1.animate().translationY(0);
            activityMainBinding.yourTurnP2.animate().translationY(-activityMainBinding.yourTurnP2.getHeight());
            activityMainBinding.mainRelativeLayout.setBackgroundColor(getResources().getColor(R.color.player1));
        }
    }

    public void randomFirstPlayer() {
        Random random = new Random();
        activePlayer = random.nextInt(2);
    }

    //new Game started
    public void initialScreen() {
        if (activePlayer == 0) {
            activityMainBinding.yourTurnP1.animate().translationY(0);
            activityMainBinding.mainRelativeLayout.setBackgroundColor(getResources().getColor(R.color.player1));
        } else {
            activityMainBinding.yourTurnP2.animate().translationY(0);
            activityMainBinding.mainRelativeLayout.setBackgroundColor(getResources().getColor(R.color.player2));
        }
    }

    public boolean isAlreadyReset() {
        boolean isReset = true;
        for (int state : gameState) {
            if (state != 2) {
                isReset = false;
                break;
            }
        }
        return isReset;
    }
}

