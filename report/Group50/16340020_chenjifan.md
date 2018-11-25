?# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 |2016  | 专业（方向） |软件工程  |
| 学号 |16340020  | 姓名 |陈吉凡  |
| 电话 |15017239913  | Email |chenjifan98.com  |
| 开始日期 |11.18  | 完成日期 |11.24

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
* 以下重新打开APP的数据仍与关闭时一致  
1.一开始查找所有在数据库中的项：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/232134_2d944cd0_2164918.png "query.png")  
2.添加功能：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/232148_ba4f873c_2164918.png "add.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/232158_0018182a_2164918.png "add2.png")  
3.删除功能  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/232335_ded5bd57_2164918.png "delete.png")  
4.修改功能：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/232402_753ed8eb_2164918.png "edit.png")
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/232417_27e6e9df_2164918.png "edit2.png")  
5.查询功能：  
![输入图片说明](https://images.gitee.com/uploads/images/2018/1125/232448_8e47d588_2164918.png "search.png")
### (2)实验步骤以及关键代码
1.实现Character类，即英雄类，便于数据库编写表：  
```
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
    
    //...基本接口
}
```
2.实现AppDAO类，即数据库管理类，创表函数：
```
@Override
    public void onCreate(SQLiteDatabase db) {
        defaultWritableDB = db;
        String createTableSql = "CREATE TABLE " + TABLE_NAME
                + "(id PRIMARY KEY, icon INTEGER, award TEXT, name TEXT, type TEXT, existValue INTEGER, " +
                "attackValue INTEGER, skillValue INTEGER, difficulty INTEGER, passive TEXT, skill1 TEXT, skill2 TEXT, skill3 TEXT)";
        db.execSQL(createTableSql);
        //初始化
        insert(new Character(R.drawable.jialuo, "花见巫女", "伽罗", "射手",
                2, 9, 4, 2, "破魔之箭", "渡灵之箭", "静默之箭", "纯净之域"));
        //其他插入
}
```
数据管理函数即增删改查，下面列出insert函数，通过SQLiteStatement实现，简便且性能高：
```
public long insert(Character character) {
        SQLiteDatabase db = getWritableDatabase();
        String insertSql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(insertSql);
        int index = 1;
        statement.bindLong(index++, character.getId());
        statement.bindLong(index++, character.getIcon());
        statement.bindString(index++, character.getAward());
        statement.bindString(index++, character.getName());
        statement.bindString(index++, character.getType());
        statement.bindLong(index++, character.getExistValue());
        statement.bindLong(index++, character.getAttackValue());
        statement.bindLong(index++, character.getSkillValue());
        statement.bindLong(index++, character.getDifficulty());
        statement.bindString(index++, character.getPassive());
        statement.bindString(index++, character.getSkill1());
        statement.bindString(index++, character.getSkill2());
        statement.bindString(index++, character.getSkill3());
        long rowID = statement.executeInsert();
        return rowID;
   }
```
查询：
```
public Cursor queryAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
```
3.在程序中具体实现增、删、改、查，下面只列出删除的代码，注意查询时是操作cursor进行遍历：
```
            Character character = (Character) mAdapterForSearch.getItem(position);
            mAdapterForSearch.deleteItem(position);
            mAdapter.deleteItem(data.indexOf(character));
            appDAO.delele(character);
            Toast.makeText(MainActivity.this, "已删除" + character.getName(), Toast.LENGTH_SHORT).show();
```

4.通过正则匹配实现搜索功能：
```
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
```
5.通过eventBus实现Activity间的数据传递：
EditEvent类：
```
public class EditEvent {
    private Character character;

    public EditEvent(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
//注册
EventBus.getDefault().register(this);
//发送
EditEvent editEvent = new EditEvent(character);
EventBus.getDefault().post(editEvent);
//销毁
if(EventBus.getDefault().isRegistered(this)) {
     EventBus.getDefault().unregister(this);
}
```



### (3) 遇到的困难： 
1.数据库初始化时，在onCreate中插入数据会出错，debug发现是getWritableDatabase()中会调用onCreate()函数，使初始化会抛出递归调用异常。遂重写getWritableDatabase()函数，使得仅在没有默认writableDatabase时才调用原getWritableDatabase()，成功解决。  
2.创建英雄类中，为了避免过于依赖可以修改的name属性，添加了id属性，初定为使用当前系统毫秒数，即通过`System.currentTimeMillis()`。测试时发现创建类过快时会出现id重复的现象。查阅资料得知还有更精确的`System.nanoTime()`，虽然前者实际也是通过后者实现，但仍有不同，修改后成功解决。  
3.实现搜索功能时，一开始使用String类的contains()进行匹配，发现无法实现类似搜索“13”可以匹配到“123”。后来改用正则匹配成功实现。

  
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