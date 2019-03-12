# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 16级  | 专业（方向） | 软件工程 |
| 学号 | 16340310 | 姓名 | 周伟标 |
| 电话 | 17728023395 | Email | 929689204@qq.com |
| 开始日期 | 11/1 | 完成日期 | 11/25
---

# 期中项目
# 王者荣耀英雄大全

---  

---  

## 项目内容
* 一个包括王者荣耀英雄人物头像、称号、名字、位置、生存能力值、攻击伤害值、技能效果值、上手难度值等信息的APP
* 具体细节可以参考http://pvp.qq.com/web201605/herolist.shtml

---  

## 项目要求
* 王者荣耀英雄人物的增删改查功能。属性包含头像、称号、名字、位置、生存能力值、攻击伤害值、技能效果值、上手难度值等，其中头像是图片
* App启动时初始化包含10个英雄信息（不要求数据库，可以代码定义或xml）


---  

## 项目扩展（可选）
* 项目拓展部分，同学们可以通过使用相似的应用进行体验后总结优缺点，从而对自己的APP进行改进从而进一步的提升用户体验。
* 参考方向：数据库保存、UI界面美化、背景音乐、提供其他娱乐功能等

---  

## 验收内容
* App启动时是否包含10个英雄信息
* 是否能增删改查英雄
* 英雄是否含有10个属性
* 项目文档（实验报告、用户说明文档、小组分工）是否齐全，其中实验报告是每人一份，各自描述各自所负责的内容
* 是否有1min(建议视频长度控制在1min以内)展示视频，视频中需要包含对本组项目app的基础操作展示，同时进行语音说明


---


## 三、实验结果
### (1)实验截图
打开应用，出现一个英雄大全图

