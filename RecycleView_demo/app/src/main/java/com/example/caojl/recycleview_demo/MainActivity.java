package com.example.caojl.recycleview_demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{
    /**
     * 数据集合
     */
    private List<String> mDatas;

    //控件
    private RecyclerView mRecyclerView;

    //适配器
    private MyAdapter mAdapter;

    private SwipeRefreshLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatas = new ArrayList<>();
        ininViews();
        initData();
        initAdapter();
        initRefreshLayout();

    }


    private void initRefreshLayout() {
        //设置刷新的颜色
        mLayout.setColorSchemeResources(
                android.R.color.background_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark);
        //拖动多长的时候开始刷新
        mLayout.setDistanceToTriggerSync(100);

        mLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorAccent));

        //设置大小
        mLayout.setSize(SwipeRefreshLayout.LARGE);
        //刷新监听器
        mLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 10; i++){
                            mAdapter.addData("new City " + i,i);
                        }
                        //通知刷新
                        mAdapter.notifyItemRangeChanged(0,10);
                        mRecyclerView.scrollToPosition(5);

                        //完成后调用完成的方法
                        mLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
    }

    //初始化布局控件
    private void ininViews() {
        mRecyclerView = findViewById(R.id.recycleview);
        mLayout = findViewById(R.id.refreshLayout);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }


    private void initAdapter() {
        //初始化adapter
        mAdapter = new MyAdapter(mDatas);
        /**
         * Support库提供了两个现成的子类：LinearLayoutManager和StaggeredGridLayoutManager。
         * 前者可以获得和ListView一样的布局，还可以是水平方向的；后者则提供了形如GridView的布局。
         */
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//ListView效果
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));//ListView效果
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));//gridView效果
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));//gridView效果


        //设置adapter
        mRecyclerView.setAdapter(mAdapter);
        //画横线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //默认动画   貌似官方就提供了这一种默认的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /**
         * 在这我们就能实现item的点击事件了
         */
        mAdapter.setListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, String str) {
                Toast.makeText(MainActivity.this,mDatas.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 初始化集合数据
     */
    private void initData() {
        mDatas.add("New York");
        mDatas.add("Bei Jing");
        mDatas.add("Boston");
        mDatas.add("London");
        mDatas.add("San Zhou");
        mDatas.add("Chicago");
        mDatas.add("Shang");
        mDatas.add("Tian Jin");
        mDatas.add("Zheng");
        mDatas.add("Hang zh");
        mDatas.add("Guang");
        mDatas.add("Fu Gou");
        mDatas.add("Zhou fu");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                mAdapter.addData("Xia Men", 3);
                mAdapter.notifyItemRangeChanged(3,mDatas.size()-3);
                break;
            case R.id.btn_delete:
                mAdapter.remove(4);
                break;
            default:
                break;
        }
    }
}
