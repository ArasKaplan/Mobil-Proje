package com.araskaplan.closett;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static com.araskaplan.closett.MainActivity.getBitmapFromBytes;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="MainDB";

    private String TableNameDrawers="Drawers";
    private String ColumnIdDrawers="id";
    private String ColumnNameDrawers="name";

    public String CreateDrawerTable="Create Table if not exists "
            +TableNameDrawers+" ( "+ColumnIdDrawers+" Integer primary key, "
            + ColumnNameDrawers + " varchar) ";

    private String TableNameClothing="Clothing";
    private String ColumnIdClothing="id";
    private String ColumnNameClothing="name";
    private String ColumnPatternClothing="pattern";
    private String ColumnColorClothing="color";
    private String ColumnTypeClothing="type";
    private String ColumnPriceClothing="price";
    private String ColumnDateClothing="date";
    private String ColumnImgClothing="img";
    private String ColumnDrawerIdClothing="drawerid";


    public String CreateClothingTable="Create table if not exists "+
            TableNameClothing+" ("+ColumnIdClothing+" integer primary key,"
            +ColumnNameClothing+" varchar, "
            +ColumnPatternClothing+" varchar, "
            +ColumnColorClothing+" varchar, "
            +ColumnTypeClothing+" varchar, "
            +ColumnPriceClothing+" varchar, "
            +ColumnDateClothing+" varchar, "
            +ColumnImgClothing+" BLOB, "
            +ColumnDrawerIdClothing+" integer)";

    private String TableNameCombination="Combination";
    private String ColumnNameCombination="name";
    private String ColumnIdCombination="combid";
    private String ColumnOverheadId="overheadid";
    private String ColumnFaceid="faceid";
    private String ColumnTorsoid="torsoid";
    private String ColumnLegsid="legsid";
    private String ColumnShoesid="shoesid";

    public String CreateCombinationTable="Create Table if not exists "+
            TableNameCombination+" ("+ColumnIdCombination+"integer primary key,"
            +ColumnNameCombination+" varchar, "
            +ColumnOverheadId+" integer, "
            +ColumnFaceid+" integer, "
            +ColumnTorsoid+" integer, "
            +ColumnLegsid+" integer, "
            +ColumnShoesid+" integer)";


    private String TableNameEvent="Event";
    private String ColumnIdEvent="id";
    private String ColumnNameEvent="name";
    private String ColumnTypeEvent="type";
    private String ColumnDateEvent="date";
    private String ColumnLocationEvent="location";
    private String ColumnCombinationEvent="combid";

    public String CreateEventTable="Create Table if not exists "
            +TableNameEvent+" ("+ColumnIdEvent+"integer primary key,"
            +ColumnNameEvent+" varchar, "
            +ColumnTypeEvent+" varchar, "
            +ColumnDateEvent+" varchar, "
            +ColumnLocationEvent+" varchar, "
            +ColumnCombinationEvent+" integer)";



    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateDrawerTable);
        db.execSQL(CreateClothingTable);
        db.execSQL(CreateCombinationTable);
        db.execSQL(CreateEventTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists "+TableNameDrawers);
        db.execSQL("Drop Table if exists "+TableNameClothing);
        db.execSQL("Drop Table if exists "+TableNameCombination);
        db.execSQL("Drop Table if exists "+TableNameEvent);
        onCreate(db);
    }

    public ArrayList<Drawer> getDrawers(){
        ArrayList<Drawer>drawers=new ArrayList<>();

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String[] columns= {
                ColumnIdDrawers,
                ColumnNameDrawers
        };
        Cursor cursor=sqLiteDatabase.query(TableNameDrawers,columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            Drawer temp=new Drawer(cursor.getString(cursor.getColumnIndex(ColumnNameDrawers)));
            temp.setId(cursor.getInt(cursor.getColumnIndex(ColumnIdDrawers)));
            temp.setClothes(getClothesofDrawer(cursor.getInt(cursor.getColumnIndex(ColumnIdDrawers))));
            drawers.add(temp);
        }
        sqLiteDatabase.close();
        return drawers;
    }

    public ArrayList<Clothing> getClothesofDrawer(int drawerid){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String[] columns={
                ColumnNameClothing,
                ColumnIdClothing,
                ColumnPatternClothing,
                ColumnColorClothing,
                ColumnTypeClothing,
                ColumnPriceClothing,
                ColumnDateClothing,
                ColumnImgClothing,
                ColumnDrawerIdClothing
        };
        String selection=ColumnDrawerIdClothing+" = ?";
        String[] selectionArgs =  {String.valueOf(drawerid)};
        Cursor cursor=sqLiteDatabase.query(TableNameClothing,columns,selection,selectionArgs,null,null,null);

        ArrayList<Clothing>clothes=new ArrayList<>();
        while (cursor.moveToNext()){
            Bitmap tempimg= MainActivity.getBitmapFromBytes(cursor.getBlob(cursor.getColumnIndex(ColumnImgClothing)));

            Clothing temp=new Clothing(
                    cursor.getString(cursor.getColumnIndex(ColumnNameClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnPatternClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnColorClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnTypeClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnPriceClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnDateClothing)));
            temp.setImg(tempimg);
            temp.setDrawerid(cursor.getInt(cursor.getColumnIndex(ColumnDrawerIdClothing)));
            temp.setId(cursor.getInt(cursor.getColumnIndex(ColumnIdClothing)));
            clothes.add(temp);
        }
        sqLiteDatabase.close();
        return clothes;
    }
    public ArrayList<Clothing> getAllClothing(){
        ArrayList<Clothing>clothes=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String[] columns={
                ColumnNameClothing,
                ColumnIdClothing,
                ColumnPatternClothing,
                ColumnColorClothing,
                ColumnTypeClothing,
                ColumnPriceClothing,
                ColumnDateClothing,
                ColumnImgClothing,
                ColumnDrawerIdClothing
        };
        Cursor cursor=sqLiteDatabase.query(TableNameClothing,columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            Bitmap tempimg= MainActivity.getBitmapFromBytes(cursor.getBlob(cursor.getColumnIndex(ColumnImgClothing)));

            Clothing temp=new Clothing(
                    cursor.getString(cursor.getColumnIndex(ColumnNameClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnPatternClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnColorClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnTypeClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnPriceClothing)),
                    cursor.getString(cursor.getColumnIndex(ColumnDateClothing)));
            temp.setImg(tempimg);
            temp.setDrawerid(cursor.getInt(cursor.getColumnIndex(ColumnDrawerIdClothing)));
            temp.setId(cursor.getInt(cursor.getColumnIndex(ColumnIdClothing)));
            clothes.add(temp);
        }
        return clothes;
    }


    public Drawer addDrawer(String drawername){//Drawer drawer
        Drawer drawer=new Drawer(drawername);
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ColumnNameDrawers,drawer.getName());
        sqLiteDatabase.insert(TableNameDrawers,null,contentValues);
        sqLiteDatabase.close();
        return drawer;
    }

    public void deleteDrawer(Drawer drawer){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(TableNameDrawers,ColumnIdDrawers+" = "+String.valueOf(drawer.getId()),null);

        sqLiteDatabase.delete(TableNameClothing,ColumnDrawerIdClothing+" = "+String.valueOf(drawer.getId()),null);
        sqLiteDatabase.close();
    }

    public Clothing getClothing(int id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String[] columns={
                ColumnNameClothing,
                ColumnIdClothing,
                ColumnPatternClothing,
                ColumnColorClothing,
                ColumnTypeClothing,
                ColumnPriceClothing,
                ColumnDateClothing,
                ColumnImgClothing,
                ColumnDrawerIdClothing
        };

        String selection=ColumnIdClothing+" = ";
        String[] selectionarg= {String.valueOf(id)};
        Clothing clothing=null;
        Cursor cursor=sqLiteDatabase.query(TableNameClothing,columns,selection,selectionarg,null,null,null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(ColumnNameClothing));
            id=cursor.getInt(cursor.getColumnIndex(ColumnIdClothing));
            String pattern=cursor.getString(cursor.getColumnIndex(ColumnPatternClothing));
            String color=cursor.getString(cursor.getColumnIndex(ColumnColorClothing));
            String type=cursor.getString(cursor.getColumnIndex(ColumnTypeClothing));
            String price=cursor.getString(cursor.getColumnIndex(ColumnPriceClothing));
            String date=cursor.getString(cursor.getColumnIndex(ColumnDateClothing));
            int drawerid=cursor.getInt(cursor.getColumnIndex(ColumnDrawerIdClothing));
            Bitmap bitmap=MainActivity.getBitmapFromBytes(cursor.getBlob(cursor.getColumnIndex(ColumnImgClothing)));
            clothing=new Clothing(name,pattern,color,type,price,date);
            clothing.setId(id);
            clothing.setDrawerid(drawerid);
            clothing.setImg(bitmap);
        }
        return clothing;
    }


    public void addClothing(Clothing clothing){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ColumnNameClothing,clothing.getName());
        contentValues.put(ColumnDateClothing,clothing.getDate_of_purchase());
        contentValues.put(ColumnPatternClothing,clothing.getPattern());
        contentValues.put(ColumnPriceClothing,clothing.getPrice());
        contentValues.put(ColumnTypeClothing,clothing.getType());
        contentValues.put(ColumnDrawerIdClothing,clothing.getDrawerid());
        contentValues.put(ColumnColorClothing,clothing.getColor());
        contentValues.put(ColumnImgClothing,MainActivity.getBytesFromBitmap(clothing.getImg()));
        sqLiteDatabase.insert(TableNameClothing,null,contentValues);
        sqLiteDatabase.close();
    }



    public void deleteClothing(Clothing clothing){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(TableNameClothing,ColumnIdClothing+" = "+String.valueOf(clothing.getId()),null);
        sqLiteDatabase.close();
    }



    public void updateClothing(Clothing clothing){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ColumnImgClothing,MainActivity.getBytesFromBitmap(clothing.getImg()));
        contentValues.put(ColumnColorClothing,clothing.getColor());
        contentValues.put(ColumnDateClothing,clothing.getDate_of_purchase());
        contentValues.put(ColumnPatternClothing,clothing.getPattern());
        contentValues.put(ColumnPriceClothing,clothing.getPrice());
        contentValues.put(ColumnTypeClothing,clothing.getType());
        contentValues.put(ColumnDrawerIdClothing,clothing.getDrawerid());
        contentValues.put(ColumnNameClothing,clothing.getName());
        sqLiteDatabase.update(TableNameClothing,contentValues,ColumnIdClothing+" = "+String.valueOf(clothing.getId()),null);
        sqLiteDatabase.close();
    }



    public ArrayList<Combination> getCombinations(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<Combination>combinations=new ArrayList<>();
        String[] columns={
                ColumnIdCombination,
                ColumnNameCombination,
                ColumnOverheadId,
                ColumnFaceid,
                ColumnTorsoid,
                ColumnLegsid,
                ColumnShoesid
        };
        Cursor cursor=sqLiteDatabase.query(TableNameCombination,columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            Combination combination=new Combination(cursor.getString(cursor.getColumnIndex(ColumnNameCombination)));
            combination.setOverhead(getClothing(cursor.getInt(cursor.getColumnIndex(ColumnOverheadId))));
            combination.setFace(getClothing(cursor.getInt(cursor.getColumnIndex(ColumnFaceid))));
            combination.setTorso(getClothing(cursor.getInt(cursor.getColumnIndex(ColumnTorsoid))));
            combination.setLegs(getClothing(cursor.getInt(cursor.getColumnIndex(ColumnLegsid))));
            combination.setFeet(getClothing(cursor.getInt(cursor.getColumnIndex(ColumnShoesid))));
            combinations.add(combination);
        }
        sqLiteDatabase.close();
        return combinations;
    }

    public void addCombination(Combination combination){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ColumnOverheadId,combination.getOverhead().getId());
        contentValues.put(ColumnFaceid,combination.getFace().getId());
        contentValues.put(ColumnTorsoid,combination.getTorso().getId());
        contentValues.put(ColumnLegsid,combination.getLegs().getId());
        contentValues.put(ColumnShoesid,combination.getFeet().getId());
        sqLiteDatabase.insert(TableNameCombination,null,contentValues);
        sqLiteDatabase.close();
    }



    /*    public String CreateCombinationTable="Create Table if not exists "+
            TableNameCombination+" ("+ColumnIdCombination+"integer primary key,"
            +ColumnNameCombination+" varchar, "
            +ColumnOverheadId+" integer, "
            +ColumnFaceid+" integer, "
            +ColumnTorsoid+" integer, "
            +ColumnLegsid+" integer, "
            +ColumnShoesid+" integer)";*/
}
/*TableNameClothing+" ("+ColumnIdClothing+" integer primary key,"
            +ColumnNameClothing+" varchar, "
            +ColumnPatternClothing+" varchar, "
            +ColumnColorClothing+" varchar, "
            +ColumnTypeClothing+" varchar, "
            +ColumnPriceClothing+" varchar, "
            +ColumnDateClothing+" varchar, "
            +ColumnImgClothing+"BLOB,"
            +ColumnDrawerIdClothing+" int)";*/