package com.example.moduleuser.kml.adapter;

import android.widget.ImageView;

import com.android.utils.GlideUtils;
import com.android.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.moduleuser.R;
import com.example.moduleuser.kml.javaforkml.entity.KmlElenmt;
import com.example.moduleuser.kml.javaforkml.entity.KmlLine;
import com.example.moduleuser.kml.javaforkml.entity.KmlPoint;
import com.example.moduleuser.kml.javaforkml.entity.KmlPolygon;
import com.example.moduleuser.kml.javaforkml.entity.KmlProperty;

import java.util.List;

/**
 * Created by ${LiuTao}.
 * User: Administrator
 * Name: ArouteDemo
 * functiona:
 * Date: 2019/7/8 0008
 * Time: 上午 8:38
 */
public class KMLAdapter extends BaseQuickAdapter<KmlElenmt, BaseViewHolder> {
    public KMLAdapter(List<KmlElenmt> list) {
        super(R.layout.user_kml_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, KmlElenmt item) {
        if (item instanceof KmlLine) {
            KmlLine kmlLine = (KmlLine) item;
            helper.setText(R.id.kml_type_tv, kmlLine.getName());
            helper.setText(R.id.kml_coordinates_tv, "线");
        } else if (item instanceof KmlPoint) {
            KmlPoint kmlPoint = (KmlPoint) item;
            helper.setText(R.id.kml_type_tv, kmlPoint.getName());
            helper.setText(R.id.kml_coordinates_tv, "点");
        } else if (item instanceof KmlPolygon) {
            KmlPolygon kmlPolygon = (KmlPolygon) item;
            helper.setText(R.id.kml_type_tv, kmlPolygon.getName());
            helper.setText(R.id.kml_coordinates_tv, "面");
        }
    }

}
