package tw.fatminmin.xposed.networkspeedindicator.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LayoutInflated.LayoutInflatedParam;

public class PositionCallbackImpl implements PositionCallback {
	
    public static final String PKG_NAME_SYSTEM_UI = "com.android.systemui";
    
	private LinearLayout mSystemIconArea;
	private LinearLayout mStatusBarContents;
	private LinearLayout container;
	private View view;

	@Override
	public void setup(MethodHookParam param, View v) {
		 view = v;
		 mSystemIconArea = (LinearLayout)XposedHelpers.getObjectField(param.thisObject, "mSystemIconArea");
         mStatusBarContents = (LinearLayout)XposedHelpers.getObjectField(param.thisObject, "mStatusBarContents");
         Context mContext = (Context)XposedHelpers.getObjectField(param.thisObject, "mContext");

         container = new LinearLayout(mContext);
         container.setOrientation(LinearLayout.HORIZONTAL);
         container.setWeightSum(1);
         container.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
         container.setVisibility(View.GONE);
         mStatusBarContents.addView(container, 0);
	}
	
	@Override
	public void setup(LayoutInflatedParam liparam, View v) {
	    view = v;
	    
	    FrameLayout root = (FrameLayout) liparam.view;
	    
	    mSystemIconArea = (LinearLayout) root.findViewById(liparam.res.getIdentifier("system_icon_area", "id",
                PKG_NAME_SYSTEM_UI));
	    mStatusBarContents = (LinearLayout) root.findViewById(liparam.res.getIdentifier("status_bar_contents", "id",
                PKG_NAME_SYSTEM_UI));
	    
	    container = new LinearLayout(root.getContext());
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setWeightSum(1);
        container.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        container.setVisibility(View.GONE);
        mStatusBarContents.addView(container, 0);
	}
	
	@Override
	public void setAbsoluteLeft() {
	    removeFromParent();

		container.addView(view);
		container.setVisibility(View.VISIBLE);
	}

	@Override
	public void setLeft() {
	    removeFromParent();

        mSystemIconArea.addView(view, 0);
        container.setVisibility(View.GONE);
	}

	@Override
	public void setRight() {
	    removeFromParent();

        mSystemIconArea.addView(view);
        container.setVisibility(View.GONE);
	}
	
	private void removeFromParent() {
        if(view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
	
	@Override
	public ViewGroup getClockParent() {
		return mStatusBarContents;

	}
}
