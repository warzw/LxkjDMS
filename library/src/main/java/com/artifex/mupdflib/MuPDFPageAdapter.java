package com.artifex.mupdflib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;

public class MuPDFPageAdapter extends BaseAdapter {
    private final Context mContext;
    private final FilePicker.FilePickerSupport mFilePickerSupport;
    private final MuPDFCore mCore;
    private final SparseArray<PointF> mPageSizes = new SparseArray<PointF>();
    private Bitmap mSharedHqBm;
    private int mPosition;
    private List<SearchItemEntity> datas;

    public MuPDFPageAdapter(Context c, FilePicker.FilePickerSupport filePickerSupport, MuPDFCore core) {
        SearchTaskResult.set(null);
        mContext = c;
        mFilePickerSupport = filePickerSupport;
        mCore = core;
    }

    public MuPDFPageAdapter(Context c, FilePicker.FilePickerSupport filePickerSupport, List<SearchItemEntity> datas, MuPDFCore core) {
        this.datas = datas;
        mContext = c;
        mFilePickerSupport = filePickerSupport;
        mCore = core;
    }

    public int getCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        } else {
            return mCore.countPages();
        }
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void releaseBitmaps() {
        //  recycle and release the shared bitmap.
        if (mSharedHqBm != null)
            mSharedHqBm.recycle();
        mSharedHqBm = null;
    }

    public View getView(int zPosition, View convertView, ViewGroup parent) {
        final MuPDFPageView pageView;
        if (convertView == null) {
            if (mSharedHqBm == null || mSharedHqBm.getWidth() != parent.getWidth() || mSharedHqBm.getHeight() != parent.getHeight())
                mSharedHqBm = Bitmap.createBitmap(parent.getWidth(), parent.getHeight(), Bitmap.Config.ARGB_8888);

            pageView = new MuPDFPageView(mContext, mFilePickerSupport, mCore, new Point(parent.getWidth(), parent.getHeight()), mSharedHqBm);
        } else {
            pageView = (MuPDFPageView) convertView;
        }
        if (datas != null && datas.size() > 0) {
            mPosition = datas.get(zPosition).getIndex();
        } else {
            mPosition = zPosition;
        }
        PointF pageSize = mPageSizes.get(mPosition);
        if (pageSize != null) {
            // We already know the page size. Set it up
            // immediately
            pageView.setPage(mPosition, pageSize);
        } else {
            // Page size as yet unknown. Blank it for now, and
            // start a background task to find the size
            pageView.blank(mPosition);
            AsyncTask<Void, Void, PointF> sizingTask = new AsyncTask<Void, Void, PointF>() {
                @Override
                protected PointF doInBackground(Void... arg0) {
                    return mCore.getPageSize(mPosition);
                }

                @Override
                protected void onPostExecute(PointF result) {
                    super.onPostExecute(result);
                    // We now know the page size
                    mPageSizes.put(mPosition, result);
                    // Check that this view hasn't been reused for
                    // another page since we started
                    if (pageView.getPage() == mPosition) {
                        pageView.setPage(mPosition, result);
                    }

                }
            };

            sizingTask.execute((Void) null);
        }
        return pageView;
    }
}
