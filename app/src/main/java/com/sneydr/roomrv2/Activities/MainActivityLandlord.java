package com.sneydr.roomrv2.Activities;

import androidx.annotation.NonNull;


import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.RabbitMQ.RabbitMQObserver;
import com.sneydr.roomrv2.Services.LeaseService;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.sneydr.roomrv2.App.Permission.INTERNET_PERMISSION_REQUEST_CODE;


public class MainActivityLandlord extends AppCompatActivity implements RabbitMQObserver {
    //BottomNavigationView bottomMenu;
    Toolbar myToolbar;


    AppBarConfiguration appBarConfiguration;
    NavController navController;
    private byte[] pdfBytes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landlord);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("authToken")){

            myToolbar = findViewById(R.id.toolbarLandlord);
            myToolbar.setTitleTextColor(getResources().getColor(R.color.White));

            navController = Navigation.findNavController(this, R.id.nav_landlord_host_fragment);
            navController.setGraph(R.navigation.landlord_nav_graph, bundle);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            NavigationUI.setupWithNavController(myToolbar, navController, appBarConfiguration);
            navController.addOnDestinationChangedListener(onDestinationChangedListener);
            return;
        }
        Dialog dialog = new Dialog(this);
        dialog.setMessage("Could not read auth token");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onBackPressed();
            }
        });
        dialog.buildErrorDialog().show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_landlord_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(!Navigation.findNavController(this, R.id.nav_landlord_host_fragment).popBackStack()){
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
        NavController navController = Navigation.findNavController(this, R.id.nav_landlord_host_fragment);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            if (resultData.getData() != null)
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(resultData.getData());
                    buildPDF(pdfBytes, outputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void onLease(byte[] lease) {
        pdfBytes = lease;
        Intent stopIntent = new Intent(this, LeaseService.class);
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_TITLE, "Ontario Lease Agreement");
        intent.setType("application/pdf");
        stopService(stopIntent);
        startActivityForResult(intent, 3);
    }


    private void buildPDF(byte[] bytes, OutputStream output)  {
        InputStream input = new ByteArrayInputStream(bytes);
        byte[] data = new byte[1024];
        int count;
        try {
            while ((count = input.read(data)) > -1) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
