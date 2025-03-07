package com.zinko.stickers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView stickerPackList;
    private TextView noPacksText;
    private StickerPackAdapter adapter;
    private List<StickerPack> stickerPacks;
    private Button createPackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stickerPackList = findViewById(R.id.stickerPackList);
        noPacksText = findViewById(R.id.noPacksText);
        createPackButton = findViewById(R.id.createPackButton);

        stickerPacks = new ArrayList<>();
        adapter = new StickerPackAdapter(this);
        stickerPackList.setLayoutManager(new LinearLayoutManager(this));
        stickerPackList.setAdapter(adapter);

        createPackButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreatePackActivity.class);
            startActivityForResult(intent, 1);  // Código de solicitud 1
        });

        updateUI();
    }

    private void updateUI() {
        if (stickerPacks.isEmpty()) {
            stickerPackList.setVisibility(View.GONE);
            noPacksText.setVisibility(View.VISIBLE);
        } else {
            stickerPackList.setVisibility(View.VISIBLE);
            noPacksText.setVisibility(View.GONE);
            adapter.setStickerPacks(stickerPacks);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            StickerPack newPack = (StickerPack) data.getSerializableExtra("newPack");
            if (newPack != null) {
                stickerPacks.add(newPack);
                updateUI();
            }
        }
    }
}