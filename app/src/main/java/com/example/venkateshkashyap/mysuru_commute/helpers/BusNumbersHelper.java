package com.example.venkateshkashyap.mysuru_commute.helpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.venkateshkashyap.mysuru_commute.NetworkManager.NetworkManager;
import com.example.venkateshkashyap.mysuru_commute.R;
import com.example.venkateshkashyap.mysuru_commute.Utils.DialogUtils;
import com.example.venkateshkashyap.mysuru_commute.Utils.NetworkUtil;
import com.example.venkateshkashyap.mysuru_commute.Utils.Utils;
import com.example.venkateshkashyap.mysuru_commute.models.BusNumbers;
import com.example.venkateshkashyap.mysuru_commute.models.BusStops;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Venkatesh Kashyap on 5/4/2017.
 */

public class BusNumbersHelper extends BaseHelper{

    private BusNumbersHelper.OnBusNumbersResponseReceived mOnBusNumbersResponseReceived;

    public BusNumbersHelper(Context context) {
        super(context);
    }

    public interface OnBusNumbersResponseReceived {
        void onBusNumbersResponseReceived(BusNumbers busNumbersData);
        void onFailure();
    }


    private static final String TAG = BusNumbersHelper.class.getSimpleName();

    public void getBusNumbers(final BusNumbersHelper.OnBusNumbersResponseReceived onBusNumbersResponseReceived, final View view, final View recyclerView) {
        mOnBusNumbersResponseReceived = onBusNumbersResponseReceived;

        if(NetworkUtil.isConnectionAvailable(mContext)) {
            DialogUtils.displayProgressDialog(mContext);
            NetworkManager.getInstance().getAllBusNumbers(mBusNumbersCallback);

        }else{

                Utils.setErrorView(recyclerView,view,mContext, ContextCompat.getDrawable(mContext, R.drawable.ic_sleep),mContext.getString(R.string.no_network_title),mContext.getString(R.string.no_network_sub_text),mContext.getString(R.string.try_again),false);

                TextView mTextViewTryAgain = (TextView) view.findViewById(R.id.text_try_again);

                mTextViewTryAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getBusNumbers(onBusNumbersResponseReceived,view, recyclerView);
                    }
                });
        }

    }

    private Callback<BusNumbers> mBusNumbersCallback = new BaseCallback<BusNumbers>(mContext) {

        @Override
        public void onResponse(Call<BusNumbers> call, Response<BusNumbers> response) {
            DialogUtils.hideProgressDialog();
            BusNumbers busNumbers = new BusNumbers();
            if (response != null && response.body() != null) {
              busNumbers.setData(response.body().getData());
                mOnBusNumbersResponseReceived.onBusNumbersResponseReceived(busNumbers);
                Log.d(TAG, "onResponse: " + response.code());
            }else {
            }
        }

        @Override
        public void onFailure(Call<BusNumbers> call, Throwable t) {
            super.onFailure(call, t);
        }
    };
}