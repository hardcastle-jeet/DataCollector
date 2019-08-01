package com.hardcastle.datacollectorapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hardcastle.datacollectorapp.RetrofitBasics.UserDTO;
import com.hardcastle.datacollectorapp.RetrofitBasics.UserDetails;
import com.hardcastle.datacollectorapp.RetrofitBasics.UserManager;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static android.Manifest.permission.CAMERA;


public class AddPointDetailsActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 0;
    private static final int RC_PERMISSIONS = 1;
    Bitmap myBitmap;
    Uri picUri;
    String encodedImage=null;
    JSONObject jsonObject;

    int SELECT_PICTURE = 101;
    int CAPTURE_IMAGE = 102;

    private ArrayList<String> permissionsToRequest;
    private ArrayList <String> permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;
      int  REQUEST_IMAGE_CAPTURE=0;
    EditText etAddress, etComment;
    ImageView imageView;
    PointDetailsModel selectedPoint;
    String userId,latitude,longitude;

    private ProgressDialog progressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point_details);

         //selectedPoint = getIntent().getParcelableExtra("Point");
        //Toast.makeText(this, "" + selectedPoint.getLatLng().latitude + " ---" + selectedPoint.getLatLng().longitude, Toast.LENGTH_SHORT).show();

        userId=getIntent().getStringExtra("userId");
        latitude=getIntent().getStringExtra("Latitude");
        longitude=getIntent().getStringExtra("Longitude");

        etAddress = findViewById(R.id.et_address);
        etComment = findViewById(R.id.et_comments);

        imageView=findViewById(R.id.imageView);
         selectedPoint=new PointDetailsModel();
        selectedPoint.setLATITUDE(latitude);
        selectedPoint.setLONGITUDE(longitude);
        selectedPoint.setADDRESS(etAddress.getText().toString());
        selectedPoint.setCOMMENTS(etComment.getText().toString());
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
    }

    public void onSave(View view) {
        if (etAddress.getText().toString().trim().equalsIgnoreCase("")){
            etAddress.setError("Please enter adress");
        }else  if (etComment.getText().toString().trim().equalsIgnoreCase("")){
            etComment.setError("Please enter comment");
        }else {
            showProgressDialog();
            String addressValue = etAddress.getText().toString();
            String latValue = String.valueOf(latitude);
            String longValue = String.valueOf(longitude);
            String commentValue = etComment.getText().toString();
            String fileUploadvalue = encodedImage;
            String userIdValue = userId;

            //loginProcessWithRetrofit(et_username.getText().toString(),et_password.getText().toString());
            Call<ResponseBody> call = UserManager.getUserManagerService(null).AddPoint(addressValue, latValue, longValue, commentValue, fileUploadvalue, userIdValue);

            // Create a Callback object, because we do not set JSON converter, so the return object is ResponseBody be default.
            retrofit2.Callback<ResponseBody> callback = new Callback<ResponseBody>() {

                // When server response.
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    hideProgressDialog();

                    StringBuffer messageBuffer = new StringBuffer();
                    int statusCode = response.code();
                    Log.i("Prasann", statusCode + "");

                    // Get return string.
                    ResponseBody returnBody = response.body();
                    // Log.i("PrasannLogin", returnBody.getSTATUS() + "");

                    if (statusCode == 200) {
                        // messageBuffer.append(registerResponse.getMessage());
                        Toast.makeText(AddPointDetailsActivity.this, "Successfully Adeded", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddPointDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    hideProgressDialog();
                    call.cancel();
                    Log.i("uvsjd", t.getMessage());
                }
            };
            call.enqueue(callback);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onCapture(View view) {
        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};
        requestPermissions(permissions, RC_PERMISSIONS);

       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);*/
       /* permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        dispatchTakePictureIntent();*/
      //  setPic(currentPhotoPath);



    }



    static final int REQUEST_TAKE_PHOTO = 1;

    private void  dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);      
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();


        return image;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

   /* @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);*/

                final AlertDialog.Builder builder = new AlertDialog.Builder(AddPointDetailsActivity.this);
                builder.setTitle("Profile Picture");
                builder.setMessage("Chooose from?");
                builder.setPositiveButton("GALLERY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, SELECT_PICTURE);
                    }
                });
                builder.show();
                builder.setNegativeButton("CANCEL", null );
            }
        }
    }


   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CAMERA)
        {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


           *//* Uri selectedImageUri = data.getData();
            Bitmap selectedImageBitmap = null;
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //selectedImage.setImageBitmap(selectedImageBitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArrayImage = byteArrayOutputStream.toByteArray();
            encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            Log.d("Vicky","I'm in.");
            new UploadImages().execute();*//*

             File destination = new File(getExternalCacheDir(),"temp.jpg");
            FileOutputStream fo;
            try {
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            new uploadFileToServerTask().execute(destination.getAbsolutePath());
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SELECT_PICTURE) {
            // Make sure the request was successful
            Log.d("Vicky","I'm out.");
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                Bitmap selectedImageBitmap = null;
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }if (selectedImageBitmap!=null) {
                    imageView.setImageBitmap(selectedImageBitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                    byte[] byteArrayImage = byteArrayOutputStream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                    new UploadImages().execute();
                }
            }
        }
    }


    private class UploadImages extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Log.d("Vicky", "encodedImage = " + encodedImage);
                jsonObject = new JSONObject();
                jsonObject.put("imageString", encodedImage);
                jsonObject.put("imageName", "+917358513024");
                String data = jsonObject.toString();
                String yourURL = "http://54.169.88.65/events/eventmain/upload_image.php";
                URL url = new URL(yourURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setFixedLengthStreamingMode(data.getBytes().length);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(data);
                Log.d("Vicky", "Data to php = " + data);
                writer.flush();
                writer.close();
                out.close();
                connection.connect();

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        in, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                String result = sb.toString();
                Log.d("Vicky", "Response from php = " + result);
                //Response = new JSONObject(result);
                connection.disconnect();
            } catch (Exception e) {
                Log.d("Vicky", "Error Encountered");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

        }
    }

    /* Show progress dialog. */
    private void showProgressDialog()
    {
        // Set progress dialog display message.
        progressDialog.setMessage("Please Wait");

        // The progress dialog can not be cancelled.
        progressDialog.setCancelable(false);

        // Show it.
        progressDialog.show();
    }

    /* Hide progress dialog. */
    private void hideProgressDialog()
    {
        // Close it.
        progressDialog.hide();
    }
}


