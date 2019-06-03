package com.example.anonymous.cikgood.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.anonymous.cikgood.R;
import com.example.anonymous.cikgood.config.ServerConfig;
import com.example.anonymous.cikgood.models.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import ui.activities.MessageActivity;

public class GuruFavAdapter extends RecyclerView.Adapter<GuruFavAdapter.ViewHolder>{

    private Context context;
    private List<User> users;
    private RequestOptions requestOptions;
    private RoundedCornersTransformation reRoundedCornersTransformation;
    private CenterCrop centerCrop;

    public GuruFavAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public GuruFavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_guru_fav, viewGroup, false);
        return new GuruFavAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuruFavAdapter.ViewHolder viewHolder, int i) {
        final User user = users.get(i);
        viewHolder.tvNama.setText(user.getNama());
        viewHolder.tvStatus.setText(user.getStatus());
        Log.d("Data User",""+user);

//            if (user.getImageURL().equals("default")){
//                viewHolder.profile_image.setImageResource(R.drawable.man);
//
//            }else{
//                Glide.with(context).load(user.getImageURL()).into(viewHolder.profile_image);
//            }

//        val radius = context.resources.getDimensionPixelSize(R.dimen.radius)
//        var requestOptions = RequestOptions()
//
//        val roundTransform = RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.TOP)
//        requestOptions = requestOptions.transform(CenterCrop(), roundTransform)
//        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//
//        if(scaleType == ImageView.ScaleType.CENTER_INSIDE) {
//            requestOptions = requestOptions.centerInside()
//        }
//
//        Glide.with(context)
//                .load(it)
//                .apply(requestOptions)
//                .into(imageView)

//        Integer radius = context.getResources().getDimensionPixelSize(R.dimen.radius);
//        requestOptions = new RequestOptions();
//        centerCrop = new CenterCrop();
//        reRoundedCornersTransformation = new RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.TOP);
////        requestOptions = requestOptions.transform(reRoundedCornersTransformation);
//        requestOptions = requestOptions.transform(centerCrop, reRoundedCornersTransformation);

//        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
float curveRadius = 20F;
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
    ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0,0, view.getWidth(), (int) (view.getHeight()+curveRadius), curveRadius);
        }
    };
    viewHolder.profile_image.setOutlineProvider(viewOutlineProvider);
    viewHolder.profile_image.setClipToOutline(true);
}

//        Picasso
//                .get()
//                .load(ServerConfig.GURU_PATH+user.getPhotoProfile())
//                .into(viewHolder.profile_image);

        Glide.with(context)
                .load(ServerConfig.GURU_PATH+user.getPhotoProfile())
                .into(viewHolder.profile_image);

        System.out.println("photonya path :"+ServerConfig.GURU_PATH+user.getPhotoProfile());
        System.out.println("photonya :"+user.getPhotoProfile());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = user.getNama();
                int id = user.getIdGuru();
                String photo = user.getPhotoProfile();

                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("nama", nama);
                intent.putExtra("id", id);
                intent.putExtra("photo_profile", photo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNama, tvStatus;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama_guru);
            tvStatus = itemView.findViewById(R.id.tv_guru_status);
            profile_image = itemView.findViewById(R.id.iv_image_guru);
        }
    }
}
