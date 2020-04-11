# Cvičenie

<!--tgen file='/home/martin/AndroidStudioProjects/HandlerThreadCvicenie/out.patch' lang=java tabs t_new="Nové" t_old="Pred úpravou" -->
Úlohou cvičenia je oboznámiť sa s použitím HandlerThread ako pracovné vlákno, ktoré odbremení hlavné 'UI' vlákno od náročných úloh.   

Vytváraná aplikácia  má za úlohu stiahnuť obrázky z internetu  a po stiahnutí ich zobrazí v pripravenom Layoute. Požiadavky na aplikáciu:

- Obrázky sťahovať na pracovnom vlákne.

- Aplikácia sa musí prispôsobiť životnému cyklu aktivity:

    -  pri reštarte aplikácie sa pracovné vlákno musí ukončiť, alebo sa opätovne využije pri reštarte aktivity.
    - Použiť singleton návrhový vzor
    - Využiť cache pamäť na uloženie stiahnutých obrázkov.

- Využiť `LifeCycle` na integrovanie životného cyklu hlavnej aktivity do pracovného vlákna

  

<!--tgen step=1.0 template="files_list.jinja"  -->
####1.0 Úprava layout-u aplikácie [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/af6bb53a561bb7a67eae5ed20c12c8eefad867f5/)
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
<!--tgen lang=xml step=1.0 nohighlight  -->
####1.0 Úprava layout-u aplikácie [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/af6bb53a561bb7a67eae5ed20c12c8eefad867f5/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/af6bb53a561bb7a67eae5ed20c12c8eefad867f5/app/src/main/res/layout/activity_main.xml) app/src/main/res/layout/activity_main.xml**
             

``` xml  
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


<!--end-->
<!--tgen step=1.1 nohighlight -->
####1.1 Vytvorenie MyHandlerThread triedy [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/5b60840ed884ad84f626b5ee98352693cabac7e6/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/5b60840ed884ad84f626b5ee98352693cabac7e6/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
                 

``` java  
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


<!--end-->
<!--tgen step=1.2.a  -->
####1.2.a Vytvorenie rozhrania, ktoré sa použije na odovzdanie stiahnutého obrázku [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/4b4a0f9a37a5ecbbc6197dbf6b74cbef1080fd07/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/4b4a0f9a37a5ecbbc6197dbf6b74cbef1080fd07/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
        

``` java  hl_lines="3 6 13 14 15 16 17 24"
package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;
import android.os.HandlerThread;
import android.os.Process;
import android.widget.ImageView;

public class MyHandlerThread extends HandlerThread {

    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();

    // Rozhranie pomocou ktorého sa odovzdá stiahnutý obrázok
    public interface Callback {
        public void onImageDownloaded(ImageView imageView, Bitmap bitmap);
    }

    public MyHandlerThread() {
        super(TAG);
        setPriority(Process.THREAD_PRIORITY_BACKGROUND); //Nastavenie
        // priority pracovnému vláknu

    }
}

```


<!--end-->
<!--tgen step=1.2.b  -->
####1.2.b Použitie rozhrania MyHandlerThread.Callback [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/f512c91c96472a26879f73d17e06ad4d1c0e8b94/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/f512c91c96472a26879f73d17e06ad4d1c0e8b94/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
          

``` java  hl_lines="3 5 9 10 17 18 19 20 21 22"
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


<!--end-->
<!--tgen step=1.3  -->
####1.3 Vytvorenie pomocnej metódy prepareHandler [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/2607e74637e807bdd1111482f54696dc050a11fe/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/2607e74637e807bdd1111482f54696dc050a11fe/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
         

``` java  hl_lines="4 6 8 9 10 17 18 19 20"
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

                  
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21"
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


<!--end-->
<!--tgen step=1.4  -->
####1.4 Vytvorenie pomocnej triedy ImageUrl [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/4982e6a93cac3633248e615b11d54e87ae8fa4bd/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/4982e6a93cac3633248e615b11d54e87ae8fa4bd/app/src/main/java/sk/uniza/handlerthreadcvicenie/ImageUrl.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/ImageUrl.java**
                        

``` java  hl_lines="1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24"
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


>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/4982e6a93cac3633248e615b11d54e87ae8fa4bd/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
 
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4"
        };
    }


}

```


