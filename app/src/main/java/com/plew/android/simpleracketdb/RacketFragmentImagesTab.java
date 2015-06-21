package com.plew.android.simpleracketdb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    private Uri fileUri;
    private Uri photoUri;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_racketimagestab,container,false);

        mRacketImagesButton = (Button)v.findViewById(R.id.button_racketImages);
        mRacketImagesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d(TAG, "onClick(): mRacketImagesButton");

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

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
                intent.setDataAndType(c.getUri(), "image/*");
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

                if (data == null) {
                    // A known bug here! The image should have saved in fileUri
                    photoUri = fileUri;

                    //Toast.makeText(getActivity(), "BUG: Image saved successfully in: " + photoUri,
                    //        Toast.LENGTH_LONG).show();
                } else {
                    photoUri = data.getData();
                    //Toast.makeText(getActivity(), "Image saved successfully in: " + data.getData(),
                    //        Toast.LENGTH_LONG).show();
                }

                ImageData imagedata = new ImageData();
                imagedata.setUri(photoUri);

                mRacket.addImageData(imagedata);
                RacketList.get(getActivity()).saveRackets();   // kluge: could not be done in addImageData
                // Peter: crashes: imagedata_adapter.notifyDataSetChanged();
                imagedata_adapter = new ImageDataArrayAdapter(getActivity(), R.layout.list_racketimage_item,
                        R.id.racket_image_name, R.id.racket_image, mImageDatas);
                mRacketImageListView.setAdapter(imagedata_adapter);

            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // User cancelled the image capture
                //Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
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

                // Delete photo
                UUID racketId = (UUID)getActivity().getIntent().getSerializableExtra(RacketFragment.EXTRA_RACKET_ID);  // chapter 10: direct method:
                boolean flag = RacketList.get(getActivity()).getRacket(racketId).deletePhoto(imagedata.getUri());
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
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        // Peter: delete: File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
        // Peter: delete:         Environment.DIRECTORY_PICTURES), getActivity().getPackageName());
        File mediaStorageDir = getActivity().getExternalFilesDir("");
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
}