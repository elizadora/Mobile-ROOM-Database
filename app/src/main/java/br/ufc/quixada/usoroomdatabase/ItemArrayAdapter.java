package br.ufc.quixada.usoroomdatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemArrayAdapter extends  RecyclerView.Adapter<ItemArrayAdapter.ViewHolder>{
    private int listItemLayout;
    private ArrayList<Item> itemList;


    public ItemArrayAdapter(int layoutId, ArrayList<Item> itemList){
        this.listItemLayout = layoutId;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView itemName = holder.itemName;
        TextView itemCourse = holder.itemCourse;
        TextView itemAge = holder.itemAge;

        itemName.setText(itemList.get(position).getName());
        itemCourse.setText(itemList.get(position).getCourse());
        itemAge.setText(String.valueOf(itemList.get(position).getAge()));
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemName;
        public TextView itemCourse;
        public TextView itemAge;

        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemCourse = (TextView) itemView.findViewById(R.id.item_course);
            itemAge = (TextView) itemView.findViewById(R.id.item_age);
        }

        @Override
        public void onClick(View v) {
            String name = itemName.getText().toString();
            Toast.makeText(v.getContext(), "Você selecionou: " + name, Toast.LENGTH_SHORT).show();
        }

    }
}
