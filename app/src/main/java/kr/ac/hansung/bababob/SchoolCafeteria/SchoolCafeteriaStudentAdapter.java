package kr.ac.hansung.bababob.SchoolCafeteria;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kr.ac.hansung.bababob.R;

/**
 * Created by Jina on 2017-12-08.
 */

public class SchoolCafeteriaStudentAdapter extends RecyclerView.Adapter<SchoolCafeteriaStudentAdapter.ViewHolder>{

    private Context mContext;

    private List<String> mMenuGroup = new ArrayList<String>();
    private List<String> mMenuGroupDetails = new ArrayList<String>();

    public SchoolCafeteriaStudentAdapter(Context context) {
        mContext = context;
        mMenuGroup.add("면류 & 찌개 & 김밥");
        mMenuGroupDetails.add("Roll & Noodles");
        mMenuGroup.add("덮밥류 & 비빔밥");
        mMenuGroupDetails.add("The bab");
        mMenuGroup.add("볶음밥 & 오므라이스 & 돈까스");
        mMenuGroupDetails.add("Fry & Rice");
    }

    @Override
    public SchoolCafeteriaStudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View menuGroupView = inflater.inflate(R.layout.school_cafeteria_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(menuGroupView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SchoolCafeteriaStudentAdapter.ViewHolder holder, int position) {
        TextView textView1 = holder.menuGroupTextView;
        textView1.setText(mMenuGroup.get(position));
        TextView textView2 = holder.menuGroupDetailTextView;
        textView2.setText(mMenuGroupDetails.get(position));
        Log.e("jinaa","student menu"+position);
        SchoolCafeteriaMenuAdapter adapter = new SchoolCafeteriaMenuAdapter(mContext,SchoolCafeteriaStudentMenu.getMenus(position));
        holder.rvSchoolCafeteriaStudentMenu.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mMenuGroup.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView menuGroupTextView;
        private TextView menuGroupDetailTextView;
        private ImageButton menuGroupDetailBtn;
        private LinearLayout menuGroupBtnLayout;
        private boolean isMenuGroupDetailOpen = true;
        private RecyclerView rvSchoolCafeteriaStudentMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            menuGroupTextView = (TextView) itemView.findViewById(R.id.cafeteria_name);;
            menuGroupDetailTextView = (TextView) itemView.findViewById(R.id.cafeteria_time);
            menuGroupDetailBtn = (ImageButton) itemView.findViewById(R.id.cafeteria_detail_button);
            menuGroupDetailBtn.setOnClickListener(this);
            menuGroupBtnLayout = (LinearLayout) itemView.findViewById(R.id.cafeteria_detail_button_layout);
            menuGroupBtnLayout.setOnClickListener(this);
            rvSchoolCafeteriaStudentMenu = (RecyclerView) itemView.findViewById(R.id.school_cafeteria_menu_recycler_view);
        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.cafeteria_detail_button_layout || view.getId()==R.id.cafeteria_detail_button){
                if(!isMenuGroupDetailOpen){
                    menuGroupDetailBtn.animate().rotation(0).start();
                    SchoolCafeteriaMenuAdapter adapter = new SchoolCafeteriaMenuAdapter(mContext, SchoolCafeteriaStudentMenu.getMenus(getAdapterPosition()));
                    rvSchoolCafeteriaStudentMenu.setAdapter(adapter);
                    isMenuGroupDetailOpen = true;
                }else if(isMenuGroupDetailOpen){
                    menuGroupDetailBtn.animate().rotation(180).start();
                    rvSchoolCafeteriaStudentMenu.setAdapter(null);
                    isMenuGroupDetailOpen = false;
                }
            }
        }
    }
}
