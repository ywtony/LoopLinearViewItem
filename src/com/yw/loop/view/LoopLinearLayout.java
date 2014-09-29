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
 * 移动动画
 * 
 * @author tony
 * 
 */
@SuppressLint("NewApi") public class LoopLinearLayout extends LinearLayout {
	// 外层循环
	private boolean flag = true;
	// 内层if
	private boolean flagIf = true;
	private Context context;
	// iamgeview的向左移动速度
	private int var = 5;
	private int needleSpeed = 200;// 设置view的移动跨度
	/* 设置暂停时间 */
	private int stopTime = 2000;// 设置初始暂停时间为2秒
	/*设置每个view的大小*/
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
	 * 移动子控件
	 */
	public void moveChild() {
		// 获取布局中的控件个数
		int count = this.getChildCount();
		View first = this.getChildAt(0);
		for (int i = 0; i < count; i++) {
			if (first.getRight() <= 0) {
				this.removeView(first);
				this.addView(first, this.getChildCount());
				
				onStop();
				/**
				 * 控件停止滚动时切换到不同的视图
				 */
				if (callback != null) {
					callback.stop();
				}
			} else {
				/*
				 * 左、上、右、下 控制上下不变，左右改变
				 */
				View view = this.getChildAt(i);
				view.layout(view.getLeft() - var, view.getTop(),
						view.getRight() - var, view.getBottom());
				// 如果view不再Layout范围，则移除
				Log.e("view.getRight", view.getRight() + "");
				Log.e("this.getLeft", this.getLeft() + "");
			}

		}
	}
	/**
	 * 动画开始
	 * @param w
	 */
	public void start() {
		// 向集合中添加数据
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				Log.e("startview", "startview");
				/*ImageView img = (ImageView) LayoutInflater.from(context)
						.inflate(R.layout.item, null);*/
				ImageView img = new ImageView(getContext());
				/**
				 * 1.给每一项增加一个点击事件
				 * 2.增加间距
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
		// Toast.makeText(context, "暂停三秒试试看", Toast.LENGTH_LONG).show();
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
	 * 当最左边的view完全超出布局时的回调函数
	 */
	private OnImageViewOutOfLayout callback;

	public void setOnImageViewOutOfLayout(OnImageViewOutOfLayout callback) {
		this.callback = callback;
	}

	public interface OnImageViewOutOfLayout {

		public void stop();
	}

	/* 点击事件* */
	/**
	 * 每一项的点击事件
	 * 
	 * @author tony
	 * 
	 */
	public interface OnClickImageListener {
		public void onItemClick(Object obj);
	}

	/**
	 * 给移动中的图片增加点击事件
	 */
	private OnClickImageListener itemClickListener;

	public void setOnItemClickListener(OnClickImageListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}
	
}

