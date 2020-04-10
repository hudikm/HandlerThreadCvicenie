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
