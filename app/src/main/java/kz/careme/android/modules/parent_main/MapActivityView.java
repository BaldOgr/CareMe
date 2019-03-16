package kz.careme.android.modules.parent_main;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObjectTapListener;

import kz.careme.android.model.Kid;

public interface MapActivityView {
    void setMarker(Point point, Kid kid);
}
