package com.example.kalpesh.textaddview;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kalpesh.textaddview.util.AutoResizeTextView;
import com.example.kalpesh.textaddview.util.ColorPickerDialog;

/**
 * Created by cheungchingai on 6/15/15.
 */
public class StickerTextView extends StickerView {
    private AutoResizeTextView tv_main;





    public StickerTextView(Context context, Activity activity, int no) {
        super(context,activity,no);

    }

    public StickerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public StickerTextView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }

    @Override
    public TextView getMainView() {
        if(tv_main != null)
            return tv_main;

        tv_main = new AutoResizeTextView(getContext());


        //tv_main.setTextSize(22);
        tv_main.setTextColor(Color.WHITE);
        tv_main.setGravity(Gravity.CENTER);

        tv_main.setTextSize(400);
        tv_main.setMinTextSize(12);
        tv_main.setShadowLayer(4, 0, 0, Color.BLACK);
        tv_main.setMaxLines(1);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.CENTER;

        tv_main.setLayoutParams(params);
        if(getImageViewFlip()!=null)
            getImageViewFlip().setVisibility(View.GONE);


        return tv_main;
    }




//    public void setText(String text){
//        if(tv_main!=null){
//
//             }
//    }
    public void setfontsize(int no){
      this.tv_main.setTextSize(no);
    }

    public void setTface(Typeface typeface){
        if(tv_main!=null){
            tv_main.setTypeface(typeface);
        }
    }

    public void setMyColor(Activity mainActivity){
        if(tv_main!=null){

            int initialColor = Color.WHITE;

            ColorPickerDialog colorPickerDialog = new ColorPickerDialog(mainActivity, initialColor, new ColorPickerDialog.OnColorSelectedListener() {

                @Override
                public void onColorSelected(int color) {
                    tv_main.setTextColor(color);
                }

            });
           colorPickerDialog.show();
        }
    }

//    public void setfontsize(int no){
//        tv_main.setTextSize(no);
//    }


//    public void setbold(){
//
//        if (boldbollean) {
//            boldbollean=false;
//        }else{
//            boldbollean=true;
//        }
//
//        MainSetup();
//
//    }
//
//   public void setitalic(){
//
//       if (italicbollean) {
//           italicbollean=false;
//       }else{
//           italicbollean=true;
//       }
//       MainSetup();
//    }
//
//  public void setunderline(){
//      if (underlinebollean) {
//          underlinebollean=false;
//      }else{
//          underlinebollean=true;
//      }
//      MainSetup();
//  }


//  public void MainSetup(){
//
//      SpannableString Maincontent=new SpannableString(Maintext);
//
//      if (boldbollean) {
//          Maincontent.setSpan(new StyleSpan(Typeface.BOLD), 0, Maincontent.length(), 0);
//      }
//      if (italicbollean) {
//          Maincontent.setSpan(new StyleSpan(Typeface.ITALIC), 0, Maincontent.length(), 0);
//      }
//
//      if (underlinebollean) {
//          Maincontent.setSpan(new UnderlineSpan(), 0, Maincontent.length(), 0);
//      }
//
//      tv_main.setText(Maincontent);
//
//  }


























    public String getText(){
        if(tv_main!=null)
            return tv_main.getText().toString();

        return null;
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

    @Override
    protected void onScaling(boolean scaleUp) {
        super.onScaling(scaleUp);
    }
}
