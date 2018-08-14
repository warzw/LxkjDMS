package com.artifex.mupdflib;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class ProgressDialogX extends ProgressDialog {
    public ProgressDialogX(Context context) {
        super(context);
    }

    private boolean mCancelled = false;

    public boolean isCancelled() {
        return mCancelled;
    }

    @Override
    public void cancel() {
        mCancelled = true;
        super.cancel();
    }
}

public abstract class SearchTask {
    private static final int SEARCH_PROGRESS_DELAY = 200;
    private final Context mContext;
    private final MuPDFCore mCore;
    private final Handler mHandler;
    private final AlertDialog.Builder mAlertBuilder;
    private AsyncTask<Void, Integer, List<SearchItemEntity>> mSearchTask;
    private List<SearchItemEntity> datas = new ArrayList<>();

    public SearchTask(Context context, MuPDFCore core) {
        mContext = context;
        mCore = core;
        mHandler = new Handler();
        mAlertBuilder = new AlertDialog.Builder(context);
    }

    protected abstract void onTextFound(List<SearchItemEntity> datas);

    public void stop() {
        if (mSearchTask != null) {
            mSearchTask.cancel(true);
            mSearchTask = null;
        }
    }

    public void go(final String text) {
        if (mCore == null)
            return;
        stop();

        final ProgressDialogX progressDialog = new ProgressDialogX(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle(mContext.getString(R.string.searching_));
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                stop();
            }
        });

        progressDialog.setMax(mCore.countPages());
        mSearchTask = new AsyncTask<Void, Integer, List<SearchItemEntity>>() {
            public int inst;

            @Override
            protected List<SearchItemEntity> doInBackground(Void... params) {
                datas.clear();
                List<RectF[]> rectFS = new ArrayList<>();
                for (int i = 0; i < mCore.countSinglePages(); i++) {
                    inst = i;
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (!progressDialog.isCancelled()) {
                                progressDialog.show();
                                progressDialog.setProgress(inst);
                            }
                        }
                    }, SEARCH_PROGRESS_DELAY);
                    RectF[] searchHits = mCore.searchPage(i, text);
                    if (searchHits != null && searchHits.length > 0) {
                        TextWord[][] textWords = mCore.textLines(i);
                        StringBuilder ss = new StringBuilder();
                        for (int j = 0; j <textWords.length ; j++) {
                            for (int k = 0; k < textWords[j].length; k++) {
                                ss.append(textWords[j][k].w);
                            }
                        }
                        String[] s = ss.toString().split(text);
                        StringBuilder content = new StringBuilder();
                        assert s.length>0;
                        for (int j = 0; j <s.length-1; j++) {
                            String star = "";
                            String end = "";
                            if (s[j].length()>15) {
                                star = s[j].substring(s[j].length()-15,s[j].length());
                            }else {
                                star = s[j];
                            }
                            if (s[j+1].length()>15) {
                                end = s[j+1].substring(0,15);
                            }else {
                                end = s[j+1];
                            }
                            content.append(star).append(",").append(end).append("。");
                        }
                        Log.i("SearchItemEntity",content.toString());
                        SearchItemEntity searchItemEntity =  new SearchItemEntity();
                        searchItemEntity.setKeyWord(text);
                        searchItemEntity.setIndex(i);
                        searchItemEntity.setContent(content.toString());
                        datas.add(searchItemEntity);
                        rectFS.add(searchHits);
                    }
                }
                SearchTaskResult.set(new SearchTaskResult(text,mCore.countSinglePages(),rectFS,null));
                return datas;
            }

            @Override
            protected void onPostExecute(List<SearchItemEntity> datas) {
                progressDialog.cancel();
                onTextFound(datas);
            }

            @Override
            protected void onCancelled() {
                progressDialog.cancel();
            }
        };

        mSearchTask.execute();
    }
}
