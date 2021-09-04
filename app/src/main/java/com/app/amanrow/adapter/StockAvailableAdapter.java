package com.app.amanrow.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.amanrow.R;
import com.app.amanrow.interfaces.ApiCallback;
import com.app.amanrow.pojo.ProductDetail;

import java.util.ArrayList;

/**
 * Created by Navya on 28-04-2016.
 */
public class StockAvailableAdapter extends RecyclerView.Adapter<StockAvailableAdapter.ViewHolder> {

    Context context;
    private ArrayList<ProductDetail> clinicArrayList = new ArrayList<>();
ApiCallback apiCallback;
    public StockAvailableAdapter(Context context, ApiCallback callback) {
        this.context = context;

        apiCallback=callback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_list, parent, false);
        ViewHolder vh;
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {





    }


    @Override
    public int getItemCount() {
        return 16;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_prdct_sn, txt_prdct_name, txt_prdct_qty;

        CardView card_view;

        public ViewHolder(View view) {
            super(view);

            txt_prdct_sn = (TextView) view.findViewById(R.id.txt_prdct_sn);
            txt_prdct_name = (TextView) view.findViewById(R.id.txt_prdct_name);
            txt_prdct_qty = (TextView) view.findViewById(R.id.txt_prdct_qty);
            card_view = (CardView) view.findViewById(R.id.card_view);
        }

    }


}
