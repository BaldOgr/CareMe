package kz.careme.android.modules;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObjectTapListener;

public interface MapActivityView {
    void setMarker(Point point, float opacity);
    void setMarker(Point point, float opacity, MapObjectTapListener listener);
}
