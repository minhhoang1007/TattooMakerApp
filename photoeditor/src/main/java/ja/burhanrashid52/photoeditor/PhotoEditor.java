package ja.burhanrashid52.photoeditor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.UiThread;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**
 * <p>
 * This class in initialize by {@link PhotoEditor.Builder} using a builder pattern with multiple
 * editing attributes
 * </p>
 *
 * @author <a href="https://github.com/burhanrashid52">Burhanuddin Rashid</a>
 * @version 0.1.1
 * @since 18/01/2017
 */
public class PhotoEditor implements BrushViewChangeListener {

    private static final String TAG = "PhotoEditor";
    public static float des;
    private final LayoutInflater mLayoutInflater;
    private Context context;
    private PhotoEditorView parentView;
    private ImageView imageView;
    private ImageView imageViewAdd;
    private View deleteView;
    private BrushDrawingView brushDrawingView;
    private List<View> addedViews;
    private List<View> redoViews;
    private OnPhotoEditorListener mOnPhotoEditorListener;
    private boolean isTextPinchZoomable;
    private Typeface mDefaultTextTypeface;
    private Typeface mDefaultEmojiTypeface;
    private int mActivePointerId = INVALID_POINTER_ID;
    private double mCurrAngle, mPrevAngle;
    public boolean isRotate = false;

    private PhotoEditor(Builder builder) {
        this.context = builder.context;
        this.parentView = builder.parentView;
        this.imageView = builder.imageView;
        this.deleteView = builder.deleteView;
        this.brushDrawingView = builder.brushDrawingView;
        this.isTextPinchZoomable = builder.isTextPinchZoomable;
        this.mDefaultTextTypeface = builder.textTypeface;
        this.mDefaultEmojiTypeface = builder.emojiTypeface;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        brushDrawingView.setBrushViewChangeListener(this);
        addedViews = new ArrayList<>();
        redoViews = new ArrayList<>();
    }

    public void addImage(Bitmap desiredImage) {
        final View imageRootView = getLayout(ViewType.IMAGE);
//        final ImageView imageView = imageRootView.findViewById(R.id.imgPhotoEditorImage);
        imageViewAdd = imageRootView.findViewById(R.id.imgPhotoEditorImage);
        final FrameLayout frmBorder = imageRootView.findViewById(R.id.frmBorder);
        final ImageView imgClose = imageRootView.findViewById(R.id.imgPhotoEditorClose);
        final ImageView imgFlip = imageRootView.findViewById(R.id.imgPhotoEditorFlip);
        final ImageView imgResize = imageRootView.findViewById(R.id.imgPhotoEditorResize);
//        final ImageView imgRotate = imageRootView.findViewById(R.id.imgPhotoEditorRotate);
        imageViewAdd.setImageBitmap(desiredImage);
        des = imageRootView.getContext().getResources().getDisplayMetrics().density;
        MultiTouchListener multiTouchListener = getMultiTouchListener(imageRootView);
        multiTouchListener.setOnGestureControl(new MultiTouchListener.OnGestureControl() {
            @Override
            public void onClick() {
                boolean isBackgroundVisible = frmBorder.getTag() != null && (boolean) frmBorder.getTag();
                frmBorder.setBackgroundResource(isBackgroundVisible ? 0 : R.drawable.rounded_border_tv);
                imgClose.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                imgFlip.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                imgResize.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
//                imgRotate.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                frmBorder.setTag(!isBackgroundVisible);
            }

            @Override
            public void onLongClick() {

            }
        });

        imageRootView.setOnTouchListener(multiTouchListener);
        imgFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageViewAdd.getScaleX() == 1)
                    imageViewAdd.setScaleX(-1);
                else imageViewAdd.setScaleX(1);
//                setAlpha(imageView, 0.2f);

            }
        });
        addViewToParent(imageRootView, ViewType.IMAGE);
        imgResize.setOnTouchListener(new View.OnTouchListener() {
            float centerX, centerY, startR, startScale, startX, startY;
            View deleteView = imageRootView.findViewById(R.id.imgPhotoEditorClose);
            View flipView = imageRootView.findViewById(R.id.imgPhotoEditorFlip);
            View scaleView = imageRootView.findViewById(R.id.imgPhotoEditorResize);
//            View rotateView = imageRootView.findViewById(R.id.imgPhotoEditorRotate);

            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {

                    // calculate center of image
                    centerX = (imageRootView.getLeft() + imageRootView.getRight()) / 10f;
                    centerY = (imageRootView.getTop() + imageRootView.getBottom()) / 10f;

                    // recalculate coordinates of starting point
                    startX = e.getRawX() - imgResize.getX() - centerX;
                    startY = e.getRawY() - imgResize.getY() - centerY;

                    // get starting distance and scale
                    startR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);
                    startScale = imageRootView.getScaleX();
                    Log.e(TAG, "onTouch: 123");
                } else if (e.getAction() == MotionEvent.ACTION_MOVE) {

                    // calculate new distance
                    float newR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);

                    // set new scale
                    float newScale = newR / startR * startScale;
                    imageRootView.setScaleX(newScale);
                    imageRootView.setScaleY(newScale);
                    if (deleteView != null) {
                        deleteView.setScaleX(1 / newScale);
                        deleteView.setScaleY(1 / newScale);
                    }
                    if (flipView != null) {
                        flipView.setScaleX(1 / newScale);
                        flipView.setScaleY(1 / newScale);
                    }
                    if (scaleView != null) {
                        scaleView.setScaleX(1 / newScale);
                        scaleView.setScaleY(1 / newScale);
                    }
//                    if (rotateView != null) {
//                        rotateView.setScaleX(1 / newScale);
//                        rotateView.setScaleY(1 / newScale);
//                    }
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins((int) (15 * des / newScale), (int) (15 * des / newScale), (int) (15 * des / newScale), (int) (15 * des / newScale));
                    frmBorder.setLayoutParams(layoutParams);
                    // move handler image
//                    imgResize.setX(centerX + emojiRootView.getWidth()/2f * newScale);
//                    imgResize.setY(centerY + emojiRootView.getHeight()/2f * newScale);
                    Log.e(TAG, "onTouch: 1234");
                } else if (e.getAction() == MotionEvent.ACTION_UP) {

                }
                imgResize.setVisibility(View.VISIBLE);
                return true;
            }
        });
