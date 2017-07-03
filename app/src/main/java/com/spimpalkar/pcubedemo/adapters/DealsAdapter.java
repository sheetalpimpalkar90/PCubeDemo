package com.spimpalkar.pcubedemo.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spimpalkar.pcubedemo.R;
import com.spimpalkar.pcubedemo.activities.BaseActivity;
import com.spimpalkar.pcubedemo.helpers.Constants;
import com.spimpalkar.pcubedemo.helpers.SPDSingleton;
import com.spimpalkar.pcubedemo.helpers.SqliteDBHandler;
import com.spimpalkar.pcubedemo.models.DealModel;
import com.spimpalkar.pcubedemo.models.DealModelDataList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

public class DealsAdapter extends BaseAdapter{

    Context context;
    ArrayList<DealModelDataList> dealModelDataListArrayList;
    LayoutInflater mInflater;

    public DealsAdapter(Context context, ArrayList<DealModelDataList> dealModelDataListArrayList) {
        this.context = context;
        this.dealModelDataListArrayList = dealModelDataListArrayList;
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dealModelDataListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cell_deal, null);
            holder = new ViewHolder();
            holder.profilePicImg = (ImageView) convertView.findViewById(R.id.deal_imageViewID);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.title_textViewID);
            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.description_textViewID);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(!(dealModelDataListArrayList.get(position).getTopDealImage().equalsIgnoreCase(""))){
            if(((BaseActivity)context).isInternetConnectionAvailable()){
                Picasso.with(context)
                        .load(dealModelDataListArrayList.get(position).getTopDealImage())
                        .placeholder(R.drawable.bg_image) //this is optional the image to display while the url image is downloading
                        .into(holder.profilePicImg);
            }else{
                holder.profilePicImg.setImageResource(R.drawable.no_image_available);
            }
        }else{
            holder.profilePicImg.setImageResource(R.drawable.no_image_available);
        }
        holder.titleTextView.setText(dealModelDataListArrayList.get(position).getTopDealTitle());
        holder.descriptionTextView.setText(dealModelDataListArrayList.get(position).getTopDealShareUrl());
        return convertView;
    }

    private static class ViewHolder {
        private TextView titleTextView, descriptionTextView;
        ImageView profilePicImg;
    }

}
