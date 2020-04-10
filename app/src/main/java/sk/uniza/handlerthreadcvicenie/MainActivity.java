package sk.uniza.handlerthreadcvicenie;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements MyHandlerThread.Callback {

    // Url online služby, ktorá generuje obrázky s textom
    private String imageUrlLink = "https://dummyimage.com/300/09f/fff" +
            ".png&text=";

    private LinearLayout imageContainer;
    private MyHandlerThread myHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        // Otestovanie či ImageView bol vytvorený v rovnakom kontexte. V
        // prípade reštatovania aplikácie, može ImageView pochádzať z už
        // zaniknutej inštancie Aplikácie.
        if (imageView.getContext() == this)
            imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandlerThread.onDestroy();

    }
}
