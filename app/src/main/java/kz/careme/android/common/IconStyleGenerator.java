package kz.careme.android.common;

import android.graphics.PointF;

import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.RotationType;

public class IconStyleGenerator {
    public static IconStyle getIconStyle() {
        return new IconStyle(new PointF(0.5f, 1f),
                RotationType.NO_ROTATION,
                1.0f,
                true,
                true,
                0.5f,
                new com.yandex.mapkit.map.Rect(new PointF(0f, 0f), new PointF(1f, 1f)));
    }

    public static IconStyle getIconStyle(float scale) {
        return new IconStyle(new PointF(0.5f, 1f),
                RotationType.NO_ROTATION,
                1.0f,
                true,
                true,
                scale,
                new com.yandex.mapkit.map.Rect(new PointF(0f, 0f), new PointF(1f, 1f)));
    }
}