//        imgRotate.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                final float xc = imageRootView.getWidth() / 2;
//                final float yc = imageRootView.getHeight() / 2;
//                final float x = event.getX();
//                final float y = event.getY();
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
////                        imageRootView.clearAnimation();
////                        double mCurrAngle = Math.toDegrees(Math.atan2(x - xc, yc - y));
//                        break;
//                    }
//                    case MotionEvent.ACTION_MOVE: {
//                        //mCurrAngle = Math.toDegrees(Math.atan2(x - xc, yc - y));
//                        mCurrAngle = Math.toDegrees(Math.atan(x-xc));
//                        mPrevAngle = Math.toDegrees(Math.atan(y-yc));
//                        //mPrevAngle = mCurrAngle;
//                        animate(imageRootView, mPrevAngle, mCurrAngle, 1500);
//                        break;
//                    }
//                    case MotionEvent.ACTION_UP: {
//                        mPrevAngle = mCurrAngle = 0;
//                        break;
//                    }
//                }
//                return true;
//            }
//        });
    }

    private void animate(View view, double fromDegrees, double toDegrees, long durationMillis) {
        final RotateAnimation rotate = new RotateAnimation((float) fromDegrees, (float) toDegrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(durationMillis);
        rotate.setFillEnabled(true);
        rotate.setFillAfter(true);
        view.startAnimation(rotate);
    }

    //    private static void adjustTranslation(View view, float deltaX, float deltaY) {
//        Log.e("adjustTranslation", "adjustTranslation: " + deltaX + "-----" + deltaY);
//        float[] deltaVector = {deltaX, deltaY};
//        view.getMatrix().mapVectors(deltaVector);
//        view.setTranslationX(view.getTranslationX() + deltaVector[0]);
//        view.setTranslationY(view.getTranslationY() + deltaVector[1]);
//    }
    @SuppressLint("ClickableViewAccessibility")
    public void addText(String text, final int colorCodeTextView) {
        addText(null, text, colorCodeTextView);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addText(@Nullable Typeface textTypeface, String text, final int colorCodeTextView) {
        final TextStyleBuilder styleBuilder = new TextStyleBuilder();

        styleBuilder.withTextColor(colorCodeTextView);
        if (textTypeface != null) {
            styleBuilder.withTextFont(textTypeface);
        }

        addText(text, styleBuilder);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void addText(String text, @Nullable TextStyleBuilder styleBuilder) {
        brushDrawingView.setBrushDrawingMode(false);
        final View textRootView = getLayout(ViewType.TEXT);
        final TextView textInputTv = textRootView.findViewById(R.id.tvPhotoEditorText);
        final ImageView imgClose = textRootView.findViewById(R.id.imgPhotoEditorClose);
        final ImageView imgFlip = textRootView.findViewById(R.id.imgPhotoEditorFlip);
        final ImageView imgResize = textRootView.findViewById(R.id.imgPhotoEditorResize);
//        final ImageView imgRotate = textRootView.findViewById(R.id.imgPhotoEditorRotate);
        final FrameLayout frmBorder = textRootView.findViewById(R.id.frmBorder);
        des = textRootView.getContext().getResources().getDisplayMetrics().density;
        textInputTv.setText(text);
        if (styleBuilder != null)
            styleBuilder.applyStyle(textInputTv);

        MultiTouchListener multiTouchListener = getMultiTouchListener(textRootView);
        multiTouchListener.setOnGestureControl(new MultiTouchListener.OnGestureControl() {
            @Override
            public void onClick() {
                boolean isBackgroundVisible = frmBorder.getTag() != null && (boolean) frmBorder.getTag();
                frmBorder.setBackgroundResource(isBackgroundVisible ? 0 : R.drawable.rounded_border_tv);
                imgClose.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                imgFlip.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                imgResize.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
//                imgRotate.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                frmBorder.setTag(!isBackgroundVisible);
            }

            @Override
            public void onLongClick() {
                String textInput = textInputTv.getText().toString();
                int currentTextColor = textInputTv.getCurrentTextColor();
                if (mOnPhotoEditorListener != null) {
                    mOnPhotoEditorListener.onEditTextChangeListener(textRootView, textInput, currentTextColor);
                }
            }
        });
        //multiTouchListener.onTouch(textRootView, imgResize.onTouchEvent());

        textRootView.setOnTouchListener(multiTouchListener);
        imgFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textInputTv.getScaleX() == 1)
                    textInputTv.setScaleX(-1);
                else textInputTv.setScaleX(1);

            }
        });
        addViewToParent(textRootView, ViewType.TEXT);
        imgResize.setOnTouchListener(new View.OnTouchListener() {
            float centerX, centerY, startR, startScale, startX, startY;
            View deleteView = textRootView.findViewById(R.id.imgPhotoEditorClose);
            View flipView = textRootView.findViewById(R.id.imgPhotoEditorFlip);
            View scaleView = textRootView.findViewById(R.id.imgPhotoEditorResize);
//            View rotateView = textRootView.findViewById(R.id.imgPhotoEditorRotate);

            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {

                    // calculate center of image
                    centerX = (textRootView.getLeft() + textRootView.getRight()) / 10f;
                    centerY = (textRootView.getTop() + textRootView.getBottom()) / 10f;

                    // recalculate coordinates of starting point
                    startX = e.getRawX() - imgResize.getX() - centerX;
                    startY = e.getRawY() - imgResize.getY() - centerY;

                    // get starting distance and scale
                    startR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);
                    startScale = textRootView.getScaleX();
                    Log.e(TAG, "onTouch: 123");
                } else if (e.getAction() == MotionEvent.ACTION_MOVE) {

                    // calculate new distance
                    float newR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);

                    // set new scale
                    float newScale = newR / startR * startScale;
                    textRootView.setScaleX(newScale);
                    textRootView.setScaleY(newScale);
                    if (deleteView != null) {
                        deleteView.setScaleX(1 / newScale);
                        deleteView.setScaleY(1 / newScale);
                    }
                    if (flipView != null) {
                        flipView.setScaleX(1 / newScale);
                        flipView.setScaleY(1 / newScale);
                    }
                    if (scaleView != null) {
                        scaleView.setScaleX(1 / newScale);
                        scaleView.setScaleY(1 / newScale);
                    }
//                    if (rotateView != null) {
//                        rotateView.setScaleX(1 / newScale);
//                        rotateView.setScaleY(1 / newScale);
//                    }
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins((int) (15 * des / newScale), (int) (15 * des / newScale), (int) (15 * des / newScale), (int) (15 * des / newScale));
                    frmBorder.setLayoutParams(layoutParams);
                    // move handler image
//                    imgResize.setX(centerX + textRootView.getWidth()/2f * newScale);
//                    imgResize.setY(centerY + textRootView.getHeight()/2f * newScale);
                    Log.e(TAG, "onTouch: 1234");
                } else if (e.getAction() == MotionEvent.ACTION_UP) {

                }

                return true;
            }

        });
