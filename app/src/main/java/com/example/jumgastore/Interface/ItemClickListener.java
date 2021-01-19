package com.example.jumgastore.Interface;

import android.view.View;

public interface ItemClickListener {

    void Onclick(View view, int position, boolean isLongClick);

    void delete(int position);
}
