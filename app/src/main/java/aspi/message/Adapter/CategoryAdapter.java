package aspi.message.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aspi.message.Model.CategoryModel;
import aspi.message.MainActivity;
import aspi.message.R;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.contact> {

    private  List<CategoryModel> CL;
    private Context context;
    private int img[] = {
            R.drawable.heart2,
            R.drawable.fun1,
            R.drawable.fun1,
            R.drawable.fun1,
            R.drawable.fun1,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.happyy,
            R.drawable.masseg,
            R.drawable.bookopenvariant,
            R.drawable.fun1,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,//father
            R.drawable.masseg,//mother
            R.drawable.masseg,//charshanbe
            R.drawable.masseg,//crismas
            R.drawable.day,
            R.drawable.night,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg,
            R.drawable.masseg};


    public CategoryAdapter(List<CategoryModel> contentList, Context contexts){
        this.CL=contentList;
        context=contexts;
    }

    @Override
    public contact onCreateViewHolder(ViewGroup parent, int viewType) {
        View recyclerlist= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category,parent,false);
        return new contact(recyclerlist);
    }

    @Override
    public void onBindViewHolder(contact holder, int position) {
        CategoryModel conlist=CL.get(position);
        holder.name_list.setText(conlist.name);
        holder.img_list.setImageResource(img[position]);
    }

    @Override
    public int getItemCount() {
        return CL.size();
    }

    public class contact extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView img_list;
        public TextView name_list;

        public contact(View itemView) {
            super(itemView);
            img_list= (ImageView) itemView.findViewById(R.id.listimg);
            name_list= (TextView) itemView.findViewById(R.id.listtext);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            context=itemView.getContext();
        }

        public void onClick(View v) {
            CategoryModel conlist=CL.get(getPosition());
            MainActivity.po=1;
            MainActivity.potion=conlist.id_group;
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
}