<!--end-->
<!--tgen step=1.5  -->
####1.5 Vytvorenie metódy handleRequest určenej na stiahnutie obrázku z internetu na pracovnom vlákne [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/bbf884bc75f66ed597f98c2985c5f1fc5b7dc525/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/bbf884bc75f66ed597f98c2985c5f1fc5b7dc525/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
       

``` java  hl_lines="4 8 14 15 16 17 18"
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

                                            
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51"
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


<!--end-->
<!--tgen step=1.6  -->
####1.6 Úprava konštruktora MyHandlerThread [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/5e4d1ca0b3101bd41f536fc4ffe8cfac3cf1d92c/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/5e4d1ca0b3101bd41f536fc4ffe8cfac3cf1d92c/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
 
 > import android.graphics.BitmapFactory;

``` java  hl_lines="4"
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.os.Trace;
import android.widget.ImageView;

import androidx.annotation.NonNull;


```

         
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7 8 14 15 19 20"
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


<!--end-->
<!--tgen step=1.7  -->
####1.7 Spracovanie prijatej správy v handleMessage metóde [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/0f5f82e1cafb524c64576b7722c7aede10b0389f/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/0f5f82e1cafb524c64576b7722c7aede10b0389f/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
    
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7"
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


<!--end-->
<!--tgen step=1.8  -->
####1.8 Vytvorenie a inicializovanie novej inštancie MyHandlerThread [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/a98f5e44094b567d9ee8996bb09f7157b0ffa5b5/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/a98f5e44094b567d9ee8996bb09f7157b0ffa5b5/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
         
 > package sk.uniza.handlerthreadcvicenie;

``` java  hl_lines="3 5 12 13 14 19 20 21 22"
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


<!--end-->
<!--tgen step=1.9  -->
####1.9 Vytvorenie a odoslanie zoznamu obrázkov na stiahnutie [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/412fc748b292794093459f67fd144249df372e37/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/412fc748b292794093459f67fd144249df372e37/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
    
 > import androidx.appcompat.app.AppCompatActivity;

``` java  hl_lines="4 5 6 7"
public class MainActivity extends AppCompatActivity
        implements MyHandlerThread.Callback {

    // Url online služby, ktorá generuje obrázky s textom
    private String imageUrlLink = "https://dummyimage.com/300/09f/fff" +
            ".png&text=";

    private LinearLayout imageContainer;
    private MyHandlerThread myHandlerThread;


```

                  
 > public class MainActivity extends AppCompatActivity

``` java  hl_lines="4 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27"
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


>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/412fc748b292794093459f67fd144249df372e37/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
          
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7 8 9 10 11 12 13"
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


<!--end-->
<!--tgen step=1.10  -->
####1.10 Zobrazenie stiahnutého obrázku [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/450569817c45620198677de4a805febb0ff35c6d/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/450569817c45620198677de4a805febb0ff35c6d/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
     
 > public class MainActivity extends AppCompatActivity

``` java  hl_lines="3 4 5 6 7"
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


<!--end-->
<!--tgen step=1.11  -->
####1.11 Pridanie povolenia pre prístup na internet [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/54ef73ef68705d93477a23159fa354c3fde99707/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/54ef73ef68705d93477a23159fa354c3fde99707/app/src/main/AndroidManifest.xml) app/src/main/AndroidManifest.xml**
 

``` java  hl_lines="4"
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="sk.uniza.handlerthreadcvicenie">

    <uses-permission android:name="android.permission.INTERNET" />
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"

```


<!--end-->
<!--tgen step=1.12  -->
####1.12 Reštart aplikácie vplyvom konfiguračnej zmeny. Minimálne riešenie [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/86fd759c56640a5864e2627749d51c258b763619/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/86fd759c56640a5864e2627749d51c258b763619/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**


``` java  
package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;

```

       
 > public class MainActivity extends AppCompatActivity

``` java  hl_lines="4 5 6 7 8 9 10"
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


<!--end-->
<!--tgen step=2.0  -->
####2.0 Využitie Singleton návrhového vzoru [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/bc1afda979d5a98775fd6d3d8838f89ffcf871ed/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/bc1afda979d5a98775fd6d3d8838f89ffcf871ed/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
       
 > public class MainActivity extends AppCompatActivity

``` java  hl_lines="4 6 7 8 9 10 11"
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

  
 > public class MainActivity extends AppCompatActivity

``` java  hl_lines="4 5"
    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}

```


