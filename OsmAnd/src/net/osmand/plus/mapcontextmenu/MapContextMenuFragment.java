package net.osmand.plus.mapcontextmenu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.osmand.PlatformUtil;
import net.osmand.plus.R;
import net.osmand.plus.activities.MapActivity;

import org.apache.commons.logging.Log;

import java.util.Map;


public class MapContextMenuFragment extends Fragment {

	public static final String TAG = "MapContextMenuFragment";
	private static final Log LOG = PlatformUtil.getLog(MapContextMenuFragment.class);

	private View view;
	private View mainView;

	private float mainViewHeight;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		/*
		if(!portrait) {
			mapActivity.getMapView().setMapPositionX(1);
			mapActivity.getMapView().refreshMap();
		}

		if(!AndroidUiHelper.isXLargeDevice(mapActivity)) {
			AndroidUiHelper.updateVisibility(mapActivity.findViewById(R.id.map_right_widgets_panel), false);
			AndroidUiHelper.updateVisibility(mapActivity.findViewById(R.id.map_left_widgets_panel), false);
		}
		if(!portrait) {
			AndroidUiHelper.updateVisibility(mapActivity.findViewById(R.id.map_route_land_left_margin), true);
		}
		*/
	}

	@Override
	public void onDetach() {
		super.onDetach();

		/*
		mapActivity.getMapView().setMapPositionX(0);
		mapActivity.getMapView().refreshMap();

		AndroidUiHelper.updateVisibility(mapActivity.findViewById(R.id.map_route_land_left_margin), false);
		AndroidUiHelper.updateVisibility(mapActivity.findViewById(R.id.map_right_widgets_panel), true);
		AndroidUiHelper.updateVisibility(mapActivity.findViewById(R.id.map_left_widgets_panel), true);
		*/
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.map_context_menu_fragment, container, false);

		View shadowView = view.findViewById(R.id.context_menu_shadow_view);
		shadowView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				dismissMenu();
				return true;
			}
		});

		View topView = view.findViewById(R.id.context_menu_top_view);
		mainView = view.findViewById(R.id.context_menu_main);

		topView.setOnTouchListener(new View.OnTouchListener() {

			private float dy;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						dy = event.getY();
						mainViewHeight = mainView.getHeight();
						break;

					case MotionEvent.ACTION_MOVE:
						float y = event.getY();
						LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mainView.getLayoutParams();
						float top = lp.topMargin + (y - dy);
						if (top < 0) {
							lp.topMargin = (int) top;
							mainView.setLayoutParams(lp);
						}
						break;

					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL:

						float posY = view.getHeight() - mainViewHeight;
						if (mainView.getY() != posY) {
							mainView.animate().y(posY).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();
						}
						break;

				}
				return true;
			}
		});

		int iconId = MapContextMenu.getInstance().getLeftIconId();

		final View iconLayout = view.findViewById(R.id.context_menu_icon_layout);
		final ImageView iconView = (ImageView)view.findViewById(R.id.context_menu_icon_view);
		if (iconId == 0) {
			iconLayout.setVisibility(View.GONE);
		} else {
			iconView.setImageResource(iconId);
		}

		TextView line1 = (TextView) view.findViewById(R.id.context_menu_line1);
		line1.setText(MapContextMenu.getInstance().getAddressStr());

		TextView line2 = (TextView) view.findViewById(R.id.context_menu_line2);
		line2.setText(MapContextMenu.getInstance().getLocationStr());

		final ImageView buttonNavigate = (ImageView) view.findViewById(R.id.context_menu_route_button);
		buttonNavigate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MapContextMenu.getInstance().buttonNavigatePressed();
			}
		});

		final ImageView buttonFavorite = (ImageView) view.findViewById(R.id.context_menu_fav_button);
		buttonFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MapContextMenu.getInstance().buttonFavoritePressed();
			}
		});

		final ImageView buttonShare = (ImageView) view.findViewById(R.id.context_menu_share_button);
		buttonShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MapContextMenu.getInstance().buttonSharePressed();
			}
		});

		final ImageView buttonMore = (ImageView) view.findViewById(R.id.context_menu_more_button);
		buttonMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MapContextMenu.getInstance().buttonMorePressed();
			}
		});

		/*
		Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
		toolbar.setTitle(R.string.poi_create_title);
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				fragmentManager.beginTransaction().remove(MapContextMenuFragment.this).commit();
				fragmentManager.popBackStack();
			}
		});

		viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		String basicTitle = getResources().getString(R.string.tab_title_basic);
		String extendedTitle = getResources().getString(R.string.tab_title_advanced);
		MyAdapter pagerAdapter = new MyAdapter(getChildFragmentManager(), basicTitle, extendedTitle);
		viewPager.setAdapter(pagerAdapter);

		final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

		// Hack due to bug in design support library v22.2.1
		// https://code.google.com/p/android/issues/detail?id=180462
		// TODO remove in new version
		if (ViewCompat.isLaidOut(tabLayout)) {
			tabLayout.setupWithViewPager(viewPager);
		} else {
			tabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
				@Override
				public void onLayoutChange(View v, int left, int top, int right, int bottom,
										   int oldLeft, int oldTop, int oldRight, int oldBottom) {
					tabLayout.setupWithViewPager(viewPager);
					tabLayout.removeOnLayoutChangeListener(this);
				}
			});
		}
		*/
		return view;
	}

	public void dismissMenu() {
		getActivity().getSupportFragmentManager().popBackStack();
	}

	public static void showInstance(final MapActivity mapActivity) {
		MapContextMenuFragment fragment = new MapContextMenuFragment();
		mapActivity.getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom)
				.add(R.id.fragmentContainer, fragment, "MapContextMenuFragment")
				.addToBackStack(null).commit();
	}
}
