package com.sneydr.roomrv2.App.Thread;

import androidx.databinding.DataBindingUtil;

import com.sneydr.roomrv2.App.Button.AddPhotoButton;
import com.sneydr.roomrv2.App.CircleTransform;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Fragments.HousesFragment;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.FragmentHouseBinding;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class OnHomeowner implements Runnable {


    private Homeowner homeowner;
    private FragmentHouseBinding binding;

    public OnHomeowner(FragmentHouseBinding binding, Homeowner homeowner) {
        this.homeowner = homeowner;
        this.binding =  binding;
    }

    @Override
    public void run() {
        if (homeowner.getImageURL() == null || homeowner.getImageURL().isEmpty()) {
            binding.componentHomeownerProfile.imageView2.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(homeowner.getImageURL())
                    .transform(new CircleTransform(binding.getRoot().getContext())).fit()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .error(R.drawable.ic_baseline_account_circle_24)
                    .centerCrop()
                    .into(binding.componentHomeownerProfile.imageView2);
        }
        binding.componentHomeownerProfile.textView3.setText(homeowner.getFullName());

    }
}
