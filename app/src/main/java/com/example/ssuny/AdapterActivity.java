package com.example.ssuny;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class AdapterActivity extends BaseAdapter {
    public ArrayList<Time> listviewitem = new ArrayList<Time>();
    private ArrayList<Time> arrayList = listviewitem;
    //백업 arrayList

    @Override public int getCount() {
        return arrayList.size();
    }

    @Override public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_theme, parent, false);

            TextView hourText = (TextView)convertView.findViewById(R.id.time);
            TextView minuteText = (TextView)convertView.findViewById(R.id.minute);
            TextView am_pm = (TextView)convertView.findViewById(R.id.am_pm);
            TextView month = (TextView)convertView.findViewById(R.id.month);
            TextView day = (TextView)convertView.findViewById(R.id.day);

            holder.hourText = hourText;
            holder.minuteText = minuteText;
            holder.am_pm = am_pm;
            holder.month = month;
            holder.day = day;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Time time = arrayList.get(position);
        holder.am_pm.setText(time.getAm_pm());
        holder.hourText.setText(time.getHour()+ "시");
        holder.minuteText.setText(time.getMinute()+ "분");
        holder.month.setText(time.getMonth()+ "월 ");
        holder.day.setText(time.getDay()+ "일");

        return convertView;
    }

    public void addItem(int hour, int minute, String am_pm, String month, String day) {
        Time time = new Time();
        time.setHour(hour);
        time.setMinute(minute);
        time.setAm_pm(am_pm);
        time.setMonth(month);
        time.setDay(day);
        listviewitem.add(time);
    }

    //List 삭제 method
    public void removeItem(int position) {
        if(listviewitem.size() < 1) {

        } else {
            listviewitem.remove(position);
        }
    }
    public void removeItem() {
        if(listviewitem.size() < 1) {

        } else {
            listviewitem.remove(listviewitem.size()-1);
        }
    }

    static class ViewHolder {
        TextView hourText, minuteText, am_pm, month, day;
    }
}
