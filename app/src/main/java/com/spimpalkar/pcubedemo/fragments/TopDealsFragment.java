package com.spimpalkar.pcubedemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.spimpalkar.pcubedemo.R;
import com.spimpalkar.pcubedemo.activities.BaseActivity;
import com.spimpalkar.pcubedemo.adapters.DealsAdapter;
import com.spimpalkar.pcubedemo.helpers.Constants;
import com.spimpalkar.pcubedemo.helpers.CustomParser;
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
 * Created by manish on 7/1/17.
 */

public class TopDealsFragment extends Fragment
{

    ListView dealListView;
    Webservices webServiceObj;
    WebServiceDelegate callback;
    int perPageCount = 10;
    int pageNo = 1;
    ArrayList<DealModelDataList> dealModelDataList;

    public TopDealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_deals_listview, container, false);
        dealListView = (ListView) view.findViewById(R.id.dealListViewID);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((BaseActivity)getActivity()).isInternetConnectionAvailable()){
            preSetup();
        }else{
            dealModelDataList = (ArrayList<DealModelDataList>) SqliteDBHandler.getSqliteInstance(getActivity()).getAllTopDeals();
            setDataToAdapter(dealModelDataList);
        }
    }

    private  void preSetup()
    {
        callbackInitialisation();
        webServiceObj = new Webservices(null, getActivity(),  callback);
        getTopDealsData();
    }

    private void getTopDealsData()
    {
        JSONObject params = new JSONObject();

        try
        {
            params.put("per_page", perPageCount); // HOW MANY OBJECTS SHOULD BE THERE IN THE RESPONSE
            params.put("page", pageNo); // THE PAGE NUMBER FOR NEXT ENTRIES
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        webServiceObj.startGetRequestWithAuthorization(Constants.GET_TOP_DEALS, params);
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
                dealModelDataList = dealModel.getDealDataModel().getModelDataListArrayList();
                setDataToAdapter(dealModelDataList);

                /*Saving the Top deals to Sqlite database*/
                SqliteDBHandler.getSqliteInstance(getActivity()).addTopDealList(dealModel.getDealDataModel().getModelDataListArrayList());
            }
        };
    }

    private void setDataToAdapter(ArrayList<DealModelDataList> dealModel) {

        DealsAdapter dealsAdapter = new DealsAdapter(getActivity(), dealModel);
        dealListView.setAdapter(dealsAdapter);
    }
}
