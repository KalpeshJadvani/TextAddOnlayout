package com.example.kalpesh.textaddview;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class StickerView extends FrameLayout {

    public static final String TAG = "com.knef.stickerView";
    private BorderView iv_border;
    private ImageView iv_scale;
    private ImageView iv_delete;
    private ImageView iv_flip;
    private ImageView iv_ok;
    private EditText input;
    private  AlertDialog.Builder alertDialog;
    private TextView MainTextView;
    private String MaintextStore;
    private Boolean boldbollean = false;
    private Boolean italicbollean=false;
    private Boolean underlinebollean=false;

    // For scalling
    private float this_orgX = -1, this_orgY = -1;
    private float scale_orgX = -1, scale_orgY = -1;
    private double scale_orgWidth = -1, scale_orgHeight = -1;
    // For rotating
    private float rotate_orgX = -1, rotate_orgY = -1, rotate_newX = -1, rotate_newY = -1;
    // For moving
    private float move_orgX =-1, move_orgY = -1;

    private double centerX, centerY;

    private final static int BUTTON_SIZE_DP = 30;
    private final static int SELF_SIZE_DP = 100;

    public static Activity activity;
    public static int No;

    public interface Returnno {
        void ObjectNo(int no);
    }
    public Returnno returnno;

    public StickerView(Context context, Activity activity, int no) {
        super(context);

        this.activity = activity;


        this.No = no;

        returnno=(Returnno)activity;

        init(context);
    }

    public StickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        this.iv_border = new BorderView(context);
        this.iv_scale = new ImageView(context);
        this.iv_delete = new ImageView(context);
        this.iv_flip = new ImageView(context);
        this.iv_ok = new ImageView(context);
        this.input = new EditText(context);




        this.MainTextView=getMainView();
        this.iv_scale.setImageResource(R.drawable.zoominout);
        this.iv_delete.setImageResource(R.drawable.remove);
        this.iv_flip.setImageResource(R.drawable.flip);
        this.iv_ok.setImageResource(R.drawable.tick);

        this.setTag("DraggableViewGroup");
        this.iv_border.setTag("iv_border");
        this.iv_scale.setTag("iv_scale");
        this.iv_delete.setTag("iv_delete");
        this.iv_flip.setTag("iv_flip");
        this.iv_ok.setTag(Integer.toString(No));

        this.MainTextView.setTag("textview");

        returnno.ObjectNo(Integer.parseInt(iv_ok.getTag().toString()));

        int margin = convertDpToPixel(BUTTON_SIZE_DP, getContext())/2;
        int size = convertDpToPixel(SELF_SIZE_DP, getContext());

        FrameLayout.LayoutParams this_params =
                new FrameLayout.LayoutParams(
                        size,
                        size
                );
        this_params.gravity = Gravity.CENTER;

        FrameLayout.LayoutParams iv_main_params =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        iv_main_params.setMargins(margin,margin,margin,margin);



        FrameLayout.LayoutParams iv_border_params =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        iv_border_params.setMargins(margin,margin,margin,margin);

        FrameLayout.LayoutParams iv_scale_params =
                new FrameLayout.LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_scale_params.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        FrameLayout.LayoutParams iv_delete_params =
                new FrameLayout.LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_delete_params.gravity = Gravity.TOP | Gravity.RIGHT;

        FrameLayout.LayoutParams iv_flip_params =
                new FrameLayout.LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_flip_params.gravity = Gravity.TOP | Gravity.LEFT;


        FrameLayout.LayoutParams iv_ok_params =
                new FrameLayout.LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_ok_params.gravity = Gravity.BOTTOM | Gravity.LEFT;

        this.setLayoutParams(this_params);

        this.addView(MainTextView, iv_main_params);
        this.addView(iv_border, iv_border_params);
        this.addView(iv_scale, iv_scale_params);
        this.addView(iv_delete, iv_delete_params);
        this.addView(iv_flip, iv_flip_params);
        this.addView(iv_ok, iv_ok_params);

        this.setOnTouchListener(mTouchListener);



        this.MainTextView.setOnTouchListener(mTouchListener);

        this.iv_scale.setOnTouchListener(mTouchListener);

        this.iv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StickerView.this.getParent()!=null){
                    ViewGroup myCanvas = ((ViewGroup)StickerView.this.getParent());
                    myCanvas.removeView(StickerView.this);
                }
            }
        });

        this.iv_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StickerView.this.getParent()!=null){
                 setControlItemsHiddenMytool(true);

                }
            }
        });
        this.iv_flip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                View mainView = MainTextView;
                mainView.setRotationY(mainView.getRotationY() == -180f? 0f: -180f);
                mainView.invalidate();
                requestLayout();
            }
        });

        MaintextStore=iv_ok.getTag().toString();
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if(input.getParent()!=null)
                    ((ViewGroup)input.getParent()).removeView(input);

                alertDialog = new AlertDialog.Builder(activity);

                alertDialog.setTitle("Enter Your Text");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setTextColor(Color.BLACK);

