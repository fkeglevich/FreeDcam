package com.troop.freedcam.ui.menu.themes.classic.menu.childs;

import android.content.Context;
import android.os.Handler;

import com.troop.freedcam.i_camera.AbstractCameraUiWrapper;
import com.troop.freedcam.i_camera.parameters.AbstractModeParameter;
import com.troop.freedcam.i_camera.parameters.SDModeParameter;
import com.troop.freedcam.ui.AppSettingsManager;
import com.troop.freedcam.ui.menu.themes.classic.menu.ExpandableGroup;
import com.troop.freedcam.utils.StringUtils;

import java.io.File;

/**
 * Created by Ingo on 24.05.2015.
 */
public class ExpandableChildSaveSD extends ExpandableChild
{
    AbstractCameraUiWrapper cameraUiWrapper;
    public ExpandableChildSaveSD(Context context, ExpandableGroup group, String name, AppSettingsManager appSettingsManager, String settingsname) {
        super(context, group, name, appSettingsManager, settingsname);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        this.parameterHolder = new SDModeParameter(null, appSettingsManager);
        valueTextView.setText(parameterHolder.GetValue());
    }

    public  void SetCameraUIWrapper(AbstractCameraUiWrapper cameraUiWrapper)
    {
        this.cameraUiWrapper = cameraUiWrapper;
    }



    @Override
    public void setValue(String value)
    {
        if (value.equals(SDModeParameter.external))
        {
            boolean canWriteExternal = false;
            final String path = StringUtils.GetExternalSDCARD() + StringUtils.freedcamFolder + "/test.t";
            final File f = new File(path);
            try {
                f.createNewFile();
                canWriteExternal = true;
                f.delete();
            }
            catch (Exception ex)
            {
                canWriteExternal =false;
            }
            if (canWriteExternal) {
                appSettingsManager.SetWriteExternal(true);
                valueTextView.setText(SDModeParameter.external);
            }
            else {
                cameraUiWrapper.onCameraError("Cant write on External SD, pls apply SD fix");
                valueTextView.setText(SDModeParameter.internal);
            }
        }
        else {
            appSettingsManager.SetWriteExternal(false);
            valueTextView.setText(value);
        }
    }




}
