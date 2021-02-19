package com.smi.test.views.home.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.smi.test.R;
import com.smi.test.models.Brand;
import com.smi.test.models.Dashboard;
import com.smi.test.models.Purchase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardVH>  {

    private FragmentActivity context;
    private List<Dashboard> dashboardList;
    private List<Dashboard> dashboardListFull;

    public DashboardAdapter(FragmentActivity context, List<Dashboard> dashboardList) {
        this.context = context;
        this.dashboardList = dashboardList;
        this.dashboardListFull = new ArrayList<>(dashboardList);
    }



    @NonNull
    @Override
    public DashboardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_item, parent, false);

        return new DashboardAdapter.DashboardVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardVH holder, int position) {
        Dashboard currentDashboard = dashboardList.get(position);

/*        if (currentDashboard.getCurrency().equals("EUR"))
             holder.ammount.setText(currentDashboard.getAmount() + "€");
         else
              holder.ammount.setText(currentDashboard.getAmount() + "$");*/

        //holder.img.setImageResource(currentDashboard.getImg());

        try {
            Glide.with(context).load(currentDashboard.getPic()).into(holder.img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.ammount.setText(currentDashboard.getAmmount());
        holder.title.setText(currentDashboard.getName());

    }

    @Override
    public int getItemCount() {
        return dashboardList.size();
    }

    public class DashboardVH extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.ammount)
        TextView ammount;

        @BindView(R.id.title)
        TextView title;

        public DashboardVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
