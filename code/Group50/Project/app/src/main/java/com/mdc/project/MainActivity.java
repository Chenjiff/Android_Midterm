package com.mdc.project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mAdapter;
    private ArrayList<Character> data;
    private boolean isHome;

    private RecyclerView recycleViewForSearch;
    private RecyclerView.LayoutManager mLayoutManagerForSearch;
    private MyRecyclerViewAdapter mAdapterForSearch;
    private ArrayList<Character> dataForSearch;
    private AppDAO appDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDAO = new AppDAO(this);

        ImageView searchButton = (ImageView) findViewById(R.id.search_button1);
        searchButton.setOnClickListener(this);

        FloatingActionButton fButton = (FloatingActionButton) findViewById(R.id.floatingButton_add);
        fButton.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if((data = getDataFromDB()) == null) {
            data = new ArrayList<>();
        }
        mAdapter = new MyRecyclerViewAdapter<Character>(data){
            @Override
            public void convert(final MyViewHolder holder, Character character, int position){
                TextView text = holder.getView(R.id.name);
                text.setText(character.getName());

                ImageView icon = holder.getView(R.id.icon);
                icon.setImageResource(character.getIcon());
            }
        };
        mAdapter.setOnItemClickListener(new ItemClickListener());
        mRecyclerView.setAdapter(mAdapter);

        createRecyclerViewForSearch();

        isHome = true;
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingButton_add);
        floatingActionButton.setOnClickListener(this);
        FloatingActionButton floatingActionButton_finish_add = (FloatingActionButton)findViewById(R.id.floatingButton_finish_add);
        floatingActionButton_finish_add.setOnClickListener(this);

        //注册EventBus
        EventBus.getDefault().register(this);
    }

    //创建用于搜索的RecyclerView，初始设为不可见
    public void createRecyclerViewForSearch() {
        recycleViewForSearch = (RecyclerView) findViewById(R.id.recycler_view_for_search);
        recycleViewForSearch.setVisibility(View.INVISIBLE);
        mLayoutManagerForSearch = new GridLayoutManager(this, 3);
        recycleViewForSearch.setLayoutManager(mLayoutManagerForSearch);
        dataForSearch = new ArrayList<>();
        mAdapterForSearch = new MyRecyclerViewAdapter<Character>(dataForSearch){
            @Override
            public void convert(final MyViewHolder holder, Character character, int position){
                TextView text = holder.getView(R.id.name);
                text.setText(character.getName());

                ImageView icon = holder.getView(R.id.icon);
                icon.setImageResource(character.getIcon());
            }
        };
        mAdapterForSearch.setOnItemClickListener(new ItemClickForSearchListener());
        recycleViewForSearch.setAdapter(mAdapterForSearch);
    }

    //从数据库获取数据并返回
    public ArrayList<Character> getDataFromDB() {
        ArrayList<Character> characters = new ArrayList<>();
        Cursor cursor = appDAO.queryAll();
        if(cursor.getCount() == 0 || !cursor.moveToFirst()) {
            return null;
        }
        do {
            Character character = new Character(cursor.getInt(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getInt(5),
                    cursor.getInt(6), cursor.getInt(7), cursor.getInt(8),
                    cursor.getString(9), cursor.getString(10), cursor.getString(11),
                    cursor.getString(12));
            character.setId(cursor.getInt(0));
            characters.add(character);
        } while(cursor.moveToNext());
        return characters;
    }

    //第一个RecyclerView的点击监听器
    class ItemClickListener implements MyRecyclerViewAdapter.OnItemClickListener {
        //短按进入英雄详情
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(MainActivity.this, Detail.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Click_Character", data.get(position));
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }

        //长按删除
        @Override
        public void onItemLongClick(View view, int position) {
            Character character = (Character) mAdapter.getItem(position);
            mAdapter.deleteItem(position);
            appDAO.delele(character);
            Toast.makeText(MainActivity.this, "已删除" + character.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    //第二个RecyclerView的点击监听器
    class ItemClickForSearchListener implements MyRecyclerViewAdapter.OnItemClickListener {
        //短按进入英雄详情
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(MainActivity.this, Detail.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Click_Character", dataForSearch.get(position));
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }

        //长按删除
        @Override
        public void onItemLongClick(View view, int position) {
            Character character = (Character) mAdapterForSearch.getItem(position);
            mAdapterForSearch.deleteItem(position);
            mAdapter.deleteItem(data.indexOf(character));
            appDAO.delele(character);
            Toast.makeText(MainActivity.this, "已删除" + character.getName(), Toast.LENGTH_SHORT).show();
        }
    }


    //一些控件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button1:
                EditText editText = (EditText) findViewById(R.id.edittext);
                String content = editText.getText().toString();
                if(content.isEmpty()) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    recycleViewForSearch.setVisibility(View.INVISIBLE);
                    break;
                }
                dataForSearch.clear();
                //名字长度默认不超过8
                if(content.length() > 8) {
                    mAdapterForSearch.notifyDataSetChanged();
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    recycleViewForSearch.setVisibility(View.VISIBLE);
                    break;
                }
                //使用正则而非contains()进行匹配，实现类似搜索“13”可以匹配到“123”。
                String regex = "^";
                for(int i = 0; i < content.length(); i++) {
                    regex += ".*" + content.charAt(i);
                }
                regex += ".*$";
                for(Character character: data) {
                    if (!character.getName().matches(regex)) {
                        continue;
                    }
                    dataForSearch.add(character);
                }
                mAdapterForSearch.notifyDataSetChanged();
                mRecyclerView.setVisibility(View.INVISIBLE);
                recycleViewForSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.floatingButton_add:
                if (isHome) {
                    findViewById(R.id.mainPage).setVisibility(View.INVISIBLE);
                    findViewById(R.id.addPage).setVisibility(View.VISIBLE);
                    isHome = false;
                    ((EditText) findViewById(R.id.ChenHao_add)).setText("");
                    ((EditText) findViewById(R.id.name_add)).setText("");
                    ((EditText) findViewById(R.id.Category_add)).setText("");
                    ((EditText) findViewById(R.id.ProgressBar1_add)).setText("");
                    ((EditText) findViewById(R.id.ProgressBar2_add)).setText("");
                    ((EditText) findViewById(R.id.ProgressBar3_add)).setText("");
                    ((EditText) findViewById(R.id.ProgressBar4_add)).setText("");
                    ((EditText) findViewById(R.id.PassiveSkill_add)).setText("");
                    ((EditText) findViewById(R.id.Skill_1_add)).setText("");
                    ((EditText) findViewById(R.id.Skill_2_add)).setText("");
                    ((EditText) findViewById(R.id.Skill_3_add)).setText("");
                }
                break;
            case R.id.floatingButton_finish_add:
                if (!isHome) {
                    findViewById(R.id.mainPage).setVisibility(View.VISIBLE);
                    findViewById(R.id.addPage).setVisibility(View.INVISIBLE);
                    isHome = true;
                    String award = ((EditText) findViewById(R.id.ChenHao_add)).getText().toString();
                    String name = ((EditText) findViewById(R.id.name_add)).getText().toString();
                    String type = ((EditText) findViewById(R.id.Category_add)).getText().toString();
                    int existValue = Integer.valueOf(((EditText) findViewById(R.id.ProgressBar1_add)).getText().toString());
                    int attackValue = Integer.valueOf(((EditText) findViewById(R.id.ProgressBar2_add)).getText().toString());
                    int skillValue = Integer.valueOf(((EditText) findViewById(R.id.ProgressBar3_add)).getText().toString());
                    int difficulty = Integer.valueOf(((EditText) findViewById(R.id.ProgressBar4_add)).getText().toString());
                    String passive = ((EditText) findViewById(R.id.PassiveSkill_add)).getText().toString();
                    String skill1 = ((EditText) findViewById(R.id.Skill_1_add)).getText().toString();
                    String skill2 = ((EditText) findViewById(R.id.Skill_2_add)).getText().toString();
                    String skill3 = ((EditText) findViewById(R.id.Skill_3_add)).getText().toString();
                    Character character = new Character(R.mipmap.mengqi, award, name, type, existValue, attackValue, skillValue,
                            difficulty, passive, skill1, skill2, skill3);
                    addCharacterToListAndDB(character);
                }
                break;
            default:
                break;
        }
    }

    public void addCharacterToListAndDB(Character character) {
        mAdapter.addNewItem(character);
        appDAO.insert(character);
    }

    @Subscribe(threadMode= ThreadMode.MAIN)
    public void Event(EditEvent editEvent) {
        Character character = editEvent.getCharacter();
        appDAO.update(character, character);
        for(Character characterInData: data) {
            if (characterInData.getId() == character.getId()) {
                int position = data.indexOf(characterInData);
                data.set(position, character);
                mAdapter.notifyItemChanged(position);
                break;
            }
        }
        if(recycleViewForSearch.getVisibility() == View.VISIBLE) {
            for(Character characterInSearchData: dataForSearch) {
                if (characterInSearchData.getId() == character.getId()) {
                    int position = dataForSearch.indexOf(characterInSearchData);
                    dataForSearch.set(position, character);
                    mAdapterForSearch.notifyItemChanged(position);
                    break;
                }
            }
        }
    }

    //Activity销毁函数，用于EventBus的注销
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus订阅
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
