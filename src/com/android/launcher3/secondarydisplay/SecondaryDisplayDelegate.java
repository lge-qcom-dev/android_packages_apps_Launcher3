/*
 * Copyright (C) 2025 The Android Open Source Project
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

import android.view.KeyEvent;

import com.android.launcher3.dagger.ActivityContextSingleton;

import javax.inject.Inject;

/**
 * Exposes Quickstep-specific APIs to {@link SecondaryDisplayLauncher}.
 */
@ActivityContextSingleton
public class SecondaryDisplayDelegate {

    @Inject
    public SecondaryDisplayDelegate() {
    }

    /**
     * Called when a home intent is delivered to the secondary display launcher.
     */
    void onHomeIntent() {
    }

    void onCreate() {
    }

    void onDestroy() {
    }

    void updateStashControllerStateFlags(int displayId, boolean isVisible) {
    }

    boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }
}
