package com.spimpalkar.pcubedemo.fragments;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.spimpalkar.pcubedemo.R;
import com.spimpalkar.pcubedemo.activities.BaseActivity;
import com.spimpalkar.pcubedemo.adapters.DealsAdapter;
import com.spimpalkar.pcubedemo.helpers.Constants;
import com.spimpalkar.pcubedemo.helpers.CustomParser;
import com.spimpalkar.pcubedemo.helpers.MPBroadcastReciever;
import com.spimpalkar.pcubedemo.helpers.NetworkConnectionListener;
import com.spimpalkar.pcubedemo.helpers.SPDSingleton;
import com.spimpalkar.pcubedemo.helpers.SqliteDBHandler;
import com.spimpalkar.pcubedemo.helpers.WebServiceDelegate;
import com.spimpalkar.pcubedemo.helpers.Webservices;
import com.spimpalkar.pcubedemo.models.DealModel;
import com.spimpalkar.pcubedemo.models.DealModelDataList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

public class TopDealsFragment extends Fragment

{

    ListView dealListView;
    Webservices webServiceObj;
    WebServiceDelegate callback;
    int perPageCount = 10;
    int pageNo = 1;
    int topDealCount = 0;
    int visibleItemCountGlobal = 0;
    int lastFirstVisibleIndex = 0;

    boolean shouldUpdateData = false;
    DealsAdapter dealsAdapter;
    ArrayList<DealModelDataList> dealModelDataList;
    private MPBroadcastReciever mpBroadcastReciever;
    private NetworkConnectionListener networkConnectionListenerCallback;

    public TopDealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_deals_listview, container, false);
        dealListView = (ListView) view.findViewById(R.id.dealListViewID);
        dealListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {

                Log.e("FVISI Count >>>> ", ""+firstVisibleItem);
                Log.e("VISI Count >>>> ", ""+visibleItemCount);
                Log.e("Total Count >>>> ", ""+totalItemCount);

                             if(totalItemCount == firstVisibleItem + visibleItemCount)
                {
                    if (totalItemCount < topDealCount)
                    {
                      // Get more data from server.
                        lastFirstVisibleIndex =  firstVisibleItem;
                        visibleItemCountGlobal =  visibleItemCount;


                        shouldUpdateData = true;
                        pageNo = pageNo + 1;
                        getTopDealsData();
                    }else
                    {
                        shouldUpdateData = false;
                    }
                }else
                {
                    shouldUpdateData = false;
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         /*If connected to internet, get the data from server else show the offline data by getting
        * data from Sqlite database*/
        if(((BaseActivity)getActivity()).isInternetConnectionAvailable()){
            preSetup();
        }else{
            topDealCount = SPDSingleton.getInstance().getIntegerFromSp(Constants.topDealCountSP,getActivity());
            dealModelDataList = (ArrayList<DealModelDataList>) SqliteDBHandler.getSqliteInstance(getActivity()).getAllTopDeals();
            setDataToAdapter(dealModelDataList);
        }
    }

    /*Call the webservice methods*/
    private  void preSetup()
    {
        callbackInitialisation();
        setUpNetworkListner();
        webServiceObj = new Webservices(null, getActivity(),  callback);
        getTopDealsData();
    }


    ///  Need to deinit broadcast receiver object and unregister listening to network changes
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("Destroy====", "View");
        if (mpBroadcastReciever != null)
        {
            getActivity().unregisterReceiver(mpBroadcastReciever);
            mpBroadcastReciever = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Destroy====", "");
        if (mpBroadcastReciever != null)
        {
            getActivity().unregisterReceiver(mpBroadcastReciever);
            mpBroadcastReciever = null;
        }
    }

    // Initialising Broadcast receiver and registered to listen to network change
    private void setUpNetworkListner()
    {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mpBroadcastReciever = new MPBroadcastReciever(networkConnectionListenerCallback);
        mpBroadcastReciever.setOnNetworkChangeListener(networkConnectionListenerCallback);
        getActivity().registerReceiver(mpBroadcastReciever, intentFilter);
    }

    private void getTopDealsData()
    {

                webServiceObj.startGetRequestWithAuthorization(Constants.GET_TOP_DEALS, null, perPageCount, pageNo);
    }

    // Initialising callback
    private  void callbackInitialisation()
    {
        callback = new WebServiceDelegate()
        {
            // Listeners
            @Override
            public void onPreFetch() {
//                SPDSingleton.getInstance().showProgressDialog(getActivity(), "Getting deals....", true);
            }

            @Override
            public void jSONResponseAfterRequest(JSONObject responseObject) {
//                SPDSingleton.getInstance().hideProgressDialog();
                Log.i("TOP DEALS ==>> ", String.valueOf(responseObject));
                DealModel dealModel = CustomParser.parseDeals(responseObject);
                topDealCount =  dealModel.getDealDataModel().getTotalDealCount();
                if(dealModelDataList != null)
                {
                    // First Check if it is required to add additional data
                    if (shouldUpdateData == true)
                    {
                        dealModelDataList.addAll(dealModel.getDealDataModel().getModelDataListArrayList());
                        shouldUpdateData = false;
                    }

                }else
                {
                    dealModelDataList = dealModel.getDealDataModel().getModelDataListArrayList();

                }
                setDataToAdapter(dealModelDataList);

                /*Saving the Top deals to Sqlite database*/
                SqliteDBHandler.getSqliteInstance(getActivity()).addTopDealList(dealModel.getDealDataModel().
                        getModelDataListArrayList());
                /*Saving the top deal count in preferences*/
                SPDSingleton.getInstance().setIntegerToSp(topDealCount, Constants.topDealCountSP,getActivity());
            }
        };

        networkConnectionListenerCallback = new NetworkConnectionListener() {
            @Override
            public void onNetworkConnectionChanged(boolean isConnected) {
                Log.i("Network", "Connectivity");
                if(isConnected){
                    getTopDealsData();
                }else{
                    dealModelDataList = (ArrayList<DealModelDataList>) SqliteDBHandler.getSqliteInstance(getActivity()).getAllTopDeals();
                    setDataToAdapter(dealModelDataList);
                }
            }
        };
    }

    private void setDataToAdapter(ArrayList<DealModelDataList> dealModel) {

        if(dealsAdapter == null){
            dealsAdapter = new DealsAdapter(getActivity(), dealModel);
            dealListView.setAdapter(dealsAdapter);
        }
        dealsAdapter.notifyDataSetChanged();
        dealListView.setSelection(lastFirstVisibleIndex + visibleItemCountGlobal - 1);
    }

}
