package com.mdc.project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;


//创建一个万能适配器！！！
public abstract class MyRecyclerViewAdapter<T> extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    //创建VH
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> views;
        private View view;
        //构造器
        public MyViewHolder(View v){
            super(v);
            view = v;
            views = new SparseArray<>();
        }

        public static MyViewHolder get(Context _context, ViewGroup _viewGroup, int _layoutId) {
            View _view = LayoutInflater.from(_context).inflate(_layoutId, _viewGroup, false);
            MyViewHolder holder = new MyViewHolder(_view);
            return holder;
        }

        public <T extends View> T getView(int _viewId) {
            View _view = views.get(_viewId);
            if (_view == null) {
                // 创建view
                _view = view.findViewById(_viewId);
                // 将view存入views
                views.put(_viewId, _view);
            }
            return (T)_view;
        }
    }

    //大部分内容都不变 -> onBindViewHolder中加入convert抽象方法！！！
    //注意这里是T， 不是Food, 且需要类传入
    private List<T> mDatas;
    private MyRecyclerViewAdapter.OnItemClickListener onItemClickListener;



    public MyRecyclerViewAdapter(List<T> mDatas){
        //初始化数据集合
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //加载布局
        MyViewHolder holder = MyViewHolder.get(parent.getContext(), parent, R.layout.recyclerview_item);
        return holder;
    }


    //定义抽象方法convert
    public abstract void convert(MyViewHolder holder, T data, int position);

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        convert(holder, mDatas.get(position), position);

        //OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount()
    {
        if(mDatas == null){
            return 0;
        }
        return mDatas.size();
    }

    public void addNewItem(T f) {
        if(mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(0, f);
        notifyItemInserted(0);
    }

    public void deleteItem(int pos) {
        if(mDatas == null || mDatas.isEmpty()) {
            return;
        }
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    public T getItem(int pos){
        if(mDatas == null || mDatas.isEmpty())
            return null;
        return mDatas.get(pos);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public  void setOnItemClickListener(OnItemClickListener _onItemClickListener) {
        this.onItemClickListener = _onItemClickListener;
    }


}

//注释