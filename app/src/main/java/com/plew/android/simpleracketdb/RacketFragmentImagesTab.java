package com.plew.android.simpleracketdb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.plew.android.common.tabview.ImageDataArrayAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tim on 4/19/2015.
 */
public class RacketFragmentImagesTab extends Fragment {

    private static final String TAG = "RacketImagesTabFragment";

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int SELECT_IMAGE_ACTIVITY_REQUEST_CODE = 200;

    private final static String FILE_URI_KEY = "fileUri";
    private Uri fileUri;    // Peter: this value needs to persist, see Method 2 at bottom

    Racket mRacket;

    Button mRacketImagesButton;
    ListView mRacketImageListView;

    ImageDataArrayAdapter imagedata_adapter;
    private ArrayList<ImageData> mImageDatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate(): ");
        super.onCreate(savedInstanceState);

        // chapter 10: delete: mRacket = new Racket();
        UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
        // chapter 10: flexible method: UUID racketId = (UUID)getArguments().getSerializable(EXTRA_RACKET_ID);  // chapter 10: flexible method:
        mRacket = RacketList.get(getActivity()).getRacket(racketId);
        mImageDatas = mRacket.getImageDatas();

        // Peter: this works but decided to use Method 2: setRetainInstance(true);   // Method 1: fileURI = NULL issue
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_racketimagestab,container,false);

        mRacketImagesButton = (Button)v.findViewById(R.id.button_racketImages);
        mRacketImagesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mRacketImagesButton");

                // Check if there is a rear facing camera.
                Context context = getActivity();
                PackageManager packageManager = context.getPackageManager();
                if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false){
                    Toast.makeText(getActivity(), "This device does not have a camera.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if temporary media file is available
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                if (fileUri == null) {
                    Toast.makeText(getActivity(), "Error creating media file.  Check external storage on device.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Start camera intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                //Log.d(TAG, "onClick(): mRacketImagesButton: fileUri: " + fileUri);
                //Toast.makeText(getActivity(), "onClick(): mRacketImagesButton: fileUri: " + fileUri,
                //        Toast.LENGTH_LONG).show();

                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        mRacketImageListView = (ListView)v.findViewById(R.id.list_racketImages);
        imagedata_adapter = new ImageDataArrayAdapter(getActivity(), R.layout.list_racketimage_item,
                R.id.racket_image_name, R.id.racket_image, mImageDatas);
        mRacketImageListView.setAdapter(imagedata_adapter);

        mRacketImageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView< ?> parent, View view, int position, long id) {
                ImageData c = (ImageData)(imagedata_adapter.getItem(position));

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(c.getImgUri(), "image/*");
                startActivityForResult(intent, SELECT_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        //Peter: context menu to delete image does not work: registerForContextMenu(mRacketImageListView);
        // Workaround to delete image on long click
        mRacketImageListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView< ?> parent, View view, int position, long id) {
                ImageData c = (ImageData)(imagedata_adapter.getItem(position));
                //Toast.makeText(getActivity(), "onItemLongClick: " + c.toString(),
                //        Toast.LENGTH_SHORT).show();

                // Pop up dialog to confirm delete image
                alertMessageDeleteImage(c);

                return true;
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri photoUri;

                if (data == null) {
                    // A known bug here! The image should have saved in fileUri
                    Log.d(TAG, "onActivityResult(): fileUri: " + fileUri);

                    if (fileUri == null) {   // this should not occur
                        Toast.makeText(getActivity(), "Image capture error!",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    photoUri = fileUri;

                    //Toast.makeText(getActivity(), "BUG: Image saved successfully in: " + photoUri,
                    //        Toast.LENGTH_LONG).show();
                } else {
                    photoUri = data.getData();
                    //Toast.makeText(getActivity(), "Image saved successfully in: " + data.getData(),
                    //        Toast.LENGTH_LONG).show();
                }

                ImageData imagedata = new ImageData();
                imagedata.setImgUri(photoUri);

                // Create thumbnail of original image
                String thumbPath = photoUri.getPath();  // photoUri contains the name of the IMG file
                thumbPath = thumbPath.replace("IMG", "THUMB");  // change IMG to THUMB
                Uri thumbUri = Uri.parse(thumbPath);
                imagedata.setThumbUri(thumbUri);  // Set thumb uri.  Should be no issues even if thumb is not created.

                File imageFile = new File(photoUri.getPath());
                if (imageFile.exists()){
                    // Peter: original: Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    Bitmap bitmap = rotateBitmapOrientation(imageFile.getAbsolutePath());   // rotate bitmap - correct Samsung Galaxy 4
                    Bitmap thumb_bitmap = ThumbnailUtils.extractThumbnail(bitmap, 80, 80, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

                    OutputStream fOutputStream = null;
                    File thumbFile = new File(thumbPath);
                    try {
                        fOutputStream = new FileOutputStream(thumbFile);
                        thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOutputStream);
                        fOutputStream.flush();
                        fOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Save Thumb Failed", Toast.LENGTH_SHORT).show();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Save Thumb Failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Add imagedata to racket and save
                mRacket.addImageData(imagedata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in addImageData

                // Peter: crashes: imagedata_adapter.notifyDataSetChanged();
                imagedata_adapter = new ImageDataArrayAdapter(getActivity(), R.layout.list_racketimage_item,
                        R.id.racket_image_name, R.id.racket_image, mImageDatas);
                mRacketImageListView.setAdapter(imagedata_adapter);

            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // User cancelled the image capture
                //Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Cancelled: " + fileUri,
                        Toast.LENGTH_LONG).show();
            } else {
                // Image capture failed, advise user
                Toast.makeText(getActivity(), "Image capture failed!",
                        Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == SELECT_IMAGE_ACTIVITY_REQUEST_CODE) {
            /* if (resultCode == getActivity().RESULT_OK) {
                Toast.makeText(getActivity(), "SELECT_IMAGE_ACTIVITY_REQUEST_CODE: OK", Toast.LENGTH_SHORT).show();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // User cancelled the image select
                Toast.makeText(getActivity(), "SELECT_IMAGE_ACTIVITY_REQUEST_CODE: Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // Image select failed, advise user
                Toast.makeText(getActivity(), "SELECT_IMAGE_ACTIVITY_REQUEST_CODE: Fail",
                        Toast.LENGTH_LONG).show();
            } */
        }
    }

    //Peter: context menu to delete image does not work
    //Peter: works in standalone code - camera4
    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info;
        try {
            // Casts the incoming data object into the type for AdapterView objects.
            info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            // If the menu object can't be cast, logs an error.
            Log.e(TAG, "bad menuInfo", e);
            return;
        }
        ImageData imagedata = imagedata_adapter.getItem(info.position);

        menu.setHeaderTitle("Action: " + imagedata.toString());

        getActivity().getMenuInflater().inflate(R.menu.image_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        ImageData imagedata = imagedata_adapter.getItem(position);

        Toast.makeText(getActivity(), "onContextItemSelected: " + position,
                Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.menu_item_edit_image:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(imagedata.getUri(), "image/*");
                startActivityForResult(intent, SELECT_IMAGE_ACTIVITY_REQUEST_CODE);

                return true;
            case R.id.menu_item_delete_image:
                // Delete photo
                deletePhoto(imagedata.getUri());

                UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
                RacketList.get(getActivity()).getRacket(racketId).deleteImageData(imagedata);  // mImageDatas.remove(imagedata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in deleteImageData
                imagedata_adapter.notifyDataSetChanged();

                return true;
        }

        return super.onContextItemSelected(item);
    }  */

    private void alertMessageDeleteImage(final ImageData imagedata) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Delete " + imagedata.toString() + "?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                // Yes button clicked

                // Delete photo - IMG and THUMB
                UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
                boolean flag = RacketList.get(getActivity()).getRacket(racketId).deletePhoto(imagedata.getImgUri());
                flag = RacketList.get(getActivity()).getRacket(racketId).deletePhoto(imagedata.getThumbUri());
                //if (flag) {
                //    Toast.makeText(getActivity(), "Deleting Image...:" + imagedata.toString(),
                //            Toast.LENGTH_LONG).show();
                //}
                //else {
                //    Toast.makeText(getActivity(), "Error: Image NOT deleted:" + imagedata.toString(),
                //            Toast.LENGTH_LONG).show();
                //}
                RacketList.get(getActivity()).getRacket(racketId).deleteImageData(imagedata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in deleteImageData
                imagedata_adapter.notifyDataSetChanged();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // No button clicked
                // do nothing
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private Uri getOutputMediaFileUri(int type){
        File mediaFile = getOutputMediaFile(type);

        if (mediaFile == null)
            return null;
        else
            return Uri.fromFile(mediaFile);
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        // Peter: delete: File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
        // Peter: delete:         Environment.DIRECTORY_PICTURES), getActivity().getPackageName());
        File mediaStorageDir = getActivity().getExternalFilesDir("");  // /mnt/sdcard/Android/data/com.plew.android.simpleracketdb/files
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");

        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    // Method 2: fileURI = NULL issue
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fileUri != null) {
            outState.putString(FILE_URI_KEY, fileUri.toString());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(FILE_URI_KEY)) {
                fileUri = Uri.parse(savedInstanceState.getString(FILE_URI_KEY));
            }
        }
    }

    // https://guides.codepath.com/android/Accessing-the-Camera-and-Stored-Media#overview
    private Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);

        ExifInterface exif;
        try {
            // Read EXIF Data
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            return bm;
        }

        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;
    }
}