package aspi.message.Adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import aspi.message.Helper.DatabaseHelper;
import aspi.message.Helper.MessageHelper;
import aspi.message.MainActivity;
import aspi.message.Model.MessageModel;
import aspi.message.R;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.contact> {

    List<MessageModel> CL;
    Context context;
    DatabaseHelper Data;

    public WishListAdapter(List<MessageModel> contentList, Context contexts) {

        CL = contentList;
        context = contexts;
    }

    @Override
    public contact onCreateViewHolder(ViewGroup parent, int viewType) {
        View recyclerlist = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message, parent, false);
        return new contact(recyclerlist);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final contact holder, int position) {
        final MessageModel conlist = CL.get(position);
        holder.text_show.setText(conlist.text);

        if (conlist.love.equals("1")) {
            holder.love_text.setBackgroundResource(R.drawable.heart);
        }

        holder.love_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Data = new DatabaseHelper(context);
                try {
                    Data.open();
                    if (conlist.love.equals("1")) {
                        Data.update("love", "0", "mesage", Integer.parseInt(conlist.id));
                        conlist.love = "0";
                        holder.love_text.setBackgroundResource(R.drawable.heart1);
                        MainActivity.getWishList(context, true);
                    } else {
                        Data.update("love", "1", "mesage", Integer.parseInt(conlist.id));
                        conlist.love = "1";
                        holder.love_text.setBackgroundResource(R.drawable.heart);
                    }
                    Data.close();
                } catch (Exception e) {
                }
            }
        });


        holder.send_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + ""));
                    intent.putExtra("sms_body", conlist.text);
                    ((Activity) context).startActivity(intent);
                } catch (Exception e) {

                }

            }
        });

        holder.copy_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(conlist.text);
                MessageHelper.TOAST(context, "متن کپی شد...!");
            }
        });

    }

    @Override
    public int getItemCount() {
        return CL.size();
    }


    public class contact extends RecyclerView.ViewHolder {

        public TextView text_show;
        public Button send_text, copy_text, love_text, delete_text;

        public contact(View itemView) {
            super(itemView);
            text_show = (TextView) itemView.findViewById(R.id.text_show);
            send_text = (Button) itemView.findViewById(R.id.send_show);
            copy_text = (Button) itemView.findViewById(R.id.copy_show);
            love_text = (Button) itemView.findViewById(R.id.love_show);
        }
    }


}