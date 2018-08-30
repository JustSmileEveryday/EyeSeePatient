package lht.wangtong.gowin120.patient.tencent.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.IOException;

import lht.wangtong.gowin120.patient.R;
import lht.wangtong.gowin120.patient.config.Api;
import lht.wangtong.gowin120.patient.tencent.utils.FileUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author luoyc
 */
public class ImageViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_view);
        String file = getIntent().getStringExtra("filename");
        String imageUrl = getIntent().getStringExtra("reportImg");
        PhotoView imageView = findViewById(R.id.photo_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //        Bitmap bitmap = getImage(FileUtil.getCacheFilePath(file));
        if (file != null) {
//            imageView.setImageBitmap(bitmap);
            Glide.with(this)
                    .load(new File(FileUtil.getCacheFilePath(file)))
                    .transition(withCrossFade())
                    .into(imageView);
        }
        if (imageUrl != null && !TextUtils.isEmpty(imageUrl)) {
            Glide.with(this)
                    .load(Api.HOST_IMG + imageUrl)
                    .apply(new RequestOptions().error(R.drawable.report_detail_default_img)
                            .placeholder(R.drawable.report_detail_default_img))
                    .transition(withCrossFade())
                    .into(imageView);
        }
    }

    private Bitmap getImage(String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int reqWidth, reqHeight, width = options.outWidth, height = options.outHeight;
        if (width > height) {
            reqWidth = getWindowManager().getDefaultDisplay().getWidth() / 2;
            reqHeight = (reqWidth * height) / width;
        } else {
            reqHeight = getWindowManager().getDefaultDisplay().getHeight() / 2;
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
        try {
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            Matrix mat = new Matrix();
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            if (bitmap == null) {
                Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                return null;
            }
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
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
        } catch (IOException e) {
            return null;
        }
    }
}
