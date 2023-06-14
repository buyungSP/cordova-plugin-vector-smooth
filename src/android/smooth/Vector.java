package smooth.plugins.cordova.vector;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.VectorDrawable;

import android.net.Uri;
import android.util.TypedValue;
import android.util.Base64;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Vector extends CordovaPlugin {

    private Drawable icon;
    private int color;
    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("open")) {
            this.callbackContext = callbackContext;
            JSONObject result = new JSONObject();
            JSONObject options = args.getJSONObject(0);
            Resources activityRes = cordova.getActivity().getResources();
            String packageName = cordova.getActivity().getPackageName();
            int resId = activityRes.getIdentifier(options.getString("name"), "drawable", packageName);
            icon = activityRes.getDrawable(resId);
            if (icon instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                result.put("src","data:image/png;base64," + Base64.encodeToString(bitmapToByteArray(bitmap), Base64.DEFAULT));
            } else if (icon instanceof VectorDrawable) {
                color = Color.parseColor(options.getString("color"));
                icon.setTint(color);
                VectorDrawable vectorDrawable = (VectorDrawable) icon;
                Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                vectorDrawable.draw(canvas);
                result.put("src","data:image/png;base64," + Base64.encodeToString(bitmapToByteArray(bitmap), Base64.DEFAULT));
            } else if (icon instanceof BitmapDrawable) {
                // Handle BMP icons
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                result.put("src","data:image/bmp;base64," + Base64.encodeToString(bitmapToByteArray(bitmap), Base64.DEFAULT));
            }else{
                TypedValue value = new TypedValue();
                activityRes.getValue(resId, value, true);
                result.put("src","file:///android_res/drawable/" + value.string.toString());
            }
            callbackContext.success(result);
        }
        return false;
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
      return stream.toByteArray();
    }
}
