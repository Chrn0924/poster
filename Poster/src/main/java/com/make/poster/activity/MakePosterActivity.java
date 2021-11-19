package com.make.poster.activity;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import static com.make.poster.utils.FileSaveHelper.isSdkHigherThan28;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.make.poster.R;
import com.make.poster.adapter.OpertionRecAdapter;
import com.make.poster.app.MPApplication;
import com.make.poster.base.MPBaseActivity;
import com.make.poster.bean.OpertionItemBean;
import com.make.poster.dialog.PosterFilterDialog;
import com.make.poster.filters.FilterListener;
import com.make.poster.filters.FilterViewAdapter;
import com.make.poster.fragment.MakePosteTextFragment;
import com.make.poster.fragment.MakePosterEmojiFragment;
import com.make.poster.fragment.MakePosterShapeFragment;
import com.make.poster.starter.PosterConstant;
import com.make.poster.starter.PosterOption;
import com.make.poster.starter.PosterShareListener;
import com.make.poster.starter.Posterix;
import com.make.poster.utils.FileSaveHelper;
import com.make.poster.utils.MakePosterLog;
import com.make.poster.utils.PhoneixUtils;
import com.make.poster.view.DrawingView;
import com.make.poster.view.OnPhotoEditorListener;
import com.make.poster.view.PhotoEditor;
import com.make.poster.view.PhotoEditorView;
import com.make.poster.view.PhotoFilter;
import com.make.poster.view.SaveSettings;
import com.make.poster.view.TextStyleBuilder;
import com.make.poster.view.ViewType;
import com.make.poster.view.shape.ShapeBuilder;
import com.make.poster.view.shape.ShapeType;


import java.util.ArrayList;
import java.util.List;

public class MakePosterActivity extends MPBaseActivity implements OpertionRecAdapter.opertionClick, OnPhotoEditorListener, MakePosterEmojiFragment.EmojiListener, FilterListener, MakePosterShapeFragment.Properties {

    //选择背景的requestcode
    final int REQUEST_CODE = 100000;
    //选择iamge
    final int REQUEST_CODEITEM = 100001;

    private PhotoEditorView mPhotoEditorView;

    private RecyclerView opertionRec;

    private PhotoEditor mPhotoEditor;

    private MakePosterEmojiFragment makePosterEmojiFragment;

    private MakePosterShapeFragment mShapeBSFragment;

    private ShapeBuilder mShapeBuilder;

    private ImageView save, recocation, nextstep;

