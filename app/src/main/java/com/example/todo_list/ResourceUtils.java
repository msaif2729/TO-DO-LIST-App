package com.example.todo_list;

import android.content.Context;

import androidx.core.content.ContextCompat;

public class ResourceUtils {

    public static int getColorResourceId(Context context, String colorName) {
        return context.getResources().getIdentifier(colorName, "color", context.getPackageName());
    }

    public static int getColorInt(Context context, String colorName) {
        int resourceId = getColorResourceId(context, colorName);
        return ContextCompat.getColor(context, resourceId);
    }
}
