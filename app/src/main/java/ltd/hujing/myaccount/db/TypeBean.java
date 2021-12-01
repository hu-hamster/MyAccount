package ltd.hujing.myaccount.db;

import java.util.Calendar;

/**
 * 表示收入或者支出具体类型的类
 */
public class TypeBean {
    private int id;
    private String typename;            //类型名称
    private int imageid;            //未被选中图片id
    private int simageid;           //被选中图片id
    private int kind;               //收入-1    支出-0

    public TypeBean(){
    }

    public TypeBean(int id, String name, int imageid, int simageid, int kind, String description, Calendar calendar) {
        this.id = id;
        this.typename = name;
        this.imageid = imageid;
        this.simageid = simageid;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getSimageid() {
        return simageid;
    }

    public void setSimageid(int simageid) {
        this.simageid = simageid;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
