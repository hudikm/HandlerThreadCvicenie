# Cvičenie
<!--tgen file='/home/martin/AndroidStudioProjects/HandlerThreadCvicenie/out.patch' lang=java prefix="Krok č. " tabs t_new="Nové" t_old="Pred úpravou" -->

<!--tgen step=1.0 template="files_list.jinja"  -->
####Krok č. 1.0 Úprava layout-u aplikácie [:link:](/commit/af6bb53a561bb7a67eae5ed20c12c8eefad867f5/)
```
 .
 └─ app
    └─ src
       └─ main
          ├─ res
          │  └─ layout
          │     └─ activity_main.xml
          ├─ java
          │  └─ sk
          │     └─ uniza
          │        └─ handlerthreadcvicenie
          │           └─ MainActivity.java
          └─ AndroidManifest.xml
```


<!--end-->

<!--tgen step=1.0-3.0  -->
####Krok č. 1.0 Úprava layout-u aplikácie [:link:](/commit/af6bb53a561bb7a67eae5ed20c12c8eefad867f5/)
>  **[🖹](/blob/af6bb53a561bb7a67eae5ed20c12c8eefad867f5/app/src/main/res/layout/activity_main.xml) app/src/main/res/layout/activity_main.xml**
             

``` java tab="Nové" hl_lines="2 6 9 12 14 15 16 17 18 19 20 21 22"
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <ScrollView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal">

        <LinearLayout
            android:id="@+id/imageContainer"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical">

        </LinearLayout>
    </ScrollView>
</LinearLayout>

```
         
