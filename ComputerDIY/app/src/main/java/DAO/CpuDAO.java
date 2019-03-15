package DAO;
import android.util.Log;

import com.example.computerdiy.CPU;
import com.example.computerdiy.DBUtil;
import com.example.computerdiy.R;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//cpu数据导入
public class CpuDAO{

    List<CPU> CPUList = new ArrayList<CPU>();
    public List<CPU> list() {
        //创建一个定长的核心线程和最大线程数都是1的FixedThreadPool线程池,使用callable和future.get()方法从线程返回值，从而获取数据库的值
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        /*String[] models = {"i9-9900k", "i7-7700k", "i5-3470"};
        String[] brands = {"Intel", "AMD", "Intel"};
        int[] images = {R.drawable.i9_9900k, R.drawable.i9_9900k, R.drawable.i9_9900k};
        String[] frequency = {"3.6GHz", "4.2GHz", "3.2GHz"};
        int[] prices={5000,2700,500};
*/
        Callable<List<CPU>> callable = new Callable<List<CPU>>() {

            @Override
            public List<CPU> call() {
                List<CPU> CPUList = new ArrayList<CPU>();
                try {
                    //Class.forName("com.mysql.jdbc.Driver");
                    Connection cn = DBUtil.getConn();//DriverManager.getConnection("jdbc:mysql://192.168.1.232:3306/computer_diy?useSSL=false", "Joshua", "123");
                    String sql = "select * from cpu";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);

                    int[] images = {R.drawable.i9_9900k, R.drawable.i9_9900k, R.drawable.i9_9900k};
                    while (rs.next()) {
                        CPU u = new CPU();
                        u.setModel(rs.getString("型号"));
                        Log.i("Mainactivity",u.getModel());
                        u.setBrand(rs.getString("品牌"));
                        u.setFrequency(rs.getString("主频"));
                        u.setPrice(rs.getInt("价格"));
                        u.setImage(images[0]);
                        CPUList.add(u);
                    }
                    DBUtil.closeConn(cn);//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return CPUList;
            }
        };
        Future<List<CPU>> future = executorService.submit(callable);

        try{
            CPUList = future.get();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
        return CPUList;
    }

}
