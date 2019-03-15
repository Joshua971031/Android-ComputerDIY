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
import java.util.List;
import java.util.Map;

import DAO.CpuDAO;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner sp_cpu;  //cpu下拉框
    private Spinner sp_hhd;  //hhd下拉框
    private Spinner sp_mainboard;  //mainboard下拉框
    private SimpleAdapter adapter_cpu;  //cpu适配器
    private  SimpleAdapter adapter_hhd; //hhd适配器
    private  SimpleAdapter adapter_mainboard; //mainboard适配器
    private List<Map<String,Object>> data_cpu;  //cpu数据
    private List<Map<String,Object>> data_hhd;  //hhd数据
    private List<Map<String,Object>> data_mainboard;  //mianboard数据
    private TextView tv1,tv2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp_cpu= findViewById(R.id.CPU_Spinner);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        data_cpu=getData_cpu(); //给数据插入值
        //初始化适配器
        adapter_cpu = new SimpleAdapter(this ,data_cpu, R.layout.simple_adapter, new String[]{"img", "Brand+Model+Frequency"}, new int[]{R.id.img,R.id.tv});
        //绑定适配器
        sp_cpu.setAdapter(adapter_cpu);
        //设置监听
        sp_cpu.setOnItemSelectedListener(this);
    }

    //插入值的方法
    private List<Map<String,Object>> getData_cpu() {
        //获取
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        List<CPU> cpus = new CpuDAO().list();
        Map<String,Object> map =  null;
        for(CPU cpu:cpus)
        {
            map = new HashMap<>();
            map.put("img",cpu.getImage());
            map.put("price",cpu.getPrice());
            map.put("Brand+Model+Frequency",cpu.getBrand()+" "+cpu.getModel()+" "+cpu.getFrequency());
            map.put("Model",cpu.getModel());
            data.add(map);
        }
        return data;

    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //监听
        tv1.setText("￥"+data_cpu.get(i).get("price"));
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }


}
