package com.example.anonymous.cikgood.adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anonymous.cikgood.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    Animation smalToBig, nothingToCome;

    public SliderAdapter(Context context){
        this.context = context;
    }

    // array

    public int[] slider_images = {
            R.drawable.eat_icon,
            R.drawable.sleep_icon,
            R.drawable.code_icon
    };

    public String[] slider_heading = {
            "EAT",
            "SLEEP",
            "CODE"
    };

    public String[] slider_desc = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Vestibulum ipsum metus, sollicitudin eget ex suscipit, ",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Vestibulum ipsum metus, sollicitudin eget ex suscipit, ",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Vestibulum ipsum metus, sollicitudin eget ex suscipit, "
    };

    @Override
    public int getCount() {
        return slider_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (ConstraintLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull View container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.slide_layout, (ViewGroup) container, false);

        ImageView sliderImageView = (ImageView) view.findViewById(R.id.slider_image);
        TextView sliderTitle = (TextView) view.findViewById(R.id.slider_title);
        TextView sliderDesc = (TextView) view.findViewById(R.id.slider_desc);

        sliderImageView.setImageResource(slider_images[position]);
        sliderTitle.setText(slider_heading[position]);
        sliderDesc.setText(slider_desc[position]);

        ((ViewGroup) container).addView(view);

        smalToBig = AnimationUtils.loadAnimation(context, R.anim.smaltobig);
        nothingToCome = AnimationUtils.loadAnimation(context, R.anim.nothingtocome);

        sliderImageView.setAnimation(smalToBig);
        sliderTitle.setAnimation(nothingToCome);
        sliderDesc.setAnimation(nothingToCome);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
