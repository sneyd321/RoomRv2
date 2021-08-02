package com.sneydr.roomrv2.Adapters;

import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.sneydr.roomrv2.Fragments.DocumentsFragment;
import com.sneydr.roomrv2.Fragments.MessageFragment;
import com.sneydr.roomrv2.Fragments.ProblemsFragment;
import com.sneydr.roomrv2.Fragments.TenantsFragment;

public class ViewPager2FragmentStateAdapter extends FragmentStateAdapter {

    private String authToken;
    private int houseId;
    private String email;
    private List<Fragment> fragments;
    private Parcelable parcelable;


    public ViewPager2FragmentStateAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }


    @Override
    public int getItemCount() {
        return fragments.size();
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }
}
