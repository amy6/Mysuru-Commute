package com.example.venkateshkashyap.mysuru_commute.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.venkateshkashyap.mysuru_commute.R;
import com.example.venkateshkashyap.mysuru_commute.fragments.BusNumbersFragment;
import com.example.venkateshkashyap.mysuru_commute.fragments.BusNumbersFragment.OnListFragmentInteractionListener;
import com.example.venkateshkashyap.mysuru_commute.models.BusNumbers;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.example.venkateshkashyap.mysuru_commute.models.BusNumbers} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BusNumbersRecyclerViewAdapter extends RecyclerView.Adapter<BusNumbersRecyclerViewAdapter.ViewHolder> {

    private BusNumbers mBusNumbersData;
    private List<String> mValues;
    private final BusNumbersFragment.OnListFragmentInteractionListener mListener;

    public BusNumbersRecyclerViewAdapter(BusNumbers busNumbersData, OnListFragmentInteractionListener listener) {
        mBusNumbersData = busNumbersData;
        mValues = mBusNumbersData.getData();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_busnumbers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void setList(BusNumbers busNumbersData){
        mBusNumbersData = busNumbersData;
        mValues.clear();
        mValues.addAll(mBusNumbersData.getData());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}