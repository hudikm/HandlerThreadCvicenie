package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.ImageView;
import android.os.Process;

import androidx.annotation.NonNull;

public class MyHandlerThread extends HandlerThread {

    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();

    // Definovanie pracovného handlera, ktorý z prideleného loopera príjima
    // pridelenú prácu
    private Handler mWorkerHandler;

    // Rozhranie pomocou ktorého sa odovzdá stiahnutý obrázok
    public interface Callback {
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap);
    }

    public MyHandlerThread() {
        super(TAG);
        setPriority(Process.THREAD_PRIORITY_BACKGROUND); //Nastavenie
        // priority pracovnému vláknu

    }

    /**
     * Pomocná metóda na inicializáciu HandlerThread ako pracovného vlákna
     */
    public void prepareHandler() {
        mWorkerHandler = new Handler(getLooper()) {
            /*
            V handleMessage callback metóde sa spracovávajú prijaté spravy na
             pracovnom vlákne HandlerThread
             */
            @Override
            public void handleMessage(@NonNull Message msg) {
                // Spracovanie prijatej správy
            }
        };
    }


}
