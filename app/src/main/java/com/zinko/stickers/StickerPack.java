package com.zinko.stickers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StickerPack implements Serializable {
    private String name;
    private String author;
    private List<String> imagePaths;

    public StickerPack(String name, String author) {
        this.name = name != null && !name.trim().isEmpty() ? name : " ";
        this.author = author != null && !author.trim().isEmpty() ? author : " ";
        this.imagePaths = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name != null && !name.trim().isEmpty() ? name : " "; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author != null && !author.trim().isEmpty() ? author : " "; }
    public List<String> getImagePaths() { return imagePaths; }
    public void addImage(String path) { imagePaths.add(path); }
    public void removeImage(String path) { imagePaths.remove(path); }
}