/*
 * Copyright (C) 2026 The Android Open Source Project
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

package com.android.launcher3.secondarydisplay

import android.content.Intent
import android.os.Bundle
import android.view.Display.DEFAULT_DISPLAY
import android.view.KeyEvent
import com.android.launcher3.DeviceProfile
import com.android.launcher3.InvariantDeviceProfile
import com.android.launcher3.uioverrides.QuickstepLauncher

/**
 * Secondary home activity using the full Quickstep launcher implementation.
 */
class SecondaryDisplayQuickstepLauncher : QuickstepLauncher() {

    private var secondaryDisplayDelegate: SecondaryDisplayDelegate? = null

    protected override fun createDeviceProfile(idp: InvariantDeviceProfile): DeviceProfile =
        if (displayId == DEFAULT_DISPLAY) {
            super.createDeviceProfile(idp)
        } else {
            idp.createDeviceProfileForSecondaryDisplay(this)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secondaryDisplayDelegate = activityComponent.secondaryDisplayDelegate.also {
            it.onCreate()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_MAIN == intent.action) {
            secondaryDisplayDelegate?.onHomeIntent()
        }
    }

    override fun onActivityFlagsChanged(changeBits: Int) {
        super.onActivityFlagsChanged(changeBits)
        if (displayId != DEFAULT_DISPLAY && (changeBits and ACTIVITY_STATE_RESUMED) != 0) {
            secondaryDisplayDelegate?.updateStashControllerStateFlags(
                displayId,
                hasBeenResumed(),
            )
        }
    }

    override fun onDestroy() {
        secondaryDisplayDelegate?.onDestroy()
        super.onDestroy()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean =
        secondaryDisplayDelegate?.dispatchKeyEvent(event) == true ||
            super.dispatchKeyEvent(event)
}
