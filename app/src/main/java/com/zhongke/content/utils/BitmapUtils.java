package com.zhongke.content.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import com.zhongke.content.HPApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by Administrator on 2016/7/25.
 * 用途：
 * 1.压缩图片
 * 2.适屏图片
 * 3.图片转为byte
 */
public class BitmapUtils {
    /**
     * 压缩bitmap,写入file中
     *
     * @param bitmap
     */
    public static void compressBitmap(Bitmap bitmap, File file, int bitmapSize, boolean isRecycle) {
        int quality = 100;//范围0~100
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            do {
                if (quality <= 10) {
                       break;
                }
                byteArrayOutputStream.reset();
                bitmap.compress(Bitmap.CompressFormat.PNG, quality, byteArrayOutputStream);
                quality -= 10;
            } while (byteArrayOutputStream.toByteArray().length / 1024 > bitmapSize);
            byte[] b = byteArrayOutputStream.toByteArray();

            byteWriteFile(b, file);
            if (isRecycle) {
                bitmap.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    public static byte[] bitmapCompressToByteArray(Bitmap bitmap, int bitmapSize, boolean isRecycle) {

        int quality = 100;//范围0~100
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,quality,byteArrayOutputStream);
            while (byteArrayOutputStream.toByteArray().length/1024>bitmapSize){
                   if(quality<=10){
                        break;
                   }
                   byteArrayOutputStream.reset();
                   quality-=10;
                   bitmap.compress(Bitmap.CompressFormat.PNG,quality,byteArrayOutputStream);
            }

            byte[] b = byteArrayOutputStream.toByteArray();
            if (isRecycle) {
                bitmap.recycle();
            }
            return  b;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }



    /**
     * 将byte写入磁盘file中
     *
     * @param b
     * @param file
     */
    public static void byteWriteFile(byte[] b, File file) {
        FileOutputStream fileOutputStream = null;
        try {
            //将流中数据写入file中
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(b);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 根据控件大小从file中生成适应屏幕的bitmap
     *
     * @param file
     * @param targetWith
     * @param targerHeight
     * @return
     */
    public static Bitmap decodeFileCreateBitmap(File file, int targetWith, int targerHeight) {

        return decodeFileCreateBitmap(file.getAbsolutePath(), targetWith, targerHeight);
    }

    public synchronized static Bitmap decodeFileCreateBitmap(String path, int targetWith, int targetHeight) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
    /*         ContentResolver contentResolver=BaseApplication.getAppContext().getContentResolver();
          *//*  LogController.i(BitmapUtils.class.getSimpleName(),"path"+path);
            BitmapFactory.decodeFile(path, options);*//*
            //MIME type需要添加前缀
            InputStream inputStream=contentResolver.openInputStream( Uri.parse(path.contains("file:")?path:"file://"+path));
            BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();*/
            decodeStreamToBitmap(path,options);

            options.inSampleSize = calculateScaleSize(options, targetWith, targetHeight);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = decodeStreamToBitmap(path,options);

            return getNormalBitmap(bitmap, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Bitmap decodeStreamToBitmap(String path, BitmapFactory.Options options){
        Bitmap bitmap=null;
        ContentResolver contentResolver= HPApplication.getInstance().getContentResolver();
        try{
            //MIME type需要添加前缀
            InputStream inputStream=contentResolver.openInputStream( Uri.parse(path.contains("file:")?path:"file://"+path));
           bitmap= BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
       return bitmap;
    }
    public static Bitmap decodeResourceCreateBitmap(int  path, int targetWith, int targetHeight){
        try{

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = calculateScaleSize(options, targetWith, targetHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource( HPApplication.getInstance().getResources(),path,options);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    /**
     * 根据存储的bitmap中旋转角度，来创建正常的bitmap
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static Bitmap getNormalBitmap(Bitmap bitmap, String path) {
        int rotate = getBitmapRotate(path);
        Bitmap normalBitmap=null;
        switch (rotate) {
            case 90:
            case 180:
            case 270:
                try {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotate);
                    normalBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    normalBitmap=bitmap;
                }
                break;
            default:
                normalBitmap=bitmap;
                break;
        }
        return normalBitmap;
    }

    /**
     * 将bitmap编码成byte
     *
     * @param bitmap
     * @param isRecycle
     * @return
     */
    public static byte[] bitmapEncodeByteArray(final Bitmap bitmap, boolean isRecycle) {
        byte[] bitmapByte = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] b = byteArrayOutputStream.toByteArray();
            if (isRecycle) {
                bitmap.recycle();
            }
            bitmapByte = b;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (Exception e) {

            }
        }
        return bitmapByte;
    }

    public static byte[] bitmapEncodeString(final Bitmap bitmap) {
        return bitmapEncodeByteArray(bitmap,false);
    }

    /**
     * 将byte编码
     *
     * @param b
     * @return
     */
    public static byte[] encodeToString(byte[] b) {
        return Base64.encode(b, Base64.DEFAULT);
    }

    /**
     * 采用向上取整的方式，计算压缩尺寸
     *
     * @param options
     * @param targetWith
     * @param targetHeight
     * @return
     */
    public static int calculateScaleSize(BitmapFactory.Options options, int targetWith, int targetHeight) {
        int simpleSize;
        if (targetWith > 0 && targetHeight>0) {
            int scaleWith = (int) Math.ceil((options.outWidth * 1.0f) / targetWith);
            int scaleHeight = (int) Math.ceil((options.outHeight * 1.0f) / targetHeight);
            simpleSize = Math.max(scaleWith, scaleHeight);
        } else {
            simpleSize = 1;
        }
        if(simpleSize==0){
            simpleSize=1;
        }
        return simpleSize;
    }

    /**
     * 获得存储bitmap的文件
     * getExternalFilesDir()提供的是私有的目录，在app卸载后会被删除
     *
     * @param context
     * @param
     * @return
     */
    public static File getBitmapDiskFile(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            cachePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        } else {
            cachePath = context.getFilesDir().getPath();
        }
        return new File(cachePath + File.separator + getBitmapFileName());
    }

    public static final String bitmapFormat = ".png";

    /**
     * 生成bitmap的文件名:日期，md5加密
     *
     * @return
     */
    public static String getBitmapFileName() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            String currentDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            mDigest.update(currentDate.getBytes("utf-8"));
            byte[] b = mDigest.digest();
            for (int i = 0; i < b.length; ++i) {
                String hex = Integer.toHexString(0xFF & b[i]);
                if (hex.length() == 1) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(hex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileName = stringBuilder.toString() + bitmapFormat;
        return fileName;
    }

    /**
     * ExifInterface ：这个类为jpeg文件记录一些image 的标记
     * 这里，获取图片的旋转角度
     *
     * @param path
     * @return
     */
    public static int getBitmapRotate(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**获取视频的第一帧
     * @param filePath
     * @param kind     kind could be MINI_KIND=1 or MICRO_KIND=3
     * @return
     */
    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null) {return null;}

        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    96,
                    96,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * 获得存储bitmap的文件
     * getExternalFilesDir()提供的是私有的目录，在app卸载后会被删除
     *
     * @param context
     * @param
     * @return
     */
    public static File getDiskFile(Context context,String fileName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)!=null ? context.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath():context.getFilesDir().getPath();
        } else {
            cachePath = context.getFilesDir().getPath();
        }
        return new File(cachePath + File.separator +fileName);
    }
}