![](https://i.imgur.com/3vwM7j4.png)

点击定位分类按钮可以切换不同类别的英雄列表

![](https://i.imgur.com/PGUCrLN.png)

输入关键词可以搜索英雄

![](https://i.imgur.com/bJzu7Y9.png)

在主页面点击加号进入添加英雄界面

![](https://i.imgur.com/A6zsR89.png)

![](https://i.imgur.com/BAIWTAC.png)

长按可以删除英雄

![](https://i.imgur.com/unD9IS8.png)

点击英雄头像进入英雄详细页面

![](https://i.imgur.com/zYUXYOI.png)

点击右下角图标可以修改英雄信息

![](https://i.imgur.com/7tbvtbB.png)

点击右下角确认图标修改完成

![](https://i.imgur.com/2mnM7Pu.png)

---

### (2)实验步骤以及关键代码
#### 1.添加背景音乐
现在res里面创建raw文件夹，添加背景音乐文件，在MainActivity里添加函数playLocalFile
```java
//背景音乐
    private void playLocalFile() {
        player = MediaPlayer.create(this, R.raw.aov);
        //播放工程res目录下的raw目录中的音乐文件aov.mp3
        try {
            player.prepare();
        } catch (IllegalStateException e) {

        } catch (IOException e) {

        }
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                // 循环播放
                try {
                    mp.start();
                }
                catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });
    }
```
然后在Oncreate函数里面执行这函数，最后在onDestroy函数里面添加Mediaplayer的释放函数
```java
if(player.isPlaying()){
            player.stop();
        }
        player.release();
```

#### 2.实现定位分类按钮切换功能
在Oncreate函数里面实现一个RadioGroup的监听器，当点击不同RadioButton时根据Button内容切换英雄定位分类表
```java
//实现RadioGroup按钮查看不同类别英雄的功能
        final RadioGroup radioGroup =findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(((RadioButton)findViewById(checkedId)).getText().toString().equals("全部"))
                {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    recycleViewForSearch.setVisibility(View.INVISIBLE);
                }
                else {
                    dataForSearch.clear();
                    for(Character character: data) {
                        if (!character.getType().matches(((RadioButton)findViewById(checkedId)).getText().toString())) {
                            continue;
                        }
                        dataForSearch.add(character);
                    }
                    mAdapterForSearch.notifyDataSetChanged();
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    recycleViewForSearch.setVisibility(View.VISIBLE);
                }
            }
        });
```

#### 3.优化UI界面
修改美化xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.constraint.ConstraintLayout
        android:id="@+id/mainPage"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="visible">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:orientation="horizontal"
            android:id="@+id/select_bar"
            >
            <TextView
                android:layout_width="60dp"
                android:layout_height="50dp"
                android:text="定位"
                android:gravity="center"
                android:textColor="#FFFFFF"
                android:textSize="20sp"
                android:background="#3A5FCD"
                />

            <RadioGroup
                android:id="@+id/radio_group"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:checkedButton="@id/rb1"
                android:orientation="horizontal"

                >

                <RadioButton
                    android:id="@+id/rb1"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="全部"
                    android:textSize="10dp" />

                <RadioButton
                    android:id="@+id/rb2"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="坦克"
                    android:textSize="11dp" />

                <RadioButton
                    android:id="@+id/rb3"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="战士"
                    android:textSize="11dp" />

                <RadioButton
                    android:id="@+id/rb4"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="刺客"
                    android:textSize="11dp" />

                <RadioButton
                    android:id="@+id/rb5"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="法师"
                    android:textSize="11dp" />

                <RadioButton
                    android:id="@+id/rb6"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="射手"
                    android:textSize="11dp" />
            </RadioGroup>
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/search_bar"
            android:orientation="horizontal"
            app:layout_constraintTop_toBottomOf="@id/select_bar"
            >

            <TextView
                android:layout_width="60dp"
                android:layout_height="50dp"
                android:text="搜索"
                android:gravity="center"
                android:textColor="#FFFFFF"
                android:textSize="20sp"
                android:background="#3A5FCD"
                />

            <EditText
                android:id="@+id/edittext"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:hint="请输入你想要搜索的英雄名"
                android:textCursorDrawable="@null"
                android:textSize="18sp"
                android:layout_marginLeft="20dp"
                />

            <ImageView
                android:id="@+id/search_button1"
                android:clickable="true"
                android:focusable="true"
                android:layout_marginLeft="20dp"
                android:layout_width="30dp"
                android:layout_height="30dp"
                android:src="@mipmap/search"
                android:layout_gravity="center"
                />

        </LinearLayout>

        <android.support.v7.widget.RecyclerView
            android:id="@+id/recyclerview"
            android:dividerHeight="10dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintTop_toBottomOf="@id/search_bar"
            />

        <android.support.v7.widget.RecyclerView
            android:id="@+id/recycler_view_for_search"
            android:dividerHeight="10dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_constraintTop_toBottomOf="@id/search_bar"
            />

        <android.support.design.widget.FloatingActionButton
            android:id="@+id/floatingButton_add"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@mipmap/floatingbutton"
            android:backgroundTint="#FFFFFF"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            android:layout_margin="25dp" />
    </android.support.constraint.ConstraintLayout>
    <android.support.constraint.ConstraintLayout
        android:id="@+id/addPage"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="invisible">
        <ImageView
            android:id="@+id/detail_icon_add"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:src="@mipmap/mengqi"
            />

        <TextView
            android:id="@+id/tv1_add"
            android:layout_width="60dp"
            android:layout_height="33dp"
            android:text="称号"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"

            app:layout_constraintLeft_toRightOf="@+id/detail_icon_add"
            />

        <EditText
            android:id="@+id/ChenHao_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:gravity="center"
            android:hint="入梦之灵"
            android:textCursorDrawable="@null"
            android:textSize="12sp"
            app:layout_constraintLeft_toRightOf="@+id/tv1_add"
            android:paddingLeft="20dp"
            />

        <TextView
            android:id="@+id/tv2_add"
            android:layout_width="60dp"
            android:layout_height="34dp"
            android:text="名字"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"

            app:layout_constraintLeft_toRightOf="@+id/detail_icon_add"
            app:layout_constraintTop_toBottomOf="@+id/tv1_add"
            />

        <EditText
            android:id="@+id/name_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:gravity="center"
            android:hint="梦奇"
            android:textCursorDrawable="@null"
            android:textSize="12sp"
            android:paddingLeft="20dp"
            app:layout_constraintLeft_toRightOf="@+id/tv2_add"
            app:layout_constraintTop_toBottomOf="@+id/ChenHao_add"
            />

        <TextView
            android:id="@+id/tv3_add"
            android:layout_width="60dp"
            android:layout_height="34dp"
            android:text="定位"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintLeft_toRightOf="@+id/detail_icon_add"
            app:layout_constraintTop_toBottomOf="@+id/tv2_add"
            />

        <EditText
            android:id="@+id/Category_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:gravity="center"
            android:hint="坦克"
            android:textCursorDrawable="@null"
            android:textSize="12sp"
            android:paddingLeft="20dp"
            app:layout_constraintLeft_toRightOf="@+id/tv3_add"
            app:layout_constraintTop_toBottomOf="@+id/name_add"
            />


        <TextView
            android:id="@+id/tv4_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:text="生存能力"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintTop_toBottomOf="@+id/detail_icon_add"
            android:layout_marginTop="10dp"
            />

        <EditText
            android:id="@+id/ProgressBar1_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:gravity="center"
            android:inputType="number"
            android:hint="5"
            android:textCursorDrawable="@null"
            android:textSize="12sp"
            android:paddingLeft="20dp"
            app:layout_constraintLeft_toRightOf="@+id/tv4_add"
            app:layout_constraintTop_toBottomOf="@id/detail_icon_add"
            />


        <TextView
            android:id="@+id/tv5_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:text="攻击伤害"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintTop_toBottomOf="@+id/tv4_add"
            />
        <EditText
            android:id="@+id/ProgressBar2_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:gravity="center"
            android:hint="5"
            android:textCursorDrawable="@null"
            android:textSize="12sp"
            android:paddingLeft="20dp"
            app:layout_constraintLeft_toRightOf="@+id/tv5_add"
            app:layout_constraintTop_toBottomOf="@id/tv4_add"
            />


        <TextView
            android:id="@+id/tv6_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:text="技能效果"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintTop_toBottomOf="@+id/tv5_add"
            />

        <EditText
            android:id="@+id/ProgressBar3_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:gravity="center"
            android:hint="5"
            android:textCursorDrawable="@null"
            android:textSize="12sp"
            android:paddingLeft="20dp"
            app:layout_constraintLeft_toRightOf="@+id/tv6_add"
            app:layout_constraintTop_toBottomOf="@id/tv5_add"
            />


        <TextView
            android:id="@+id/tv7_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:text="上手难度"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintTop_toBottomOf="@+id/tv6_add"
            />
        <EditText
            android:id="@+id/ProgressBar4_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:gravity="center"
            android:hint="5"
            android:textCursorDrawable="@null"
            android:textSize="12sp"
            android:paddingLeft="20dp"
            app:layout_constraintLeft_toRightOf="@+id/tv7_add"
            app:layout_constraintTop_toBottomOf="@id/tv6_add"
            />

        <TextView
            android:id="@+id/tv8_add"
            android:layout_width="100dp"
            android:layout_height="34dp"
            android:text="技能效果"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintTop_toBottomOf="@+id/tv7_add"
            android:layout_marginTop="15dp"
            />
        <TextView
            android:id="@+id/tv9_add"
            android:layout_width="100dp"
            android:layout_height="50dp"
            android:text="被动技能"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintTop_toBottomOf="@+id/tv8_add"
            />
        <EditText
            android:id="@+id/PassiveSkill_add"
            android:layout_width="100dp"
            android:layout_height="50dp"
            android:gravity="center"
            android:hint="食梦"
            android:textCursorDrawable="@null"
            android:textSize="20sp"
            android:layout_marginLeft="15dp"
            app:layout_constraintTop_toBottomOf="@+id/tv8_add"
            app:layout_constraintLeft_toRightOf="@+id/tv9_add"
            />

        <TextView
            android:id="@+id/tv10_add"
            android:layout_width="100dp"
            android:layout_height="50dp"
            android:text="技能一"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintTop_toBottomOf="@+id/tv9_add"
            />
        <EditText
            android:id="@+id/Skill_1_add"
            android:layout_width="100dp"
            android:layout_height="50dp"
            android:gravity="center"
            android:hint="梦境萦绕"
            android:textCursorDrawable="@null"
            android:textSize="20sp"
            android:layout_marginLeft="15dp"
            app:layout_constraintTop_toBottomOf="@+id/tv9_add"
            app:layout_constraintLeft_toRightOf="@+id/tv10_add"
            />

        <TextView
            android:id="@+id/tv11_add"
            android:layout_width="100dp"
            android:layout_height="50dp"
            android:text="技能二"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintTop_toBottomOf="@+id/tv10_add"
            />
        <EditText
            android:id="@+id/Skill_2_add"
            android:layout_width="100dp"
            android:layout_height="50dp"
            android:gravity="center"
            android:hint="梦境挥洒"
            android:textCursorDrawable="@null"
            android:textSize="20sp"
            android:layout_marginLeft="15dp"
            app:layout_constraintTop_toBottomOf="@+id/tv10_add"
            app:layout_constraintLeft_toRightOf="@+id/tv11_add"
            />

        <TextView
            android:id="@+id/tv12_add"
            android:layout_width="100dp"
            android:layout_height="50dp"
            android:text="技能三"
            android:gravity="center"
            android:textColor="#FFFFFF"
            android:textSize="20sp"
            android:background="#3A5FCD"
            app:layout_constraintTop_toBottomOf="@+id/tv11_add"
            />
        <EditText
            android:id="@+id/Skill_3_add"
            android:layout_width="100dp"
            android:layout_height="50dp"
            android:gravity="center"
            android:hint="梦境漩涡"
            android:textCursorDrawable="@null"
            android:textSize="20sp"
            android:layout_marginLeft="15dp"
            app:layout_constraintTop_toBottomOf="@+id/tv11_add"
            app:layout_constraintLeft_toRightOf="@+id/tv12_add"
            />

        <android.support.design.widget.FloatingActionButton
            android:id="@+id/floatingButton_finish_add"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@mipmap/finish"
            android:scaleType="fitCenter"
            android:backgroundTint="#FFFFFF"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            android:layout_margin="25dp"
            >
        </android.support.design.widget.FloatingActionButton>

    </android.support.constraint.ConstraintLayout>

</android.support.constraint.ConstraintLayout>
```

在英雄详细界面实现标题栏上的返回按钮，在onCreate里添加
```java
//标题栏添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
```
再添加重构函数onOptionsItemSelected
```java
//返回英雄列表
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
```



---
### (3)实验遇到的困难以及解决思路
在实现定位分类按钮时，本想在Onclick里面监听RadioButton，后来觉得逐个监听导致函数太冗余，转为监听RadioGroup，将RadioButton分为全部和其他两部分，点击其他的button时，将所有英雄的定位属性与所点Button进行比较得到所选定位的英雄列表
```java
else {
                    dataForSearch.clear();
                    for(Character character: data) {
                        if (!character.getType().matches(((RadioButton)findViewById(checkedId)).getText().toString())) {
                            continue;
                        }
                        dataForSearch.add(character);
                    }
                    mAdapterForSearch.notifyDataSetChanged();
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    recycleViewForSearch.setVisibility(View.VISIBLE);
                }
```



---

## 四、实验思考及感想
这次期中作业由团队共同完成，体验到了团队合作的重要性，能够完成自己一个人比较难完成任务。合理的分工有助于更高效地完成项目，但不小心遇到了些pull冲突问题，应避免组员间同时修改同一个文件，进而产生不同版本导致冲突。