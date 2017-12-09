package aggrathon.eyewitnessapp.light.utils;

//See https://stackoverflow.com/questions/39705739/android-n-change-language-programatically/40849142#40849142

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class ContextWrapper extends android.content.ContextWrapper {

	public ContextWrapper(Context base) {
		super(base);
	}

	public static ContextWrapper wrap(Context context, Locale newLocale) {

		Resources res = context.getResources();
		Configuration configuration = res.getConfiguration();
		configuration.setLocale(newLocale);
		context = context.createConfigurationContext(configuration);

		return new ContextWrapper(context);
	}
}
