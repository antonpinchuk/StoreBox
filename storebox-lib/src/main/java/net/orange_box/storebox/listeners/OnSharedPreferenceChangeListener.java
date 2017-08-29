package net.orange_box.storebox.listeners;

import net.orange_box.storebox.StoreEngine;

public interface OnSharedPreferenceChangeListener {
    void onSharedPreferenceChanged(StoreEngine engine, String key);
}
