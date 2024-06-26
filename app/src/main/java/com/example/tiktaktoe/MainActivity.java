package com.example.tiktaktoe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiktaktoe.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 9 zeros
    private int playerTurn = 1;
    private int totalSelectedBoxes = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});

        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");
        binding.playerOneName.setText(getPlayerOneName);
        binding.playerTwoName.setText(getPlayerTwoName);

        binding.image1.setOnClickListener(view -> {
            if (isBoxSelectable(0)) {
                performAction((ImageView) view, 0);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.image2.setOnClickListener(view -> {
            if (isBoxSelectable(1)) {
                performAction((ImageView) view, 1);
            }
        });
        binding.image3.setOnClickListener(view -> {
            if (isBoxSelectable(2)) {
                performAction((ImageView) view, 2);
            }
        });
        binding.image4.setOnClickListener(view -> {
            if (isBoxSelectable(3)) {
                performAction((ImageView) view, 3);
            }
        });
        binding.image5.setOnClickListener(view -> {
            if (isBoxSelectable(4)) {
                performAction((ImageView) view, 4);
            }
        });
        binding.image6.setOnClickListener(view -> {
            if (isBoxSelectable(5)) {
                performAction((ImageView) view, 5);
            }
        });
        binding.image7.setOnClickListener(view -> {
            if (isBoxSelectable(6)) {
                performAction((ImageView) view, 6);
            }
        });
        binding.image8.setOnClickListener(view -> {
            if (isBoxSelectable(7)) {
                performAction((ImageView) view, 7);
            }
        });
        binding.image9.setOnClickListener(view -> {
            if (isBoxSelectable(8)) {
                performAction((ImageView) view, 8);
            }
        });
    }

    protected void performAction(ImageView imageView, int selectedBoxPosition) {
        boxPositions[selectedBoxPosition] = playerTurn;

        if (playerTurn == 1) {
            imageView.setImageResource(R.drawable.xx);
            if (checkResults()) {
                ResultDialoge resultDialoge = new ResultDialoge(MainActivity.this, binding.playerOneName.getText().toString() + " is a Winner!", MainActivity.this);
                resultDialoge.setCancelable(false);
                resultDialoge.show();
            } else if (totalSelectedBoxes == 9) {
                ResultDialoge resultDialog = new ResultDialoge(MainActivity.this, "Match Draw", MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(2);
                totalSelectedBoxes++;
            }
        } else {
            imageView.setImageResource(R.drawable.oo);
            if (checkResults()) {
                ResultDialoge resultDialoge = new ResultDialoge(MainActivity.this, binding.playerTwoName.getText().toString() + " is a Winner!", MainActivity.this);
                resultDialoge.setCancelable(false);
                resultDialoge.show();
            } else if (totalSelectedBoxes == 9) {
                ResultDialoge resultDialog = new ResultDialoge(MainActivity.this, "Match Draw", MainActivity.this);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else {
                changePlayerTurn(1);
                totalSelectedBoxes++;
            }
        }
    }

    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            binding.playerOneLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerTwoLayout.setBackgroundResource(R.drawable.white_box);
        } else {
            binding.playerTwoLayout.setBackgroundResource(R.drawable.black_border);
            binding.playerOneLayout.setBackgroundResource(R.drawable.white_box);
        }
    }

    private boolean checkResults() {
        boolean response = false;
        for (int[] combination : combinationList) {
            if (boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {
                response = true;
                break;
            }
        }
        return response;
    }

    private boolean isBoxSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0;
    }

    public void restartMatch() {
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}; // 9 zeros
        playerTurn = 1;
        totalSelectedBoxes = 1;

        binding.image1.setImageDrawable(null);
        binding.image2.setImageDrawable(null);
        binding.image3.setImageDrawable(null);
        binding.image4.setImageDrawable(null);
        binding.image5.setImageDrawable(null);
        binding.image6.setImageDrawable(null);
        binding.image7.setImageDrawable(null);
        binding.image8.setImageDrawable(null);
        binding.image9.setImageDrawable(null);

        changePlayerTurn(1);
    }
}
