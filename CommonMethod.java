package internship.batch1;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class CommonMethod {

    public CommonMethod(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public CommonMethod(View view,String message){
        Snackbar.make(view,message, Snackbar.LENGTH_LONG).show();
    }

    public CommonMethod(Context context,Class<?> nextClass){
        Intent intent = new Intent(context,nextClass);
        context.startActivity(intent);
    }

}
