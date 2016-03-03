package com.yishu.idcarder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public class SQLiteDatabaseUtils
{

    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private static final String TAG = "===SQLiteUtils===";

//    public SQLiteDatabaseUtils(){}

    public SQLiteDatabaseUtils(MyDBHelper myDBHelper)
    {
        this.myDBHelper = myDBHelper;
    }
    public void save(Users user)
    {
        db = myDBHelper.getWritableDatabase();
        db.beginTransaction();
        try
        {
            db.execSQL("INSERT INTO users (username, password, phone_number, money, tag, enterprise_name, affiliate) values (?, ?, ?, ?, ?, ?, ?)",
                    new String[]{user.getUsername(), user.getPassword(), user.getPhone_number(), String.valueOf(user.getMoney()), user.getTag(), user.getEnterprise_name(), user.getAffiliate()});
//            getCounts();
            db.setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {
            db.endTransaction();
            db.close();
        }
    }

    public Users find(String username)
    {
        db = myDBHelper.getReadableDatabase();
        db.beginTransaction();
        try
        {
            cursor = db.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
            Log.e(TAG, "===");
            if (cursor.moveToFirst())
            {
                String name = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String phone_number = cursor.getString(cursor.getColumnIndex("phone_number"));
                double money = cursor.getDouble(cursor.getColumnIndex("money"));
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                String enterprise_name = cursor.getString(cursor.getColumnIndex("enterprise_name"));
                String affiliate = cursor.getString(cursor.getColumnIndex("affiliate"));
                Log.e(TAG, tag);
                db.setTransactionSuccessful();
//                cursor.close();
//                db.close();
                Log.e(TAG, tag);
                return new Users(name, password, phone_number, money, tag, enterprise_name, affiliate);
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            db.endTransaction();
            cursor.close();
            db.close();
        }
//        return  new Users("exception", "y", "0", 0, "", "", "");
        return  null;
    }
    public Users findByPhone(String phoneNumber)
    {
        db = myDBHelper.getReadableDatabase();
        db.beginTransaction();
        try
        {
            cursor = db.rawQuery("SELECT * FROM users WHERE phone_number=?", new String[]{phoneNumber});
            if (cursor.moveToFirst())
            {
                String name = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String phone_number = cursor.getString(cursor.getColumnIndex("phone_number"));
                double money = cursor.getDouble(cursor.getColumnIndex("money"));
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                String enterprise_name = cursor.getString(cursor.getColumnIndex("enterprise_name"));
                String affiliate = cursor.getString(cursor.getColumnIndex("affiliate"));
                db.setTransactionSuccessful();
//                cursor.close();
//                db.close();
                Log.e(TAG, tag);
                return new Users(name, password, phone_number, money, tag, enterprise_name, affiliate);
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            db.endTransaction();
            cursor.close();
            db.close();
        }
//        return  new Users("", "y", "0", 0, "", "", "");
        return  null;
    }

    public long getCounts()
    {
        db = myDBHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM users", null);
        long counts = cursor.getCount();
        Log.d("Counts ==============:", String.valueOf(counts));
        db.close();
        return counts;
    }

    public boolean delete(String username)
    {
        db = myDBHelper.getWritableDatabase();
        db.beginTransaction();
        try
        {
            db.execSQL("DELETE FROM users WHERE username=?", new String[]{username});
            db.setTransactionSuccessful();
            return true;
        }catch (Exception e){e.printStackTrace();}
        finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }
    public boolean deleteByPhoneNumber(String phoneNumber)
    {
        db = myDBHelper.getWritableDatabase();
        db.beginTransaction();
        try
        {
            db.execSQL("DELETE FROM users WHERE phone_number=?", new String[]{phoneNumber});
            db.setTransactionSuccessful();
            return true;
        }catch (Exception e){e.printStackTrace();}
        finally {
            db.endTransaction();
            db.close();
        }
        return false;
    }


    public void recharge(double money, String username)
    {
        db = myDBHelper.getWritableDatabase();
        db.beginTransaction();
        try
        {
            //        Log.e(TAG, String.valueOf(money));
            db.execSQL("UPDATE users SET money=?+money WHERE username=?", new String[]{String.valueOf(money), username});
//        String[] test = new String[]{String.valueOf(money), username};
//        Users user = new Users();
//        user = find(username);
//        Log.e(TAG, user.getUsername() + " " + user.getPassword() + " " + user.getMoney());
//        Log.e(TAG, test[1] + " " + test[0]);
            db.setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {
            db.endTransaction();
            db.close();
        }

    }
    public void reduceMoney(String username)
    {
        db = myDBHelper.getWritableDatabase();
        db.beginTransaction();
        try
        {
            db.execSQL("UPDATE users SET money=money-1 WHERE username=?",new String[]{ username});
            db.setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {
            db.endTransaction();
            db.close();
        }
    }
    public void changeInfo(String _key, String _value,  String username)
    {
        db = myDBHelper.getWritableDatabase();
        db.beginTransaction();
        try
        {
            db.execSQL("UPDATE users SET " + _key + "=? WHERE username=?",new String[]{_value, username});
            db.setTransactionSuccessful();
        }catch (Exception e){e.printStackTrace();}
        finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<Users> findEnterpriseMembers(String enterpriseName)
    {
        db = myDBHelper.getReadableDatabase();
        List<Users> enterpriseMembers = new LinkedList<Users>();
        try
        {
            cursor = db.rawQuery("SELECT * FROM users WHERE affiliate=?", new String[]{enterpriseName});
            Log.e(TAG, "===");
            String name = "", password = "", phone_number = "", tag = "", enterprise_name = "", affiliate = "";
            double money = 0;
            while (cursor.moveToNext())
            {
                name = cursor.getString(cursor.getColumnIndex("username"));
                password = cursor.getString(cursor.getColumnIndex("password"));
                phone_number = cursor.getString(cursor.getColumnIndex("phone_number"));
                money = cursor.getDouble(cursor.getColumnIndex("money"));
                tag = cursor.getString(cursor.getColumnIndex("tag"));
                enterprise_name = cursor.getString(cursor.getColumnIndex("enterprise_name"));
                affiliate = cursor.getString(cursor.getColumnIndex("affiliate"));
                Log.e(TAG, tag);
                Users user = new Users(name, password, phone_number, money, tag, enterprise_name, affiliate);
                enterpriseMembers.add(user);
            }
//                cursor.close();
//                db.close();
            Log.e(TAG, tag);
            return enterpriseMembers;
        }catch (Exception e){e.printStackTrace();}
        finally {
            cursor.close();
            db.close();
        }
        return null;
    }
    public boolean isRegistered(String _key_userInfo, String _value_userInfo)
    {
        db = myDBHelper.getReadableDatabase();
        try
        {
            cursor = db.rawQuery("SELECT * FROM users WHERE " + _key_userInfo + "=?", new String[]{_value_userInfo});
            if (cursor.moveToFirst())
            {
                return true;
            }
        }catch (Exception e){e.printStackTrace();}
        finally {
            cursor.close();
            db.close();
        }
        return  false;
    }
}
