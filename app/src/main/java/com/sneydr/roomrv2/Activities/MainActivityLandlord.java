package com.sneydr.roomrv2.Activities;

import androidx.annotation.NonNull;


import com.sneydr.roomrv2.App.ConnectionManager;
import com.sneydr.roomrv2.App.Constants;
import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.App.NotificationHelper;
import com.sneydr.roomrv2.Network.Observables.ActivityObservable;
import com.sneydr.roomrv2.Network.Observers.ActivityObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Services.NotificationJobService;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;



import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.content.ContentValues.TAG;
import static com.sneydr.roomrv2.App.Permission.INTERNET_PERMISSION_REQUEST_CODE;


public class MainActivityLandlord extends AppCompatActivity implements ActivityObservable {
    //BottomNavigationView bottomMenu;
    Toolbar myToolbar;


    AppBarConfiguration appBarConfiguration;
    NavController navController;
    private byte[] pdfBytes;
    private ActivityObserver fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landlord);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("authToken")) {

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
        if (requestCode == Constants.DOCUMENT_INTENT && resultCode == Activity.RESULT_OK) {
            if (resultData.getData() != null) {
                ConnectionManager connectionManager = ConnectionManager.getInstance();
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(resultData.getData());
                    DownloadTask downloadTask = new DownloadTask(this, connectionManager.getLeaseUrl(), outputStream, resultData.getData());
                    downloadTask.execute();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == Constants.IMAGE_INTENT && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), resultData.getData());
                    if (bitmap != null) { // sanity check
                        File outputFile = new File(getCacheDir(), "Profile.jpg"); // follow the API for createTempFile
                        FileOutputStream stream = new FileOutputStream (outputFile, false); // Add false here so we don't append an image to another image. That would be weird.
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.close();
                        notifyObserver(outputFile);
                    }
                    else {
                        notifyFailure("Activity","Bitmap failed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    notifyFailure("Activity","No file space in cache");
                }
            }
            else {
                notifyFailure("Activity","Image not selected");
            }

        }
    }

    @Override
    public void notifyObserver(File file) {
        this.fragment.onFile(file);
    }

    @Override
    public void registerObserver(NetworkObserver networkObserver) {
        this.fragment = (ActivityObserver) networkObserver;
    }

    @Override
    public void clearObserver() {
        this.fragment = null;
    }

    @Override
    public void notifyFailure(String tag, String response) {
        this.fragment.onFailure(tag, response);
    }




    private class DownloadTask extends AsyncTask<Void, Void, Void> {

        private String leaseUrl;
        private ConnectionManager connectionManager;
        private NotificationHelper notificationHelper;
        private OutputStream outputStream;
        private Uri uri;



        public DownloadTask(Context context, String leaseUrl, OutputStream outputStream, Uri uri) {
            this.leaseUrl = leaseUrl;
            this.notificationHelper = new NotificationHelper(context);
            this.connectionManager = ConnectionManager.getInstance();
            this.outputStream = outputStream;
            this.uri = uri;
        }

        @Override
        protected Void doInBackground(Void... voids) {


            URL url = connectionManager.parseURL(leaseUrl);
            if (url == null) return null;

            URLConnection connection = connectionManager.openConnection(url);
            if (connection == null) return null;

            int lengthOfFile = connectionManager.getFileLength(connection);

            notificationHelper.setContentTitle("Ontario Lease Agreement");
            notificationHelper.setContentText("Downloading...");
            notificationHelper.setSmallIcon(R.drawable.ic_assignment_black_24dp);
            notificationHelper.setPriority(NotificationCompat.PRIORITY_LOW);
            notificationHelper.setProgress(0);
            notificationHelper.build();

            try {
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                byte data[] = new byte[1024];

                int count;
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    notificationHelper.setProgress((int)((total * 100) / lengthOfFile));
                    outputStream.write(data, 0, count);
                }

                // flushing output
                outputStream.flush();

                // closing streams
                outputStream.close();
                input.close();


                notificationHelper.setContentText("Download Complete");
                notificationHelper.setProgress(100);
                notificationHelper.build();

                //File file = new File(Environment.getExternalStorageDirectory().toString() + "/Download/OntarioLease.pdf");

                //Uri photoURI = FileProvider .getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setDataAndType(uri, "application/pdf");

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);





            } catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
            }


            return null;
        }


    }



}