``` java tab="Pred úpravou" hl_lines="2 3 9 12 13 14 15 16 18"
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>

```
####Krok č. 1.1 Vytvorenie MyHandlerThread triedy [:link:](/commit/5b60840ed884ad84f626b5ee98352693cabac7e6/)
>  **[🖹](/blob/5b60840ed884ad84f626b5ee98352693cabac7e6/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
                 

``` java tab="Nové" hl_lines="1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17"
package sk.uniza.handlerthreadcvicenie;

import android.os.HandlerThread;
import android.os.Process;

public class MyHandlerThread extends HandlerThread {

    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();

    public MyHandlerThread() {
        super(TAG);
        setPriority(Process.THREAD_PRIORITY_BACKGROUND); //Nastavenie
        // priority pracovnému vláknu

    }
}

```

####Krok č. 1.2 Vytvorenie rozhrania, ktoré sa použije na odovzdanie stiahnutého obrázku [:link:](/commit/e1f221b75852b5cba4f9c547aac72675b5c5b5b7/)
>  **[🖹](/blob/e1f221b75852b5cba4f9c547aac72675b5c5b5b7/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
         

``` java tab="Nové" hl_lines="3 5 9 10 17 18 19 20 21"
package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements MyHandlerThread.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {

    }
}

```
 
``` java tab="Pred úpravou" hl_lines="7"
package sk.uniza.handlerthreadcvicenie;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

```

>  **[🖹](/blob/e1f221b75852b5cba4f9c547aac72675b5c5b5b7/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
  

``` java tab="Nové" hl_lines="3 5"
package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;
import android.os.HandlerThread;
import android.widget.ImageView;
import android.os.Process;

public class MyHandlerThread extends HandlerThread {

```

``` java tab="Pred úpravou" 
package sk.uniza.handlerthreadcvicenie;

import android.os.HandlerThread;
import android.os.Process;

public class MyHandlerThread extends HandlerThread {

```
     
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7 8"
    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();

    // Rozhranie pomocou ktorého sa odovzdá stiahnutý obrázok
    public interface Callback {
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap);
    }

    public MyHandlerThread() {
        super(TAG);
        setPriority(Process.THREAD_PRIORITY_BACKGROUND); //Nastavenie

```

``` java tab="Pred úpravou" 
    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();

    public MyHandlerThread() {
        super(TAG);
        setPriority(Process.THREAD_PRIORITY_BACKGROUND); //Nastavenie

```
####Krok č. 1.3 Vytvorenie pomocnej metódy prepareHandler [:link:](/commit/e2dce6e195ffcba734aff8a75c4123ea4065b52b/)
>  **[🖹](/blob/e2dce6e195ffcba734aff8a75c4123ea4065b52b/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
        

``` java tab="Nové" hl_lines="4 6 10 11 17 18 19 20"
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

```

``` java tab="Pred úpravou" 
package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;
import android.os.HandlerThread;
import android.widget.ImageView;
import android.os.Process;

public class MyHandlerThread extends HandlerThread {

    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();

    // Rozhranie pomocou ktorého sa odovzdá stiahnutý obrázok
    public interface Callback {
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap);

```
                 
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20"
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

```

``` java tab="Pred úpravou" 
        // priority pracovnému vláknu

    }
}

```
####Krok č. 1.4 Vytvorenie pomocnej triedy ImageUrl [:link:](/commit/50fe6348f677a68489bd5d9cf88f9cb6be8728d2/)
>  **[🖹](/blob/50fe6348f677a68489bd5d9cf88f9cb6be8728d2/app/src/main/java/sk/uniza/handlerthreadcvicenie/ImageUrl.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/ImageUrl.java**
                        

``` java tab="Nové" hl_lines="1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24"
package sk.uniza.handlerthreadcvicenie;

import android.widget.ImageView;

import java.lang.ref.WeakReference;

/*
  Dátova trieda, ktorá reprezentuje jeden obrázok, ktorý sa bude sťahovať
 */
public class ImageUrl {
    public static final int WHAT = 101; // Identifikátor správy
    public final String urlOfImage; //url adresa obrázku
    /*
     Referancia na  ImageView v ktorom sa zobrazí štiahnutý obrázok. Táto
     referencia je vo forme WeakReference, ktorá má zabezpečiť aby
     nedochádzalo k memory leaku v pripade restartovania aplikácie
     */
    public final WeakReference<ImageView> uiToShowImage;

    public ImageUrl(String urlOfImage, ImageView uiToShowImage) {
        this.urlOfImage = urlOfImage;
        this.uiToShowImage = new WeakReference<>(uiToShowImage);
    }
}

```


>  **[🖹](/blob/50fe6348f677a68489bd5d9cf88f9cb6be8728d2/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
 
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4"
        };
    }


}

```

``` java tab="Pred úpravou" 
        };
    }

}

```
####Krok č. 1.5 Vytvorenie metódy handleRequest určenej na stiahnutie obrázku z internetu na pracovnom vlákne [:link:](/commit/f49851732dcc449a8696ab73cf7dcee7c5f3b305/)
>  **[🖹](/blob/f49851732dcc449a8696ab73cf7dcee7c5f3b305/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
       

``` java tab="Nové" hl_lines="4 8 14 15 16 17 18"
package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Trace;
import android.widget.ImageView;
import android.os.Process;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHandlerThread extends HandlerThread {

    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie

```

``` java tab="Pred úpravou" 
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

```
                                            
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51"
            @Override
            public void handleMessage(@NonNull Message msg) {
                // Spracovanie prijatej správy

            }
        };
    }

    /*
        Metóda na stiahnutie obrázku z internetu, Táto metóda je je spustená
        na pracovnom vlákne!
     */
    private void handleRequest(final ImageUrl imageUrl) {

        try {
            final Bitmap bitmap;

            Trace.beginSection("HTTP download");
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(imageUrl.urlOfImage)
                            .openConnection();
            connection.setRequestMethod("GET");
            bitmap = BitmapFactory
                    .decodeStream((InputStream) connection.getContent());

            try {
                this.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*
                Stiahnutý obrázok sa odovzdá s pomocu Handler triedy UI
                vláknu, ktoré zavolá callback metódu
             */
            if (bitmap != null) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

```

``` java tab="Pred úpravou" 
            @Override
            public void handleMessage(@NonNull Message msg) {
                // Spracovanie prijatej správy
            }
        };
    }


}

```
####Krok č. 1.6 Úprava konštruktora MyHandlerThread [:link:](/commit/fd7ae37e023502c138e2dd10fac2802a11e67c87/)
>  **[🖹](/blob/fd7ae37e023502c138e2dd10fac2802a11e67c87/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
 
 > import android.graphics.BitmapFactory;

``` java tab="Nové" hl_lines="4"
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.os.Trace;
import android.widget.ImageView;

import androidx.annotation.NonNull;


```
 
``` java tab="Pred úpravou" hl_lines="6"
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Trace;
import android.widget.ImageView;
import android.os.Process;

import androidx.annotation.NonNull;


```
         
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7 8 14 15 19 20"
    // pridelenú prácu
    private Handler mWorkerHandler;

    // Referencia na Handler, ktorý je spätý s UI vláknom
    private Handler mResponseHandler;
    // Referencia na Callback rozhranie
    private Callback mCallback;

    // Rozhranie pomocou ktorého sa odovzdá stiahnutý obrázok
    public interface Callback {
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap);
    }

    public MyHandlerThread(Handler responseHandler,
                           Callback callback) {
        super(TAG);
        setPriority(Process.THREAD_PRIORITY_BACKGROUND); //Nastavenie
        // priority pracovnému vláknu
        mResponseHandler = responseHandler;
        mCallback = callback;
    }

    /**

```
  
``` java tab="Pred úpravou" hl_lines="9 13"
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

```
####Krok č. 1.7 Spracovanie prijatej správy v handleMessage metóde [:link:](/commit/d73bdc2bcf888894977519095e7470aac55416fa/)
>  **[🖹](/blob/d73bdc2bcf888894977519095e7470aac55416fa/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
    
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7"
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

```
 
``` java tab="Pred úpravou" hl_lines="4"
            @Override
            public void handleMessage(@NonNull Message msg) {
                // Spracovanie prijatej správy

            }
        };
    }

```
####Krok č. 1.8 Vytvorenie a inicializovanie novej inštancie MyHandlerThread [:link:](/commit/c1d31f53283ebc7bc2221f67fb9f0a7ea26928f1/)
>  **[🖹](/blob/c1d31f53283ebc7bc2221f67fb9f0a7ea26928f1/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
         
 > package sk.uniza.handlerthreadcvicenie;

``` java tab="Nové" hl_lines="3 5 12 13 14 19 20 21 22"
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

```

``` java tab="Pred úpravou" 
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements MyHandlerThread.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override

```
####Krok č. 1.9 Vytvorenie a odoslanie zoznamu obrázkov na stiahnutie [:link:](/commit/b5c9a3968fc2e4d7bdc27f6db24807dad43186dd/)
>  **[🖹](/blob/b5c9a3968fc2e4d7bdc27f6db24807dad43186dd/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
    
 > import androidx.appcompat.app.AppCompatActivity;

``` java tab="Nové" hl_lines="4 5 6 7"
public class MainActivity extends AppCompatActivity
        implements MyHandlerThread.Callback {

    // Url online služby, ktorá generuje obrázky s textom
    private String imageUrlLink = "https://dummyimage.com/300/09f/fff" +
            ".png&text=";

    private LinearLayout imageContainer;
    private MyHandlerThread myHandlerThread;


```

``` java tab="Pred úpravou" 
public class MainActivity extends AppCompatActivity
        implements MyHandlerThread.Callback {

    private LinearLayout imageContainer;
    private MyHandlerThread myHandlerThread;


```
                  
 > public class MainActivity extends AppCompatActivity

``` java tab="Nové" hl_lines="4 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27"
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageContainer = findViewById(R.id.imageContainer);
        myHandlerThread = new MyHandlerThread(new Handler(), this);

        myHandlerThread.start();
        myHandlerThread.prepareHandler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (Integer i = 0; i < 10; i++) {
            ImageView imageView = new ImageView(this);
            // Informačný obrázok, že prebieha štahovanie
            imageView.setImageResource(R.drawable.loading);
            imageContainer.addView(imageView);

            // Zavolanie pomocnej metódy na uloženie správy do zásobíka.
            // Správy zo zásobníka sú spracované na pracovnom vlákne
            myHandlerThread
                    .queueTask(new ImageUrl(imageUrlLink.concat("img+" + i),
                            imageView));
        }
    }

    @Override
    public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {


```

``` java tab="Pred úpravou" 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHandlerThread = new MyHandlerThread(new Handler(), this);

        myHandlerThread.start();
        myHandlerThread.prepareHandler();
    }

    @Override
    public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {


```

>  **[🖹](/blob/b5c9a3968fc2e4d7bdc27f6db24807dad43186dd/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
          
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13"
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

    /*
        Metóda na stiahnutie obrázku z internetu, Táto metóda je je spustená
        na pracovnom vlákne!

```

``` java tab="Pred úpravou" 
        };
    }

    /*
        Metóda na stiahnutie obrázku z internetu, Táto metóda je je spustená
        na pracovnom vlákne!

```
####Krok č. 1.10 Zobrazenie stiahnutého obrázku [:link:](/commit/8e3254b6d2b12324d823f840129113c5e7eae83f/)
>  **[🖹](/blob/8e3254b6d2b12324d823f840129113c5e7eae83f/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
     
 > public class MainActivity extends AppCompatActivity

``` java tab="Nové" hl_lines="3 4 5 6 7"
    @Override
    public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {
        // Otestovanie či ImageView bol vytvorený v rovnakom kontexte. V
        // prípade reštatovania aplikácie, može ImageView pochádzať z už
        // zaniknutej inštancie Aplikácie.
        if (imageView.getContext() == this)
            imageView.setImageBitmap(bitmap);
    }
}

```
 
``` java tab="Pred úpravou" hl_lines="3"
    @Override
    public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {

    }
}

```
####Krok č. 1.11 Pridanie povolenia pre prístup na internet [:link:](/commit/b9d330af0d1088636cce3e2c3405dc84a2d98515/)
>  **[🖹](/blob/b9d330af0d1088636cce3e2c3405dc84a2d98515/app/src/main/AndroidManifest.xml) app/src/main/AndroidManifest.xml**
 

``` java tab="Nové" hl_lines="4"
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="sk.uniza.handlerthreadcvicenie">

    <uses-permission android:name="android.permission.INTERNET" />
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"

```

``` java tab="Pred úpravou" 
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="sk.uniza.handlerthreadcvicenie">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"

```
####Krok č. 1.12 Reštart aplikácie vplyvom konfiguračnej zmeny. Minimálne riešenie [:link:](/commit/6dfcd7ef344b62d1b4bef63821fd86bc721d6f02/)
>  **[🖹](/blob/6dfcd7ef344b62d1b4bef63821fd86bc721d6f02/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
      
 > public class MainActivity extends AppCompatActivity

``` java tab="Nové" hl_lines="4 5 6 7 8 9"
        if (imageView.getContext() == this)
            imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandlerThread.quit();
    }
}

```

``` java tab="Pred úpravou" 
        if (imageView.getContext() == this)
            imageView.setImageBitmap(bitmap);
    }
}

```
####Krok č. 2.0 Využitie Singleton návrhového vzoru [:link:](/commit/ce66f77a589451c6e7ce32680c701b730ee7a372/)
>  **[🖹](/blob/ce66f77a589451c6e7ce32680c701b730ee7a372/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
       
 > public class MainActivity extends AppCompatActivity

``` java tab="Nové" hl_lines="4 6 7 8 9 10 11"
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageContainer = findViewById(R.id.imageContainer);
        myHandlerThread = MyHandlerThread.getInstance(new Handler(), this);

        if (!myHandlerThread.isAlive()) {
            // Iba pri prvom spustení musí prebehnúť inicializácia
            // HandlerThread triedy
            myHandlerThread.start();
            myHandlerThread.prepareHandler();
        }
    }

    @Override

```
   
``` java tab="Pred úpravou" hl_lines="4 6 7"
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageContainer = findViewById(R.id.imageContainer);
        myHandlerThread = new MyHandlerThread(new Handler(), this);

        myHandlerThread.start();
        myHandlerThread.prepareHandler();
    }

    @Override

```
  
 > public class MainActivity extends AppCompatActivity

``` java tab="Nové" hl_lines="4 5"
    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}

```
 
``` java tab="Pred úpravou" hl_lines="4"
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandlerThread.quit();
    }
}

```

>  **[🖹](/blob/ce66f77a589451c6e7ce32680c701b730ee7a372/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
   
 > import java.net.URL;

``` java tab="Nové" hl_lines="3 4 5"
public class MyHandlerThread extends HandlerThread {

    // Referencia na vytvorenú štanciu MyHandlerThread
    private static MyHandlerThread instance = null;

    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();


```

``` java tab="Pred úpravou" 
public class MyHandlerThread extends HandlerThread {

    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();


```
              
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 8 9 10 11 12 13 14 15 16 17 18 19 20"
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap);
    }

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

        instance.mResponseHandler = responseHandler;
        instance.mCallback = callback;

        return instance;
    }

    /**

```
    
``` java tab="Pred úpravou" hl_lines="4 5 9 10"
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap);
    }

    public MyHandlerThread(Handler responseHandler,
                           Callback callback) {
        super(TAG);
        setPriority(Process.THREAD_PRIORITY_BACKGROUND); //Nastavenie
        // priority pracovnému vláknu
        mResponseHandler = responseHandler;
        mCallback = callback;
    }

    /**

```
####Krok č. 2.1 Optimalizácia s pohladu reštartovania aktivity [:link:](/commit/21516f8a7dedc8d805214d5a2262832315c8e881/)
>  **[🖹](/blob/21516f8a7dedc8d805214d5a2262832315c8e881/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
 
 > public class MainActivity extends AppCompatActivity

``` java tab="Nové" hl_lines="4"
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandlerThread.onDestroy();

    }
}

```
 
``` java tab="Pred úpravou" hl_lines="4"
    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}

```

>  **[🖹](/blob/21516f8a7dedc8d805214d5a2262832315c8e881/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
        
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11"
                .sendToTarget();
    }

    /**
     * Pomocná metóda, ktorá má byť spustená keď dôjde k reštartu aktivity
     */
    public void onDestroy() {
        // Odstránenie všetkých ešte nestiahnutých url adries so zásobníka
        mWorkerHandler.removeMessages(ImageUrl.WHAT);
    }

    /*
        Metóda na stiahnutie obrázku z internetu, Táto metóda je je spustená
        na pracovnom vlákne!

```

``` java tab="Pred úpravou" 
                .sendToTarget();
    }

    /*
        Metóda na stiahnutie obrázku z internetu, Táto metóda je je spustená
        na pracovnom vlákne!

```
####Krok č. 2.2 Bezpečný prístupu k zdieľaným premenným z viacerých vláken [:link:](/commit/14afdd154be446ee907a342bac25db418cc26b4f/)
>  **[🖹](/blob/14afdd154be446ee907a342bac25db418cc26b4f/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
  
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5"
    private Handler mResponseHandler;
    // Referencia na Callback rozhranie
    private Callback mCallback;
    // Objekt určený na zabezpečenie bezpečného prístupu pri využití vláken
    private Object syncObj = new Object();

    // Rozhranie pomocou ktorého sa odovzdá stiahnutý obrázok
    public interface Callback {

```

``` java tab="Pred úpravou" 
    private Handler mResponseHandler;
    // Referencia na Callback rozhranie
    private Callback mCallback;

    // Rozhranie pomocou ktorého sa odovzdá stiahnutý obrázok
    public interface Callback {

```
    
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7"
        if (instance == null || !instance.isAlive()) {
            instance = new MyHandlerThread();
        }
        synchronized (instance.syncObj) {
            instance.mResponseHandler = responseHandler;
            instance.mCallback = callback;
        }
        return instance;
    }


```
    
``` java tab="Pred úpravou" hl_lines="4 5 6 7"
        if (instance == null || !instance.isAlive()) {
            instance = new MyHandlerThread();
        }

        instance.mResponseHandler = responseHandler;
        instance.mCallback = callback;

        return instance;
    }


```
     
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7 8"
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

```

``` java tab="Pred úpravou" 
    public void onDestroy() {
        // Odstránenie všetkých ešte nestiahnutých url adries so zásobníka
        mWorkerHandler.removeMessages(ImageUrl.WHAT);
    }

    /*

```
              
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17"
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

```
          
``` java tab="Pred úpravou" hl_lines="4 5 6 7 8 9 10 11 12 13"
                Stiahnutý obrázok sa odovzdá s pomocu Handler triedy UI
                vláknu, ktoré zavolá callback metódu
             */
            if (bitmap != null) {
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

        } catch (IOException e) {

```
####Krok č. 2.3 Aplikovanie Cache na rýchle načítanie už stiahnutých obrázkov [:link:](/commit/598937e7d9a24dd25956596161eadcb87b693538/)
>  **[🖹](/blob/598937e7d9a24dd25956596161eadcb87b693538/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
 
 > import android.os.Handler;

``` java tab="Nové" hl_lines="7"
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

import java.io.IOException;
import java.io.InputStream;

```
 
``` java tab="Pred úpravou" hl_lines="4"
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.os.Trace;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

```
         
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12"
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

```

``` java tab="Pred úpravou" 
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap);
    }

    public MyHandlerThread() {
        super(TAG);
        setPriority(Process.THREAD_PRIORITY_BACKGROUND); //Nastavenie

```
                  
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21"
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

```
             
``` java tab="Pred úpravou" hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 17"
        try {
            final Bitmap bitmap;

            Trace.beginSection("HTTP download");
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(imageUrl.urlOfImage)
                            .openConnection();
            connection.setRequestMethod("GET");
            bitmap = BitmapFactory
                    .decodeStream((InputStream) connection.getContent());

            try {
                this.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*
                Stiahnutý obrázok sa odovzdá s pomocu Handler triedy UI
                vláknu, ktoré zavolá callback metódu

```
####Krok č. 3.0 Implementovanie LifeCycle AndroidX komponentu [:link:](/commit/eddf187baad520199a103b95ca164b0bfbbf03ae/)
>  **[🖹](/blob/eddf187baad520199a103b95ca164b0bfbbf03ae/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
  
 > public class MainActivity extends AppCompatActivity

``` java tab="Nové" hl_lines="4 5"
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageContainer = findViewById(R.id.imageContainer);
        myHandlerThread = MyHandlerThread
                .getInstance(new Handler(), this, getLifecycle());

        if (!myHandlerThread.isAlive()) {
            // Iba pri prvom spustení musí prebehnúť inicializácia

```
 
``` java tab="Pred úpravou" hl_lines="4"
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageContainer = findViewById(R.id.imageContainer);
        myHandlerThread = MyHandlerThread.getInstance(new Handler(), this);

        if (!myHandlerThread.isAlive()) {
            // Iba pri prvom spustení musí prebehnúť inicializácia

```

 > public class MainActivity extends AppCompatActivity

``` java tab="Nové" 
            imageView.setImageBitmap(bitmap);
    }

}

```
      
``` java tab="Pred úpravou" hl_lines="4 5 6 7 8 9"
            imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandlerThread.onDestroy();

    }
}

```

>  **[🖹](/blob/eddf187baad520199a103b95ca164b0bfbbf03ae/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
     
 > import android.widget.ImageView;

``` java tab="Nové" hl_lines="3 4 5 12 13"
import androidx.annotation.NonNull;
import androidx.collection.LruCache;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHandlerThread extends HandlerThread implements
        LifecycleObserver {

    // Referencia na vytvorenú štanciu MyHandlerThread
    private static MyHandlerThread instance = null;

```
 
``` java tab="Pred úpravou" hl_lines="9"
import androidx.annotation.NonNull;
import androidx.collection.LruCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHandlerThread extends HandlerThread {

    // Referencia na vytvorenú štanciu MyHandlerThread
    private static MyHandlerThread instance = null;

```
  
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5"
    }

    public static MyHandlerThread getInstance(@NonNull Handler responseHandler,
                                              @NonNull Callback callback,
                                              @NonNull Lifecycle lifecycle) {

        if (instance == null || !instance.isAlive()) {
            instance = new MyHandlerThread();

```
 
``` java tab="Pred úpravou" hl_lines="4"
    }

    public static MyHandlerThread getInstance(@NonNull Handler responseHandler,
                                              @NonNull Callback callback) {

        if (instance == null || !instance.isAlive()) {
            instance = new MyHandlerThread();

```
   
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="4 5 6"
        synchronized (instance.syncObj) {
            instance.mResponseHandler = responseHandler;
            instance.mCallback = callback;
            // Zaregistrovanie LifeCycleObserver, ktorým je táto trieda
            // pripojená na životný cyklus nadradenej aktivity
            lifecycle.addObserver(instance);
        }
        return instance;
    }

```

``` java tab="Pred úpravou" 
        synchronized (instance.syncObj) {
            instance.mResponseHandler = responseHandler;
            instance.mCallback = callback;
        }
        return instance;
    }

```
  
 > public class MyHandlerThread extends HandlerThread {

``` java tab="Nové" hl_lines="3 5"
    /**
     * Pomocná metóda, ktorá má byť spustená keď dôjde k reštartu aktivity
     * Táto metóda je teraz pripojená na životný cyklus nadradenej aktivity
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        // Odstránenie všetkých ešte nestiahnutých url adries so zásobníka
        mWorkerHandler.removeMessages(ImageUrl.WHAT);

```

``` java tab="Pred úpravou" 
    /**
     * Pomocná metóda, ktorá má byť spustená keď dôjde k reštartu aktivity
     */
    public void onDestroy() {
        // Odstránenie všetkých ešte nestiahnutých url adries so zásobníka
        mWorkerHandler.removeMessages(ImageUrl.WHAT);

```

<!--end-->