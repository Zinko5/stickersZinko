package com.zinko.stickers;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class CreatePackActivity extends AppCompatActivity {
    private EditText nameEditText, authorEditText;
    private Button addImagesButton, saveButton, cancelButton;
    private LinearLayout imagesContainer;
    private List<Uri> selectedImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pack);

        // Vincular vistas
        nameEditText = findViewById(R.id.nameEditText);
        authorEditText = findViewById(R.id.authorEditText);
        addImagesButton = findViewById(R.id.addImagesButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        imagesContainer = findViewById(R.id.imagesContainer);

        // Configurar listeners
        addImagesButton.setOnClickListener(v -> openImageSelector());
        saveButton.setOnClickListener(v -> savePack());
        cancelButton.setOnClickListener(v -> confirmCancel());
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Selecciona imágenes"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                    displayImageThumbnail(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);
                displayImageThumbnail(imageUri);
            }
        }
    }

    private void displayImageThumbnail(Uri imageUri) {
        View thumbnailView = LayoutInflater.from(this).inflate(R.layout.item_image_thumbnail, imagesContainer, false);
        ImageView thumbnailImage = thumbnailView.findViewById(R.id.thumbnailImage);
        ImageView deleteIcon = thumbnailView.findViewById(R.id.deleteIcon);

        Glide.with(this).load(imageUri).into(thumbnailImage);

        deleteIcon.setOnClickListener(v -> {
            selectedImages.remove(imageUri);
            imagesContainer.removeView(thumbnailView);
        });

        imagesContainer.addView(thumbnailView);
    }

    private void savePack() {
        String name = nameEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
    
        if (name.isEmpty()) name = " ";
        if (author.isEmpty()) author = " ";
    
        if (selectedImages.isEmpty()) {
            Toast.makeText(this, "Añade al menos una imagen", Toast.LENGTH_SHORT).show();
            return;
        }
    
        // Crear el paquete
        StickerPack newPack = new StickerPack(name, author);
        for (Uri uri : selectedImages) {
            newPack.addImage(uri.toString());  // Guardar la URI como String
        }
    
        // Devolver el paquete a MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newPack", newPack);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void confirmCancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estás seguro de que quieres cancelar la creación del paquete?");
        builder.setPositiveButton("Sí", (dialog, which) -> finish());
        builder.setNegativeButton("No", null);
        builder.show();
    }
}