/*
 * Copyright 2015 Martin Bella
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.orange_box.storebox;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import net.orange_box.storebox.adapters.extra.UriTypeAdapter;
import net.orange_box.storebox.annotations.type.ActivityPreferences;
import net.orange_box.storebox.annotations.type.DefaultSharedPreferences;
import net.orange_box.storebox.annotations.type.FilePreferences;
import net.orange_box.storebox.engines.SharedPreferencesEngine;
import net.orange_box.storebox.enums.PreferencesMode;
import net.orange_box.storebox.enums.PreferencesType;
import net.orange_box.storebox.utils.TypeUtils;

import java.lang.reflect.Proxy;
import java.util.Locale;

/**
 * StoreBox Classic
 *
 * Creates a no-thrills instance of the supplied interface, by reading any
 * options provided through interface-level annotations.
 * <p>
 * If you'd like to provide options dynamically at run-time, take a look at
 * {@link Builder}.
 */
public final class StoreBox extends StoreBoxSE {

    /**
     * @param context - the context under which the
     * {@link android.content.SharedPreferences} should be opened
     * @param cls - the interface class which should be instantiated
     * @return new instance of class {@code cls} using {@code context}
     */
    public static <T> T create(Context context, Class<T> cls) {
        return new Builder<>(context, cls).build();
    }

    protected StoreBox() {
    }

    /**
     * Can be used to provide a customised instance of the supplied interface,
     * by setting custom options through builder methods.
     * 
     * @param <T>
     */
    public static final class Builder<T> extends StoreBoxSE.Builder<T> {

        private final Context context;

        private PreferencesType preferencesType = PreferencesType.DEFAULT_SHARED;
        private String preferencesName = "";
        private PreferencesMode preferencesMode = PreferencesMode.MODE_PRIVATE;


        public Builder(Context context, Class<T> cls) {
            super(new SharedPreferencesEngine(context), cls);
            this.context = context;
        }

        public Builder preferencesType(PreferencesType value) {
            preferencesType = value;
            return this;
        }

        public Builder preferencesType(
                PreferencesType value, String name) {

            preferencesType = value;
            preferencesName = name;
            return this;
        }

        public Builder preferencesMode(PreferencesMode value) {
            preferencesMode = value;
            return this;
        }

        /**
         * @return new instance of class {@code cls} using {@code context}
         */
        @Override
        @SuppressWarnings("unchecked")
        public T build() {
            validate();

            SharedPreferences prefs;
            switch (preferencesType) {
                case ACTIVITY:
                    prefs = ((Activity) context).getPreferences(
                            preferencesMode.value());
                    break;

                case FILE:
                    prefs = context.getSharedPreferences(
                            preferencesName, preferencesMode.value());
                    break;

                case DEFAULT_SHARED:
                default:
                    prefs = PreferenceManager.getDefaultSharedPreferences(context);
            }
            ((SharedPreferencesEngine) engine).setSharedPreferences(prefs);

            return (T) Proxy.newProxyInstance(
                    cls.getClassLoader(),
                    new Class[]{cls},
                    new StoreBoxInvocationHandler(
                            engine,
                            cls,
                            saveMode));
        }
        
        @Override
        protected void readAnnotations() {
            super.readAnnotations();

            // type/mode option
            if (cls.isAnnotationPresent(DefaultSharedPreferences.class)) {
                preferencesType(PreferencesType.DEFAULT_SHARED);
            } else if (cls.isAnnotationPresent(ActivityPreferences.class)) {
                final ActivityPreferences annotation =
                        cls.getAnnotation(ActivityPreferences.class);

                preferencesType(PreferencesType.ACTIVITY);
                preferencesMode(annotation.mode());
            } else if (cls.isAnnotationPresent(FilePreferences.class)) {
                final FilePreferences annotation =
                        cls.getAnnotation(FilePreferences.class);

                preferencesType(PreferencesType.FILE, annotation.value());
                preferencesMode(annotation.mode());
            }
        }

        @Override
        protected void validate() {
            super.validate();

            if (context == null) {
                throw new IllegalArgumentException(
                        "Context cannot be null");
            }

            if (preferencesType == PreferencesType.ACTIVITY) {
                if (!(context instanceof Activity)) {
                    throw new IllegalArgumentException(String.format(
                            Locale.ENGLISH,
                            "Cannot use %1$s without an Activity context",
                            PreferencesType.ACTIVITY.name()));
                }
            } else if (preferencesType == PreferencesType.FILE) {
                if (TextUtils.isEmpty(preferencesName)) {
                    throw new IllegalArgumentException(String.format(
                            Locale.ENGLISH,
                            "Cannot use %1$s with an empty file name",
                            PreferencesType.FILE.name()));
                }
            }
        }
    }

    static {
        TypeUtils.ADAPTERS_MAP.put(Uri.class, new UriTypeAdapter());
    }

}
