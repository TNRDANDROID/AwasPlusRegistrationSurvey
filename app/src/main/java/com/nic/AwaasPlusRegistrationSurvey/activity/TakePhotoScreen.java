package com.nic.AwaasPlusRegistrationSurvey.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.nic.AwaasPlusRegistrationSurvey.R;
import com.nic.AwaasPlusRegistrationSurvey.constant.AppConstant;
import com.nic.AwaasPlusRegistrationSurvey.dataBase.dbData;
import com.nic.AwaasPlusRegistrationSurvey.databinding.TakePhotoBinding;
import com.nic.AwaasPlusRegistrationSurvey.model.AwaasPlusRegistrationSurvey;
import com.nic.AwaasPlusRegistrationSurvey.utils.Utils;

import java.util.ArrayList;

public class TakePhotoScreen extends AppCompatActivity {
    public TakePhotoBinding takePhotoBinding;
    private dbData dbData = new dbData(this);
    String pmay_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhotoBinding = DataBindingUtil.setContentView(this, R.layout.take_photo);
        takePhotoBinding.setActivity(this);

        pmay_id = getIntent().getStringExtra("lastInsertedID");
    }

    public void viewCamera(final int type_of_photo) {

        if(type_of_photo == 2){
            dbData.open();
            ArrayList<AwaasPlusRegistrationSurvey> imageOffline = dbData.getSavedPMAYImages(pmay_id,"1");

            if (!(imageOffline.size() > 0)){
                Utils.showAlert(this, "Please Capture Beneficiary Image");
               return;
            }
        }
        if (type_of_photo == 2) {
            new AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage("Capture Beneficiary house 10 meter, near from his house")
                    .setIcon(R.mipmap.alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            openCameraScreen(type_of_photo);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    }).show();
        } else {
            openCameraScreen(type_of_photo);
        }

    }

    public void openCameraScreen(int type_of_photo) {
        Intent intent = new Intent(TakePhotoScreen.this, CameraScreen.class);
        intent.putExtra("lastInsertedID", getIntent().getStringExtra("lastInsertedID"));
        intent.putExtra(AppConstant.TYPE_OF_PHOTO, String.valueOf(type_of_photo));
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void homePage() {
        Intent intent = new Intent(this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Home", "Home");
        startActivity(intent);
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }

    @Override
    public void onBackPressed() {
        dbData.open();
        ArrayList<AwaasPlusRegistrationSurvey> imageOffline = dbData.getSavedPMAYImages(pmay_id,"");
        if(imageOffline.size() == 2){
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage("Missing Photo. Please,Capture it")
                    .setIcon(R.mipmap.alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    }).show();
        }


    }

    public void onBackPress() {
        dbData.open();
        ArrayList<AwaasPlusRegistrationSurvey> imageOffline = dbData.getSavedPMAYImages(pmay_id,"");
        if(imageOffline.size() == 2){
            super.onBackPressed();
            setResult(Activity.RESULT_CANCELED);
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage("Missing Photo. Please,Capture it")
                    .setIcon(R.mipmap.alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    }).show();

        }

    }
}
