package lht.wangtong.gowin120.patient.tencent.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.view.OrdinaryTitleBar;

/**
 * 图片展示预览
 *
 * @author luoyc
 */
public class ImagePreviewActivity extends Activity {

    private String path;
    private CheckBox isOri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        path = getIntent().getStringExtra("path");
        isOri = findViewById(R.id.isOri);
        OrdinaryTitleBar title = findViewById(R.id.imagePreviewTitle);
        TextView sendBtn = findViewById(R.id.right_text);
        sendBtn.setText("发送");
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("path", path);
                intent.putExtra("isOri", isOri.isChecked());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        showImage();

    }

    private void showImage() {
        if (path.equals("")) {
            return;
        }
        File file = new File(path);
        if (file.exists()) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            if (file.length() == 0 && options.outWidth == 0) {
                finish();
                return;
            }
            long fileLength = file.length();
            if (fileLength == 0) {
                fileLength = options.outWidth * options.outHeight / 3;
            }
            int reqWidth, reqHeight, width = options.outWidth, height = options.outHeight;
            if (width > height) {
                reqWidth = getWindowManager().getDefaultDisplay().getWidth();
                reqHeight = (reqWidth * height) / width;
            } else {
                reqHeight = getWindowManager().getDefaultDisplay().getHeight();
                reqWidth = (width * reqHeight) / height;
            }
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {
                final int halfHeight = height / 2;
                final int halfWidth = width / 2;
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }
            isOri.setText(getString(R.string.chat_image_preview_ori) + "(" + getFileSize(fileLength) + ")");
            try {
                options.inSampleSize = inSampleSize;
                options.inJustDecodeBounds = false;
                float scaleX = (float) reqWidth / (float) (width / inSampleSize);
                float scaleY = (float) reqHeight / (float) (height / inSampleSize);
                Matrix mat = new Matrix();
                mat.postScale(scaleX, scaleY);
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                ExifInterface ei = new ExifInterface(path);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        mat.postRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        mat.postRotate(180);
                        break;
                    default:
                        break;
                }
                ImageView imageView = findViewById(R.id.image);
                imageView.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true));
            } catch (IOException e) {
                Toast.makeText(this, getString(R.string.chat_image_preview_load_err), Toast.LENGTH_SHORT).show();
            }
        } else {
            finish();
        }
    }

    private String getFileSize(long size) {
        StringBuilder strSize = new StringBuilder();
        if (size < 1024) {
            strSize.append(size).append("B");
        } else if (size < 1024 * 1024) {
            strSize.append(size / 1024).append("K");
        } else {
            strSize.append(size / 1024 / 1024).append("M");
        }
        return strSize.toString();
    }


}