>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/bc1afda979d5a98775fd6d3d8838f89ffcf871ed/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
   
 > import java.net.URL;

``` java  hl_lines="3 4 5"
public class MyHandlerThread extends HandlerThread {

    // Referencia na vytvorenú štanciu MyHandlerThread
    private static MyHandlerThread instance = null;

    // Názov vlákna, ktorý je zobrazený pri debugovaní aplikácie
    private static final String TAG = MyHandlerThread.class.getSimpleName();


```

              
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 8 9 10 11 12 13 14 15 16 17 18 19 20"
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


<!--end-->
<!--tgen step=2.1  -->
####2.1 Optimalizácia s pohladu reštartovania aktivity [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/50622b7e7ace7e8daa4fa48456de70fa20a19ca5/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/50622b7e7ace7e8daa4fa48456de70fa20a19ca5/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
 
 > public class MainActivity extends AppCompatActivity

``` java  hl_lines="4"
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandlerThread.onDestroy();

    }
}

```


>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/50622b7e7ace7e8daa4fa48456de70fa20a19ca5/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
        
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7 8 9 10 11"
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


<!--end-->
<!--tgen step=2.2  -->
####2.2 Bezpečný prístupu k zdieľaným premenným z viacerých vláken [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/5640d902328f7dd0b5cd5f5f998ad11f8ea71744/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/5640d902328f7dd0b5cd5f5f998ad11f8ea71744/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
  
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5"
    private Handler mResponseHandler;
    // Referencia na Callback rozhranie
    private Callback mCallback;
    // Objekt určený na zabezpečenie bezpečného prístupu pri využití vláken
    private Object syncObj = new Object();

    // Rozhranie pomocou ktorého sa odovzdá stiahnutý obrázok
    public interface Callback {

```

    
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7"
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

     
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7 8"
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

              
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17"
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


<!--end-->
<!--tgen step=2.3  -->
####2.3 Aplikovanie Cache na rýchle načítanie už stiahnutých obrázkov [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/9e00659e63e8d9b85a3e694b561fb1764bfdf114/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/9e00659e63e8d9b85a3e694b561fb1764bfdf114/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
 
 > import android.os.Handler;

``` java  hl_lines="7"
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

import java.io.IOException;
import java.io.InputStream;

```

         
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7 8 9 10 11 12"
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

                  
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21"
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


<!--end-->
<!--tgen step=3.0  -->
####3.0 Implementovanie LifeCycle AndroidX komponentu [:link:](https://github.com/hudikm/HandlerThreadCvicenie/commit/194619708e3bd14f62d08a6436d4907e3f9b450b/)
>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/194619708e3bd14f62d08a6436d4907e3f9b450b/app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MainActivity.java**
  
 > public class MainActivity extends AppCompatActivity

``` java  hl_lines="4 5"
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageContainer = findViewById(R.id.imageContainer);
        myHandlerThread = MyHandlerThread
                .getInstance(new Handler(), this, getLifecycle());

        if (!myHandlerThread.isAlive()) {
            // Iba pri prvom spustení musí prebehnúť inicializácia

```


 > public class MainActivity extends AppCompatActivity

``` java  
            imageView.setImageBitmap(bitmap);
    }

}

```


>  **[🖹](https://github.com/hudikm/HandlerThreadCvicenie/blob/194619708e3bd14f62d08a6436d4907e3f9b450b/app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java) app/src/main/java/sk/uniza/handlerthreadcvicenie/MyHandlerThread.java**
     
 > import android.widget.ImageView;

``` java  hl_lines="3 4 5 12 13"
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

  
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5"
    }

    public static MyHandlerThread getInstance(@NonNull Handler responseHandler,
                                              @NonNull Callback callback,
                                              @NonNull Lifecycle lifecycle) {

        if (instance == null || !instance.isAlive()) {
            instance = new MyHandlerThread();

```

   
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="4 5 6"
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

  
 > public class MyHandlerThread extends HandlerThread {

``` java  hl_lines="3 5"
    /**
     * Pomocná metóda, ktorá má byť spustená keď dôjde k reštartu aktivity
     * Táto metóda je teraz pripojená na životný cyklus nadradenej aktivity
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        // Odstránenie všetkých ešte nestiahnutých url adries so zásobníka
        mWorkerHandler.removeMessages(ImageUrl.WHAT);

```


<!--end-->







