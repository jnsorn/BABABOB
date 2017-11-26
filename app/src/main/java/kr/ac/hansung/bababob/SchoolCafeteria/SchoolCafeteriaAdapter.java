package kr.ac.hansung.bababob.SchoolCafeteria;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import kr.ac.hansung.bababob.R;

/**
 * Created by Jina on 2017-11-26.
 */


public class SchoolCafeteriaAdapter extends RecyclerView.Adapter<SchoolCafeteriaAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView cafeteriaTextView;
        private ImageButton cafeteriaDetailBtn;
        private boolean isCafeteriaDetailOpen = false;

        public ViewHolder(View itemView) {
            super(itemView);
            cafeteriaTextView = (TextView) itemView.findViewById(R.id.cafeteria_name);
            cafeteriaDetailBtn = (ImageButton) itemView.findViewById(R.id.cafeteria_detail_button) ;
            cafeteriaDetailBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.cafeteria_detail_button:{
                    if(!isCafeteriaDetailOpen){
                        cafeteriaDetailBtn.animate().rotation(180).start();
                        isCafeteriaDetailOpen = true;
                    }else if(isCafeteriaDetailOpen){
                        cafeteriaDetailBtn.animate().rotation(0).start();
                        isCafeteriaDetailOpen = false;
                    }
                    break;
                }
            }
        }
    }

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
        TextView textView = holder.cafeteriaTextView;
        textView.setText(cafeteria.getCafeteria());
    }

    @Override
    public int getItemCount() {
        return mCafeterias.size();
    }
}
