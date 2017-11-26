package kr.ac.hansung.bababob.SchoolCafeteria;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kr.ac.hansung.bababob.R;

/**
 * Created by Jina on 2017-11-13.
 */

public class SchoolMenuAdapter extends RecyclerView.Adapter<SchoolMenuAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView menuTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            menuTextView = (TextView) itemView.findViewById(R.id.menu_name);
            menuTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
        }
    }

    private List<StudentMenu> mMenus;
    private Context mContext;

    public SchoolMenuAdapter(Context context, List<StudentMenu> menus) {
        mMenus = menus;
        mContext = context;
    }

    @Override
    public SchoolMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.school_cafeteria_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SchoolMenuAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        StudentMenu menu = mMenus.get(position);
        // Set item views based on your views and data model
        TextView textView = holder.menuTextView;
        textView.setText(menu.getMenu());
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }
}
