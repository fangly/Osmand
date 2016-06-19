package net.osmand.plus.highupplugin;

import android.app.Activity;

import net.osmand.plus.OsmandApplication;
import net.osmand.plus.OsmandPlugin;
import net.osmand.plus.R;
import net.osmand.plus.render.RendererRegistry;

public class HighUpPlugin extends OsmandPlugin {

	public static final String ID = "highup.plugin";
	public static final String COMPONENT = "net.osmand.highupPlugin";
	private OsmandApplication app;
	private String previousRenderer = RendererRegistry.DEFAULT_RENDER;
	
	public HighUpPlugin(OsmandApplication app) {
		this.app = app;
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
	public boolean init(final OsmandApplication app, final Activity activity) {
		if(activity != null) {
			// called from UI 
			previousRenderer = app.getSettings().RENDERER.get(); 
			app.getSettings().RENDERER.set(RendererRegistry.WINTER_SKI_RENDER);
		}
		return true;
	}
	
	@Override
	public void disable(OsmandApplication app) {
		super.disable(app);
		if(app.getSettings().RENDERER.get().equals(RendererRegistry.WINTER_SKI_RENDER)) {
			app.getSettings().RENDERER.set(previousRenderer);
		}
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public Class<? extends Activity> getSettingsActivity() {
		return null;
	}
}
