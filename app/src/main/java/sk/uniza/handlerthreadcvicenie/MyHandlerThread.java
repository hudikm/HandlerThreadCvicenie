package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHandlerThread extends HandlerThread {

    // Referencia na vytvorenú štanciu MyHandlerThread
    private static MyHandlerThread instance = null;

    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();

    // Definovanie pracovného handlera, ktorý z prideleného loopera príjima
    // pridelenú prácu
    private Handler mWorkerHandler;

    // Referencia na Handler, ktorý je spätý s UI vláknom
    private Handler mResponseHandler;
    // Referencia na Callback rozhranie
    private Callback mCallback;
    // Objekt určený na zabezpečenie bezpečného prístupu pri využití vláken
    private Object syncObj = new Object();

    // Rozhranie pomocou ktorého sa odovzdá stiahnutý obrázok
    public interface Callback {
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap);
    }

    // Cache na uloženie sťahovaných obrázkov
    final int cacheSize = 4 * 1024 * 1024; // 4MiB
    LruCache<String, Bitmap> bitmapCache =
            new LruCache<String, Bitmap>(cacheSize) {
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };

    public MyHandlerThread() {
        super(TAG);
        setPriority(Process.THREAD_PRIORITY_BACKGROUND); //Nastavenie
        // priority pracovnému vláknu
    }

    public static MyHandlerThread getInstance(@NonNull Handler responseHandler,
                                              @NonNull Callback callback) {

        if (instance == null || !instance.isAlive()) {
            instance = new MyHandlerThread();
        }
        synchronized (instance.syncObj) {
            instance.mResponseHandler = responseHandler;
            instance.mCallback = callback;
        }
        return instance;
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
                // Stiahnutie obrázkov z internetu
                if (msg.obj != null && msg.obj instanceof ImageUrl) {
                    handleRequest((ImageUrl) msg.obj);
                }
            }
        };
    }

    /**
     * Pomocná metóda na posunutie správy pracovnému HandlerThread vláknu
     *
     * @param imageUrl - obrázok, ktorý sa má stiahnuť
     */
    public void queueTask(ImageUrl imageUrl) {
        mWorkerHandler.obtainMessage(imageUrl.WHAT, imageUrl)
                .sendToTarget();
    }

    /**
     * Pomocná metóda, ktorá má byť spustená keď dôjde k reštartu aktivity
     */
    public void onDestroy() {
        // Odstránenie všetkých ešte nestiahnutých url adries so zásobníka
        mWorkerHandler.removeMessages(ImageUrl.WHAT);
        // Bezpečné vynulovanie referencie na Handler, ktorý pri reštarte
        // aktivity bude po dobu reštartu neplatná
        synchronized (syncObj) {
            mResponseHandler = null;
        }
    }

    /*
        Metóda na stiahnutie obrázku z internetu, Táto metóda je je spustená
        na pracovnom vlákne!
     */
    private void handleRequest(final ImageUrl imageUrl) {

        try {
            final Bitmap bitmap;

            // Načítanie obrázku z cache(klúč je url adresa obrázku), ak nebol
            // nájdený metóda vráti null hodnotu
            Bitmap bitmapTmp = bitmapCache.get(imageUrl.urlOfImage);
            if (bitmapTmp == null) {
                HttpURLConnection connection =
                        (HttpURLConnection) new URL(imageUrl.urlOfImage)
                                .openConnection();
                connection.setRequestMethod("GET");
                bitmap = BitmapFactory
                        .decodeStream((InputStream) connection.getContent());
                bitmapCache.put(imageUrl.urlOfImage, bitmap);
                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                bitmap = bitmapTmp;
            }
            /*
                Stiahnutý obrázok sa odovzdá s pomocu Handler triedy UI
                vláknu, ktoré zavolá callback metódu
             */
            // Bezpečný prístup z pracovného vlákna
            synchronized (syncObj) {
                // Overenie, že referencia na Handler je platná
                if (mResponseHandler != null && bitmap != null) {
                    mResponseHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Spustené na UI vlákne
                            mCallback.onImageDownloaded(
                                    imageUrl.uiToShowImage.get(),
                                    bitmap);
                        }
                    });
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
