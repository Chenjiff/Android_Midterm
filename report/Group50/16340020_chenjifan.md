?# ��ɽ��ѧ���ݿ�ѧ������ѧԺ������ʵ�鱨��
## ��2018���＾ѧ�ڣ�
| �γ����� | �ֻ�ƽ̨Ӧ�ÿ��� | �ο���ʦ | ֣��� |
| :------------: | :-------------: | :------------: | :-------------: |
| �꼶 |2016  | רҵ������ |�������  |
| ѧ�� |16340020  | ���� |�¼���  |
| �绰 |15017239913  | Email |chenjifan98.com  |
| ��ʼ���� |11.18  | ������� |11.24

---

## һ��ʵ����Ŀ

������ҫӢ�۴�ȫ

---

## ��������ʵ�������Լ���Ŀ��չ

* ������ҫӢ���������ɾ�Ĳ鹦�ܡ����԰���ͷ�񡢳ƺš����֡�λ�á���������ֵ�������˺�ֵ������Ч��ֵ�������Ѷ�ֵ�ȣ�����ͷ����ͼƬ
* App����ʱ��ʼ������10��Ӣ����Ϣ����Ҫ�����ݿ⣬���Դ��붨���xml��

* ��Ŀ��չ���ݣ����ݿⱣ�桢UI������������������
---

## ��������ʵ����
### (1)ʵ���ͼ
* �������´�APP����������ر�ʱһ��  
1.һ��ʼ�������������ݿ��е��  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1125/232134_2d944cd0_2164918.png "query.png")  
2.��ӹ��ܣ�  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1125/232148_ba4f873c_2164918.png "add.png")
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1125/232158_0018182a_2164918.png "add2.png")  
3.ɾ������  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1125/232335_ded5bd57_2164918.png "delete.png")  
4.�޸Ĺ��ܣ�  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1125/232402_753ed8eb_2164918.png "edit.png")
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1125/232417_27e6e9df_2164918.png "edit2.png")  
5.��ѯ���ܣ�  
![����ͼƬ˵��](https://images.gitee.com/uploads/images/2018/1125/232448_8e47d588_2164918.png "search.png")
### (2)ʵ�鲽���Լ��ؼ�����
1.ʵ��Character�࣬��Ӣ���࣬�������ݿ��д��  
```
public class Character implements Serializable {
    private long id;
    //ͷ��
    int icon;
    //�ƺ�
    String award;
    //����
    private String name;
    //��λ
    private String type;
    //��������ֵ�������˺�ֵ������Ч��ֵ�������Ѷ�ֵ -> ��10Ϊ���ֵ
    private int existValue;
    private int attackValue;
    private int skillValue;
    private int difficulty;
    //����+��������: ÿ�����ܵ������Լ�ÿ�����ܵ�Ч��
    private String passive;
    private String skill1;
    private String skill2;
    private String skill3;
    
    //...�����ӿ�
}
```
2.ʵ��AppDAO�࣬�����ݿ�����࣬��������
```
@Override
    public void onCreate(SQLiteDatabase db) {
        defaultWritableDB = db;
        String createTableSql = "CREATE TABLE " + TABLE_NAME
                + "(id PRIMARY KEY, icon INTEGER, award TEXT, name TEXT, type TEXT, existValue INTEGER, " +
                "attackValue INTEGER, skillValue INTEGER, difficulty INTEGER, passive TEXT, skill1 TEXT, skill2 TEXT, skill3 TEXT)";
        db.execSQL(createTableSql);
        //��ʼ��
        insert(new Character(R.drawable.jialuo, "������Ů", "٤��", "����",
                2, 9, 4, 2, "��ħ֮��", "����֮��", "��Ĭ֮��", "����֮��"));
        //��������
}
```
���ݹ���������ɾ�Ĳ飬�����г�insert������ͨ��SQLiteStatementʵ�֣���������ܸߣ�
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
��ѯ��
```
public Cursor queryAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
```
3.�ڳ����о���ʵ������ɾ���ġ��飬����ֻ�г�ɾ���Ĵ��룬ע���ѯʱ�ǲ���cursor���б�����
```
            Character character = (Character) mAdapterForSearch.getItem(position);
            mAdapterForSearch.deleteItem(position);
            mAdapter.deleteItem(data.indexOf(character));
            appDAO.delele(character);
            Toast.makeText(MainActivity.this, "��ɾ��" + character.getName(), Toast.LENGTH_SHORT).show();
```

4.ͨ������ƥ��ʵ���������ܣ�
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
5.ͨ��eventBusʵ��Activity������ݴ��ݣ�
EditEvent�ࣺ
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
//ע��
EventBus.getDefault().register(this);
//����
EditEvent editEvent = new EditEvent(character);
EventBus.getDefault().post(editEvent);
//����
if(EventBus.getDefault().isRegistered(this)) {
     EventBus.getDefault().unregister(this);
}
```



### (3) ���������ѣ� 
1.���ݿ��ʼ��ʱ����onCreate�в������ݻ����debug������getWritableDatabase()�л����onCreate()������ʹ��ʼ�����׳��ݹ�����쳣������дgetWritableDatabase()������ʹ�ý���û��Ĭ��writableDatabaseʱ�ŵ���ԭgetWritableDatabase()���ɹ������  
2.����Ӣ�����У�Ϊ�˱���������������޸ĵ�name���ԣ������id���ԣ�����Ϊʹ�õ�ǰϵͳ����������ͨ��`System.currentTimeMillis()`������ʱ���ִ��������ʱ�����id�ظ������󡣲������ϵ�֪���и���ȷ��`System.nanoTime()`����Ȼǰ��ʵ��Ҳ��ͨ������ʵ�֣������в�ͬ���޸ĺ�ɹ������  
3.ʵ����������ʱ��һ��ʼʹ��String���contains()����ƥ�䣬�����޷�ʵ������������13������ƥ�䵽��123����������������ƥ��ɹ�ʵ�֡�

  
---

## �ġ��κ�ʵ����
���ڱ��ļ��е���Ƶ

---

## �塢ʵ��˼��������
* Ӧ��Ԥ�������ʱ�䣬���������������ڽ�����������ڲ��и������ԣ
* ���鵽���ŶӺ�������Ҫ�ԣ�������ŶӺ����ܼ�������Ч��
* �˽⵽�˺��������ʱ�����������ѣ��Լ�����ֹ�����Ҫ��
---

#### ��ҵҪ��
* ����Ҫ��ѧ��_����_ʵ���ţ�����12345678_����_lab1.md
* ʵ�鱨���ύ��ʽΪmd
* ʵ�����ݲ�����Ϯ������Ҫ���д������ƶȶԱȡ��緢�ֳ�Ϯ����0�ִ���![����ͼƬ˵��]