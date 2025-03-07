package com.zinko.stickers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class StickerPackAdapter extends RecyclerView.Adapter<StickerPackAdapter.ViewHolder> {
    private List<StickerPack> stickerPacks;
    private Context context;

    public StickerPackAdapter(Context context) {
        this.context = context;
        this.stickerPacks = new ArrayList<>();
    }

    public void setStickerPacks(List<StickerPack> packs) {
        this.stickerPacks = packs;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sticker_pack, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StickerPack pack = stickerPacks.get(position);
        holder.packName.setText(pack.getName());
        holder.packAuthor.setText(pack.getAuthor());

        // Añadir miniaturas (máximo 5)
        holder.previewContainer.removeAllViews();
        int previewCount = Math.min(pack.getImagePaths().size(), 5);
        for (int i = 0; i < previewCount; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
            params.setMargins(0, 0, 8, 0);
            imageView.setLayoutParams(params);
            Glide.with(context).load(pack.getImagePaths().get(i)).into(imageView);
            holder.previewContainer.addView(imageView);
        }

        holder.deleteButton.setOnClickListener(v -> {
            // Lógica de borrado (se implementará en el Paso 3)
        });

        holder.shareButton.setOnClickListener(v -> {
            // Lógica de compartir (se implementará en el Paso 3)
        });

        holder.itemView.setOnClickListener(v -> {
            // Lógica de edición (se implementará en el Paso 3)
        });
    }

    @Override
    public int getItemCount() {
        return stickerPacks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView packName, packAuthor;
        LinearLayout previewContainer;
        Button deleteButton, shareButton;

        ViewHolder(View itemView) {
            super(itemView);
            packName = itemView.findViewById(R.id.packName);
            packAuthor = itemView.findViewById(R.id.packAuthor);
            previewContainer = itemView.findViewById(R.id.previewContainer);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            shareButton = itemView.findViewById(R.id.shareButton);
        }
    }
}