//        imgRotate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isRotate == false) {
//                    textInputTv.setRotation(180);
//                    isRotate = true;
//                } else {
//                    textInputTv.setRotation(0);
//                    isRotate = false;
//                }
//
//            }
//        });
    }

    /**
     * This will update text and color on provided view
     *
     * @param view      view on which you want update
     * @param inputText text to update {@link TextView}
     * @param colorCode color to update on {@link TextView}
     */
    public void editText(@NonNull View view, String inputText, @NonNull int colorCode) {
        editText(view, null, inputText, colorCode);
    }

    /**
     * This will update the text and color on provided view
     *
     * @param view         root view where text view is a child
     * @param textTypeface update typeface for custom font in the text
     * @param inputText    text to update {@link TextView}
     * @param colorCode    color to update on {@link TextView}
     */
    public void editText(@NonNull View view, @Nullable Typeface textTypeface, String inputText, @NonNull int colorCode) {
        final TextStyleBuilder styleBuilder = new TextStyleBuilder();
        styleBuilder.withTextColor(colorCode);
        if (textTypeface != null) {
            styleBuilder.withTextFont(textTypeface);
        }

        editText(view, inputText, styleBuilder);
    }

    /**
     * This will update the text and color on provided view
     *
     * @param view         root view where text view is a child
     * @param inputText    text to update {@link TextView}
     * @param styleBuilder style to apply on {@link TextView}
     */
    public void editText(@NonNull View view, String inputText, @Nullable TextStyleBuilder styleBuilder) {
        TextView inputTextView = view.findViewById(R.id.tvPhotoEditorText);
        if (inputTextView != null && addedViews.contains(view) && !TextUtils.isEmpty(inputText)) {
            inputTextView.setText(inputText);
            if (styleBuilder != null)
                styleBuilder.applyStyle(inputTextView);
            parentView.updateViewLayout(view, view.getLayoutParams());
            int i = addedViews.indexOf(view);
            if (i > -1) addedViews.set(i, view);
        }
    }

    /**
     * Adds emoji to the {@link PhotoEditorView} which you drag,rotate and scale using pinch
     * if {@link PhotoEditor.Builder#setPinchTextScalable(boolean)} enabled
     *
     * @param emojiName unicode in form of string to display emoji
     */
    public void addEmoji(String emojiName) {
        addEmoji(null, emojiName);
    }

    /**
     * Adds emoji to the {@link PhotoEditorView} which you drag,rotate and scale using pinch
     * if {@link PhotoEditor.Builder#setPinchTextScalable(boolean)} enabled
     *
     * @param emojiTypeface typeface for custom font to show emoji unicode in specific font
     * @param emojiName     unicode in form of string to display emoji
     */
    public void addEmoji(Typeface emojiTypeface, String emojiName) {
        brushDrawingView.setBrushDrawingMode(false);
        final View emojiRootView = getLayout(ViewType.EMOJI);
        final TextView emojiTextView = emojiRootView.findViewById(R.id.tvPhotoEditorText);
        final FrameLayout frmBorder = emojiRootView.findViewById(R.id.frmBorder);
        final ImageView imgClose = emojiRootView.findViewById(R.id.imgPhotoEditorClose);
        final ImageView imgFlip = emojiRootView.findViewById(R.id.imgPhotoEditorFlip);
        final ImageView imgResize = emojiRootView.findViewById(R.id.imgPhotoEditorResize);
//        final ImageView imgRotate = emojiRootView.findViewById(R.id.imgPhotoEditorRotate);
        des = emojiRootView.getContext().getResources().getDisplayMetrics().density;
        if (emojiTypeface != null) {
            emojiTextView.setTypeface(emojiTypeface);
        }
        emojiTextView.setTextSize(56);
        emojiTextView.setText(emojiName);
        MultiTouchListener multiTouchListener = getMultiTouchListener(emojiRootView);
        multiTouchListener.setOnGestureControl(new MultiTouchListener.OnGestureControl() {
            @Override
            public void onClick() {
                boolean isBackgroundVisible = frmBorder.getTag() != null && (boolean) frmBorder.getTag();
                frmBorder.setBackgroundResource(isBackgroundVisible ? 0 : R.drawable.rounded_border_tv);
                imgClose.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                imgFlip.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                imgResize.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
//                imgRotate.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                frmBorder.setTag(!isBackgroundVisible);
            }

            @Override
            public void onLongClick() {
            }
        });
        emojiRootView.setOnTouchListener(multiTouchListener);
        imgResize.setOnTouchListener(new View.OnTouchListener() {
            float centerX, centerY, startR, startScale, startX, startY;
            View deleteView = emojiRootView.findViewById(R.id.imgPhotoEditorClose);
            View flipView = emojiRootView.findViewById(R.id.imgPhotoEditorFlip);
            View scaleView = emojiRootView.findViewById(R.id.imgPhotoEditorResize);
//            View rotateView = emojiRootView.findViewById(R.id.imgPhotoEditorRotate);

            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {

                    // calculate center of image
                    centerX = (emojiRootView.getLeft() + emojiRootView.getRight()) / 10f;
                    centerY = (emojiRootView.getTop() + emojiRootView.getBottom()) / 10f;

                    // recalculate coordinates of starting point
                    startX = e.getRawX() - imgResize.getX() - centerX;
                    startY = e.getRawY() - imgResize.getY() - centerY;

                    // get starting distance and scale
                    startR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);
                    startScale = emojiRootView.getScaleX();
                    Log.e(TAG, "onTouch: 123");
                } else if (e.getAction() == MotionEvent.ACTION_MOVE) {

                    // calculate new distance
                    float newR = (float) Math.hypot(e.getRawX() - startX, e.getRawY() - startY);

                    // set new scale
                    float newScale = newR / startR * startScale;
                    emojiRootView.setScaleX(newScale);
                    emojiRootView.setScaleY(newScale);
                    if (deleteView != null) {
                        deleteView.setScaleX(1 / newScale);
                        deleteView.setScaleY(1 / newScale);
                    }
                    if (flipView != null) {
                        flipView.setScaleX(1 / newScale);
                        flipView.setScaleY(1 / newScale);
                    }
                    if (scaleView != null) {
                        scaleView.setScaleX(1 / newScale);
                        scaleView.setScaleY(1 / newScale);
                    }
//                    if (rotateView != null) {
//                        rotateView.setScaleX(1 / newScale);
//                        rotateView.setScaleY(1 / newScale);
//                    }
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins((int) (15 * des / newScale), (int) (15 * des / newScale), (int) (15 * des / newScale), (int) (15 * des / newScale));
                    frmBorder.setLayoutParams(layoutParams);
                    // move handler image
//                    imgResize.setX(centerX + emojiRootView.getWidth()/2f * newScale);
//                    imgResize.setY(centerY + emojiRootView.getHeight()/2f * newScale);
                    Log.e(TAG, "onTouch: 1234");
                } else if (e.getAction() == MotionEvent.ACTION_UP) {

                }
                imgResize.setVisibility(View.VISIBLE);
                return true;
            }
        });
