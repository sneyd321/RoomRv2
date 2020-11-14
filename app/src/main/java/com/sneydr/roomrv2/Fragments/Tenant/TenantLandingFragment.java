package com.sneydr.roomrv2.Fragments.Tenant;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sneydr.roomrv2.Adapters.TenantLandingUtilitiesRecyclerViewAdapter;
import com.sneydr.roomrv2.App.UI.CircularProgressBarAnimation;
import com.sneydr.roomrv2.Network.Observers.HouseObserver;
import com.sneydr.roomrv2.Network.Observers.TenantObserver;
import com.sneydr.roomrv2.Repositories.HouseRepository;
import com.sneydr.roomrv2.Repositories.TenantRepository;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.RentDetails.CalendarHandler;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Fragments.FragmentTemplate;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.R;

import okhttp3.Request;


public class TenantLandingFragment extends FragmentTemplate implements TenantObserver, HouseObserver {


    ProgressBar circularProgressBar;
    TextView txtDueDate, txtRentAmount, txtRentalUnitPrimaryAddress, txtRentalUnitSecondaryAddress, txtHomeownerName, txtDaysLeft;
    RecyclerView rcyUtilities;
    TenantLandingUtilitiesRecyclerViewAdapter adapter;
    CalendarHandler calendarHandler;
    Button btnPayRent;
    private int tenantId;
    private HouseRepository houseRepository;



    @Override
    protected void initUI(View view) {
        houseRepository = new HouseRepository(this);
        txtDueDate = view.findViewById(R.id.txtTenantRentDueDate);
        txtDaysLeft = view.findViewById(R.id.txtDaysLeft);
        txtRentAmount = view.findViewById(R.id.txtAmountDue);
        btnPayRent = view.findViewById(R.id.btnPayRent);
        btnPayRent.setOnClickListener(onPayRent);
        txtRentalUnitPrimaryAddress = view.findViewById(R.id.txtTenantLandingAddress);
        txtRentalUnitSecondaryAddress = view.findViewById(R.id.txtTenantLandingAddressSecondary);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);
        txtHomeownerName = view.findViewById(R.id.txtHomeownerName);
        rcyUtilities = view.findViewById(R.id.rcyTenantLandingUtilities);
        rcyUtilities.setLayoutManager(new LinearLayoutManager(context));
        calendarHandler = new CalendarHandler();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tenant_landing_page, container, false);
        TenantRepository tenantRepository = new TenantRepository(this);
        tenantRepository.getTenant(tenantId);
        initUI(view);
        return view;
    }

    @Override
    public void onTenant(Tenant tenant) {
        if (tenant != null) {
            if (tenant.isApproved()){
                houseRepository.getHouse(tenant.getHouseId());
            }
        }
    }

    @Override
    public void onHouse(House house) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (house != null) {
                    Animation anim = new CircularProgressBarAnimation(circularProgressBar, 0, calendarHandler.getDayInMonth(), calendarHandler.getTotalDaysInMonth());
                    anim.setDuration(1500);
                    String rentDueDate = house.getRentDetails().getRentDueDate();
                    circularProgressBar.startAnimation(anim);
                    int daysLeft = calendarHandler.getDaysUntilDueDate(rentDueDate) - calendarHandler.getDayInMonth();
                    txtDaysLeft.setText(daysLeft + "\ndays");
                    txtRentAmount.setText("$" + Integer.toString(house.getRentDetails().getBaseRent()));
                    txtDueDate.setText(calendarHandler.getFormattedDueDate(rentDueDate));
                    adapter = new TenantLandingUtilitiesRecyclerViewAdapter(context, house.getUtilities());
                    rcyUtilities.swapAdapter(adapter, true);
                    txtRentalUnitPrimaryAddress.setText(house.getRentalUnitLocation().getFormattedPrimaryAddress());
                    txtRentalUnitSecondaryAddress.setText(house.getRentalUnitLocation().getFormattedSecondaryAddress());
                    String rentMadePayable = house.getRentDetails().getRentMadePayableTo();
                    if (rentMadePayable == null) {
                        txtHomeownerName.setText("Cheque option not enabled");
                    } else {
                        txtHomeownerName.setText(rentMadePayable);
                    }
                }
            }
        });


    }

    private View.OnClickListener onPayRent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Not implemented for release: 1", Toast.LENGTH_SHORT).show();
        }
    };


    public TenantLandingFragment setTenantId(int tenantId) {
        this.tenantId = tenantId;
        return this;
    }
















}
