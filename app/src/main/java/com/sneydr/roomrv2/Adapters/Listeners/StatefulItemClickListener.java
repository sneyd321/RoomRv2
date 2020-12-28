package com.sneydr.roomrv2.Adapters.Listeners;

import android.view.View;

import com.sneydr.roomrv2.Adapters.ButtonState.ButtonStateContext;

public interface StatefulItemClickListener {

    void onItemClick(View view, int position, ButtonStateContext buttonStateContext);
}
