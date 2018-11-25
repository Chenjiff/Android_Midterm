package com.mdc.project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;


public class Detail extends AppCompatActivity {
    //用于判断是否进入编辑界面
    private boolean isHome;
    private Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        ImageView im = (ImageView) findViewById(R.id.detail_icon);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        //标题栏添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        character = (Character)getIntent().getSerializableExtra("Click_Character");
        setViewContentByCharacter(character);

        isHome = true;
        FloatingActionButton floatingButton_edit = (FloatingActionButton) findViewById(R.id.floatingButton_edit);
        FloatingActionButton floatingButton_finish = (FloatingActionButton) findViewById(R.id.floatingButton_finish);

        floatingButton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHome) {
                    findViewById(R.id.detail_layout).setVisibility(View.INVISIBLE);
                    findViewById(R.id.edit_layout).setVisibility(View.VISIBLE);
                    setEditContentByCharacter(character);
                    isHome = false;
                }
            }
        });

        floatingButton_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isHome) {
                    findViewById(R.id.detail_layout).setVisibility(View.VISIBLE);
                    findViewById(R.id.edit_layout).setVisibility(View.INVISIBLE);
                    isHome = true;

                    character.setAward(((EditText) findViewById(R.id.ChenHao_edit)).getText().toString());
                    character.setName(((EditText) findViewById(R.id.name_edit)).getText().toString());
                    character.setType(((EditText) findViewById(R.id.Category_edit)).getText().toString());
                    character.setExistValue(Integer.valueOf(((EditText) findViewById(R.id.ProgressBar1_edit)).getText().toString()));
                    character.setAttackValue(Integer.valueOf(((EditText) findViewById(R.id.ProgressBar2_edit)).getText().toString()));
                    character.setSkillValue(Integer.valueOf(((EditText) findViewById(R.id.ProgressBar3_edit)).getText().toString()));
                    character.setDifficulty(Integer.valueOf(((EditText) findViewById(R.id.ProgressBar4_edit)).getText().toString()));
                    character.setPassive(((EditText) findViewById(R.id.PassiveSkill_edit)).getText().toString());
                    character.setSkill1(((EditText) findViewById(R.id.Skill_1_edit)).getText().toString());
                    character.setSkill2(((EditText) findViewById(R.id.Skill_2_edit)).getText().toString());
                    character.setSkill3(((EditText) findViewById(R.id.Skill_3_edit)).getText().toString());
                    setViewContentByCharacter(character);
                    //发送EventBus
                    EditEvent editEvent = new EditEvent(character);
                    EventBus.getDefault().post(editEvent);
                }
            }
        });
    }
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

    //设置属性视图内容
    public void setViewContentByCharacter(Character character) {
        ((ImageView) findViewById(R.id.detail_icon)).setImageResource(character.getIcon());
        ((TextView) findViewById(R.id.ChenHao)).setText(character.getAward());
        ((TextView) findViewById(R.id.name)).setText(character.getName());
        ((TextView) findViewById(R.id.Category)).setText(character.getType());
        ((ProgressBar) findViewById(R.id.ProgressBar1)).setProgress(character.getExistValue());
        ((ProgressBar) findViewById(R.id.ProgressBar2)).setProgress(character.getAttackValue());
        ((ProgressBar) findViewById(R.id.ProgressBar3)).setProgress(character.getSkillValue());
        ((ProgressBar) findViewById(R.id.ProgressBar4)).setProgress(character.getDifficulty());
        ((TextView) findViewById(R.id.PassiveSkill)).setText(character.getPassive());
        ((TextView) findViewById(R.id.Skill_1)).setText(character.getSkill1());
        ((TextView) findViewById(R.id.Skill_2)).setText(character.getSkill2());
        ((TextView) findViewById(R.id.Skill_3)).setText(character.getSkill3());
    }

    //设置编辑视图内容
    public void setEditContentByCharacter(Character character) {
        ((ImageView) findViewById(R.id.detail_icon_edit)).setImageResource(character.getIcon());
        ((EditText) findViewById(R.id.ChenHao_edit)).setText(character.getAward());
        ((EditText) findViewById(R.id.name_edit)).setText(character.getName());
        ((EditText) findViewById(R.id.Category_edit)).setText(character.getType());
        ((EditText) findViewById(R.id.ProgressBar1_edit)).setText(String.valueOf(character.getExistValue()));
        ((EditText) findViewById(R.id.ProgressBar2_edit)).setText(String.valueOf(character.getAttackValue()));
        ((EditText) findViewById(R.id.ProgressBar3_edit)).setText(String.valueOf(character.getSkillValue()));
        ((EditText) findViewById(R.id.ProgressBar4_edit)).setText(String.valueOf(character.getDifficulty()));
        ((EditText) findViewById(R.id.PassiveSkill_edit)).setText(character.getPassive());
        ((EditText) findViewById(R.id.Skill_1_edit)).setText(character.getSkill1());
        ((EditText) findViewById(R.id.Skill_2_edit)).setText(character.getSkill2());
        ((EditText) findViewById(R.id.Skill_3_edit)).setText(character.getSkill3());
    }

}

//注释