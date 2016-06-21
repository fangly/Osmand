package net.osmand.plus.highupplugin;


import android.app.Activity;

import java.util.List;

import net.osmand.plus.activities.MapActivity;
import net.osmand.plus.ApplicationMode;
import net.osmand.plus.OsmandApplication;
import net.osmand.plus.OsmandPlugin;
import net.osmand.plus.R;
import net.osmand.plus.views.MapInfoLayer;
import net.osmand.plus.views.mapwidgets.TextInfoWidget;
import net.osmand.plus.views.OsmandMapLayer.DrawSettings;
import net.osmand.plus.views.OsmandMapTileView;


public class HighUpPlugin extends OsmandPlugin {

	public static final String ID = "highup.plugin";
	public static final String COMPONENT = "net.osmand.highupPlugin";
	private OsmandApplication app;

	private TextInfoWidget monitoringControl;

	public HighUpPlugin(OsmandApplication app) {
		this.app = app;
		final List<ApplicationMode> am = ApplicationMode.allPossibleValues();
		ApplicationMode.regWidget("highup", am.toArray(new ApplicationMode[am.size()]));
	}

	@Override
	public String getDescription() {
		return app.getString(net.osmand.plus.R.string.plugin_highup_descr);
	}

	@Override
	public String getName() {
		return app.getString(net.osmand.plus.R.string.plugin_highup_name);
	}

	@Override
	public int getLogoResourceId() {
		return R.drawable.ic_plugin_highup;
	}

	@Override
	public int getAssetResourceName() {
		return R.drawable.highup;
	}

