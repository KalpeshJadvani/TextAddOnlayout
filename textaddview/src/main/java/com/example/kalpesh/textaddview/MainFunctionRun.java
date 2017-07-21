package com.example.kalpesh.textaddview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by kalpesh on 5/16/2017.
 */

public class MainFunctionRun {
    public static ListView FnameList;
    public static ListView FsizeList;
    public static Contenor_font contenor_font;
    public Activity mContext;

    public static int ObjNo=0;

    public static int CurrentObj;
    FrameLayout canvas;
    ArrayList<StickerTextView> stickerText = new ArrayList<StickerTextView>();
    FontManager fontManager;

    boolean cleanAll = true;

     public MainFunctionRun(Activity context, View view, FrameLayout frameLayout){
         mContext = context;
         canvas = frameLayout;
         FnameList = (ListView) view.findViewById(R.id.fontname_list);
         FsizeList = (ListView) view.findViewById(R.id.fontsize_list);

         fontManager = new FontManager(mContext.getApplicationContext());







         Contenor_fontSize contenor_fontSize = new Contenor_fontSize(mContext,fontManager.FontSize);

         FsizeList.setAdapter(contenor_fontSize);

         FsizeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 {
                     // TODO Auto-generated method stub
                     if(position==9){
                         stickerText.get(CurrentObj).setfontsize(400);
                     }else if(position==8) {
                         stickerText.get(CurrentObj).setfontsize(300);
                     }else if(position==7) {
                         stickerText.get(CurrentObj).setfontsize(150);
                     }else if(position==6) {
                         stickerText.get(CurrentObj).setfontsize(100);
                     }else if(position==5) {
                         stickerText.get(CurrentObj).setfontsize(50);
                     }else if(position==4) {
                         stickerText.get(CurrentObj).setfontsize(30);
                     }else {
                         stickerText.get(CurrentObj).setfontsize(fontManager.FontSize[position]);
                     }
                     View_fadout(FsizeList);



                 }
             }
         });





         view.findViewById(R.id.toolbar_bold).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if(stickerText.size()!=0)
                     stickerText.get(CurrentObj).setbold();
             }

         });


         view.findViewById(R.id.toolbar_italic).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(stickerText.size()!=0)
                     stickerText.get(CurrentObj).setitalic();
             }
         });

         view.findViewById(R.id.toolbar_underline).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(stickerText.size()!=0)
                     stickerText.get(CurrentObj).setunderline();

             }
         });

         view.findViewById(R.id.toolbar_font).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if(stickerText.size()!=0) {

                     if (FnameList.getVisibility() != View.VISIBLE) {
                         View_fadin(FnameList);
                     } else {

                         View_fadout(FnameList);
                     }
                 }

             }
         });

         view.findViewById(R.id.toolbar_fontcolor).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if(stickerText.size()!=0)
                     stickerText.get(CurrentObj).setMyColor(mContext);
             }
         });

         view.findViewById(R.id.toolbar_fontsize).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(stickerText.size()!=0){
                     if(FsizeList.getVisibility()!= View.VISIBLE){

                         View_fadin(FsizeList);

                     }else {


                         View_fadout(FsizeList);
                     }
                 }
             }
         });


         View_fadin(FsizeList);
         View_fadout(FsizeList);
         View_fadin(FnameList);
         View_fadout(FnameList);
     }


     public void assingFontObj(){
         contenor_font = new Contenor_font(mContext, fontManager.FontName,fontManager.FontName_With_Path);
         FnameList.setAdapter(contenor_font);
         FnameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 {
                     Typeface typeface = fontManager.FontName_With_Path.get(position);
                     stickerText.get(CurrentObj).setTface(typeface);
                     FnameList.setVisibility(View.INVISIBLE);

                 }
             }
         });

     }


     public boolean isBackTrue(){
         if(FsizeList.getVisibility()!= View.VISIBLE && FnameList.getVisibility()!= View.VISIBLE &&  ObjNo == 0) {
             return true;
         }else {
             return false;
         }
     }

     public void onBack(){

         if(FsizeList.getVisibility()== View.VISIBLE){
             View_fadout(FsizeList);
         }else if(FnameList.getVisibility()== View.VISIBLE){
             View_fadout(FnameList);
         }else {
             //View_fadout(horizontalScrollView);
             CurrentObj = 1000;
             RemoveBordeAllObjects();
             stickerText.clear();
             canvas.removeAllViews();
             ObjNo = 0;
         }

     }



    public void DoneAll(){
        CurrentObj=1000;
        RemoveBordeAllObjects();

    }

    public void Removall(){

        stickerText.clear();
        canvas.removeAllViews();
        ObjNo=0;
    }



    public void ObjectNo(int no) {
        CurrentObj = no;
        RemoveBordeAllObjects();
    }


      public void  addnewtext(){

//          stickerText.add(ObjNo, new StickerTextView(mContext.getBaseContext(),mContext,ObjNo));
//          canvas.addView(stickerText.get(ObjNo));
//          CurrentObj = ObjNo;
//          RemoveBordeAllObjects();
//          ObjNo++;

          stickerText.add(ObjNo, new StickerTextView(mContext.getBaseContext(),mContext,ObjNo));
          canvas.addView(stickerText.get(ObjNo));
          CurrentObj=ObjNo;
          RemoveBordeAllObjects();
          stickerText.get(ObjNo).setDobbleClick();
          ObjNo++;

      }

    public void RemoveBordeAllObjects(){

        for(int i=0;i<stickerText.size();i++){
            if( CurrentObj == i )
            {
                stickerText.get(i).setControlItemsHiddenMytool(false);
            }else {
                stickerText.get(i).setControlItemsHiddenMytool(true);
            }
        }

    }


    public void View_fadout(View view){
        final View view1=view;
        view1.animate()
                .translationY(view1.getHeight())
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view1.setVisibility(View.INVISIBLE);
                    }
                });

    }

    public void View_fadin(View view){
        final View view1=view;
        view1.animate()
                .translationY(0)
                .alpha(1.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationEnd(animation);
                        view1.setVisibility(View.VISIBLE);
                    }
                });
    }



    public class FontManager {
        public  final char EXTENSION_SEPARATOR = '.';
        /**
         * The Unix separator character.
         */
        private  final char UNIX_SEPARATOR = '/';
        /**
         * The Windows separator character.
         */
        private  final char WINDOWS_SEPARATOR = '\\';

        public ArrayList<String> FontName = new ArrayList<String>();
        public ArrayList<Typeface> FontName_With_Path = new ArrayList<Typeface>();

        public  int[] FontSize;
        public  Context Mcontext;
        private Map<String, String> ASSET_FONTS_BY_NAME = new TreeMap<String, String>();
        private Map<String, String> TempFont = new TreeMap<String, String>();

        public FontManager(Context context) {
            Mcontext = context;
            FontSize = new int[10];
            FontSize[0] = 10;
            FontSize[1] = 12;
            FontSize[2] = 14;
            FontSize[3] = 16;
            FontSize[4] = 18;
            FontSize[5] = 20;
            FontSize[6] = 24;
            FontSize[7] = 28;
            FontSize[8] = 32;
            FontSize[9] = 36;
                /*
         * Fonts from the assets folder
         */
            new AsyncTaskForFont().execute();
        }
        public  class AsyncTaskForFont extends AsyncTask<String, String, Void> {

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Void doInBackground(String... strings) {

                Map<String, String> assetFonts = getAssetFonts(Mcontext);
                AssetManager assets = Mcontext.getResources().getAssets();
                for (String fontName : assetFonts.keySet()) {
                    try {
                        FontName.add(fontName);
                    }
                    catch (Exception e) {
                        // this can happen if we don't have access to the font or it's not a font or...
                    }
                }

                for (int i=0;i<FontName.size();i++) {
                    try {
                        Typeface typeface = Typeface.createFromAsset(assets, assetFonts.get(FontName.get(i)));
                        FontName_With_Path.add(i,typeface);
                    }

                    catch (Exception e) {
                        // this can happen if we don't have access to the font or it's not a font or...
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                assingFontObj();

            }

            @Override
            protected void onCancelled() {


            }

        }

        private  Map<String, String> getAssetFonts(Context context) {

            synchronized (ASSET_FONTS_BY_NAME) {
            /*
             * Let's do this only once because it's expensive and the result won't change in any case.
             */
                if (ASSET_FONTS_BY_NAME.isEmpty()) {

                    //Collection<String> fontFiles = AssetIndex.getAssetIndex(Mcontext);
                    Collection<String> fontFiles=null;

                    if (fontFiles == null || fontFiles.isEmpty()) {
                        fontFiles = listFontFiles(Mcontext);

                    }

                    for (String filePath : fontFiles) {

                        if (filePath.toLowerCase(Locale.getDefault()).endsWith("ttf")) {
                            String fontName = removeExtension(filePath);
                            ASSET_FONTS_BY_NAME.put(fontName, filePath);
                        }
                    }

                }

                return ASSET_FONTS_BY_NAME;
            }
        }

        public  String removeExtension(String filename) {
            if (filename == null) {
                return null;
            }
            int index = indexOfExtension(filename);
            if (index == -1) {
                return filename;
            } else {
                return filename.substring(0, index);
            }
        }
        public  int indexOfExtension(String filename) {
            if (filename == null) {
                return -1;
            }
            int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
            int lastSeparator = indexOfLastSeparator(filename);
            return lastSeparator > extensionPos ? -1 : extensionPos;
        }

        public  Collection<String> listFontFiles(Context res) {
            Collection<String> fonts = new ArrayList<String>();

            listFontFiles(res.getResources().getAssets(), fonts, "");

            return fonts;
        }

        public  int indexOfLastSeparator(String filename) {
            if (filename == null) {
                return -1;
            }
            int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
            int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
            return Math.max(lastUnixPos, lastWindowsPos);
        }
        private  void listFontFiles(AssetManager assets, Collection<String> fonts, String path) {
            try {
                String[] list = assets.list(path);
                if (list != null && list.length > 0) {
                    // it's a folder
                    for (String file : list) {
                        String prefix = "".equals(path) ? "" : path + File.separator;
                        listFontFiles(assets, fonts, prefix + file);
                    }
                } else if (path.endsWith("ttf")) {
                    // it's a font file
                    fonts.add(path);
                }
            } catch (IOException ignore) {
            }
        }

    }



}