//                input.setText(iv_ok.getTag().toString());

                input.setText(MainTextView.getText());

                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainTextView.setText(input.getText().toString());
                                MaintextStore=input.getText().toString();
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                input.setText("");
                                dialog.cancel();
                            }
                        });

                alertDialog.show();

                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                // if the second tap hadn't been released and it's being moved
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

        });


        this.MainTextView.setText(Integer.toString(No));
    }

    public boolean isFlip(){
        return MainTextView.getRotationY() == -180f;
    }

    protected abstract TextView getMainView();



    public void setDobbleClick(){
        alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("Enter Your Text");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setTextColor(Color.BLACK);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainTextView.setText(input.getText().toString());
                        MaintextStore=input.getText().toString();
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        input.setText("");
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    public void setbold(){

        if (boldbollean) {
            boldbollean=false;

        }else{
            boldbollean=true;
        }
        if(MaintextStore!=null)
        MainSetup();

    }

    public void setitalic(){

        if (italicbollean) {
            italicbollean=false;
        }else{
            italicbollean=true;
        }
        if(MaintextStore!=null)
        MainSetup();
    }

    public void setunderline(){
        if (underlinebollean) {
            underlinebollean=false;
        }else{
            underlinebollean=true;
        }
        if(MaintextStore!=null)
        MainSetup();
    }

    public void MainSetup(){

        SpannableString Maincontent=new SpannableString(MaintextStore);

        if (boldbollean) {
            Maincontent.setSpan(new StyleSpan(Typeface.BOLD), 0, Maincontent.length(), 0);
        }
        if (italicbollean) {
            Maincontent.setSpan(new StyleSpan(Typeface.ITALIC), 0, Maincontent.length(), 0);
        }

        if (underlinebollean) {
            Maincontent.setSpan(new UnderlineSpan(), 0, Maincontent.length(), 0);
        }

        this.MainTextView.setText(Maincontent);

    }



    final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            setControlItemsHiddenMytool(false);
        }
    });



    private OnTouchListener mTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            returnno.ObjectNo(Integer.parseInt(iv_ok.getTag().toString()));


            if(view.getTag().equals("DraggableViewGroup")) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();

                        break;
                    case MotionEvent.ACTION_MOVE:

                        float offsetX = event.getRawX() - move_orgX;
                        float offsetY = event.getRawY() - move_orgY;
                        StickerView.this.setX(StickerView.this.getX()+offsetX);
                        StickerView.this.setY(StickerView.this.getY() + offsetY);
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:

                        break;

                }

            }else if(view.getTag().equals("textview")){
                gestureDetector.onTouchEvent(event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();

                        break;

                    case MotionEvent.ACTION_MOVE:

                        float offsetX = event.getRawX() - move_orgX;
                        float offsetY = event.getRawY() - move_orgY;
                        StickerView.this.setX(StickerView.this.getX()+offsetX);
                        StickerView.this.setY(StickerView.this.getY() + offsetY);
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }

            }else if(view.getTag().equals("iv_scale")){
                switch (event.getAction()) {


                    case MotionEvent.ACTION_DOWN:




                        this_orgX = StickerView.this.getX();
                        this_orgY = StickerView.this.getY();

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();
                        scale_orgWidth = StickerView.this.getLayoutParams().width;
                        scale_orgHeight = StickerView.this.getLayoutParams().height;

                        rotate_orgX = event.getRawX();
                        rotate_orgY = event.getRawY();

                        centerX = StickerView.this.getX()+
                                ((View)StickerView.this.getParent()).getX()+
                                (float)StickerView.this.getWidth()/2;


                        //double statusBarHeight = Math.ceil(25 * getContext().getResources().getDisplayMetrics().density);
                        int result = 0;
                        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                        if (resourceId > 0) {
                            result = getResources().getDimensionPixelSize(resourceId);
                        }
                        double statusBarHeight = result;
                        centerY = StickerView.this.getY()+
                                ((View)StickerView.this.getParent()).getY()+
                                statusBarHeight+
                                (float)StickerView.this.getHeight()/2;

                        //-----------------------------------------------



                        //rotate
//                        double tx = event.getRawX() - centerX, ty = event.getRawY() - centerY;
//                        double t_length = Math.sqrt(tx*tx + ty*ty);
//                        double a = Math.acos(ty / t_length);

                     //   double angle = Math.atan2(event.getRawY() - centerY, event.getRawX() - centerX) * 180 / Math.PI;


                        //setRotation((float) angle - 45);
//                        Log.i("my","angle - > "+a);
//                        setRotation((float) a);


//                        onRotating();



//                        scale_orgX = event.getRawX();
//                        scale_orgY = event.getRawY();
//
//                        postInvalidate();
//                        requestLayout();





                        break;
                    case MotionEvent.ACTION_MOVE:


                        rotate_newX = event.getRawX();
                        rotate_newY = event.getRawY();

                        double angle_diff = Math.abs(
                                Math.atan2(event.getRawY() - scale_orgY , event.getRawX() - scale_orgX)
                                        - Math.atan2(scale_orgY - centerY, scale_orgX - centerX))*180/ Math.PI;


                        double length1 = getLength(centerX, centerY, scale_orgX, scale_orgY);
                        double length2 = getLength(centerX, centerY, event.getRawX(), event.getRawY());

                        int size = convertDpToPixel(SELF_SIZE_DP, getContext());
                        if(length2 > length1
                                && (angle_diff < 25 || Math.abs(angle_diff-180)<25)
                                ) {
                            //scale up
                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            StickerView.this.getLayoutParams().width += offset;
                            StickerView.this.getLayoutParams().height += offset;
                            onScaling(true);
                            //DraggableViewGroup.this.setX((float) (getX() - offset / 2));
                            //DraggableViewGroup.this.setY((float) (getY() - offset / 2));
                        }else if(length2 < length1
                                && (angle_diff < 25 || Math.abs(angle_diff-180)<25)
                                && StickerView.this.getLayoutParams().width > size/2
                                && StickerView.this.getLayoutParams().height > size/2) {
                            //scale down
                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            StickerView.this.getLayoutParams().width -= offset;
                            StickerView.this.getLayoutParams().height -= offset;
                            onScaling(false);
                        }

                        //rotate

                        double  angle = Math.atan2(event.getRawY() - centerY, event.getRawX() - centerX) * 180 / Math.PI;
                        setRotation((float) angle - 45);

                        onRotating();

                        rotate_orgX = rotate_newX;
                        rotate_orgY = rotate_newY;

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();

                        postInvalidate();
                        requestLayout();
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
            }
            return true;
        }
    };


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private double getLength(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(y2-y1, 2)+ Math.pow(x2-x1, 2));
    }

    private float[] getRelativePos(float absX, float absY){
        Log.v("ken", "getRelativePos getX:"+((View)this.getParent()).getX());
        Log.v("ken", "getRelativePos getY:"+((View)this.getParent()).getY());
        float [] pos = new float[]{
                absX-((View)this.getParent()).getX(),
                absY-((View)this.getParent()).getY()
        };
        Log.v(TAG, "getRelativePos absY:"+absY);
        Log.v(TAG, "getRelativePos relativeY:"+pos[1]);
        return pos;
    }



    protected View getImageViewFlip(){
        return iv_flip;
    }

    protected void onScaling(boolean scaleUp){}

    protected void onRotating(){}

    private class BorderView extends View {

        public BorderView(Context context) {
            super(context);
        }

        public BorderView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public BorderView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // Draw sticker border

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)this.getLayoutParams();


            Rect border = new Rect();
            border.left = (int)this.getLeft()-params.leftMargin;
            border.top = (int)this.getTop()-params.topMargin;
            border.right = (int)this.getRight()-params.rightMargin;
            border.bottom = (int)this.getBottom()-params.bottomMargin;
            Paint borderPaint = new Paint();
            borderPaint.setStrokeWidth(6);
            borderPaint.setColor(Color.WHITE);
            borderPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(border, borderPaint);

        }
    }

    private static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int)px;
    }

    public void setControlsVisibility(boolean isVisible) {
        if(!isVisible) {
            iv_border.setVisibility(View.GONE);
            iv_delete.setVisibility(View.GONE);
            iv_flip.setVisibility(View.GONE);
            iv_scale.setVisibility(View.GONE);
            iv_ok.setVisibility(View.GONE);
        }else{
            iv_border.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.VISIBLE);
            iv_flip.setVisibility(View.VISIBLE);
            iv_scale.setVisibility(View.VISIBLE);
            iv_ok.setVisibility(View.VISIBLE);
        }

    }

    public void setControlItemsHidden(boolean isHidden){
        if(isHidden) {
            iv_border.setVisibility(View.INVISIBLE);
            iv_scale.setVisibility(View.INVISIBLE);
            iv_delete.setVisibility(View.INVISIBLE);
            iv_flip.setVisibility(View.INVISIBLE);
            iv_ok.setVisibility(View.INVISIBLE);

        }else{
            iv_border.setVisibility(View.VISIBLE);
            iv_scale.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.VISIBLE);
            iv_flip.setVisibility(View.VISIBLE);
            iv_ok.setVisibility(View.VISIBLE);
        }
    }

    public void setControlItemsHiddenMytool(boolean isHidden){

       // Log.i("my","is Hidden -> "+isHidden);

        if(isHidden) {
            if(iv_border.getVisibility() == View.VISIBLE) {
                iv_border.setVisibility(View.INVISIBLE);
                iv_scale.setVisibility(View.INVISIBLE);
                iv_delete.setVisibility(View.INVISIBLE);
                iv_ok.setVisibility(View.INVISIBLE);
                iv_scale.setClickable(false);
            }
        }else{
            iv_border.setVisibility(View.VISIBLE);
            iv_scale.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.VISIBLE);
            iv_ok.setVisibility(View.VISIBLE);

            iv_scale.setClickable(true);
        }
    }


}