//        imgRotate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isRotate == false) {
//                    emojiTextView.setRotation(180);
//                    isRotate = true;
//                } else {
//                    emojiTextView.setRotation(0);
//                    isRotate = false;
//                }
//
//            }
//        });
        imgFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emojiTextView.getScaleX() == 1)
                    emojiTextView.setScaleX(-1);
                else emojiTextView.setScaleX(1);

            }
        });
        addViewToParent(emojiRootView, ViewType.EMOJI);

    }


    /**
     * Add to root view from image,emoji and text to our parent view
     *
     * @param rootView rootview of image,text and emoji
     */
    private void addViewToParent(View rootView, ViewType viewType) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        parentView.addView(rootView, params);
        addedViews.add(rootView);
        if (mOnPhotoEditorListener != null)
            mOnPhotoEditorListener.onAddViewListener(viewType, addedViews.size());
    }

    /**
     * Create a new instance and scalable touchview
     *
     * @return scalable multitouch listener
     */
    @NonNull
    private MultiTouchListener getMultiTouchListener(View rootView) {
        MultiTouchListener multiTouchListener = new MultiTouchListener(
                rootView,
                parentView,
                this.imageView,
                isTextPinchZoomable,
                mOnPhotoEditorListener);

        //multiTouchListener.setOnMultiTouchListener(this);

        return multiTouchListener;
    }

    /**
     * Get root view by its type i.e image,text and emoji
     *
     * @param viewType image,text or emoji
     * @return rootview
     */
    private View getLayout(final ViewType viewType) {
        View rootView = null;
        switch (viewType) {
            case TEXT:
                rootView = mLayoutInflater.inflate(R.layout.view_photo_editor_text, null);
                final TextView txtText = rootView.findViewById(R.id.tvPhotoEditorText);
                if (txtText != null && mDefaultTextTypeface != null) {
                    txtText.setGravity(Gravity.CENTER);
                    if (mDefaultEmojiTypeface != null) {
                        txtText.setTypeface(mDefaultTextTypeface);
                    }
                }
//                ImageView flip = rootView.findViewById(R.id.imgPhotoEditorFlip);
//                if (flip != null)
//                    flip.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            txtText.setScaleX(-1);
//                        }
//                    });
                break;
            case IMAGE: {
                rootView = mLayoutInflater.inflate(R.layout.view_photo_editor_image, null);
                final ImageView imgSticker = rootView.findViewById(R.id.imgPhotoEditorImage);
                //flip
//                ImageView imgFlip = rootView.findViewById(R.id.imgPhotoEditorFlip);
//                if (imgFlip != null)
//                    imgFlip.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            imgSticker.setScaleX(-1);
//                        }
//                    });

            }
            break;
            case EMOJI: {
                rootView = mLayoutInflater.inflate(R.layout.view_photo_editor_text, null);
                final TextView txtTextEmoji = rootView.findViewById(R.id.tvPhotoEditorText);
                if (txtTextEmoji != null) {
                    if (mDefaultEmojiTypeface != null) {
                        txtTextEmoji.setTypeface(mDefaultEmojiTypeface);
                    }
                    txtTextEmoji.setGravity(Gravity.CENTER);
                    txtTextEmoji.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }
//                ImageView imgFlip = rootView.findViewById(R.id.imgPhotoEditorFlip);
//                if (imgFlip != null)
//                    imgFlip.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            txtTextEmoji.setScaleX(-1);
//                        }
//                    });
                break;
            }
        }

        if (rootView != null) {
            //We are setting tag as ViewType to identify what type of the view it is
            //when we remove the view from stack i.e onRemoveViewListener(ViewType viewType, int numberOfAddedViews);
            rootView.setTag(viewType);
            final ImageView imgClose = rootView.findViewById(R.id.imgPhotoEditorClose);
            final View finalRootView = rootView;
            if (imgClose != null) {
                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewUndo(finalRootView, viewType);
                    }
                });
            }
            final ImageView imageViewFlip = rootView.findViewById(R.id.imgPhotoEditorFlip);
            if (imageViewFlip != null) {
                imageViewFlip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
            final ImageView imageViewResize = rootView.findViewById(R.id.imgPhotoEditorResize);
            if (imageViewResize != null) {
                imageViewResize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }
        return rootView;
    }

    /**
     * Enable/Disable drawing mode to draw on {@link PhotoEditorView}
     *
     * @param brushDrawingMode true if mode is enabled
     */
    public void setBrushDrawingMode(boolean brushDrawingMode) {
        if (brushDrawingView != null)
            brushDrawingView.setBrushDrawingMode(brushDrawingMode);
    }

    /**
     * @return true is brush mode is enabled
     */
    public Boolean getBrushDrawableMode() {
        return brushDrawingView != null && brushDrawingView.getBrushDrawingMode();
    }

    /**
     * set the size of bursh user want to paint on canvas i.e {@link BrushDrawingView}
     *
     * @param size size of brush
     */
    public void setBrushSize(float size) {
        if (brushDrawingView != null)
            brushDrawingView.setBrushSize(size);
    }

    /**
     * set opacity/transparency of brush while painting on {@link BrushDrawingView}
     *
     * @param opacity opacity is in form of percentage
     */
    public void setOpacity(@IntRange(from = 0, to = 100) int opacity) {
        if (brushDrawingView != null) {
            opacity = (int) ((opacity / 100.0) * 255.0);
            brushDrawingView.setOpacity(opacity);
        }
    }

    /**
     * set brush color which user want to paint
     *
     * @param color color value for paint
     */
    public void setBrushColor(@ColorInt int color) {
        if (brushDrawingView != null)
            brushDrawingView.setBrushColor(color);
    }

    /**
     * set the eraser size
     * <br></br>
     * <b>Note :</b> Eraser size is different from the normal brush size
     *
     * @param brushEraserSize size of eraser
     */
    public void setBrushEraserSize(float brushEraserSize) {
        if (brushDrawingView != null)
            brushDrawingView.setBrushEraserSize(brushEraserSize);
    }

    void setBrushEraserColor(@ColorInt int color) {
        if (brushDrawingView != null)
            brushDrawingView.setBrushEraserColor(color);
    }

    /**
     * @return provide the size of eraser
     * @see PhotoEditor#setBrushEraserSize(float)
     */
    public float getEraserSize() {
        return brushDrawingView != null ? brushDrawingView.getEraserSize() : 0;
    }

    /**
     * @return provide the size of eraser
     * @see PhotoEditor#setBrushSize(float)
     */
    public float getBrushSize() {
        if (brushDrawingView != null)
            return brushDrawingView.getBrushSize();
        return 0;
    }

    /**
     * @return provide the size of eraser
     * @see PhotoEditor#setBrushColor(int)
     */
    public int getBrushColor() {
        if (brushDrawingView != null)
            return brushDrawingView.getBrushColor();
        return 0;
    }

    /**
     * <p>
     * Its enables eraser mode after that whenever user drags on screen this will erase the existing
     * paint
     * <br>
     * <b>Note</b> : This eraser will work on paint views only
     * <p>
     */
    public void brushEraser() {
        if (brushDrawingView != null)
            brushDrawingView.brushEraser();
    }

    /*private void viewUndo() {
        if (addedViews.size() > 0) {
            parentView.removeView(addedViews.remove(addedViews.size() - 1));
            if (mOnPhotoEditorListener != null)
                mOnPhotoEditorListener.onRemoveViewListener(addedViews.size());
        }
    }*/

    private void viewUndo(View removedView, ViewType viewType) {
        if (addedViews.size() > 0) {
            if (addedViews.contains(removedView)) {
                parentView.removeView(removedView);
                addedViews.remove(removedView);
                redoViews.add(removedView);
                if (mOnPhotoEditorListener != null) {
                    mOnPhotoEditorListener.onRemoveViewListener(viewType, addedViews.size());
                }
            }
        }
    }

    /**
     * Undo the last operation perform on the {@link PhotoEditor}
     *
     * @return true if there nothing more to undo
     */
    public boolean undo() {
        if (addedViews.size() > 0) {
            View removeView = addedViews.get(addedViews.size() - 1);
            if (removeView instanceof BrushDrawingView) {
                return brushDrawingView != null && brushDrawingView.undo();
            } else {
                addedViews.remove(addedViews.size() - 1);
                parentView.removeView(removeView);
                redoViews.add(removeView);
            }
            if (mOnPhotoEditorListener != null) {
                Object viewTag = removeView.getTag();
                if (viewTag != null && viewTag instanceof ViewType) {
                    mOnPhotoEditorListener.onRemoveViewListener(((ViewType) viewTag), addedViews.size());
                }
            }
        }
        return addedViews.size() != 0;
    }

    public void setAlpha(float alpha) {
        imageViewAdd.setAlpha(alpha);
    }

    /**
     * Redo the last operation perform on the {@link PhotoEditor}
     *
     * @return true if there nothing more to redo
     */
    public boolean redo() {
        if (redoViews.size() > 0) {
            View redoView = redoViews.get(redoViews.size() - 1);
            if (redoView instanceof BrushDrawingView) {
                return brushDrawingView != null && brushDrawingView.redo();
            } else {
                redoViews.remove(redoViews.size() - 1);
                parentView.addView(redoView);
                addedViews.add(redoView);
            }
            Object viewTag = redoView.getTag();
            if (mOnPhotoEditorListener != null && viewTag != null && viewTag instanceof ViewType) {
                mOnPhotoEditorListener.onAddViewListener(((ViewType) viewTag), addedViews.size());
            }
        }
        return redoViews.size() != 0;
    }

    private void clearBrushAllViews() {
        if (brushDrawingView != null)
            brushDrawingView.clearAll();
    }

    /**
     * Removes all the edited operations performed {@link PhotoEditorView}
     * This will also clear the undo and redo stack
     */
    public void clearAllViews() {
        for (int i = 0; i < addedViews.size(); i++) {
            parentView.removeView(addedViews.get(i));
        }
        if (addedViews.contains(brushDrawingView)) {
            parentView.addView(brushDrawingView);
        }
        addedViews.clear();
        redoViews.clear();
        clearBrushAllViews();
    }

    /**
     * Remove all helper boxes from views
     */
    @UiThread
    public void clearHelperBox() {
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View childAt = parentView.getChildAt(i);
            FrameLayout frmBorder = childAt.findViewById(R.id.frmBorder);
            if (frmBorder != null) {
                frmBorder.setBackgroundResource(0);
            }
            ImageView imgClose = childAt.findViewById(R.id.imgPhotoEditorClose);
            ImageView imgFlip = childAt.findViewById(R.id.imgPhotoEditorFlip);
            ImageView imgResize = childAt.findViewById(R.id.imgPhotoEditorResize);
            if (imgClose != null & imgFlip != null & imgResize != null) {
                imgClose.setVisibility(View.GONE);
                imgFlip.setVisibility(View.GONE);
                imgResize.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Setup of custom effect using effect type and set parameters values
     *
     * @param customEffect {@link CustomEffect.Builder#setParameter(String, Object)}
     */
    public void setFilterEffect(CustomEffect customEffect) {
        parentView.setFilterEffect(customEffect);
    }

    /**
     * Set pre-define filter available
     *
     * @param filterType type of filter want to apply {@link PhotoEditor}
     */
    public void setFilterEffect(PhotoFilter filterType) {
        parentView.setFilterEffect(filterType);
    }

    /**
     * A callback to save the edited image asynchronously
     */
    public interface OnSaveListener {

        /**
         * Call when edited image is saved successfully on given path
         *
         * @param imagePath path on which image is saved
         */
        void onSuccess(@NonNull String imagePath);

        /**
         * Call when failed to saved image on given path
         *
         * @param exception exception thrown while saving image
         */
        void onFailure(@NonNull Exception exception);
    }

    /**
     * Save the edited image on given path
     *
     * @param imagePath      path on which image to be saved
     * @param onSaveListener callback for saving image
     * @see OnSaveListener
     */
    @RequiresPermission(allOf = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void saveAsFile(@NonNull final String imagePath, @NonNull final OnSaveListener onSaveListener) {
        saveAsFile(imagePath, new SaveSettings.Builder().build(), onSaveListener);
    }

    /**
     * Save the edited image on given path
     *
     * @param imagePath      path on which image to be saved
     * @param saveSettings   builder for multiple save options {@link SaveSettings}
     * @param onSaveListener callback for saving image
     * @see OnSaveListener
     */
    @SuppressLint("StaticFieldLeak")
    @RequiresPermission(allOf = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void saveAsFile(@NonNull final String imagePath,
                           @NonNull final SaveSettings saveSettings,
                           @NonNull final OnSaveListener onSaveListener) {
        Log.d(TAG, "Image Path: " + imagePath);
        parentView.saveFilter(new OnSaveBitmap() {
            @Override
            public void onBitmapReady(Bitmap saveBitmap) {
                new AsyncTask<String, String, Exception>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        clearHelperBox();
                        parentView.setDrawingCacheEnabled(false);
                    }

                    @SuppressLint("MissingPermission")
                    @Override
                    protected Exception doInBackground(String... strings) {
                        // Create a media file name
                        File file = new File(imagePath);
                        try {
                            FileOutputStream out = new FileOutputStream(file, false);
                            if (parentView != null) {
                                parentView.setDrawingCacheEnabled(true);
                                Bitmap drawingCache = saveSettings.isTransparencyEnabled()
                                        ? BitmapUtil.removeTransparency(parentView.getDrawingCache())
                                        : parentView.getDrawingCache();
                                drawingCache.compress(saveSettings.getCompressFormat(), saveSettings.getCompressQuality(), out);
                            }
                            out.flush();
                            out.close();
                            Log.d(TAG, "Filed Saved Successfully");
                            return null;
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG, "Failed to save File");
                            return e;
                        }
                    }

                    @Override
                    protected void onPostExecute(Exception e) {
                        super.onPostExecute(e);
                        if (e == null) {
                            //Clear all views if its enabled in save settings
                            if (saveSettings.isClearViewsEnabled()) clearAllViews();
                            onSaveListener.onSuccess(imagePath);
                        } else {
                            onSaveListener.onFailure(e);
                        }
                    }

                }.execute();
            }

            @Override
            public void onFailure(Exception e) {
                onSaveListener.onFailure(e);
            }
        });
    }

    /**
     * Save the edited image as bitmap
     *
     * @param onSaveBitmap callback for saving image as bitmap
     * @see OnSaveBitmap
     */
    @SuppressLint("StaticFieldLeak")
    public void saveAsBitmap(@NonNull final OnSaveBitmap onSaveBitmap) {
        saveAsBitmap(new SaveSettings.Builder().build(), onSaveBitmap);
    }

    /**
     * Save the edited image as bitmap
     *
     * @param saveSettings builder for multiple save options {@link SaveSettings}
     * @param onSaveBitmap callback for saving image as bitmap
     * @see OnSaveBitmap
     */
    @SuppressLint("StaticFieldLeak")
    public void saveAsBitmap(@NonNull final SaveSettings saveSettings,
                             @NonNull final OnSaveBitmap onSaveBitmap) {
        parentView.saveFilter(new OnSaveBitmap() {
            @Override
            public void onBitmapReady(Bitmap saveBitmap) {
                new AsyncTask<String, String, Bitmap>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        clearHelperBox();
                        parentView.setDrawingCacheEnabled(false);
                    }

                    @Override
                    protected Bitmap doInBackground(String... strings) {
                        if (parentView != null) {
                            parentView.setDrawingCacheEnabled(true);
                            return saveSettings.isTransparencyEnabled() ?
                                    BitmapUtil.removeTransparency(parentView.getDrawingCache())
                                    : parentView.getDrawingCache();
                        } else {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        if (bitmap != null) {
                            if (saveSettings.isClearViewsEnabled()) clearAllViews();
                            onSaveBitmap.onBitmapReady(bitmap);
                        } else {
                            onSaveBitmap.onFailure(new Exception("Failed to load the bitmap"));
                        }
                    }

                }.execute();
            }

            @Override
            public void onFailure(Exception e) {
                onSaveBitmap.onFailure(e);
            }
        });
    }

    private static String convertEmoji(String emoji) {
        String returnedEmoji;
        try {
            int convertEmojiToInt = Integer.parseInt(emoji.substring(2), 16);
            returnedEmoji = new String(Character.toChars(convertEmojiToInt));
        } catch (NumberFormatException e) {
            returnedEmoji = "";
        }
        return returnedEmoji;
    }

    /**
     * Callback on editing operation perform on {@link PhotoEditorView}
     *
     * @param onPhotoEditorListener {@link OnPhotoEditorListener}
     */
    public void setOnPhotoEditorListener(@NonNull OnPhotoEditorListener onPhotoEditorListener) {
        this.mOnPhotoEditorListener = onPhotoEditorListener;
    }

    /**
     * Check if any changes made need to save
     *
     * @return true if nothing is there to change
     */
    public boolean isCacheEmpty() {
        return addedViews.size() == 0 && redoViews.size() == 0;
    }


    @Override
    public void onViewAdd(BrushDrawingView brushDrawingView) {
        if (redoViews.size() > 0) {
            redoViews.remove(redoViews.size() - 1);
        }
        addedViews.add(brushDrawingView);
        if (mOnPhotoEditorListener != null) {
            mOnPhotoEditorListener.onAddViewListener(ViewType.BRUSH_DRAWING, addedViews.size());
        }
    }

    @Override
    public void onViewRemoved(BrushDrawingView brushDrawingView) {
        if (addedViews.size() > 0) {
            View removeView = addedViews.remove(addedViews.size() - 1);
            if (!(removeView instanceof BrushDrawingView)) {
                parentView.removeView(removeView);
            }
            redoViews.add(removeView);
        }
        if (mOnPhotoEditorListener != null) {
            mOnPhotoEditorListener.onRemoveViewListener(ViewType.BRUSH_DRAWING, addedViews.size());
        }
    }

    @Override
    public void onStartDrawing() {
        if (mOnPhotoEditorListener != null) {
            mOnPhotoEditorListener.onStartViewChangeListener(ViewType.BRUSH_DRAWING);
        }
    }

    @Override
    public void onStopDrawing() {
        if (mOnPhotoEditorListener != null) {
            mOnPhotoEditorListener.onStopViewChangeListener(ViewType.BRUSH_DRAWING);
        }
    }


    /**
     * Builder pattern to define {@link PhotoEditor} Instance
     */
    public static class Builder {

        private Context context;
        private PhotoEditorView parentView;
        private ImageView imageView;
        private View deleteView;
        private BrushDrawingView brushDrawingView;
        private Typeface textTypeface;
        private Typeface emojiTypeface;
        //By Default pinch zoom on text is enabled
        private boolean isTextPinchZoomable = true;

        /**
         * Building a PhotoEditor which requires a Context and PhotoEditorView
         * which we have setup in our xml layout
         *
         * @param context         context
         * @param photoEditorView {@link PhotoEditorView}
         */
        public Builder(Context context, PhotoEditorView photoEditorView) {
            this.context = context;
            parentView = photoEditorView;
            imageView = photoEditorView.getSource();
            brushDrawingView = photoEditorView.getBrushDrawingView();
        }

        Builder setDeleteView(View deleteView) {
            this.deleteView = deleteView;
            return this;
        }

        /**
         * set default text font to be added on image
         *
         * @param textTypeface typeface for custom font
         * @return {@link Builder} instant to build {@link PhotoEditor}
         */
        public Builder setDefaultTextTypeface(Typeface textTypeface) {
            this.textTypeface = textTypeface;
            return this;
        }

        /**
         * set default font specific to add emojis
         *
         * @param emojiTypeface typeface for custom font
         * @return {@link Builder} instant to build {@link PhotoEditor}
         */
        public Builder setDefaultEmojiTypeface(Typeface emojiTypeface) {
            this.emojiTypeface = emojiTypeface;
            return this;
        }

        /**
         * set false to disable pinch to zoom on text insertion.By deafult its true
         *
         * @param isTextPinchZoomable flag to make pinch to zoom
         * @return {@link Builder} instant to build {@link PhotoEditor}
         */
        public Builder setPinchTextScalable(boolean isTextPinchZoomable) {
            this.isTextPinchZoomable = isTextPinchZoomable;
            return this;
        }

        /**
         * @return build PhotoEditor instance
         */
        public PhotoEditor build() {
            return new PhotoEditor(this);
        }

    }

    /**
     * Provide the list of emoji in form of unicode string
     *
     * @param context context
     * @return list of emoji unicode
     */
    public static ArrayList<String> getEmojis(Context context) {
        ArrayList<String> convertedEmojiList = new ArrayList<>();
        String[] emojiList = context.getResources().getStringArray(R.array.photo_editor_emoji);
        for (String emojiUnicode : emojiList) {
            convertedEmojiList.add(convertEmoji(emojiUnicode));
        }
        return convertedEmojiList;
    }
}
