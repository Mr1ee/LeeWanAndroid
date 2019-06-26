package com.lee.leewanandroid.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lee.leewanandroid.R;
import com.lee.leewanandroid.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description: DemoFlipperActivity
 * @Author: lihuayong
 * @CreateDate: 2019-06-26 15:54
 * @UpdateUser:
 * @UpdateDate: 2019-06-26 15:54
 * @UpdateRemark:
 * @Version: 1.0
 */
public class DemoFlipperActivity extends AppCompatActivity {

    private List<Integer> colors = new ArrayList<>();
    private List<Pair<Integer, String>> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_flipper);
        initData();

        ViewFlipper flipper = findViewById(R.id.flipper);
        flipper.setAdapter(new FlipperAdapter(items));
        flipper.startFlipping();
    }

    private void initData() {
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);
        items.add(new Pair<>(R.drawable.ic_like, "感受停在我发端的指尖"));
        items.add(new Pair<>(R.drawable.ic_like, "如何瞬间 冻结时间"));
        items.add(new Pair<>(R.drawable.ic_action_browser, "记住望着我坚定的双眼"));
        items.add(new Pair<>(R.drawable.ic_action_share, "也许已经 没有明天"));
        items.add(new Pair<>(R.drawable.ic_about, "面对浩瀚的星海"));
        items.add(new Pair<>(R.drawable.ic_action_share, "我们微小得像尘埃"));
        items.add(new Pair<>(R.drawable.ic_like, "漂浮在 一片无奈"));
        items.add(new Pair<>(R.drawable.ic_about, "缘份让我们相遇乱世以外"));
        items.add(new Pair<>(R.drawable.ic_action_browser, "命运却要我们危难中相爱"));
        items.add(new Pair<>(R.drawable.ic_action_share, "也许未来遥远在光年之外"));
        items.add(new Pair<>(R.drawable.ic_about, "我愿守候未知里为你等待"));
        items.add(new Pair<>(R.drawable.ic_action_share, "我没想到 为了你 我能疯狂到"));
        items.add(new Pair<>(R.drawable.ic_like, "山崩海啸 没有你 根本不想逃"));
        items.add(new Pair<>(R.drawable.ic_like, "我的大脑 为了你 已经疯狂到"));
        items.add(new Pair<>(R.drawable.ic_about, "脉搏心跳 没有你 根本不重要"));
        items.add(new Pair<>(R.drawable.ic_action_browser, "一双围在我胸口的臂弯"));
        items.add(new Pair<>(R.drawable.ic_action_share, "足够抵挡 天旋地转"));
        items.add(new Pair<>(R.drawable.ic_action_share, "一种执迷不放手的倔强"));
        items.add(new Pair<>(R.drawable.ic_about, "足以点燃 所有希望"));
        items.add(new Pair<>(R.drawable.ic_action_share, "宇宙磅礡而冷漠"));
        items.add(new Pair<>(R.drawable.ic_like, "我们的爱微小却闪烁"));
        items.add(new Pair<>(R.drawable.ic_about, "颠簸 却如此忘我"));
        items.add(new Pair<>(R.drawable.ic_action_browser, "也许航道以外 是醒不来的梦"));
        items.add(new Pair<>(R.drawable.ic_action_share, "乱世以外 是纯粹的相拥"));
    }

    class FlipperAdapter extends RecyclerView.Adapter<VH> {
        private List<Pair<Integer, String>> list;

        FlipperAdapter(List<Pair<Integer, String>> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_flipper_child, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            holder.bind(list.get(position).first, list.get(position).second);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class VH extends RecyclerView.ViewHolder {
        private TextView tvLyric;
        private ImageView iv;

        VH(@NonNull View itemView) {
            super(itemView);
            tvLyric = itemView.findViewById(R.id.tv_child);
            iv = itemView.findViewById(R.id.iv_child);
        }

        void bind(int drawableId, String lyric) {
            iv.setImageResource(drawableId);
            tvLyric.setText(lyric);
            tvLyric.setTextColor(colors.get(new Random(System.currentTimeMillis()).nextInt(5)));
        }
    }
}
