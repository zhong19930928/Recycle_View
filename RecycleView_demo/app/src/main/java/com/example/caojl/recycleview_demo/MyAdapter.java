package com.example.caojl.recycleview_demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * ＠author zhonghuibin
 * create at 2017/9/20.
 * describe
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private LayoutInflater mInflater;

    private List<String> mLists;

    private OnItemClickListener mListener;

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyAdapter(List<String> lists) {
        mLists = lists;
    }

    //创建布局
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        //加载布局
        View view = mInflater.inflate(R.layout.item_recycle, parent,false);
        //返回viewholder
        return new MyViewHolder(view);
    }


    /**
     * 在指定的位置添加数据
     *
     * @param data
     * @param position
     */
    public void addData(String data, int position) {
        //通知布局在那个地方插入的数据
        if (mLists.size() >= position){
            mLists.add(position,data);
            notifyItemInserted(position);
            notifyItemRangeChanged(3,mLists.size()-3);
        }
    }

    /**
     * 移除特定的位置的数据
     *
     * @param position 索引位置
     */
    public void remove(int position) {
        if (mLists.size() >= position){
            mLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addData(List<String> datas) {
        mLists.addAll(datas);
        //通知添加数据，第一个位置 数据的数量
        notifyItemRangeChanged(0, datas.size());
    }

    //绑定布局
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mLists.get(position));
    }

    //获取到item的个数
    @Override
    public int getItemCount() {
        return mLists.size();
    }

    // 强制使用ViewHolder
    /**
     * 在ListView性能优化方面，Android就推荐使用ViewHolder来减少findViewById()的使用以提高效率。
     * 不过对于ListView上的ViewHolder，Android只是建议而非强制使用。不过因为使用ViewHolder
     * 模式太有意义了，所以在RecyclerView中ViewHolder就变成了必须使用的模式，Adapter要求返回的也
     * 从普通的View变成了ViewHolder。
     * 不过如果实现时没有自定义的一些View实际变量，ViewHolder也依然失去其意义。
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                mTextView = (TextView) itemView.findViewById(R.id.textview);
                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            /**
                             * getLayoutPosition（）  返回的是item的当前点击的位置
                             * Returns the position of the ViewHolder in terms of the latest layout pass.
                             */
                            int position = 0;
                            for (int i=0;i<mLists.size();i++){
                                if (mTextView.getText().equals(mLists.get(i))){
                                    position = i;
                                }
                            }
                            mListener.onClick(v, position, mLists.get(position));
                        }
                    }
                });
            }
        }
    }

    /**
     * 没有OnItemClickListener
     * 必须我们自己去实现
     */
    interface OnItemClickListener {
        void onClick(View v, int position, String str);
    }
}
