package com.apps.alexs7.pointop;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.alexs7.pointop.fragments.CleanPreviewFragment;

/**
 * Created by alex on 09/08/15.
 */
public class UIHelper {

    private Activity mActivity;
    private Resources resources;
    private BProcessor mBProcessor;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public UIHelper(Activity activity,BProcessor processor) {
        mActivity  = activity;
        mBProcessor = processor;
        resources = mActivity.getResources();
    }

    public void buildListWithChoices() {
        mRecyclerView = (RecyclerView) mActivity.findViewById(R.id.listOptionsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mActivity);
        mAdapter = new ProcessingOptionsAdapter(resources.getStringArray(R.array.list_of_image_processing_choices));

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class ProcessingOptionsAdapter extends RecyclerView.Adapter<ProcessingOptionsAdapter.ViewHolder> {

        private String[] choices;

        //Inner class for each item on the list
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public CardView mCardView;

            public ViewHolder(LinearLayout l) {
                super(l);
                mTextView = (TextView) l.findViewById(R.id.choice);
                mCardView = (CardView) l.findViewById(R.id.choice_card_view);
            }
        }

        public ProcessingOptionsAdapter(String[] stringArray) {
            choices = stringArray;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.choice_card_view, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(choices[position]);
            holder.mCardView.setOnClickListener(new selectChoiceListener(position));
        }

        @Override
        public int getItemCount() {
            return choices.length;
        }
    }

    private class selectChoiceListener implements View.OnClickListener {
        private int position;
        public selectChoiceListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            switch (position) {
                case 0:
                    if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } else {
                        Toast.makeText(mActivity, R.string.portrait_warning, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    if (resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        Toast.makeText(mActivity, R.string.landscape_warning, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 9:
                    CleanPreviewFragment cleanPreviewFragment = (CleanPreviewFragment)
                            mActivity.getFragmentManager().findFragmentByTag(FragmentHelper.CLEAN_PREVIEW_TAG);
                    cleanPreviewFragment.stopPreview();
                    mBProcessor.setFunction(position);
                    break;
                default:
                    mBProcessor.setFunction(position);
                    break;
            }
        }
    }
}
