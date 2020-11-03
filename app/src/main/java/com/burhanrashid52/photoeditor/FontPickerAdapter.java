package com.burhanrashid52.photoeditor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FontPickerAdapter extends RecyclerView.Adapter<FontPickerAdapter.ViewHolder> {
    private static final String TAG = "FontPickerAdapter";
    private Context context;
    private LayoutInflater inflater;
    private List<String> fontPickerFonts;
    private OnFontPickerClickListener onFontPickerClickListener;

    FontPickerAdapter(@NonNull Context context, @NonNull List<String> fontPickerFonts) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fontPickerFonts = fontPickerFonts;
    }

    FontPickerAdapter(@NonNull Context context) {
        this(context, getDefaultFonts(context));
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.font_picker_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Typeface tf = Typeface.createFromAsset(context.getAssets(),fontPickerFonts.get(position));
//        Typeface tf = ResourcesCompat.getFont(context, fontPickerFonts.get(position));
//        Log.e(TAG, "onBindViewHolder: " + tf );
//        holder.fontPickerView.setTypeface(tf);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPickerFonts.get(position));
        holder.fontPickerView.setTypeface(tf);
        holder.fontPickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFontPickerClickListener.onFontPickerClickListener(fontPickerFonts.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return fontPickerFonts.size();
    }

    private void buildColorPickerView(View view, int colorCode) {
        view.setVisibility(View.VISIBLE);

        ShapeDrawable biggerCircle = new ShapeDrawable(new OvalShape());
        biggerCircle.setIntrinsicHeight(20);
        biggerCircle.setIntrinsicWidth(20);
        biggerCircle.setBounds(new Rect(0, 0, 20, 20));
        biggerCircle.getPaint().setColor(colorCode);

        ShapeDrawable smallerCircle = new ShapeDrawable(new OvalShape());
        smallerCircle.setIntrinsicHeight(5);
        smallerCircle.setIntrinsicWidth(5);
        smallerCircle.setBounds(new Rect(0, 0, 5, 5));
        smallerCircle.getPaint().setColor(Color.WHITE);
        smallerCircle.setPadding(10, 10, 10, 10);
        Drawable[] drawables = {smallerCircle, biggerCircle};

        LayerDrawable layerDrawable = new LayerDrawable(drawables);

        view.setBackgroundDrawable(layerDrawable);
    }

    public void setOnFontPickerClickListener(OnFontPickerClickListener onFontPickerClickListener) {
        this.onFontPickerClickListener = onFontPickerClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView fontPickerView;

        public ViewHolder(View itemView) {
            super(itemView);
            fontPickerView = itemView.findViewById(R.id.font_picker_view);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onFontPickerClickListener != null)
//                        onFontPickerClickListener.onFontPickerClickListener(fontPickerFonts.get(getAdapterPosition()));
//                }
//            });
        }
    }

    public interface OnFontPickerClickListener {
        void onFontPickerClickListener(String colorCode);
    }

    public static List<String> getDefaultFonts(Context context) {
        ArrayList<String> colorPickerColors = new ArrayList<>();
        colorPickerColors.add("beyond_wonderland.ttf");
        colorPickerColors.add("emojione-android.ttf");
        colorPickerColors.add("roboto_black.ttf");
        colorPickerColors.add("roboto_blackitalic.ttf");
        colorPickerColors.add("roboto_bold.ttf");
        colorPickerColors.add("iloveyou.ttf");
        colorPickerColors.add("scrap_cursive.ttf");
        colorPickerColors.add("vniongdo.ttf");
        colorPickerColors.add("vnithufap.ttf");
        colorPickerColors.add("spring_time.ttf");
        colorPickerColors.add("alexbrush.ttf");
        return colorPickerColors;
    }
}
