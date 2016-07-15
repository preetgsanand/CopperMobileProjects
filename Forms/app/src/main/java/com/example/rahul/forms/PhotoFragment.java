package com.example.rahul.forms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoFragment extends Fragment {
    View externalView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_photo, container, false);
        externalView = view;
        Button upload = (Button) view.findViewById(R.id.upload);
        Button submit = (Button) view.findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setSignInForm();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopup(view);
            }
        });
        return view;
    }
    public void setPopup(View view) {
        final View v = view;
        String[] options = {"Take a Picture","Upload From Gallery"};
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(v.getContext());
        builderSingle.setIcon(R.drawable.photo);
        builderSingle.setTitle("Select Option");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(builderSingle.getContext(),
                android.R.layout.select_dialog_singlechoice,
                options);
        builderSingle.setAdapter(
                adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0:Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, 0);
                                break;
                            case 1:Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , 1);
                                        break;
                        }
                    }
                });

        builderSingle.show();
    }
    public static PhotoFragment newInstance() {
        PhotoFragment photoFragment = new PhotoFragment();
        return photoFragment;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        ImageView imageview = (ImageView)externalView.findViewById(R.id.photo);
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                Bitmap bitmap = (Bitmap)imageReturnedIntent.getExtras().get("data");
                imageview.setImageBitmap(bitmap);
                break;
            case 1:
                Uri selectedImage1 = imageReturnedIntent.getData();
                imageview.setImageURI(selectedImage1);
                break;
        }
    }
}

