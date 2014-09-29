package com.yw.loop.view;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.yw.loop.bean.LoopBean;


/**
 * �ƶ�����
 * 
 * @author tony
 * 
 */
@SuppressLint("NewApi") public class LoopLinearLayout extends LinearLayout {
	// ���ѭ��
	private boolean flag = true;
	// �ڲ�if
	private boolean flagIf = true;
	private Context context;
	// iamgeview�������ƶ��ٶ�
	private int var = 5;
	private int needleSpeed = 200;// ����view���ƶ����
	/* ������ͣʱ�� */
	private int stopTime = 2000;// ���ó�ʼ��ͣʱ��Ϊ2��
	/*����ÿ��view�Ĵ�С*/
	private int wh = 80;
	
	public int getWh() {
		return wh;
	}

	public void setWh(int wh) {
		this.wh = wh;
	}

	public int getNeedleSpeed() {
		return needleSpeed;
	}

	public void setNeedleSpeed(int needleSpeed) {
		this.needleSpeed = needleSpeed;
	}

	public int getVar() {
		return var;
	}

	public void setVar(int var) {
		this.var = var;
	}

	public int getStopTime() {
		return stopTime;
	}

	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
	}

	private List<LoopBean> datas = new ArrayList<LoopBean>();

	public LoopLinearLayout(Context context) {
		super(context);
		this.context = context;
	}

	public LoopLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public LoopLinearLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public void setResource(List<LoopBean> datas) {
		this.datas = datas;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

	}

	/**
	 * �ƶ��ӿؼ�
	 */
	public void moveChild() {
		// ��ȡ�����еĿؼ�����
		int count = this.getChildCount();
		View first = this.getChildAt(0);
		for (int i = 0; i < count; i++) {
			if (first.getRight() <= 0) {
				this.removeView(first);
				this.addView(first, this.getChildCount());
				
				onStop();
				/**
				 * �ؼ�ֹͣ����ʱ�л�����ͬ����ͼ
				 */
				if (callback != null) {
					callback.stop();
				}
			} else {
				/*
				 * ���ϡ��ҡ��� �������²��䣬���Ҹı�
				 */
				View view = this.getChildAt(i);
				view.layout(view.getLeft() - var, view.getTop(),
						view.getRight() - var, view.getBottom());
				// ���view����Layout��Χ�����Ƴ�
				Log.e("view.getRight", view.getRight() + "");
				Log.e("this.getLeft", this.getLeft() + "");
			}

		}
	}
	/**
	 * ������ʼ
	 * @param w
	 */
	public void start() {
		// �򼯺����������
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				Log.e("startview", "startview");
				/*ImageView img = (ImageView) LayoutInflater.from(context)
						.inflate(R.layout.item, null);*/
				ImageView img = new ImageView(getContext());
				/**
				 * 1.��ÿһ������һ������¼�
				 * 2.���Ӽ��
				 */
				img.setTag(datas.get(i));
				img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (itemClickListener != null) {
							itemClickListener.onItemClick(((LoopBean)v.getTag()).getText());
						}
					}
				});
				
				img.setImageResource(datas.get(i).getResId());
				/*
				 * img.setLayoutParams(new LinearLayout.LayoutParams(
				 * LinearLayout.LayoutParams.WRAP_CONTENT,
				 * LinearLayout.LayoutParams.WRAP_CONTENT));
				 */
				img.setLayoutParams(new LinearLayout.LayoutParams(wh, wh));
				Log.e("endview", "endview");
				Log.e("resid", datas.get(i).getResId() + "dd");
				this.addView(img);
				
			}
		}
		
		new Thread() {
			public void run() {
				try {
					while (flag) {
						if (flagIf) {
							Thread.sleep(needleSpeed);
							handler.sendEmptyMessage(0);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	public void stop() {
		flagIf = false;
		flag = false;
	}

	private void onStop() {
		// Toast.makeText(context, "��ͣ�������Կ�", Toast.LENGTH_LONG).show();
		new Thread() {
			public void run() {
				try {
					flagIf = false;
					Thread.sleep(stopTime);
					flagIf = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				moveChild();
				break;
			}
		};
	};

	/*
	 * ������ߵ�view��ȫ��������ʱ�Ļص�����
	 */
	private OnImageViewOutOfLayout callback;

	public void setOnImageViewOutOfLayout(OnImageViewOutOfLayout callback) {
		this.callback = callback;
	}

	public interface OnImageViewOutOfLayout {

		public void stop();
	}

	/* ����¼�* */
	/**
	 * ÿһ��ĵ���¼�
	 * 
	 * @author tony
	 * 
	 */
	public interface OnClickImageListener {
		public void onItemClick(Object obj);
	}

	/**
	 * ���ƶ��е�ͼƬ���ӵ���¼�
	 */
	private OnClickImageListener itemClickListener;

	public void setOnItemClickListener(OnClickImageListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}
	
}

