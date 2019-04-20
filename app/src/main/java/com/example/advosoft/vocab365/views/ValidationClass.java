package com.example.advosoft.vocab365.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by Advosoft2 on 6/28/2017.
 */

public class ValidationClass {

    public static boolean validateEmail(String email) {
        final Pattern emailPattern = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)\\b", 2);
        return emailPattern.matcher(email).matches();
    }
    public static boolean validateMobile(String mobile) {
        String regex = "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:x.+)?$";
        final Pattern emailPattern = Pattern.compile(regex);
        return emailPattern.matcher(mobile).matches();
    }
    public static void showMessage(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();

    }
    public static ProgressDialog showProgress(Context context, String title, String msg) {
		/*ProgressBar bar = new ProgressBar(context)
		bar.showContextMenu();*/
        //bar.destroyDrawingCache();

        ProgressDialog dialog = new ProgressDialog(context);
        //dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
        return dialog;
    }
}
