package com.example.movieapp;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.example.movieapp.R;

import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends SherlockActivity implements
		ViewSwitcher.ViewFactory {
	boolean modestatus = false;
	ActionMode mMode;
	private ImageSwitcher mSwitcher;

	private int mPosition = 0;
	private GestureDetector mGestureDetector;

	private Handler _handle;
	private Runnable _runable;

	private static final float HORIZONTAL_SCROLL_DISTANCE = 10f;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		mSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitch);
		mSwitcher.setFactory(this);
		
		setupOnTouchListeners(findViewById(R.id.rootview));
		mSwitcher.setImageResource(mImageIds[mPosition]);

		_handle = new Handler();
		_runable = new Runnable() {
			@Override
			public void run() {

				if (mPosition == (mImageIds.length - 1)) {
					// Toast.makeText(MyGalleryActivity.this, "", 0).show();
				} else {
					mSwitcher.setInAnimation(AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.slide_in_right));
					mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
							MainActivity.this, R.anim.slide_out_left));
					mSwitcher.setImageResource(mImageIds[++mPosition]);
					_handle.postDelayed(_runable, 3000);
				}
			}
		};
	}

	private void setupOnTouchListeners(View rootView) {
		mGestureDetector = new GestureDetector(this, new MyGestureListener());

		OnTouchListener rootListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				// We do not use the return value of
				// mGestureDetector.onTouchEvent because we will not receive
				// the "up" event if we return false for the "down" event.
				return true;
			}
		};
		rootView.setOnTouchListener(rootListener);
	}
	
	
	public void onPause() {
		super.onPause();
		_handle.removeCallbacks(_runable);
	}

	private class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (Math.abs(velocityY) <= Math.abs(velocityX)
					&& Math.abs(velocityX) > HORIZONTAL_SCROLL_DISTANCE) {
				//
				System.out.println(velocityX);
				if (velocityX > 0) {
					if (mPosition > 0) {
						_handle.removeCallbacks(_runable);
						//
						mSwitcher.setInAnimation(AnimationUtils.loadAnimation(
								MainActivity.this, R.anim.slide_in_left));
						mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
								MainActivity.this, R.anim.slide_out_right));
						mSwitcher.setImageResource(mImageIds[--mPosition]);

					}
				} else {
					if (mPosition < (mImageIds.length - 1)) {
						_handle.removeCallbacks(_runable);

						mSwitcher.setInAnimation(AnimationUtils.loadAnimation(
								MainActivity.this, R.anim.slide_in_right));
						mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
								MainActivity.this, R.anim.slide_out_left));
						mSwitcher.setImageResource(mImageIds[++mPosition]);

					} else if (mPosition == (mImageIds.length - 1)) {
						_handle.removeCallbacks(_runable);
						Toast.makeText(MainActivity.this, "最后一张", 0).show();
						return true;
					}
				}
			}
			return true;
		}
		
		public void onShowPress(MotionEvent e){
			if (!modestatus) {
				mMode = startActionMode(new AnActionModeOfEpicProportions());
				modestatus = true;
			} else {
				mMode.finish();
				modestatus = false;
			}
		}
		
	}

	private Integer[] mImageIds = { R.drawable.p1929463, R.drawable.p4202204,
			R.drawable.p6011805 };

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}

	private final class AnActionModeOfEpicProportions implements
			ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//			SubMenu subMenu1 = menu.addSubMenu("菜单");
//	        subMenu1.add("Sample").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//	        subMenu1.add("Menu").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_WITH_TEXT);;
//	        subMenu1.add("Items").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_WITH_TEXT);;
//
//	        MenuItem subMenu1Item = subMenu1.getItem();
//	        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			
			SubMenu submenu = menu.addSubMenu(0, Menu.NONE, 1, "菜单");
			submenu.add("附近影院");
			submenu.add("场次票价");
			submenu.add("豆瓣评论");
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Toast.makeText(MainActivity.this, "Got click: " + item,
					Toast.LENGTH_SHORT).show();
			//mode.finish();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
		}
	}
}
