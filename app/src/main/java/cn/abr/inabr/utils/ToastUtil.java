package cn.abr.inabr.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Show
 * @author Administrator
 *
 */
public class ToastUtil {
	private static Toast mToast = null;

	public static void show(Context context, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, msg.length() < 10 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
		} else {
			mToast.setText(msg);
			mToast.setDuration(msg.length() < 10 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
		}
		// mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
	}
}
