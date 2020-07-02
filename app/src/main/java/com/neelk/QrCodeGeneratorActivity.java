package com.neelk.robotics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCodeGeneratorActivity extends AppCompatActivity {

    private ImageView qrCodeImageView;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);

        qrCodeImageView = findViewById(R.id.qrCodeImageView);
        finishButton = findViewById(R.id.finishButton);

        Intent scoutData = getIntent();
        String data = scoutData.getStringExtra("data");
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        Bitmap qrCodeBitmap = getQrCodeBitmap(data, 400, 400);
        qrCodeImageView.setImageBitmap(qrCodeBitmap);
        finishButton.setOnClickListener(finishOnClick);


    }

    private Bitmap getQrCodeBitmap(String data, int imageWidth, int imageHeight) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        Bitmap bmp = null;

        try {
            bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, imageWidth, imageHeight);
            int height = bitMatrix.getHeight();
            int width = bitMatrix.getWidth();
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    private View.OnClickListener finishOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(QrCodeGeneratorActivity.this, NavigationBarManager.class);
            startActivity(intent);
        }
    };
}
