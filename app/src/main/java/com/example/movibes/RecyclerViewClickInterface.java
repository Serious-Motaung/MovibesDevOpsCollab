package com.example.movibes;

import android.view.View;

public interface RecyclerViewClickInterface {
    void onItemClick(int position);
    void onLongItemClick(int position);
    void onVibeClick(int position, View view);
}
