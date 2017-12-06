package com.example.abnlt.myphotozigtask;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ABN LT on 12/3/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static File outputFile2;
    private static ProgressDialog pDialog;
    private List<Listitem> listItems;
    Context context;
    public String myHTTpUrl = "http://pbmedia.pepblast.com/pz_challenge/assets/";
    File outputFile;

    public MyAdapter(List<Listitem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_xml, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Listitem listitem = listItems.get(position);
        holder.name.setText(listitem.getName());
        Picasso.with(context)
                .load(myHTTpUrl + listitem.getImageUrl())
                .into(holder.img);
        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Toast.makeText(context,"Please Wait File is Downloading.....",
                        Toast.LENGTH_LONG).show();
                new Thread() {

                    @Override
                    public void run() {

                        try{
                        //your code here
                         outputFile = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "filename.mp3");
                        downloadFile(myHTTpUrl + listitem.getSg(), outputFile, context);
                        outputFile2 = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "filename.mp4");
                        downloadFile(myHTTpUrl + listitem.getBg(), outputFile, context);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView img;
        public Button downloadBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvName);
            img = (ImageView) itemView.findViewById(R.id.imageView1);
            downloadBtn = (Button) itemView.findViewById(R.id.button);
        }
    }

    private static void downloadFile(String url, File outputFile,Context context) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();

            Intent intent = new Intent(context, PlayerActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("mp3",outputFile.getPath());
            intent.putExtra("mp4",outputFile2.getPath());
            context.startActivity(intent);
        } catch (FileNotFoundException e) {
            return; // swallow a 404
        } catch (IOException e) {
            return; // swallow a 404
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }


}
