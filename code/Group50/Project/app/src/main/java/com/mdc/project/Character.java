package com.mdc.project;

import java.io.Serializable;
import java.util.UUID;

public class Character implements Serializable {
    private long id;
    //头像
    int icon;
    //称号
    String award;
    //名字
    private String name;
    //定位
    private String type;
    //生存能力值、攻击伤害值、技能效果值、上手难度值 -> 以10为最大值
    private int existValue;
    private int attackValue;
    private int skillValue;
    private int difficulty;
    //被动+技能三个: 每个技能的名称以及每个技能的效果
    private String passive;
    private String skill1;
    private String skill2;
    private String skill3;

    public Character(int icon, String award, String name, String type, int existValue, int attackValue,
                     int skillValue, int difficulty, String passive, String skill1, String skill2, String skill3) {
        this.id = System.currentTimeMillis();
        this.icon = icon;
        this.award = award;
        this.name = name;
        this.type = type;
        this.existValue = existValue;
        this.attackValue = attackValue;
        this.skillValue = skillValue;
        this.difficulty = difficulty;
        this.passive = passive;
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getAward() {
        return award;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setExistValue(int existValue) {
        this.existValue = existValue;
    }

    public int getExistValue() {
        return existValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setSkillValue(int skillValue) {
        this.skillValue = skillValue;
    }

    public int getSkillValue() {
        return skillValue;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setPassive(String passive) {
        this.passive = passive;
    }

    public String getPassive() {
        return passive;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public String getSkill1() {
        return skill1;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public String getSkill2() {
        return skill2;
    }

    public void setSkill3(String skill3) {
        this.skill3 = skill3;
    }

    public String getSkill3() {
        return skill3;
    }


}

