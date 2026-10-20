/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.launcher3.secondarydisplay;

import static android.view.Display.DEFAULT_DISPLAY;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.InvariantDeviceProfile;
import com.android.launcher3.Launcher;

/** Secondary home activity using the standard Launcher workspace and all-apps UI. */
public class SecondaryDisplayLauncher extends Launcher {

    private SecondaryDisplayDelegate mSecondaryDisplayDelegate;

    @Override
    protected DeviceProfile createDeviceProfile(InvariantDeviceProfile idp) {
        return getDisplayId() == DEFAULT_DISPLAY
                ? super.createDeviceProfile(idp)
                : idp.createDeviceProfileForSecondaryDisplay(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSecondaryDisplayDelegate = getActivityComponent().getSecondaryDisplayDelegate();
        mSecondaryDisplayDelegate.onCreate();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_MAIN.equals(intent.getAction()) && mSecondaryDisplayDelegate != null) {
            mSecondaryDisplayDelegate.onHomeIntent();
        }
    }

    @Override
    protected void onActivityFlagsChanged(int changeBits) {
        super.onActivityFlagsChanged(changeBits);
        if (mSecondaryDisplayDelegate != null
                && getDisplayId() != DEFAULT_DISPLAY
                && (changeBits & ACTIVITY_STATE_RESUMED) != 0) {
            mSecondaryDisplayDelegate.updateStashControllerStateFlags(
                    getDisplayId(), hasBeenResumed());
        }
    }

    @Override
    public void onDestroy() {
        if (mSecondaryDisplayDelegate != null) {
            mSecondaryDisplayDelegate.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return (mSecondaryDisplayDelegate != null
                        && mSecondaryDisplayDelegate.dispatchKeyEvent(event))
                || super.dispatchKeyEvent(event);
    }
}