    private final FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);

    @Nullable
    @VisibleForTesting
    Uri mSaveImageUri;

    private FileSaveHelper mSaveFileHelper;
    private PosterOption mPosterOption;


    @Override
    protected int initLayout() {
        setModel(true);
        return R.layout.make_poster_activity;
    }

    @Override
    protected void initLayoutId() {

        Intent intent = getIntent();
        if (intent.getParcelableExtra(PosterConstant.POSTER_OPTION) != null){
            Parcelable parcelableExtra = intent.getParcelableExtra(PosterConstant.POSTER_OPTION);
            mPosterOption = (PosterOption) parcelableExtra;
        }

        mSaveFileHelper = new FileSaveHelper(this);

        mPhotoEditorView = (PhotoEditorView) findViewById(R.id.make_poster_backgrond_image);

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(false)
                .build();

        mPhotoEditor.setOnPhotoEditorListener(this);

        opertionRec = (RecyclerView) findViewById(R.id.operation_rec);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        opertionRec.setLayoutManager(manager);

        //保存 撤回 下一步按钮
        save = findViewById(R.id.opertion_save_image);
        Drawable saveDrawable = this.getResources().getDrawable(R.drawable.poster_opertion_save_pciture).mutate();
        saveDrawable.setColorFilter(this.getResources().getColor(MPApplication.OPERTIONSVGCOLOR), PorterDuff.Mode.SRC_ATOP);
        save.setImageDrawable(saveDrawable);

        recocation = findViewById(R.id.opertion_revocation);
        Drawable recocationDrawable = this.getResources().getDrawable(R.drawable.poster_opertion_recall).mutate();
        recocationDrawable.setColorFilter(this.getResources().getColor(MPApplication.OPERTIONSVGCOLOR), PorterDuff.Mode.SRC_ATOP);
        recocation.setImageDrawable(recocationDrawable);

        nextstep = findViewById(R.id.opertion_nextstep);
        Drawable nextstepDrawable = this.getResources().getDrawable(R.drawable.poster_opertion_next).mutate();
        nextstepDrawable.setColorFilter(this.getResources().getColor(MPApplication.OPERTIONSVGCOLOR), PorterDuff.Mode.SRC_ATOP);
        nextstep.setImageDrawable(nextstepDrawable);

    }

    @Override
    protected void initLayoutClick() {

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

        recocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoEditor.undo();
            }
        });

        nextstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoEditor.redo();
            }
        });

    }



    private void saveImage() {
        final String fileName = System.currentTimeMillis() + ".png";
        final boolean hasStoragePermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED;
        if (hasStoragePermission || isSdkHigherThan28()) {
            showLoading("Saving...");
            mSaveFileHelper.createFile(fileName, (fileCreated, filePath, error, uri) -> {
                if (fileCreated) {
                    SaveSettings saveSettings = new SaveSettings.Builder()
                            .setClearViewsEnabled(true)
                            .setTransparencyEnabled(true)
                            .build();

                    mPhotoEditor.saveAsFile(filePath, saveSettings, new PhotoEditor.OnSaveListener() {
                        @Override
                        public void onSuccess(@NonNull String imagePath) {
                            mSaveFileHelper.notifyThatFileIsNowPubliclyAvailable(getContentResolver());
                            hideLoading();
                            showToast("保存成功");
                            mSaveImageUri = uri;
                            mPhotoEditorView.getSource().setImageURI(mSaveImageUri);
                        }

                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            hideLoading();
                            showToast("保存失败");
                        }
                    });

                } else {
                    hideLoading();
                    showToast(error);
                }
            });
        } else {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void initData() {

        //初始化操作按钮

        List<OpertionItemBean> opertionItemBeanList = new ArrayList<>();

        OpertionItemBean opertionItemBean;

        if(mPosterOption.isOpenShape()){
            opertionItemBean = new OpertionItemBean();
            opertionItemBean.setOpertionImageId(R.drawable.poster_opertion_line_picture);
            opertionItemBean.setOpertionTips("画线");
            opertionItemBean.setOpertionType(1);
            opertionItemBean.setOpenFunction(true);
            opertionItemBeanList.add(opertionItemBean);
        }

        if(mPosterOption.isOpenText()){
            opertionItemBean = new OpertionItemBean();
            opertionItemBean.setOpertionImageId(R.drawable.poster_opertion_text_picture);
            opertionItemBean.setOpertionTips("文本");
            opertionItemBean.setOpertionType(2);
            opertionItemBean.setOpenFunction(true);
            opertionItemBeanList.add(opertionItemBean);
        }

        if(mPosterOption.isOpenEraser()){
            opertionItemBean = new OpertionItemBean();
            opertionItemBean.setOpertionImageId(R.drawable.poster_opertion_eraser_picture);
            opertionItemBean.setOpertionTips("橡皮擦");
            opertionItemBean.setOpertionType(3);
            opertionItemBean.setOpenFunction(true);
            opertionItemBeanList.add(opertionItemBean);
        }

        if(mPosterOption.isOpenFilter()){
            opertionItemBean = new OpertionItemBean();
            opertionItemBean.setOpertionImageId(R.drawable.poster_opertion_filter_picture);
            opertionItemBean.setOpertionTips("滤镜");
            opertionItemBean.setOpertionType(4);
            opertionItemBean.setOpenFunction(true);
            opertionItemBeanList.add(opertionItemBean);
        }

        if(mPosterOption.isOpenEmoji()){
            opertionItemBean = new OpertionItemBean();
            opertionItemBean.setOpertionImageId(R.drawable.poster_opertion_emoji_picture);
            opertionItemBean.setOpertionTips("表情");
            opertionItemBean.setOpertionType(5);
            opertionItemBean.setOpenFunction(true);
            opertionItemBeanList.add(opertionItemBean);
        }

        if(mPosterOption.isOpenPicture()){
            opertionItemBean = new OpertionItemBean();
            opertionItemBean.setOpertionImageId(R.drawable.poster_opertion_picture_picture);
            opertionItemBean.setOpertionTips("图片");
            opertionItemBean.setOpertionType(6);
            opertionItemBean.setOpenFunction(true);
            opertionItemBeanList.add(opertionItemBean);
        }

        if(mPosterOption.isOpenBackgrond()){
            opertionItemBean = new OpertionItemBean();
            opertionItemBean.setOpertionImageId(R.drawable.poster_opertion_background_picture);
            opertionItemBean.setOpertionTips("背景");
            opertionItemBean.setOpertionType(7);
            opertionItemBean.setOpenFunction(true);
            opertionItemBeanList.add(opertionItemBean);
        }


        OpertionRecAdapter opertionRecAdapter = new OpertionRecAdapter(opertionItemBeanList, this, this);
        opertionRec.setAdapter(opertionRecAdapter);

        makePosterEmojiFragment = new MakePosterEmojiFragment();
        makePosterEmojiFragment.setEmojiListener(this);

        mShapeBSFragment = new MakePosterShapeFragment();
        mShapeBSFragment.setPropertiesChangeListener(this);

    }

    @Override
    protected void initOthers() {

    }

    @Override
    protected void onResumeMethod() {

    }

    @Override
    protected void initTestMethod() {
        MakePosterLog.e("测试一下哈哈哈");
    }

    @Override
    protected void setTtitle(TextView val) {
        val.setText(mPosterOption.getPageTitle() == null ? "海报制做" : mPosterOption.getPageTitle());
    }

    @Override
    protected void setShareContent(TextView val) {
        val.setVisibility(View.GONE);
//        if(!mPosterOption.isOpenShare()){
//        }else {
//            val.setText(mPosterOption.getShareTitle() == null ? "分享" : mPosterOption.getShareTitle());
//        }
    }

    @Override
    protected void shareClickListener() {

    }

    @Override
    protected void backImage(ImageView backImage) {

    }

    @Override
    protected void backImageClick() {
        finish();
    }


    @Override
    protected void destoryInit() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //返回的数据
            List<MediaEntity> result = Phoenix.result(data);
            Bitmap bitmap = BitmapFactory.decodeFile(result.get(0).getLocalPath().toString());
            mPhotoEditorView.getSource().setScaleType(ImageView.ScaleType.FIT_XY);
            mPhotoEditorView.getSource().setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODEITEM && resultCode == RESULT_OK) {
            List<MediaEntity> result = Phoenix.result(data);
            Bitmap bitmap = BitmapFactory.decodeFile(result.get(0).getLocalPath().toString());
            mPhotoEditor.addImage(bitmap);
        }
    }

    @Override
    public void itemClick(int opertionType, boolean open) {

        MakePosterLog.e("opertionType = " + opertionType);


        switch (opertionType) {

            case 1:
                mPhotoEditor.setBrushDrawingMode(true);
                mShapeBuilder = new ShapeBuilder();
                mPhotoEditor.setShape(mShapeBuilder);
                showBottomSheetDialogFragment(mShapeBSFragment);
                break;
            case 2:
                MakePosteTextFragment textEditorDialogFragment = MakePosteTextFragment.show(this);
                textEditorDialogFragment.setOnTextEditorListener((inputText, colorCode) -> {
                    final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                    styleBuilder.withTextColor(colorCode);
                    mPhotoEditor.addText(inputText, styleBuilder);
                });
                break;
            case 3:
                mPhotoEditor.brushEraser();
                break;
            case 4:
                PosterFilterDialog.showFilterDialog(this, mFilterViewAdapter);
                break;
            case 5:
                showBottomSheetDialogFragment(makePosterEmojiFragment);
                break;
            case 6:
                PhoneixUtils.phoneixSelect(MakePosterActivity.this, REQUEST_CODEITEM, 1, 1, true, true, true);
                break;
            case 7:
                PhoneixUtils.phoneixSelect(MakePosterActivity.this, REQUEST_CODE, 1, 1, true, true, true);
                break;

        }

    }

    //弹出滤镜
    private void showBottomSheetDialogFragment(BottomSheetDialogFragment fragment) {
        if (fragment == null || fragment.isAdded()) {
            return;
        }
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }




    @Override
    public void onEditTextChangeListener(View rootView, String text, int colorCode) {
        MakePosteTextFragment textEditorDialogFragment = MakePosteTextFragment.show(this, text, colorCode);
        textEditorDialogFragment.setOnTextEditorListener((inputText, newColorCode) -> {
            final TextStyleBuilder styleBuilder = new TextStyleBuilder();
            styleBuilder.withTextColor(newColorCode);
            mPhotoEditor.editText(rootView, inputText, styleBuilder);
        });
    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onTouchSourceImage(MotionEvent event) {

    }

    @Override
    public void onEmojiClick(String emojiUnicode) {
        mPhotoEditor.addEmoji(emojiUnicode);
    }


    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter);
    }

    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeColor(colorCode));
    }

    @Override
    public void onOpacityChanged(int opacity) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeOpacity(opacity));
    }

    @Override
    public void onShapeSizeChanged(int shapeSize) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeSize(shapeSize));
    }

    @Override
    public void onShapePicked(ShapeType shapeType) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeType(shapeType));
    }
}
