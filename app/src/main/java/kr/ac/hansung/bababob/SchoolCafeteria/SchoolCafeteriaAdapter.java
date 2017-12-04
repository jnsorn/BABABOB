package kr.ac.hansung.bababob.SchoolCafeteria;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import kr.ac.hansung.bababob.MainActivity;
import kr.ac.hansung.bababob.R;

/**
 * Created by Jina on 2017-11-26.
 */


public class SchoolCafeteriaAdapter extends RecyclerView.Adapter<SchoolCafeteriaAdapter.ViewHolder>{

    private Context mContext;
    private List<SchoolCafeteria> mCafeterias;

    public SchoolCafeteriaAdapter(Context context, List<SchoolCafeteria> cafeterias) {
        mContext = context;
        mCafeterias = cafeterias;
    }

    @Override
    public SchoolCafeteriaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.school_cafeteria_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SchoolCafeteriaAdapter.ViewHolder holder, int position) {
        SchoolCafeteria cafeteria = mCafeterias.get(position);
        TextView textView1 = holder.cafeteriaTextView;
        textView1.setText(cafeteria.getCafeteria());
        TextView textView2 = holder.cafeteriaTimeTextView;
        textView2.setText(cafeteria.getTime());
    }

    @Override
    public int getItemCount() {
        return mCafeterias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView cafeteriaTextView;
        private TextView cafeteriaTimeTextView;
        private ImageButton cafeteriaDetailBtn;
        private TextView cafeteriaBtn;
        private LinearLayout cafeteriaBtnLayout;
        private boolean isCafeteriaDetailOpen = false;
        private RecyclerView rvSchoolCafeteriaMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            cafeteriaTextView = (TextView) itemView.findViewById(R.id.cafeteria_name);
            cafeteriaTimeTextView = (TextView) itemView.findViewById(R.id.cafeteria_time);
            cafeteriaDetailBtn = (ImageButton) itemView.findViewById(R.id.cafeteria_detail_button);
            cafeteriaDetailBtn.setOnClickListener(this);
            cafeteriaBtnLayout = (LinearLayout) itemView.findViewById(R.id.cafeteria_detail_button_layout);
            cafeteriaBtnLayout.setOnClickListener(this);
            rvSchoolCafeteriaMenu = (RecyclerView) itemView.findViewById(R.id.school_cafeteria_menu_recycler_view);
            cafeteriaBtn = (TextView) itemView.findViewById(R.id.cafeteria_menu_more);
            cafeteriaBtn.setOnClickListener(this);
            //SchoolCafeteriaMenuAdapter adapter = new SchoolCafeteriaMenuAdapter(mContext,SchoolCafeteriaMenu.getMenus(getLayoutPosition()));
            //rvSchoolCafeteriaMenu.setAdapter(adapter);
        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.cafeteria_detail_button_layout || view.getId()==R.id.cafeteria_detail_button){
                if(!isCafeteriaDetailOpen){
                    cafeteriaDetailBtn.animate().rotation(180).start();
                    SchoolCafeteriaMenuAdapter adapter = new SchoolCafeteriaMenuAdapter(mContext,SchoolCafeteriaMenu.getMenus(getLayoutPosition()));
                    rvSchoolCafeteriaMenu.setAdapter(adapter);
                    cafeteriaBtn.setVisibility(View.VISIBLE);
                    isCafeteriaDetailOpen = true;
                }else if(isCafeteriaDetailOpen){
                    cafeteriaDetailBtn.animate().rotation(0).start();
                    rvSchoolCafeteriaMenu.setAdapter(null);
                    cafeteriaBtn.setVisibility(View.GONE);
                    isCafeteriaDetailOpen = false;
                }
            } else if(view.getId()==R.id.cafeteria_menu_more){
                Bundle bundle = new Bundle();
                bundle.putString("change", "SchoolCafeteriaInfoActivity");
                bundle.putInt("CafeteriaName",getLayoutPosition());
                Message message = new Message();
                message.setData(bundle);
                MainActivity.myHandler.sendMessage(message);
            }
        }
    }
}
