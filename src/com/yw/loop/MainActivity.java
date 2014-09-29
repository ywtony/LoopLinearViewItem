package com.yw.loop;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yw.loop.bean.LoopBean;
import com.yw.loop.view.LoopLinearLayout;
import com.yw.loop.view.LoopLinearLayout.OnClickImageListener;
import com.yw.loop.view.LoopLinearLayout.OnImageViewOutOfLayout;

public class MainActivity extends Activity implements OnImageViewOutOfLayout,
		OnClickImageListener {
	private LoopLinearLayout linear = null;
	private TextView tv;
	private List<LoopBean> datas = new ArrayList<LoopBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initData();
	}

	private int initWh() {
		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		// ��ʼ��whΪ��Ļ��8��֮һ
		return (int) width / 8;
	}

	private void initData() {
		datas.add(new LoopBean("", R.drawable.loop1, "item1"));
		datas.add(new LoopBean("", R.drawable.loop2, "item2"));
		datas.add(new LoopBean("", R.drawable.loop3, "item3"));
		datas.add(new LoopBean("", R.drawable.loop4, "item4"));
		datas.add(new LoopBean("", R.drawable.loop1, "item5"));
		datas.add(new LoopBean("", R.drawable.loop2, "item6"));
		datas.add(new LoopBean("", R.drawable.loop3, "item7"));
		datas.add(new LoopBean("", R.drawable.loop4, "item8"));
		datas.add(new LoopBean("", R.drawable.ic_launcher, "item9"));
	}

	private void initViews() {
		linear = (LoopLinearLayout) findViewById(R.id.layoutanimation_linear);
		// ��ʼ��LoopLinearLayoutÿһ��item�Ŀ��
		linear.setWh(initWh());
		// ��ʼ��LoopLinearLayout������Դ
		linear.setResource(datas);
		// ���ü�������
		linear.setOnImageViewOutOfLayout(this);
		linear.setOnItemClickListener(this);
		tv = (TextView)findViewById(R.id.layoutanimation_tv_title);
	}

	@Override
	public void onItemClick(Object obj) {
//		Toast.makeText(this, "������ˣ�" + (String) obj, Toast.LENGTH_LONG).show();
		tv.setText((String)obj);
	}

	@Override
	public void stop() {
		Toast.makeText(this, "��ͣ", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		linear.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		linear.stop();
	}

}
