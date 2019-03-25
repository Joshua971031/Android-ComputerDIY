package com.example.computerdiy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import DAO.CaseDAO;
import DAO.CpuDAO;
import DAO.DisplayDAO;
import DAO.GpuDAO;
import DAO.HhdDAO;
import DAO.MainboardDAO;
import DAO.PowerDAO;
import DAO.RamDAO;
import DAO.SsdDAO;
import objects.CASE;
import objects.CPU;
import objects.DISPLAY;
import objects.GPU;
import objects.HHD;
import objects.MAINBOARD;
import objects.POWER;
import objects.RAM;
import objects.SSD;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner sp_cpu;  //cpu下拉框
    private Spinner sp_hhd;  //hhd下拉框
    private Spinner sp_mainboard;  //mainboard下拉框
    private Spinner sp_gpu;  //gpu下拉框
    private Spinner sp_ram;  //ram下拉框
    private Spinner sp_ssd;  //ssd下拉框
    private Spinner sp_case;  //case下拉框
    private Spinner sp_power;  //power下拉框
    private Spinner sp_display;  //display下拉框
    private SimpleAdapter adapter_cpu;  //适配器
    private SimpleAdapter adapter_hhd;
    private SimpleAdapter adapter_mainboard;
    private SimpleAdapter adapter_gpu;
    private SimpleAdapter adapter_ssd;
    private SimpleAdapter adapter_ram;
    private SimpleAdapter adapter_case;
    private SimpleAdapter adapter_power;
    private SimpleAdapter adapter_display;
    private List<Map<String,Object>> data_cpu;  //数据
    private List<Map<String,Object>> data_hhd;
    private List<Map<String,Object>> data_mainboard;
    private List<Map<String,Object>> data_gpu;
    private List<Map<String,Object>> data_ssd;
    private List<Map<String,Object>> data_ram;
    private List<Map<String,Object>> data_case;
    private List<Map<String,Object>> data_power;
    private List<Map<String,Object>> data_display;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,sum;
    String cpu_flag = null;
    String mb_flag = null;
    String case_flag = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp_cpu = findViewById(R.id.CPU_Spinner);
        sp_mainboard = findViewById(R.id.Mainboard_Spinner);
        sp_hhd = findViewById(R.id.HHD_Spinner);
        sp_gpu = findViewById(R.id.GPU_Spinner);
        sp_ram = findViewById(R.id.RAM_Spinner);
        sp_ssd = findViewById(R.id.SSD_Spinner);
        sp_case = findViewById(R.id.Case_Spinner);
        sp_power = findViewById(R.id.Power_Spinner);
        sp_display = findViewById(R.id.Display_Spinner);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        tv9 = findViewById(R.id.tv9);
        sum = findViewById(R.id.sum);
        data_cpu = getData_cpu(); //传数据
        //data_mainboard = getData_mainboard();
        data_hhd = getData_hhd();
        data_gpu = getData_gpu();
        data_ram = getData_ram();
        data_ssd = getData_ssd();
        //data_case = getData_case();
        data_power = getData_power();
        data_display = getData_display();
        //初始化适配器
        adapter_cpu = new SimpleAdapter(this ,data_cpu, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Frequency"}, new int[]{R.id.img,R.id.tv});
        //adapter_mainboard = new SimpleAdapter(this ,data_mainboard, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Platform+Size"}, new int[]{R.id.img,R.id.tv});
        adapter_hhd = new SimpleAdapter(this ,data_hhd, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Capacity"}, new int[]{R.id.img,R.id.tv});
        adapter_gpu = new SimpleAdapter(this ,data_gpu, R.layout.simple_adapter, new String[]{"img", "Brand+Model+GRAM+Size"}, new int[]{R.id.img,R.id.tv});
        adapter_ram = new SimpleAdapter(this ,data_ram, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Capacity+Frequency"}, new int[]{R.id.img,R.id.tv});
        adapter_ssd = new SimpleAdapter(this ,data_ssd, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Capacity"}, new int[]{R.id.img,R.id.tv});
        //adapter_case = new SimpleAdapter(this ,data_case, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Size"}, new int[]{R.id.img,R.id.tv});
        adapter_power = new SimpleAdapter(this ,data_power, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Power+Size"}, new int[]{R.id.img,R.id.tv});
        adapter_display = new SimpleAdapter(this ,data_display, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Size"}, new int[]{R.id.img,R.id.tv});

        //绑定适配器
        sp_cpu.setAdapter(adapter_cpu);
        //sp_mainboard.setAdapter(adapter_mainboard);
        sp_hhd.setAdapter(adapter_hhd);
        sp_gpu.setAdapter(adapter_gpu);
        sp_ram.setAdapter(adapter_ram);
        sp_ssd.setAdapter(adapter_ssd);
        sp_case.setAdapter(adapter_case);
        sp_power.setAdapter(adapter_power);
        sp_display.setAdapter(adapter_display);

        //设置监听

        sp_cpu.setOnItemSelectedListener(this);
        sp_mainboard.setOnItemSelectedListener(this);
        sp_gpu.setOnItemSelectedListener(this);
        sp_ram.setOnItemSelectedListener(this);
        sp_hhd.setOnItemSelectedListener(this);
        sp_ssd.setOnItemSelectedListener(this);
        sp_case.setOnItemSelectedListener(this);
        sp_power.setOnItemSelectedListener(this);
        sp_display.setOnItemSelectedListener(this);


    }

    //传入值 hashmap键值对
    private List<Map<String,Object>> getData_cpu() {
        //获取
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<CPU> cpus = new CpuDAO().list();
        Map<String,Object> map =  null;
        for(CPU cpu:cpus)
        {
            map = new HashMap<>();
            map.put("img",cpu.getImage());
            map.put("Brand+Model+Frequency",cpu.getBrand()+" "+cpu.getModel()+" "+cpu.getFrequency());
            map.put("Brand", cpu.getBrand());
            map.put("price",cpu.getPrice());
            data.add(map);
        }
        return data;

    }

    private List<Map<String,Object>> getData_hhd(){
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<HHD> hs = new HhdDAO().list();
        Map<String,Object> map =  null;
        for(HHD h:hs)
        {
            map = new HashMap<>();
            map.put("img",h.getImage());
            map.put("Brand+Model+Capacity",h.getBrand()+" "+h.getModel()+" "+h.getCapacity());
            map.put("price",h.getPrice());
            data.add(map);
        }
        return data;
    }

    private List<Map<String,Object>> getData_mainboard(){
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<MAINBOARD> ms = new MainboardDAO().list();
        Map<String,Object> map =  null;
        for(MAINBOARD m:ms)
        {
            map = new HashMap<>();
            map.put("img",m.getImage());
            map.put("Brand+Model+Platform+Size",m.getBrand()+" "+m.getModel()+" "+m.getPlatform()+" "+m.getSize());
            map.put("Platform", m.getPlatform());
            map.put("Size", m.getSize());
            map.put("price",m.getPrice());
            data.add(map);
        }
        return data;
    }

    private List<Map<String,Object>> getData_gpu(){
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<GPU> ms = new GpuDAO().list();
        Map<String,Object> map =  null;
        for(GPU m:ms)
        {
            map = new HashMap<>();
            map.put("img",m.getImage());
            map.put("Brand+Model+GRAM+Size",m.getBrand()+" "+m.getModel()+" "+m.getGRAM()+" "+m.getSize());
            map.put("price",m.getPrice());
            data.add(map);
        }
        return data;
    }

    private List<Map<String,Object>> getData_ram(){
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<RAM> ms = new RamDAO().list();
        Map<String,Object> map =  null;
        for(RAM m:ms)
        {
            map = new HashMap<>();
            map.put("img",m.getImage());
            map.put("Brand+Model+Capacity+Frequency",m.getBrand()+" "+m.getModel()+" "+m.getCapacity()+" "+m.getFrequency());
            map.put("price",m.getPrice());
            data.add(map);
        }
        return data;
    }

    private List<Map<String,Object>> getData_ssd(){
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<SSD> ms = new SsdDAO().list();
        Map<String,Object> map =  null;
        for(SSD m:ms)
        {
            map = new HashMap<>();
            map.put("img",m.getImage());
            map.put("Brand+Model+Capacity",m.getBrand()+" "+m.getModel()+" "+m.getCapacity());
            map.put("price",m.getPrice());
            data.add(map);
        }
        return data;
    }

    private List<Map<String,Object>> getData_case(){
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<CASE> ms = new CaseDAO().list();
        Map<String,Object> map =  null;
        for(CASE m:ms)
        {
            map = new HashMap<>();
            map.put("img",m.getImage());
            map.put("Brand+Model+Size",m.getBrand()+" "+m.getModel()+" "+m.getSize());
            map.put("Size", m.getSize());
            map.put("price",m.getPrice());
            data.add(map);
        }
        return data;
    }

    private List<Map<String,Object>> getData_power(){
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<POWER> ms = new PowerDAO().list();
        Map<String,Object> map =  null;
        for(POWER m:ms)
        {
            map = new HashMap<>();
            map.put("img",m.getImage());
            map.put("Brand+Model+Power+Size",m.getBrand()+" "+m.getModel()+" "+m.getPower()+" "+m.getSize());
            map.put("Size", m.getSize());
            map.put("price",m.getPrice());
            data.add(map);
        }
        return data;
    }

    private List<Map<String,Object>> getData_display(){
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<DISPLAY> ms = new DisplayDAO().list();
        Map<String,Object> map =  null;
        for(DISPLAY m:ms)
        {
            map = new HashMap<>();
            map.put("img",m.getImage());
            map.put("Brand+Model+Size",m.getBrand()+" "+m.getModel()+" "+m.getSize());
            map.put("price",m.getPrice());
            data.add(map);
        }
        return data;
    }

    private int get_stringnum(String str) {
        //获取字符串中的数字
        str = str.trim();
        String str2 = "";
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                }
            }
        }else {
            str2="0";
        }
        int i =  Integer.valueOf(str2);
        return i;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        //监听

        switch(adapterView.getId()) {
            case R.id.CPU_Spinner:
                tv1.setText("￥" + data_cpu.get(position).get("price"));
                cpu_flag = data_cpu.get(position).get("Brand").toString();
                //cpu和主板 平台兼容
                data_mainboard = getData_mainboard();
                Iterator<Map<String,Object>> iterator = data_mainboard.iterator(); //使用迭代器遍历删除cpu为相应平台的主板数据
                while(iterator.hasNext())
                {
                    Map<String,Object> m = iterator.next();
                    if (!cpu_flag.equals(m.get("Platform")))
                        iterator.remove();
                }
                adapter_mainboard = new SimpleAdapter(this ,data_mainboard, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Platform+Size"}, new int[]{R.id.img,R.id.tv});
                sp_mainboard.setAdapter(adapter_mainboard);

                break;
            case R.id.Mainboard_Spinner:
                tv2.setText("￥" + data_mainboard.get(position).get("price"));
                mb_flag = data_mainboard.get(position).get("Size").toString();
                //主板和机箱 尺寸兼容
                data_case = getData_case();
                Iterator<Map<String,Object>> iterator1 = data_case.iterator(); //使用迭代器遍历删除cpu为相应平台的主板数据
                while(iterator1.hasNext())
                {
                    Map<String,Object> m = iterator1.next();
                    if (mb_flag.equals("ATX")&&!mb_flag.equals(m.get("Size")))
                        iterator1.remove();
                    else if (mb_flag.equals("mATX")&&!mb_flag.equals(m.get("Size")))
                        iterator1.remove();
                    else if (mb_flag.equals("ITX")&&!mb_flag.equals(m.get("Size")))
                        iterator1.remove();
                }
                adapter_case = new SimpleAdapter(this ,data_case, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Size"}, new int[]{R.id.img,R.id.tv});
                sp_case.setAdapter(adapter_case);

                break;
            case R.id.GPU_Spinner:
                tv3.setText("￥" + data_gpu.get(position).get("price"));
                break;
            case R.id.RAM_Spinner:
                tv4.setText("￥" + data_ram.get(position).get("price"));
                break;
            case R.id.HHD_Spinner:
                tv5.setText("￥" + data_hhd.get(position).get("price"));
                break;
            case R.id.SSD_Spinner:
                tv6.setText("￥" + data_ssd.get(position).get("price"));
                break;
            case R.id.Power_Spinner:
                tv7.setText("￥" + data_power.get(position).get("price"));
                break;
            case R.id.Case_Spinner:
                tv8.setText("￥" + data_case.get(position).get("price"));
                case_flag = data_case.get(position).get("Size").toString();
                //主板和机箱 尺寸兼容
                data_power = getData_power();
                Iterator<Map<String,Object>> iterator2 = data_power.iterator(); //使用迭代器遍历删除cpu为相应平台的主板数据
                while(iterator2.hasNext())
                {
                    Map<String,Object> m = iterator2.next();
                    if (!case_flag.equals(m.get("Size")))
                        iterator2.remove();
                }
                adapter_power = new SimpleAdapter(this ,data_power, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Power+Size"}, new int[]{R.id.img,R.id.tv});
                sp_power.setAdapter(adapter_power);
                break;
            case R.id.Display_Spinner:
                tv9.setText("￥" + data_display.get(position).get("price"));
                break;

        }
        sum.setText("总价￥"+String.valueOf(get_stringnum(tv1.getText().toString())+get_stringnum(tv2.getText().toString())+get_stringnum(tv3.getText().toString())+get_stringnum(tv4.getText().toString())+get_stringnum(tv5.getText().toString())+get_stringnum(tv6.getText().toString())+get_stringnum(tv7.getText().toString())+get_stringnum(tv8.getText().toString())+get_stringnum(tv9.getText().toString())));
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }


}
