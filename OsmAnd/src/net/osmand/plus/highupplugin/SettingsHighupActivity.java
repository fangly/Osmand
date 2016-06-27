package net.osmand.plus.highupplugin;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

import net.osmand.PlatformUtil;
import net.osmand.plus.OsmandApplication;
import net.osmand.plus.OsmandPlugin;
import net.osmand.plus.OsmandSettings;
import net.osmand.plus.OsmandSettings.CommonPreference;
import net.osmand.plus.R;
import net.osmand.plus.activities.SettingsBaseActivity;
import net.osmand.plus.audionotes.AudioVideoNotesPlugin;

import org.apache.commons.logging.Log;


public class SettingsHighupActivity extends SettingsBaseActivity {

	private static final Log log = PlatformUtil.getLog(AudioVideoNotesPlugin.class);


	@Override
	public void onCreate(Bundle savedInstanceState) {
		((OsmandApplication) getApplication()).applyTheme(this);
		super.onCreate(savedInstanceState);
		getToolbar().setTitle(R.string.av_settings);
		PreferenceScreen grp = getPreferenceScreen();

		OsmandSettings settings = ((OsmandApplication) getApplication()).getSettings();
		final CommonPreference<Boolean> AV_EXTERNAL_PHOTO_CAM =
			settings.registerBooleanPreference("av_external_cam", true).makeGlobal();
		final CommonPreference<Integer> AV_AUDIO_BITRATE =
			settings.registerIntPreference("av_audio_bitrate", 64 * 1024).makeGlobal();

		String[] entries;
		Integer[] intValues;

		// photo settings
		PreferenceCategory photo = new PreferenceCategory(this);
		photo.setTitle(R.string.shared_string_photo);
		grp.addPreference(photo);

		final Camera cam = openCamera();
		if (cam != null) {
			photo.addPreference(createCheckBoxPreference(AV_EXTERNAL_PHOTO_CAM, R.string.av_use_external_camera,
					R.string.av_use_external_camera_descr));
			cam.release();
		}

		// audio settings
		PreferenceCategory audio = new PreferenceCategory(this);
		audio.setTitle(R.string.shared_string_audio);
		grp.addPreference(audio);

		entries = new String[]{  "16 kbps", "32 kbps", "48 kbps", "64 kbps", "96 kbps", "128 kbps"};
		intValues = new Integer[]{16 * 1024, 32 * 1024, 48 * 1024, 64 * 1024, 96 * 1024, 128 * 1024};
		ListPreference lp = createListPreference(AV_AUDIO_BITRATE, entries, intValues,
				R.string.av_audio_bitrate, R.string.av_audio_bitrate_descr);
		audio.addPreference(lp);

	}

	protected Camera openCamera() {
		try {
			return Camera.open();
		} catch (Exception e) {
			log.error("Error open camera", e);
			return null;
		}
	}
}
