package com.example.timetable;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import me.jfenn.colorpickerdialog.dialogs.ColorPickerDialog;
import me.jfenn.colorpickerdialog.interfaces.OnColorPickedListener;

public class ColorPicker extends Fragment {



      private  int colors [];
      private int col;
    public ColorPicker() {
        colors = new int []{
                  Color.parseColor("#CD5C5C"),
                  Color.parseColor("#F08080"),
                  Color.parseColor("#FA8072"),
                  Color.parseColor("#E9967A"),
                  Color.parseColor("#FFA07A"),
                  Color.parseColor("#177181"),
                  Color.parseColor("#009091"),
                  Color.parseColor("#2DAF93"),
                  Color.parseColor("#6ACB89"),
                  Color.parseColor("#ADE47B"),
                  Color.parseColor("#F9F871"),
                  Color.parseColor("#297598"),
                  Color.parseColor("#5275A8"),
                  Color.parseColor("#7E72AC"),
                  Color.parseColor("#A76DA3"),
                  Color.parseColor("#C56B8E"),
                  Color.parseColor("#BFFAFF"),
                  Color.parseColor("#FC8F3A"),
                  Color.parseColor("#009F7E"),
                  Color.parseColor("#8CF8B8"),
                  Color.parseColor("#52BF83"),
                  Color.parseColor("#028951"),
                  Color.parseColor("#00A8D3"),
                  Color.parseColor("#A04B6B"),
                  Color.parseColor("#4ACBB1"),
                  Color.parseColor("#1A9E9F"),
                  Color.parseColor("#005B6A"),
                  Color.parseColor("#001621"),
                  Color.parseColor("#84D1E2"),
                  Color.parseColor("#84D1E2"),
                  Color.parseColor("#FF6F91"),
                  Color.parseColor("#FF6F91"),
                  Color.parseColor("#F9F871"),
                  Color.parseColor("#2C73D2"),
                  Color.parseColor("#0081CF"),
                  Color.parseColor("#C34A36"),
                  Color.parseColor("#FF8066"),
                  Color.parseColor("#F3C5FF"),
                  Color.parseColor("#845EC2"),
                  Color.parseColor("#A178DF"),
                  Color.parseColor("#BE93FD"),
                  Color.parseColor("#DCB0FF"),
                  Color.parseColor("#FACCFF"),
                  Color.parseColor("#FF9671"),
                  Color.parseColor("#D3704D"),
                  Color.parseColor("#A74B2B"),
                  Color.parseColor("#7D270B"),
                  Color.parseColor("#550000"),
                  Color.parseColor("#F01E1E"),
                  Color.parseColor("#CE0004"),
                  Color.parseColor("#AC0000"),
                  Color.parseColor("#8C0000"),
                  Color.parseColor("#6E0000"),
                  Color.parseColor("#F01E1E"),
                  Color.parseColor("#FFA281"),
                  Color.parseColor("#F32320"),
                  Color.parseColor("#BE0000"),
  //                Color.parseColor(""),
  //                Color.parseColor(""),
  //                Color.parseColor(""),
  //                Color.parseColor(""),
  //                Color.parseColor(""),
  //                Color.parseColor(""),s
  //                Color.parseColor(""),

          };
    }

    public int[] getColors() {
        return colors;
    }
    public void openColorPicker(FragmentManager fm, final Button colbtn, final Button btnTemp){
        final int[] cols = new int[1];
        new ColorPickerDialog()
                .withPresets(colors)

                // the default / initial color
                .withListener(new OnColorPickedListener<ColorPickerDialog>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onColorPicked(@Nullable ColorPickerDialog dialog, int color) {
                        colbtn.setBackgroundTintList(ColorStateList.valueOf(color));
                        btnTemp.setBackgroundColor(color);
                    }
                })
                .show(fm, "colorPicker");

    }

    public int getCol() {
        return col;
    }
}


