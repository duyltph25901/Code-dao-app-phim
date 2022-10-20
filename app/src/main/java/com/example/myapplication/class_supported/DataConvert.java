package com.example.myapplication.class_supported;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DataConvert {
    // Convert img to by arr
    public static byte[] convertImageToByArr (Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    // Convert By arr to img
    public static Bitmap convertByArrToImage(byte[] arr) {
        return BitmapFactory.decodeByteArray(arr, 0, arr.length);
    }

    // Reduce image size
    public static byte[] imagemTratada(byte[] imagem_img){

        while (imagem_img.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }

        return imagem_img;
    }


}
