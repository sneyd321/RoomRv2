package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TenantLandingFragment extends Fragment {


    CircularProgressBar circularProgressBar;
    TextView txtDueDate;

    Tenant tenant;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenant_landing_page, container, false);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);
        txtDueDate = view.findViewById(R.id.txtTenantRentDueDate);
        circularProgressBar.setProgressMax(getTotalDaysInMonth());
        circularProgressBar.setProgressWithAnimation(getDayInMonth(), 2000l);
        txtDueDate.setText(getDueDate("Last"));





        //TenantNameRecyclerViewAdapter adapter = new TenantNameRecyclerViewAdapter(getActivity(), house.getTenants());
        //rcyTenantName.setAdapter(adapter);
        //adapter.notifyDataSetChanged();



        return view;
    }

    private String getDueDate(String dueDate) {
        Calendar calendar = Calendar.getInstance();
        int day = 0;
        if (dueDate.equals("First")) {
            day = calendar.getActualMinimum(Calendar.DATE);
        }
        if (dueDate.equals("Second")) {
            day = calendar.getActualMinimum(Calendar.DATE) + 1;
        }
        if (dueDate.equals("Last")) {
            day = calendar.getActualMaximum(Calendar.DATE);
        }
        if (day == 0) return "Error getting date";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MMMM");
        String weekDayMonth = simpleDateFormat.format(calendar.getTime());

        return weekDayMonth + " " + day;

    }

    private int getDayInMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    private int getTotalDaysInMonth() {
        Calendar calendar = Calendar.getInstance();
        switch (calendar.get(Calendar.MONTH)){
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                if (isLeapYear()) return 29;
                return 28;
            default:
                return 31;
        }
    }

    private boolean isLeapYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year % 4 == 0;
    }




    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }


}
