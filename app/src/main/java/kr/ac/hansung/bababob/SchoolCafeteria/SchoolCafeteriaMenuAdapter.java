package kr.ac.hansung.bababob.SchoolCafeteria;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import kr.ac.hansung.bababob.R;

/**
 * Created by Jina on 2017-11-25.
 */

public class SchoolCafeteriaMenuAdapter extends RecyclerView.Adapter<SchoolCafeteriaMenuAdapter.ViewHolder>{

    private Context mContext;
    private List<SchoolCafeteriaMenu> mMenus;

    public SchoolCafeteriaMenuAdapter(Context context, List<SchoolCafeteriaMenu> menus) {
        mContext = context;
        mMenus= menus;
    }

    @Override
    public SchoolCafeteriaMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.school_cafeteria_menu_item, parent, false);
        SchoolCafeteriaMenuAdapter.ViewHolder viewHolder = new SchoolCafeteriaMenuAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SchoolCafeteriaMenuAdapter.ViewHolder holder, int position) {
        SchoolCafeteriaMenu cafeteria = mMenus.get(position);
        TextView textView1 = holder.cafeteriaMenuTextView;
        TextView textView2 = holder.cafeteriaMenuPriceTextView;
        textView1.setText(cafeteria.getMenu());
        textView2.setText(cafeteria.getPrice());
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cafeteriaMenuTextView;
        private TextView cafeteriaMenuPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cafeteriaMenuTextView = (TextView) itemView.findViewById(R.id.cafeteria_menu);
            cafeteriaMenuPriceTextView = (TextView) itemView.findViewById(R.id.cafeteria_menu_price);
        }
    }
}
