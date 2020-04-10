
package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements MyHandlerThread.Callback {

    private LinearLayout imageContainer;
    private MyHandlerThread myHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHandlerThread = new MyHandlerThread(new Handler(), this);

        myHandlerThread.start();
        myHandlerThread.prepareHandler();
    }

    @Override
    public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {

    }
}