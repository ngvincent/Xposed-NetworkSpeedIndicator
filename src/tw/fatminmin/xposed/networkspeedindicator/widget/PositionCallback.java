package tw.fatminmin.xposed.networkspeedindicator.widget;

import android.view.View;
import android.view.ViewGroup;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated.LayoutInflatedParam;

public interface PositionCallback {
	void setup(MethodHookParam param, View v);
	void setup(LayoutInflatedParam liparam, View v);
	void setAbsoluteLeft();
	void setLeft();
	void setRight();
	ViewGroup getClockParent();
}
