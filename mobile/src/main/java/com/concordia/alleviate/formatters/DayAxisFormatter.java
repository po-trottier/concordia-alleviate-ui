package com.concordia.alleviate.formatters;

import android.content.Context;
import com.concordia.alleviate.R;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class DayAxisFormatter extends ValueFormatter {

  Context context;

  public DayAxisFormatter(Context context) {
    super();
    this.context = context;
  }

  @Override
  public String getAxisLabel(float value, AxisBase axis) {
    switch ((int) value) {
      case 1: return context.getString(R.string.day_monday);
      case 2: return context.getString(R.string.day_tuesday);
      case 3: return context.getString(R.string.day_wednesday);
      case 4: return context.getString(R.string.day_thursday);
      case 5: return context.getString(R.string.day_friday);
      case 6: return context.getString(R.string.day_saturday);
      default:
      case 0: return context.getString(R.string.day_sunday);
    }
  }

  @Override
  public String getBarLabel(BarEntry barEntry) {
    return super.getBarLabel(barEntry);
  }
}
