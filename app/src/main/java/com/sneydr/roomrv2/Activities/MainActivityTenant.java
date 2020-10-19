package com.sneydr.roomrv2.Activities;

import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static com.sneydr.roomrv2.App.Permission.INTERNET_PERMISSION_REQUEST_CODE;


public class MainActivityTenant extends AppCompatActivity {


    BottomNavigationView bottomMenu;
    Toolbar myToolbar;
    AppBarConfiguration appBarConfiguration;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tenant);




        bottomMenu = findViewById(R.id.navBarTenant);
        myToolbar = findViewById(R.id.toolbarTenant);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.White));

        navController = Navigation.findNavController(this, R.id.nav_tenant_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(myToolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomMenu, navController);
        navController.addOnDestinationChangedListener(onDestinationChangedListener);


        if (savedInstanceState == null) {
            bottomMenu.getMenu().getItem(1).setChecked(true);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_tenant_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if(!Navigation.findNavController(this, R.id.nav_tenant_host_fragment).popBackStack()){
            finish();
        }
    }


    private NavController.OnDestinationChangedListener onDestinationChangedListener = new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
            myToolbar.setTitle(destination.getLabel());
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_tenant_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == INTERNET_PERMISSION_REQUEST_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Internet permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Internet permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }





}








