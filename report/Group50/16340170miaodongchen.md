# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 |2016  | 专业（方向） |软件工程  |
| 学号 |16340170  | 姓名 |缪东辰  |
| 电话 |  | Email |  |
| 开始日期 |  | 完成日期 |11.24

---

## 一、实验题目

王者荣耀英雄大全
---

## 二、基础实现内容以及项目扩展

* 王者荣耀英雄人物的增删改查功能。属性包含头像、称号、名字、位置、生存能力值、攻击伤害值、技能效果值、上手难度值等，其中头像是图片
* App启动时初始化包含10个英雄信息（不要求数据库，可以代码定义或xml）

* 项目拓展内容：数据库保存、UI界面美化、背景音乐
---

## 三、课堂实验结果
### (1)实验截图
我这次主要负责的部分是UI设计和recyclerviewAdapter。设计的几个界面的UI如下，
主界面
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/205017_3ca01da8_2162412.png "Screenshot_20181125-204610.png")
detail界面
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/205036_520b0035_2162412.png "Screenshot_20181125-204622.png")
修改界面
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/205051_10c27182_2162412.png "Screenshot_20181125-204627.png")
添加界面
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/205239_ef7247e9_2162412.png "Screenshot_20181125-204636.png")

### (2)实验步骤以及关键代码
1. RecyclerViewAdapter我用的是之前的万能适配器，在使用时修改代码即可，十分方便。

```
//ViewHolder如下，
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
//其他主要部分如下，
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
```

(3) 遇到的困难：<br>
    做修改界面时，想过用一个新的类似detail界面来修改，通过点击一个编辑键来实现，但是后来发现这样逻辑就变得复杂很多，对之后开发的同学也很不友好，于是用了和第一个项目相似的方法，通过FloatingActionButton来切换隐藏界面来实现功能。
  
---

## 四、课后实验结果
见于本文件夹的视频
---

## 五、实验思考及感想
* 应当预留更多的时间，尽量把任务在早期解决，这样后期才有更多的余裕
* 体验到了团队合作的重要性，优秀的团队合作能极大地提高效率
* 了解到了合作编代码时会遇到的困难，以及合理分工的重要性
---

#### 作业要求
* 命名要求：学号_姓名_实验编号，例如12345678_张三_lab1.md
* 实验报告提交格式为md
* 实验内容不允许抄袭，我们要进行代码相似度对比。如发现抄袭，按0分处理![输入图片说明]