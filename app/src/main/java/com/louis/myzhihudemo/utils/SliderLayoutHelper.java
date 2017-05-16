package com.louis.myzhihudemo.utils;

import android.content.Context;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.louis.myzhihudemo.api.bean.HomeStory;

/**
 * Created by Louis on 2017/5/16.
 */

public class SliderLayoutHelper {

    public static void init(Context context, SliderLayout layout, HomeStory stories) {
        for (HomeStory.HomeStories story : stories.top_stories) {
            TextSliderView textSliderView = new TextSliderView(context);
            System.out.println("===========");
            System.out.println(story.title);
            System.out.println(story.image);
            System.out.println("===========");

            textSliderView.description(story.title)
                    .image(story.image)
                    .empty(DefIconFactory.provideIcon())
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            ToastUtils.showToast("点击了大图");

                        }
                    });
            layout.addSlider(textSliderView);
        }
        layout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        layout.setPresetTransformer(SliderLayout.Transformer.Accordion);// 设置切换动画
        layout.setCustomAnimation(new DescriptionAnimation());
        layout.setDuration(4000);

    }

}
