package kr.ac.hansung.bababob.SchoolCafeteria;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import kr.ac.hansung.bababob.MainActivity;
import kr.ac.hansung.bababob.R;

/**
 * Created by Jina on 2017-11-26.
 */


public class SchoolCafeteriaAdapter extends RecyclerView.Adapter<SchoolCafeteriaAdapter.ViewHolder>{

    private Context mContext;
    private List<SchoolCafeteria> mCafeterias;
    //get professor menu data by Jsoup
    public static SchoolCafeteriaProfessorMenu[] schoolCafeteriaProfessorMenus = new SchoolCafeteriaProfessorMenu[5];

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
        //Log.e("jina",""+position);
        if(position == SchoolCafeteria.PROFESSOR_CAFETERIA){
            Log.e("jinaa","prof"+position);
            new JsoupRecyclerView(holder).execute();
        } else if(position == SchoolCafeteria.STUDENT_CAFETERIA){
            Log.e("jinaa","stu"+position);
            SchoolCafeteriaMenuAdapter adapter = new SchoolCafeteriaMenuAdapter(mContext,SchoolCafeteriaMenu.getMenus(position));
            holder.rvSchoolCafeteriaMenu.setAdapter(adapter);
        }
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
        private boolean isCafeteriaDetailOpen = true;
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
            cafeteriaBtn.setVisibility(View.VISIBLE);
            cafeteriaBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.cafeteria_detail_button_layout || view.getId()==R.id.cafeteria_detail_button){
                if(!isCafeteriaDetailOpen){
                    cafeteriaDetailBtn.animate().rotation(0).start();
                    SchoolCafeteriaMenuAdapter adapter = new SchoolCafeteriaMenuAdapter(mContext,SchoolCafeteriaMenu.getMenus(getAdapterPosition()));
                    rvSchoolCafeteriaMenu.setAdapter(adapter);
                    cafeteriaBtn.setVisibility(View.VISIBLE);
                    isCafeteriaDetailOpen = true;
                }else if(isCafeteriaDetailOpen){
                    cafeteriaDetailBtn.animate().rotation(180).start();
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

    private class JsoupRecyclerView extends AsyncTask<Void,Void,Void> {

        SchoolCafeteriaAdapter.ViewHolder holder;

        public JsoupRecyclerView(SchoolCafeteriaAdapter.ViewHolder holder) {
            super();
            this.holder = holder;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            for(int i=0;i<5;i++) {
                schoolCafeteriaProfessorMenus[i] = new SchoolCafeteriaProfessorMenu();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            StringBuilder builder = new StringBuilder();

            try {
                Document doc = Jsoup.connect("http://www.hansung.ac.kr/web/www/life_03_01_t2").get();
                Element tbody = doc.select("table").first();
                Elements rows = tbody.select("tr");

                for(int i=0; i<5; i++) {
                    Elements tds = rows.get(1).select("td");
                    String str = tds.get(i).text();
                    String tmp = "";
                    for (String string : str.split(" ")) {
                        tmp += string + " / ";
                    }
                    schoolCafeteriaProfessorMenus[i].setLunch(new SchoolCafeteriaMenu(tmp, 0));
                }

                for(int i=0; i<5; i++) {
                    Elements tds = rows.get(2).select("td");
                    String str = tds.get(i+1).text();
                    String tmp = "";
                    for (String string : str.split(" ")) {
                        if(!string.equals(""))
                            tmp += string + " / ";
                    }
                    schoolCafeteriaProfessorMenus[i].setDinner(new SchoolCafeteriaMenu(tmp, 0));
                }

            } catch (IOException e) {
                builder.append("Error : ").append(e.getMessage()).append("\n");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SchoolCafeteriaMenuAdapter adapter = new SchoolCafeteriaMenuAdapter(mContext,SchoolCafeteriaMenu.getMenus(SchoolCafeteria.PROFESSOR_CAFETERIA));
            holder.rvSchoolCafeteriaMenu.setAdapter(adapter);
        }
    }
}