	@Override
	public String getHelpFileName() {
		return "feature_articles/highup-plugin.html";
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public Class<? extends Activity> getSettingsActivity() {
		return null;
	}



    /////////////////////////////////////////////////////////

	@Override
	public void registerLayers(MapActivity activity) {
		registerWidget(activity);
	}

	@Override
	public void updateLayers(OsmandMapTileView mapView, MapActivity activity) {
		if (isActive()) {
			if (monitoringControl == null) {
				registerWidget(activity);
			}
		} else {
			if (monitoringControl != null) {
				MapInfoLayer layer = activity.getMapLayers().getMapInfoLayer();
				layer.removeSideWidget(monitoringControl);
				layer.recreateControls();
				monitoringControl = null;
			}
		}
	}

	private void registerWidget(MapActivity activity) {
		monitoringControl = createMonitoringControl(activity);
		MapInfoLayer layer = activity.getMapLayers().getMapInfoLayer();
		layer.registerSideWidget(monitoringControl,
				R.drawable.ic_action_play_dark, R.string.map_widget_monitoring, "monitoring", false, 18);
		layer.recreateControls();
	}


	/**
	 * creates (if it wasn't created previously) the control to be added on a MapInfoLayer that shows a monitoring state (recorded/stopped)
	 */
	private TextInfoWidget createMonitoringControl(final MapActivity map) {
		monitoringControl = new TextInfoWidget(map) {
			long lastUpdateTime;
			@Override
			public boolean updateInfo(DrawSettings drawSettings) {
				setText(map.getString(R.string.shared_string_save), "");
				setIcons(R.drawable.widget_monitoring_rec_big_day, R.drawable.widget_monitoring_rec_big_night);

				//if(isSaving){
				//	setText(map.getString(R.string.shared_string_save), "");
				//	setIcons(R.drawable.widget_monitoring_rec_big_day, R.drawable.widget_monitoring_rec_big_night);
				//	return true;
				//}
				//String txt = map.getString(R.string.monitoring_control_start);
				//String subtxt = null;
				//int dn = R.drawable.widget_monitoring_rec_inactive_night;
				//int d = R.drawable.widget_monitoring_rec_inactive_day;
				//long last = lastUpdateTime;
				//final boolean globalRecord = settings.SAVE_GLOBAL_TRACK_TO_GPX.get();
				//final boolean isRecording = app.getSavingTrackHelper().getIsRecording();
				//float dist = app.getSavingTrackHelper().getDistance();

				// //make sure widget always shows recorded track distance if unsaved track exists
				//if (dist > 0) {
				//	last = app.getSavingTrackHelper().getLastTimeUpdated();
				//	String ds = OsmAndFormatter.getFormattedDistance(dist, map.getMyApplication());
				//	int ls = ds.lastIndexOf(' ');
				//	if (ls == -1) {
				//		txt = ds;
				//	} else {
				//		txt = ds.substring(0, ls);
				//		subtxt = ds.substring(ls + 1);
				//	}
				//}

				//if(globalRecord) {
				//	//indicates global recording (+background recording)
				//	dn = R.drawable.widget_monitoring_rec_big_night;
				//	d = R.drawable.widget_monitoring_rec_big_day;
				//} else if (isRecording) {
				//	//indicates (profile-based, configured in settings) recording (looks like is only active during nav in follow mode)
				//	dn = R.drawable.widget_monitoring_rec_small_night;
				//	d = R.drawable.widget_monitoring_rec_small_day;
				//} else {
				//	dn = R.drawable.widget_monitoring_rec_inactive_night;
				//	d = R.drawable.widget_monitoring_rec_inactive_day;
				//}

				//setText(txt, subtxt);
				//setIcons(d, dn);
				//if ((last != lastUpdateTime) && (globalRecord || isRecording)) {
				//	lastUpdateTime = last;
				//	//blink implementation with 2 indicator states (global logging + profile/navigation logging)
				//	if (globalRecord) {
				//		setIcons(R.drawable.widget_monitoring_rec_small_day,
				//			R.drawable.widget_monitoring_rec_small_night);
				//	} else {
				//		setIcons(R.drawable.widget_monitoring_rec_small_day,
				//				R.drawable.widget_monitoring_rec_small_night);
				//	}

				//	map.getMyApplication().runInUIThread(new Runnable() {
				//		@Override
				//		public void run() {
				//			if (globalRecord) {
				//				setIcons(R.drawable.widget_monitoring_rec_big_day,
				//						R.drawable.widget_monitoring_rec_big_night);
				//			} else {
				//				setIcons(R.drawable.widget_monitoring_rec_small_day,
				//						R.drawable.widget_monitoring_rec_small_night);
				//			}
				//		}
				//	}, 500);
				//}
				return true;
			}
		};
		monitoringControl.updateInfo(null);

		//monitoringControl.setOnClickListener(new View.OnClickListener() {
		//	@Override
		//	public void onClick(View v) {
		//		controlDialog(map);
		//	}
		//});

		return monitoringControl;
	}


    ////////////////////////////////////////////////////////


//	@Override
//	public void registerMapContextMenuActions(final MapActivity mapActivity, final double latitude, final double longitude,
//			ContextMenuAdapter adapter, Object selectedObj) {
//		ContextMenuAdapter.ItemClickListener listener = new ContextMenuAdapter.ItemClickListener() {
//			@Override
//			public boolean onContextMenuClick(ArrayAdapter<ContextMenuItem> adapter, int resId, int pos, boolean isChecked) {
//				if (resId == R.string.context_menu_item_add_waypoint) {
//					mapActivity.getContextMenu().addWptPt();
//				} else if (resId == R.string.context_menu_item_edit_waypoint) {
//					mapActivity.getContextMenu().editWptPt();
//				}
//				return true;
//			}
//		};
//		adapter.addItem(new ContextMenuItem.ItemBuilder()
//				.setTitleId(R.string.context_menu_item_add_waypoint, mapActivity)
//				.setIcon(R.drawable.ic_action_gnew_label_dark)
//				.setListener(listener).createItem());
//		if (selectedObj instanceof WptPt) {
//			WptPt pt = (WptPt) selectedObj;
//			if (app.getSelectedGpxHelper().getSelectedGPXFile(pt) != null) {
//				adapter.addItem(new ContextMenuItem.ItemBuilder()
//						.setTitleId(R.string.context_menu_item_edit_waypoint, mapActivity)
//						.setIcon(R.drawable.ic_action_edit_dark)
//						.setListener(listener).createItem());
//			}
//		}
//	}
//
//	public static final int[] SECONDS = new int[] {0, 1, 2, 3, 5, 10, 15, 30, 60, 90};
//	public static final int[] MINUTES = new int[] {2, 3, 5};

//
//	@Override
//	public Class<? extends Activity> getSettingsActivity() {
//		return SettingsMonitoringActivity.class;
//	}

//

}
